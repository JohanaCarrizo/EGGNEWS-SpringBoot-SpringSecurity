package com.egg.news.service.impl;

import com.egg.news.entity.Usuario;
import com.egg.news.enumerated.Rol;
import com.egg.news.exception.MiExcepcion;
import com.egg.news.repository.UsuarioRepositorio;
import com.egg.news.service.interfaz.IUsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UsuarioServicio implements UserDetailsService, IUsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepo;

    public void registrar(String nombreUsuario, String pass, String pass2) throws MiExcepcion{

        validar(nombreUsuario, pass, pass2);
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setPassword(new BCryptPasswordEncoder().encode(pass));
        usuario.setFechaAlta(new Date());
        usuario.setActivo(true);
        usuario.setRol(Rol.USUARIO);
        usuarioRepo.save(usuario);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepo.findAll();
    }

    @Override
    public List<Usuario> listarPeriodistas() {
        return usuarioRepo.buscarPeriodista();
    }

    @Override
    public List<Rol> listarRoles() {
        return usuarioRepo.listarRoles();
    }


    private void validar(String nombreUsuario, String password, String password2) throws MiExcepcion{

        if (nombreUsuario.isEmpty() || nombreUsuario == null) {
            throw new MiExcepcion("El nombre no puede ser nulo o estar vacio");
        }

        if (password.isEmpty() || password == null || password.length()<6) {
            throw new MiExcepcion("El email no puede ser nulo y debe tener mas de 5 digitos");
        }

        if (!password.equals(password2)) {
            throw new MiExcepcion("Las contraseñas ingresasdas deben ser iguales");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepo.findByNombreUsuario(nombreUsuario);

        if(usuario != null){

            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+usuario.getRol().toString());
            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);

            return new User(usuario.getNombreUsuario(), usuario.getPassword(), permisos);
        }else{
            return null;
        }
    }
}
