package br.com.unit.tokseg.armariointeligente.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.unit.tokseg.armariointeligente.model.TipoUsuario;
import br.com.unit.tokseg.armariointeligente.model.Usuario;
import br.com.unit.tokseg.armariointeligente.repository.TipoUsuarioRepository;
import br.com.unit.tokseg.armariointeligente.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;


    @Transactional
    public Usuario criarUsuario(Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        if (usuario.getTipoUsuario() != null && usuario.getTipoUsuario().getId() != null) {
            TipoUsuario tipoUsuario =
                    tipoUsuarioRepository.findById(usuario.getTipoUsuario().getId()).orElseThrow(
                            () -> new RuntimeException("Tipo de usuário não encontrado com o ID:"
                                    + usuario.getTipoUsuario().getId()));
            usuario.setTipoUsuario(tipoUsuario);
        }
        return usuarioRepository.save(usuario);
    }
}


