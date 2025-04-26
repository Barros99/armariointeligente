package br.com.unit.tokseg.armariointeligente.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.unit.tokseg.armariointeligente.model.TipoUsuario;
import br.com.unit.tokseg.armariointeligente.repository.TipoUsuarioRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;

@Service
public class TipoUsuarioService {
    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Transactional
    public TipoUsuario criarTipoUsuario(TipoUsuario tipoUsuario) {
        if (tipoUsuario == null) {
            throw new IllegalArgumentException("O tipo de usuário não pode ser nulo");
        }
        if (tipoUsuario.getNome() == null || tipoUsuario.getNome().isEmpty()) {
            throw new IllegalArgumentException(
                    "O nome do tipo de usuário não pode ser nulo ou vazio");
        }

        tipoUsuarioRepository.findByNome(tipoUsuario.getNome()).ifPresent(t -> {
            throw new RuntimeException("Tipo de usuário já cadastrado");
        });
        return tipoUsuarioRepository.save(tipoUsuario);
    }

    @Transactional
    public List<TipoUsuario> listarTiposUsuarios() {
        return tipoUsuarioRepository.findAll();
    }

    @Transactional
    public Optional<TipoUsuario> buscarTipoUsuarioPorId(Long id) {
        return tipoUsuarioRepository.findById(id);
    }

    @Transactional
    public TipoUsuario atualizarTipoUsuario(Long id, TipoUsuario tipoUsuario) {
        TipoUsuario tipoExistente = tipoUsuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de usuário não encontrado"));

        // Verifica se o novo nome já existe para outro tipo de usuário
        if (tipoUsuario.getNome() != null && !tipoUsuario.getNome().isEmpty()) {
            Optional<TipoUsuario> tipoComMesmoNome =
                    tipoUsuarioRepository.findByNome(tipoUsuario.getNome());
            if (tipoComMesmoNome.isPresent() && !tipoComMesmoNome.get().getId().equals(id)) {
                throw new RuntimeException("Já existe um tipo de usuário com este nome");
            }
            tipoExistente.setNome(tipoUsuario.getNome());
        }

        if (tipoUsuario.getDescricao() != null) {
            tipoExistente.setDescricao(tipoUsuario.getDescricao());
        }

        return tipoUsuarioRepository.save(tipoExistente);
    }

    @Transactional
    public void deletarTipoUsuario(Long id) {
        TipoUsuario tipoUsuario = tipoUsuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de usuário não encontrado"));

        if (!tipoUsuario.getUsuarios().isEmpty()) {
            throw new RuntimeException(
                    "Não é possível excluir este tipo de usuário pois existem usuários vinculados a ele");
        }

        tipoUsuarioRepository.deleteById(id);
    }
}
