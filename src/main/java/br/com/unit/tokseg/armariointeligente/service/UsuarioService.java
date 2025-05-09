package br.com.unit.tokseg.armariointeligente.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.unit.tokseg.armariointeligente.exception.BadRequestException;
import br.com.unit.tokseg.armariointeligente.exception.ResourceAlreadyExistsException;
import br.com.unit.tokseg.armariointeligente.exception.ResourceNotFoundException;
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
        if (usuario == null) {
            throw new BadRequestException("Usuário não pode ser nulo");
        }
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            throw new BadRequestException("Email não pode ser nulo ou vazio");
        }
        if (usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
            throw new BadRequestException("Senha não pode ser nula ou vazia");
        }

        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("Usuário", "email", usuario.getEmail());
        }

        if (usuario.getTipoUsuario() == null || usuario.getTipoUsuario().getId() == null) {
            throw new BadRequestException("Tipo de usuário é obrigatório");
        }

        TipoUsuario tipoUsuario = tipoUsuarioRepository.findById(usuario.getTipoUsuario().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de usuário", "id",
                        usuario.getTipoUsuario().getId()));

        usuario.setTipoUsuario(tipoUsuario);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Transactional
    public Usuario atualizarUsuario(Long id, Usuario usuario) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", "id", id));

        // Verifica se o email já está em uso por outro usuário
        if (usuario.getEmail() != null && !usuario.getEmail().isEmpty()) {
            Optional<Usuario> usuarioComMesmoEmail =
                    usuarioRepository.findByEmail(usuario.getEmail());
            if (usuarioComMesmoEmail.isPresent()
                    && !usuarioComMesmoEmail.get().getId().equals(id)) {
                throw new ResourceAlreadyExistsException("Usuário", "email", usuario.getEmail());
            }
            usuarioExistente.setEmail(usuario.getEmail());
        }

        // Atualiza o tipo de usuário se fornecido
        if (usuario.getTipoUsuario() != null && usuario.getTipoUsuario().getId() != null) {
            TipoUsuario tipoUsuario =
                    tipoUsuarioRepository.findById(usuario.getTipoUsuario().getId())
                            .orElseThrow(() -> new ResourceNotFoundException("Tipo de usuário",
                                    "id", usuario.getTipoUsuario().getId()));
            usuarioExistente.setTipoUsuario(tipoUsuario);
        }

        // Atualiza outros campos se fornecidos
        if (usuario.getNome() != null && !usuario.getNome().isEmpty()) {
            usuarioExistente.setNome(usuario.getNome());
        }

        if (usuario.getTelefone() != null && !usuario.getTelefone().isEmpty()) {
            usuarioExistente.setTelefone(usuario.getTelefone());
        }

        if (usuario.getSenha() != null && !usuario.getSenha().isEmpty()) {
            usuarioExistente.setSenha(usuario.getSenha());
        }

        return usuarioRepository.save(usuarioExistente);
    }

    @Transactional
    public void deletarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário", "id", id);
        }
        usuarioRepository.deleteById(id);
    }
}


