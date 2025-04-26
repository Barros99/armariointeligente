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

                TipoUsuario entregador = new TipoUsuario();
                entregador.setNome("Entregador");
                entregador.setDescricao("Entregador com acesso administrativo ao sistema");
                entregador = tipoUsuarioRepository.save(entregador);


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
                    Usuario entregadorUser = new Usuario();
                    entregadorUser.setNome("Maria Oliveira");
                    entregadorUser.setEmail("maria.oliveira@exemplo.com");
                    entregadorUser.setSenha("senha456");
                    entregadorUser.setTelefone("(81) 97777-2222");
                    entregadorUser.setTipoUsuario(entregador);
                    usuarioRepository.save(entregadorUser);



                    System.out.println("Usuários criados com sucesso!");
                }

                System.out.println("Dados iniciais carregados com sucesso!");
            }
        };
    }
}
