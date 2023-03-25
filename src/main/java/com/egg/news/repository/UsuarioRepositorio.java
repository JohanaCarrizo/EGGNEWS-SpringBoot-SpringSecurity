package com.egg.news.repository;

import com.egg.news.entity.Usuario;
import com.egg.news.enumerated.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombre")
    public Usuario findByNombreUsuario(@Param("nombre") String nombreUsuario);
    @Query("SELECT u FROM Usuario u WHERE u.id = :id")
    public Usuario buscarPorId(@Param("id") Long id);

    @Query("SELECT u FROM Usuario u WHERE u.rol = 'PERIODISTA'")
    public List<Usuario> buscarPeriodista();

    @Query("SELECT DISTINCT u.rol FROM Usuario u")
    public List<Rol> listarRoles();
}
