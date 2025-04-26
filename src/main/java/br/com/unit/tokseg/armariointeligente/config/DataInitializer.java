package br.com.unit.tokseg.armariointeligente.config;

import br.com.unit.tokseg.armariointeligente.model.TipoUsuario;
import br.com.unit.tokseg.armariointeligente.model.Usuario;
import br.com.unit.tokseg.armariointeligente.repository.TipoUsuarioRepository;
import br.com.unit.tokseg.armariointeligente.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            // Adiciona alguns tipos de usuário iniciais
            if (tipoUsuarioRepository.count() == 0) {
                System.out.println("Carregando dados iniciais...");

                // Criando tipos de usuário
                TipoUsuario admin = new TipoUsuario();
                admin.setNome("Administrador");
                admin.setDescricao("Usuário com acesso total ao sistema");
                admin = tipoUsuarioRepository.save(admin);

                TipoUsuario cliente = new TipoUsuario();
                cliente.setNome("Cliente");
                cliente.setDescricao("Usuário com acesso limitado ao sistema");
                cliente = tipoUsuarioRepository.save(cliente);

                TipoUsuario funcionario = new TipoUsuario();
                funcionario.setNome("Funcionário");
                funcionario.setDescricao("Funcionário da empresa com acesso intermediário");
                funcionario = tipoUsuarioRepository.save(funcionario);

                TipoUsuario gerente = new TipoUsuario();
                gerente.setNome("Gerente");
                gerente.setDescricao("Gerente com acesso administrativo ao sistema");
                gerente = tipoUsuarioRepository.save(gerente);

                TipoUsuario tecnico = new TipoUsuario();
                tecnico.setNome("Técnico");
                tecnico.setDescricao("Técnico responsável pela manutenção dos equipamentos");
                tecnico = tipoUsuarioRepository.save(tecnico);

                System.out.println("Tipos de usuário criados com sucesso!");

                // Criando usuários
                if (usuarioRepository.count() == 0) {
                    // Usuário Administrador
                    Usuario adminUser = new Usuario();
                    adminUser.setNome("Admin Sistema");
                    adminUser.setEmail("admin@smartlocker.com");
                    adminUser.setSenha("admin123");
                    adminUser.setTelefone("(81) 99999-0000");
                    adminUser.setTipoUsuario(admin);
                    usuarioRepository.save(adminUser);

                    // Usuário Cliente
                    Usuario clienteUser = new Usuario();
                    clienteUser.setNome("João Silva");
                    clienteUser.setEmail("joao.silva@exemplo.com");
                    clienteUser.setSenha("senha123");
                    clienteUser.setTelefone("(81) 98888-1111");
                    clienteUser.setTipoUsuario(cliente);
                    usuarioRepository.save(clienteUser);

                    // Usuário Funcionário
                    Usuario funcionarioUser = new Usuario();
                    funcionarioUser.setNome("Maria Oliveira");
                    funcionarioUser.setEmail("maria.oliveira@exemplo.com");
                    funcionarioUser.setSenha("senha456");
                    funcionarioUser.setTelefone("(81) 97777-2222");
                    funcionarioUser.setTipoUsuario(funcionario);
                    usuarioRepository.save(funcionarioUser);

                    // Usuário Gerente
                    Usuario gerenteUser = new Usuario();
                    gerenteUser.setNome("Carlos Pereira");
                    gerenteUser.setEmail("carlos.pereira@exemplo.com");
                    gerenteUser.setSenha("senha789");
                    gerenteUser.setTelefone("(81) 96666-3333");
                    gerenteUser.setTipoUsuario(gerente);
                    usuarioRepository.save(gerenteUser);

                    // Usuário Técnico
                    Usuario tecnicoUser = new Usuario();
                    tecnicoUser.setNome("Ana Santos");
                    tecnicoUser.setEmail("ana.santos@exemplo.com");
                    tecnicoUser.setSenha("senha101112");
                    tecnicoUser.setTelefone("(81) 95555-4444");
                    tecnicoUser.setTipoUsuario(tecnico);
                    usuarioRepository.save(tecnicoUser);

                    System.out.println("Usuários criados com sucesso!");
                }

                System.out.println("Dados iniciais carregados com sucesso!");
            }
        };
    }
}
