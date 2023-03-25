package com.egg.news.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Table(name="Noticia")
@Entity
public class Noticia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_NOTICIA")
    private long id;

    @Column(name = "TITULO")
    private String titulo;

    @Column(name = "CUERPO")
    private String cuerpo;

    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "FECHA_ALTA")
    private Date alta;

    @ManyToOne
    @JoinColumn(name = "IdCreador")
    private Periodista creador;
}
