package com.egg.news.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@DiscriminatorValue("PERIODISTA")
@Entity
public class Periodista extends Usuario{

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "creador")
    private List<Noticia> misNoticias;

    @Column(name = "SUELDO_MENSUAL")
    private double sueldoMensual;
}
