package com.egg.news.repository;

import com.egg.news.entity.Noticia;
import com.egg.news.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticiaRepositorio extends JpaRepository<Noticia, Long> {

    @Query("SELECT n FROM Noticia n")
    public List<Noticia> listarTodasLasNoticias();

    @Query("SELECT n FROM Noticia n WHERE n.creador.id = :id")
    public List<Noticia> listarNoticiaDeUnPeriodista(@Param("id") Long id);
}
