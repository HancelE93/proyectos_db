package com.krakedev.proyectos.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import com.krakedev.proyectos.entidades.Usuario;
import com.krakedev.proyectos.repositories.UsuarioRepository;
import com.krakedev.proyectos.security.JwtUtil;
import com.krakedev.proyectos.services.TokenBlackListService;
import com.krakedev.proyectos.services.UsuarioService;


@CrossOrigin(
        origins = "http://localhost:5173",
        allowedHeaders = {
                "Authorization",
                "Content-Type"
        },
        methods = {
                RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.PUT,
                RequestMethod.DELETE
        }
)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final TokenBlackListService blacklistService;



    public AuthController(UsuarioService usuarioService, UsuarioRepository usuarioRepository,
			TokenBlackListService blacklistService) {
		super();
		this.usuarioService = usuarioService;
		this.usuarioRepository = usuarioRepository;
		this.blacklistService = blacklistService;
	}

	@PostMapping("/registrar")
    public ResponseEntity<?> registrar(
            @RequestBody Usuario usuario) {

        return ResponseEntity.ok(
                usuarioService.registrar(usuario));
    }

	@PostMapping("/login")
	public ResponseEntity<?> login(
	        @RequestBody Map<String,String> datos) {

	    String username = datos.get("username");
	    String password = datos.get("password");

	    boolean autenticado =
	            usuarioService.autenticar(username, password);

	    if (autenticado) {

	        Usuario usuario =
	                usuarioRepository.findByUsername(username).get();

	        String token =
	                JwtUtil.generarToken(
	                        usuario.getUsername(),
	                        usuario.getRol());

	        return ResponseEntity.ok(
	                Map.of("token", token));
	    }

	    return ResponseEntity.status(401)
	            .body("Credenciales incorrectas");
	}
	
	@PostMapping("/logout")
	public ResponseEntity<?> logout(
	        @RequestHeader("Authorization")
	        String authHeader) {

	    String token = authHeader.substring(7);

	    blacklistService.invalidarToken(token);

	    return ResponseEntity.ok(
	            "Sesión cerrada correctamente");
	}
	
	@GetMapping("/perfil")
	public ResponseEntity<?> perfil() {

	    Authentication auth =
	            SecurityContextHolder.getContext().getAuthentication();

	    String username = auth.getName();

	    String rol =
	            auth.getAuthorities()
	                .iterator()
	                .next()
	                .getAuthority();

	    return ResponseEntity.ok(
	            Map.of(
	                    "usuario", username,
	                    "rol", rol,
	                    "mensaje", "Acceso autorizado"));
	}
}
