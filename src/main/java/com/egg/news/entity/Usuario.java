package com.egg.news.entity;

import com.egg.news.enumerated.Rol;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Table(name = "Usuario")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="ROL", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("USUARIO")
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private long id;

    @Column(name = "NOMBRE_USUARIO")
    private String nombreUsuario;

    @Column(name = "CONTRASEÑA")
    private String password;

    @Column(name = "FECHA_ALTA")
    @Temporal(TemporalType.DATE)
    private Date fechaAlta;

    @Column(name = "USUARIO_ACTIVO")
    private boolean activo;

    @Column(name = "ROL", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private Rol rol;
}
