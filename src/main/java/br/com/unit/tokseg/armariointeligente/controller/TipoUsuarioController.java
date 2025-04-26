package br.com.unit.tokseg.armariointeligente.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.unit.tokseg.armariointeligente.model.TipoUsuario;
import br.com.unit.tokseg.armariointeligente.service.TipoUsuarioService;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/v1/tipos-usuarios")
public class TipoUsuarioController {

    @Autowired
    private TipoUsuarioService tipoUsuarioService;

    @PostMapping
    public ResponseEntity<?> criarTipoUsuario(@RequestBody TipoUsuario tipoUsuario) {
        try {
            TipoUsuario novoTipoUsuario = tipoUsuarioService.criarTipoUsuario(tipoUsuario);
            return ResponseEntity.ok(novoTipoUsuario);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listarTiposUsuarios() {
        try {
            return ResponseEntity.ok(tipoUsuarioService.listarTiposUsuarios());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> buscarTipoUsuarioPorId(@PathVariable Long id) {
        try {
            TipoUsuario tipoUsuario = tipoUsuarioService.buscarTipoUsuarioPorId(id)
                    .orElseThrow(() -> new RuntimeException("Tipo de usuário não encontrado"));
            return ResponseEntity.ok(tipoUsuario);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarTipoUsuario(@PathVariable Long id,
            @RequestBody TipoUsuario tipoUsuario) {
        try {
            TipoUsuario tipoUsuarioAtualizado = tipoUsuarioService.buscarTipoUsuarioPorId(id)
                    .orElseThrow(() -> new RuntimeException("Tipo de usuário não encontrado"));
            tipoUsuarioAtualizado.setNome(tipoUsuario.getNome());
            tipoUsuarioAtualizado.setDescricao(tipoUsuario.getDescricao());
            tipoUsuarioService.atualizarTipoUsuario(tipoUsuarioAtualizado);
            return ResponseEntity.ok(tipoUsuarioAtualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarTipoUsuario(@PathVariable Long id) {
        try {
            tipoUsuarioService.deletarTipoUsuario(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
