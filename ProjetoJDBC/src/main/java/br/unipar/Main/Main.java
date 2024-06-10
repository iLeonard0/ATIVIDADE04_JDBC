package br.unipar.Main;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Scanner;

public class Main {

    private static final String url = "jdbc:postgresql://localhost:5432/Exemplo1";
    private static final String user = "postgres";
    private static final String password = "admin123";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        criarTabelaUsuario();
        criarTabelaCliente();
        criarTabelaProduto();
        criarTabelaVenda();

        while (true) {
            System.out.println();
            System.out.println("[- Menu de escolhas -]");
            System.out.println("1 - Usuário");
            System.out.println("2 - Cliente");
            System.out.println("3 - Produto");
            System.out.println("4 - Venda");
            System.out.println("5 - Sair do sistema");
            System.out.print("Digite sua opção: ");
            int escolha = scanner.nextInt();
            switch (escolha) {
                case 1:
                    menuUsuario(scanner);
                    break;
                case 2:
                    menuCliente(scanner);
                    break;
                case 3:
                    menuProduto(scanner);
                    break;
                case 4:
                    menuVenda(scanner);
                    break;
                case 5:
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void menuUsuario(Scanner scanner) {
        System.out.println();
        System.out.println("Menu de Usuário");
        System.out.println("[1] - Inserir");
        System.out.println("[2] - Alterar");
        System.out.println("[3] - Listar");
        System.out.println("[4] - Excluir");
        System.out.println("[5] - Voltar");
        System.out.print("Digite sua opção: ");
        int opcaoUsuario = scanner.nextInt();
        switch (opcaoUsuario) {
            case 1:
                inserirUsuario(scanner);
                break;
            case 2:
                atualizarUsuario(scanner);
                break;
            case 3:
                listarTodosUsuarios();
                break;
            case 4:
                excluirUsuario(scanner);
                break;
            case 5:
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }

    private static void menuCliente(Scanner scanner) {
        System.out.println();
        System.out.println("Menu de Cliente");
        System.out.println("[1] - Cadastrar");
        System.out.println("[2] - Alterar");
        System.out.println("[3] - Listar");
        System.out.println("[4] - Excluir");
        System.out.println("[5] - Voltar");
        System.out.print("Digite sua opção: ");
        int opcaoCliente = scanner.nextInt();
        switch (opcaoCliente) {
            case 1:
                cadastrarCliente(scanner);
                break;
            case 2:
                atualizarCliente(scanner);
                break;
            case 3:
                listarTodosClientes();
                break;
            case 4:
                excluirCliente(scanner);
                break;
            case 5:
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }

    private static void menuProduto(Scanner scanner) {
        System.out.println();
        System.out.println("Menu de Produtos");
        System.out.println("[1] - Cadastrar");
        System.out.println("[2] - Alterar");
        System.out.println("[3] - Listar");
        System.out.println("[4] - Excluir");
        System.out.println("[5] - Voltar");
        System.out.print("Digite sua opção: ");
        int opcaoProduto = scanner.nextInt();
        switch (opcaoProduto) {
            case 1:
                scanner.nextLine();
                System.out.print("Digite a descrição do produto: ");
                String descricao = scanner.nextLine();
                System.out.print("Digite o valor do produto: ");
                BigDecimal valor = scanner.nextBigDecimal();
                cadastrarProduto(descricao, valor);
                break;
            case 2:
                atualizarProduto(scanner);
                break;
            case 3:
                listarTodosProdutos();
                break;
            case 4:
                excluirProduto(scanner);
                break;
            case 5:
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }
    private static void menuVenda(Scanner scanner) {
        System.out.println();
        System.out.println("Menu de Vendas");
        System.out.println("[1] - Cadastrar");
        System.out.println("[2] - Listar");
        System.out.println("[3] - Excluir");
        System.out.println("[4] - Voltar");
        System.out.print("Digite sua opção: ");
        int opcaoVenda = scanner.nextInt();
        switch (opcaoVenda) {
            case 1:
                cadastrarVenda(scanner);
                break;
            case 2:
                listarVendas();
                break;
            case 3:
                excluirVenda(scanner);
                break;
            case 4:
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }

    public static Connection connection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public static void criarTabelaUsuario() {
        try (Connection conn = connection();
             Statement statement = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS usuarios ("
                    + "codigo SERIAL PRIMARY KEY,"
                    + "username VARCHAR(50) NOT NULL UNIQUE,"
                    + "password VARCHAR(300) NOT NULL,"
                    + "nome VARCHAR(50) NOT NULL,"
                    + "nascimento DATE"
                    + ");";
            statement.executeUpdate(sql);
            System.out.println("Tabela de usuários criada com sucesso!");
        } catch (SQLException exception) {
            System.out.println("Erro ao criar tabela de usuários.");
            exception.printStackTrace();
        }
    }

    public static void inserirUsuario(Scanner scanner) {
        scanner.nextLine();
        System.out.print("Digite seu usuário: ");
        String usuario = scanner.nextLine();
        System.out.print("Digite sua senha: ");
        String senha = scanner.nextLine();
        System.out.print("Digite seu nome: ");
        String nome = scanner.nextLine();
        System.out.print("Digite sua data de nascimento (AAAA-MM-DD): ");
        String dataNascimento = scanner.nextLine();

        if (!dataNascimento.matches("\\d{4}-\\d{2}-\\d{2}")) {
            System.out.println("Formato de data inválido. Use o formato AAAA-MM-DD.");
            return;
        }

        try (Connection conn = connection()) {
            if (usuarioExiste(conn, usuario)) {
                System.out.println("Usuário já existe.");
                return;
            }

            try (PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO usuarios (username, password, nome, nascimento) VALUES (?, ?, ?, ?)")) {
                preparedStatement.setString(1, usuario);
                preparedStatement.setString(2, senha);
                preparedStatement.setString(3, nome);
                preparedStatement.setDate(4, Date.valueOf(dataNascimento));
                preparedStatement.executeUpdate();
                System.out.println("Usuário inserido com sucesso!");
            } catch (SQLException e) {
                System.out.println("Erro ao inserir usuário.");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados.");
            e.printStackTrace();
        }
    }

    private static boolean usuarioExiste(Connection conn, String usuario) {
        try (PreparedStatement preparedStatement = conn.prepareStatement(
                "SELECT * FROM usuarios WHERE username = ?")) {
            preparedStatement.setString(1, usuario);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar se o usuário existe.");
            e.printStackTrace();
            return false;
        }
    }

    public static void atualizarUsuario(Scanner scanner) {
        System.out.print("Digite o código do usuário que deseja atualizar: ");
        int codigo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite o novo usuário: ");
        String novoUsuario = scanner.nextLine();
        System.out.print("Digite a nova senha: ");
        String novaSenha = scanner.nextLine();
        System.out.print("Digite o novo nome: ");
        String novoNome = scanner.nextLine();
        System.out.print("Digite a nova data de nascimento (AAAA-MM-DD): ");
        String novaDataNascimento = scanner.nextLine();

        try (Connection conn = connection();
             PreparedStatement preparedStatement = conn.prepareStatement(
                     "UPDATE usuarios SET username = ?, password = ?, nome = ?, nascimento = ? WHERE codigo = ?")) {
            preparedStatement.setString(1, novoUsuario);
            preparedStatement.setString(2, novaSenha);
            preparedStatement.setString(3, novoNome);
            preparedStatement.setDate(4, Date.valueOf(novaDataNascimento));
            preparedStatement.setInt(5, codigo);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Usuário atualizado com sucesso!");
            } else {
                System.out.println("Nenhum usuário foi encontrado com o código especificado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar usuário.");
            e.printStackTrace();
        }
    }

    public static void listarTodosUsuarios() {
        try (Connection conn = connection();
             Statement statement = conn.createStatement();
             ResultSet result = statement.executeQuery("SELECT * FROM usuarios")) {
            while (result.next()) {
                System.out.println("===========================");
                System.out.println("Código do Usuário: " + result.getInt("codigo"));
                System.out.println("Usuário: " + result.getString("username"));
                System.out.println("Nome: " + result.getString("nome"));
                System.out.println("Nascimento: " + result.getDate("nascimento"));
                System.out.println("===========================");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar usuários.");
            e.printStackTrace();
        }
    }

    public static void excluirUsuario(Scanner scanner) {
        System.out.print("Digite o código do usuário que deseja excluir: ");
        int codigo = scanner.nextInt();

        try (Connection conn = connection();
             PreparedStatement preparedStatement = conn.prepareStatement(
                     "DELETE FROM usuarios WHERE codigo = ?")) {
            preparedStatement.setInt(1, codigo);
            preparedStatement.executeUpdate();
            System.out.println("Usuário excluído com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao excluir usuário.");
            e.printStackTrace();
        }
    }

    public static void criarTabelaCliente() {
        try {
            Connection conn = connection();
            Statement statement = conn.createStatement();
            String sql = " CREATE TABLE IF NOT EXISTS cliente ("
                    + "id_cliente SERIAL CONSTRAINT PK_CLIENTE PRIMARY KEY,"
                    + "nome VARCHAR(255) NOT NULL,"
                    + "CPF VARCHAR(11) UNIQUE"
                    + ");";
            statement.executeUpdate(sql);
            System.out.println("Tabela de clientes criada com sucesso.");
        } catch (SQLException exception) {
            System.out.println("Erro ao criar tabela de Clientes");
            exception.printStackTrace();
        }
    }

    private static boolean clienteExiste(Connection conn, String nome){
        try (PreparedStatement preparedStatement = conn.prepareStatement(
                "SELECT * FROM cliente WHERE nome = ?")) {
            preparedStatement.setString(1, nome);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar se o usuário existe.");
            e.printStackTrace();
            return false;
        }
    }

    public static void cadastrarCliente(Scanner scanner) {

        scanner.nextLine();
        System.out.print("Digite o nome do cliente: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o CPF do cliente: ");
        String CPF = scanner.nextLine();

        try (Connection conn = connection()) {
            if (clienteExiste(conn, nome)) {
                System.out.println("Cliente já existe.");
                return;
            }

            try (PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO cliente (nome, CPF) VALUES (?, ?)")) {
                preparedStatement.setString(1, nome);
                preparedStatement.setString(2, CPF);
                preparedStatement.executeUpdate();
                System.out.println("Cliente inserido com sucesso!");
            } catch (SQLException e) {
                System.out.println("Erro ao inserir Cliente.");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados.");
            e.printStackTrace();
        }
    }

    public static void atualizarCliente(Scanner scanner){
        System.out.print("Digite o código do Cliente que deseja atualizar: ");
        int codigo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite o novo nome: ");
        String novoNome = scanner.nextLine();
        System.out.print("Digite o novo CPF: ");
        String novoCPF = scanner.nextLine();

        try (Connection conn = connection();
             PreparedStatement preparedStatement = conn.prepareStatement(
                     "UPDATE cliente SET nome = ?, CPF = ? WHERE id_cliente = ?")) {
            preparedStatement.setString(1, novoNome);
            preparedStatement.setString(2, novoCPF);
            preparedStatement.setInt(3, codigo);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Cliente atualizado com sucesso!");
            } else {
                System.out.println("Nenhum Cliente foi encontrado com o código especificado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar Cliente.");
            e.printStackTrace();
        }
    }
    public static void listarTodosClientes(){
        try (Connection conn = connection();
             Statement statement = conn.createStatement();
             ResultSet result = statement.executeQuery("SELECT * FROM cliente")) {
            while (result.next()) {
                System.out.println("===========================");
                System.out.println("Código do Cliente: " + result.getInt("id_cliente"));
                System.out.println("Nome: " + result.getString("nome"));
                System.out.println("CPF: " + result.getString("CPF"));
                System.out.println("===========================");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar clientes.");
            e.printStackTrace();
        }
    }

    public static void excluirCliente(Scanner scanner){
        System.out.print("Digite o código do Cliente que deseja excluir: ");
        int codigo = scanner.nextInt();

        try (Connection conn = connection();
             PreparedStatement preparedStatement = conn.prepareStatement(
                     "DELETE FROM cliente WHERE id_cliente = ?")) {
            preparedStatement.setInt(1, codigo);
            preparedStatement.executeUpdate();
            System.out.println("Cliente excluído com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao excluir Cliente.");
            e.printStackTrace();
        }
    }

    public static void criarTabelaProduto() {
        try {
            Connection conn = connection();

            Statement statement = conn.createStatement();
            String sql = " CREATE TABLE IF NOT EXISTS produto ("
                    + "id_produto SERIAL CONSTRAINT PK_PRODUTO PRIMARY KEY,"
                    + "descricao VARCHAR(255) NOT NULL,"
                    + "valor MONEY NOT NULL"
                    + ");";

            statement.executeUpdate(sql);

            System.out.println("Tabela de produtos criada com sucesso.");

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
    public static void cadastrarProduto(String descricao, BigDecimal valor) {
        try {
            Connection conn = connection();
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT into produto (descricao, valor) VALUES (?,?)"
            );
            preparedStatement.setString(1, descricao);
            preparedStatement.setBigDecimal(2, valor.setScale(2, BigDecimal.ROUND_HALF_UP));

            preparedStatement.executeUpdate();

            System.out.println("Produto Inserido!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void atualizarProduto(Scanner scanner) {
        System.out.print("Digite o código do produto que deseja atualizar: ");
        int codigo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite a nova descricao do produto: ");
        String novoNome = scanner.nextLine();
        System.out.print("Digite o novo valor do produto: ");
        double novoPreco = scanner.nextDouble();

        try (Connection conn = connection();
             PreparedStatement preparedStatement = conn.prepareStatement(
                     "UPDATE produto SET descricao = ?, valor = ? WHERE id_produto = ?")) {
            preparedStatement.setString(1, novoNome);
            preparedStatement.setDouble(2, novoPreco);
            preparedStatement.setInt(3, codigo);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Produto atualizado com sucesso!");
            } else {
                System.out.println("Nenhum produto foi encontrado com o código especificado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar produto.");
            e.printStackTrace();
        }
    }

    public static void listarTodosProdutos() {

        try {
            Connection conn = connection();
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM produto");
            while(result.next()){
                System.out.println("ID: ["+result.getInt("id_produto")+
                        "], Descrição: ["+result.getString("descricao")+
                        "], Valor: ["+result.getString("valor")+"]");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void excluirProduto(Scanner scanner) {
        System.out.print("Digite o código do produto que deseja excluir: ");
        int codigo = scanner.nextInt();

        try (Connection conn = connection();
             PreparedStatement preparedStatement = conn.prepareStatement(
                     "DELETE FROM produto WHERE id_produto = ?")) {
            preparedStatement.setInt(1, codigo);
            preparedStatement.executeUpdate();
            System.out.println("Produto excluído com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao excluir produto.");
            e.printStackTrace();
        }
    }

    public static void criarTabelaVenda() {
        try {
            Connection conn = connection();

            Statement statement = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS venda ("
                    + "id_venda SERIAL PRIMARY KEY,"
                    + "cliente INTEGER REFERENCES cliente(id_cliente),"
                    + "produto INTEGER REFERENCES produto(id_produto)"
                    + ");";

            statement.executeUpdate(sql);

            System.out.println("Tabela de vendas criada com sucesso");

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
    
    public static void cadastrarVenda(Scanner scanner) {
        scanner.nextLine();
        System.out.print("Digite o ID do cliente: ");
        int cliente = scanner.nextInt();
        System.out.print("Digite o ID do produto: ");
        int produto = scanner.nextInt();

        try (Connection conn = connection()) {
            try (PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO venda (cliente, produto) VALUES (?, ?)")) {
                preparedStatement.setInt(1, cliente);
                preparedStatement.setInt(2, produto);

                preparedStatement.executeUpdate();
                System.out.println("Venda cadastrada com sucesso!");
            } catch (SQLException e) {
                System.out.println("Erro ao cadastrar venda.");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados.");
            e.printStackTrace();
        }
    }

    public static void listarVendas() {
        try (Connection conn = connection();
             Statement statement = conn.createStatement();
             ResultSet result = statement.executeQuery("SELECT * FROM venda")) {
            while (result.next()) {
                System.out.println("===========================");
                System.out.println("ID da Venda: " + result.getInt("id_venda"));
                System.out.println("ID do Cliente: " + result.getInt("cliente"));
                System.out.println("ID do Produto: " + result.getInt("produto"));
                System.out.println("===========================");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar vendas.");
            e.printStackTrace();
        }
    }
    public static void excluirVenda(Scanner scanner) {
        System.out.print("Digite o ID da venda que deseja excluir: ");
        int idVenda = scanner.nextInt();

        try (Connection conn = connection();
             PreparedStatement preparedStatement = conn.prepareStatement(
                     "DELETE FROM venda WHERE id_venda = ?")) {
            preparedStatement.setInt(1, idVenda);
            preparedStatement.executeUpdate();
            System.out.println("Venda excluída com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao excluir venda.");
            e.printStackTrace();
        }
    }
}


