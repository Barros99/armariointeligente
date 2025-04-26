package br.com.unit.tokseg.armariointeligente.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.unit.tokseg.armariointeligente.model.Usuario;
import br.com.unit.tokseg.armariointeligente.service.UsuarioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<?> criarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario novoUsuario = usuarioService.criarUsuario(usuario);
            return ResponseEntity.ok(novoUsuario);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listarUsuarios() {
        try {
            return ResponseEntity.ok(usuarioService.listarUsuarios());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUsuarioPorId(@PathVariable Long id) {
        try {
            Optional<Usuario> usuario = usuarioService.buscarUsuarioPorId(id);
            if (usuario.isPresent()) {
                return ResponseEntity.ok(usuario.get());
            } else {
                return ResponseEntity.status(404).body("Usuário não encontrado com o ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            Usuario usuarioAtualizado = usuarioService.atualizarUsuario(id, usuario);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarUsuario(@PathVariable Long id) {
        try {
            usuarioService.deletarUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
