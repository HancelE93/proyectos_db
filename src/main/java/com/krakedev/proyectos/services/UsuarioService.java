package com.krakedev.proyectos.services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.krakedev.proyectos.entidades.Usuario;
import com.krakedev.proyectos.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario registrar(Usuario usuario) {

        String passwordHash =
                BCrypt.hashpw(usuario.getPassword(),
                              BCrypt.gensalt());

        usuario.setPassword(passwordHash);

        return usuarioRepository.save(usuario);
    }

    public boolean autenticar(String username,
                              String password) {

        Optional<Usuario> usuarioEncontrado =
                usuarioRepository.findByUsername(username);

        if (usuarioEncontrado.isPresent()) {

            Usuario usuario = usuarioEncontrado.get();

            return BCrypt.checkpw(
                    password,
                    usuario.getPassword());
        }

        return false;
    }
}