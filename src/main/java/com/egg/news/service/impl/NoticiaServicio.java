package com.egg.news.service.impl;

import com.egg.news.entity.Noticia;
import com.egg.news.entity.Periodista;
import com.egg.news.entity.Usuario;
import com.egg.news.enumerated.Rol;
import com.egg.news.exception.MiExcepcion;
import com.egg.news.repository.NoticiaRepositorio;
import com.egg.news.repository.UsuarioRepositorio;
import com.egg.news.service.interfaz.INoticiaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NoticiaServicio implements INoticiaServicio {

    @Autowired
    private NoticiaRepositorio noticiaRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public void crearNoticia(Long creador, String titulo, String cuerpo) throws MiExcepcion{

        validar(titulo, cuerpo);
        Noticia noticia = new Noticia();
        noticia.setTitulo(titulo);
        noticia.setCuerpo(cuerpo);
        noticia.setAlta(new Date());

        Usuario periodista = usuarioRepositorio.buscarPorId(creador);

        if(periodista.getRol() == Rol.PERIODISTA){

            Periodista p = new Periodista();
            p.setId(periodista.getId());

            noticia.setCreador(p);

        }

        noticiaRepositorio.save(noticia);

    }

    @Override
    public Noticia obtenerUnaNoticia(Long id) {

        return noticiaRepositorio.getOne(id);
    }

    public List<Noticia> listarNoticias(){
        List<Noticia> aux = noticiaRepositorio.findAll();

        for (Noticia noticia: aux) {

           noticia.setCuerpo(noticia.getCuerpo().substring(0, 50).concat("..."));

        }

        return aux;
    }

    public List<Noticia> listarNoticiasDeUnPeriodista(Long id){
        List<Noticia> aux = noticiaRepositorio.listarNoticiaDeUnPeriodista(id);

        return aux;
    }

    @Override
    public void editarNoticia(Long id, String titulo, String cuerpo) throws MiExcepcion{

        validar(titulo, cuerpo);

        Noticia noticia = obtenerUnaNoticia(id);
        noticia.setTitulo(titulo);
        noticia.setCuerpo(cuerpo);

        noticiaRepositorio.save(noticia);
    }

    @Transactional
    @Override
    public void eliminarNoticia(Long id) {
        noticiaRepositorio.deleteById(id);
    }

    private void validar(String titulo, String cuerpo) throws MiExcepcion {

        if (titulo.isEmpty() || titulo == null) {
            throw new MiExcepcion("El título no puede ser nulo o estar vacio");
        }

        if (cuerpo.isEmpty() || cuerpo == null) {
            throw new MiExcepcion("El cuerpo no puede ser nulo o estar vacio");
        }

    }


}
