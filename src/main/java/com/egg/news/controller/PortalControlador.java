package com.egg.news.controller;

import com.egg.news.entity.Usuario;
import com.egg.news.exception.MiExcepcion;
import com.egg.news.service.impl.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    private UsuarioServicio usuarioService;

    @GetMapping("/")
    public String portal(){

        return "index.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USUARIO', 'ROLE_PERIODISTA')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session){

        Usuario logueado = (Usuario) session.getAttribute("usuariosession");

        if(logueado.getRol().toString().equals("ADMIN") || logueado.getRol().toString().equals("PERIODISTA")){

            return "redirect:/admin/dashboard";
        }

        return "redirect:/noticia/listar";
    }

    @GetMapping("/registro")
    public String registrar(){

        return "registrar.html";
    }

    @PostMapping("/registrar")
    public String registroUsuario(@RequestParam String nombreUsuario, @RequestParam String pass, @RequestParam String pass2, ModelMap modelo){

        try {
            usuarioService.registrar(nombreUsuario, pass, pass2);
            modelo.put("exito", "Usuario registrado con exito");

        } catch (MiExcepcion e) {
            modelo.put("error", e.getMessage());
            modelo.put("nombre", nombreUsuario);
            return "redirect:/registro";
        }
        return "redirect:/sesion";
    }

    @GetMapping("/sesion")
    public String login(@RequestParam(required = false) String error, ModelMap modelo){

        if(error != null){

            modelo.put("error", "Usuario o contraseña incorrectas");
        }
        return "login.html";
    }

}
