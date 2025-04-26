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
    public TipoUsuario atualizarTipoUsuario(TipoUsuario tipoUsuario) {
        tipoUsuario.setNome(tipoUsuario.getNome());
        tipoUsuario.setDescricao(tipoUsuario.getDescricao());

        return tipoUsuarioRepository.save(tipoUsuario);

    }

    @Transactional
    public void deletarTipoUsuario(Long id) {
        try {
            if (!tipoUsuarioRepository.existsById(id)) {
                throw new RuntimeException("Tipo de usuário não encontrado");
            }
            tipoUsuarioRepository.deleteById(id);
            tipoUsuarioRepository.flush();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar tipo de usuário: " + e.getMessage());
        }
    }
}
