package com.egg.news.service.interfaz;

import com.egg.news.entity.Usuario;
import com.egg.news.enumerated.Rol;
import com.egg.news.exception.MiExcepcion;

import java.util.List;

public interface IUsuarioServicio {

    void registrar(String nombreUsuario, String pass, String pass2) throws MiExcepcion;
    List<Usuario> listarUsuarios();

    List<Usuario> listarPeriodistas();

    List<Rol> listarRoles();
}
