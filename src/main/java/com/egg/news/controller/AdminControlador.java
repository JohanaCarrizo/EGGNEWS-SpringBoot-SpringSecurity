package com.egg.news.controller;

import com.egg.news.entity.Periodista;
import com.egg.news.entity.Usuario;
import com.egg.news.exception.MiExcepcion;
import com.egg.news.service.impl.NoticiaServicio;
import com.egg.news.service.impl.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminControlador {

    @Autowired
    private NoticiaServicio noticiaServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/dashboard")
    public String panelAdmin(HttpSession session, ModelMap modelo){

        Usuario logueado = (Usuario) session.getAttribute("usuariosession");

        if(logueado.getRol().toString().equals("ADMIN")){

            modelo.addAttribute("listarNoticias", noticiaServicio.listarNoticias());
            modelo.addAttribute("listarPeriodistas", usuarioServicio.listarPeriodistas());
            modelo.addAttribute("listarRoles", usuarioServicio.listarRoles());
            return "panel.html";
        }else if(logueado.getRol().toString().equals("PERIODISTA")){
            modelo.addAttribute("listarNoticias", noticiaServicio.listarNoticiasDeUnPeriodista(logueado.getId()));
            return "panel.html";
        }

        return null;
    }

    @GetMapping("/dashboard/usuarios")
    public String gestionarUsuarios(ModelMap modelo){
        modelo.addAttribute("listarUsuarios", noticiaServicio.listarNoticias());
        return "usuario.html";
    }

    @GetMapping("/dashboard/periodistas")
    public String gestionarPeriodistas(ModelMap modelo){
        modelo.addAttribute("listarPeriodistas", noticiaServicio.listarNoticias());
        return "periodistas.html";
    }



}
