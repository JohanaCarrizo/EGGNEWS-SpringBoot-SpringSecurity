package com.egg.news.service.interfaz;

import com.egg.news.entity.Noticia;
import com.egg.news.entity.Periodista;
import com.egg.news.exception.MiExcepcion;

import java.util.List;

public interface INoticiaServicio {

    void crearNoticia(Long creador, String titulo, String cuerpo) throws MiExcepcion;
    Noticia obtenerUnaNoticia(Long id);

    List<Noticia> listarNoticias();
    void editarNoticia(Long id, String titulo, String cuerpo) throws MiExcepcion;

    void eliminarNoticia(Long id);
}
