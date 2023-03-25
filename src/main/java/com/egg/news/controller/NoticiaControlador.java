package com.egg.news.controller;

import com.egg.news.entity.Periodista;
import com.egg.news.exception.MiExcepcion;
import com.egg.news.repository.NoticiaRepositorio;
import com.egg.news.service.impl.NoticiaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/noticia")
public class NoticiaControlador {

    @Autowired
    private NoticiaServicio noticiaServicio;
    @Autowired
    private NoticiaRepositorio noticiaRepositorio;

    @PostMapping("/crear")
    public String crearNoticia(@RequestParam String creador, @RequestParam String titulo, @RequestParam String cuerpo, ModelMap modelo){

        try {

            noticiaServicio.crearNoticia(Long.parseLong(creador), titulo, cuerpo);
            modelo.put("exito", "La noticia se creo correctamente!!");

        } catch (MiExcepcion e) {
            modelo.put("error", e.getMessage());
            return "redirect:/admin/dashboard";
        }

        return "redirect:/admin/dashboard";
    }

    @PreAuthorize("hasRole('ROLE_USUARIO')")
    @GetMapping("/listar")
    public String listar(ModelMap modelo){
        modelo.addAttribute("listarNoticias", noticiaServicio.listarNoticias());
        return "inicio.html";
    }

    @GetMapping("/ver/{id}")
    public String verNoticia(@PathVariable Long id, ModelMap modelo){

        modelo.addAttribute("noticia", noticiaServicio.obtenerUnaNoticia(id));
        return "noticia.html";
    }

    @GetMapping("/editar/{id}")
    public String editarNoticia(@PathVariable Long id, ModelMap modelo){
        modelo.put("editar", "Presionaro el boton editar");
        modelo.put("noticia", noticiaServicio.obtenerUnaNoticia(id));

        return "panel.html";
    }

    @PostMapping("/guardarEdicion")
    public String guardarModificacion(@RequestParam Long id, @RequestParam String titulo, @RequestParam String cuerpo, ModelMap modelo){

        try {
            noticiaServicio.editarNoticia(id, titulo, cuerpo);
            modelo.put("exito", "La noticia se guardo correctamente!!");
        } catch (MiExcepcion e) {
            modelo.put("error", e.getMessage());
            return "redirect:/admin/dashboard";
        }

        return "redirect:/admin/dashboard";
    }

    @GetMapping("/advertencia/{id}")
    public String advertir(@PathVariable Long id, ModelMap modelo){
        modelo.put("advertencia", "¿Usted esta seguro de eliminar?");
        modelo.put("noticia", noticiaServicio.obtenerUnaNoticia(id));
        return "panel.html";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, ModelMap modelo){

        noticiaServicio.eliminarNoticia(id);
        modelo.put("exito", "La noticia se eliminó correctamente!!");
        return "redirect:/admin/dashboard";
    }

}
