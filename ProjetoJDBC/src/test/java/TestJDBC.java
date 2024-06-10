import br.unipar.Main.Main;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Scanner;

import static org.junit.Assert.fail;

public class TestJDBC {


    private final InputStream systemIn = System.in;
    private ByteArrayInputStream testIn;

    @Before
    public void setUp() {
        System.setIn(systemIn);
    }

    @After
    public void tearDown() {
        System.setIn(systemIn);
    }

    private void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @Test
    public void testCriarTabelaUsuario() {
        Main.criarTabelaUsuario();
    }

    @Test
    public void testInserirUsuario() {
        provideInput("testuser\ntestpass\nTest User\n1990-01-01\n");
        try {
            Main.inserirUsuario(new Scanner(System.in));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertFalse(Boolean.parseBoolean("Falha ao inserir usuário: " + e.getMessage()));
        }
    }
    @Test
    public void testAtualizarUsuario() {
        try {
            Main.atualizarUsuario(new Scanner(new ByteArrayInputStream("1\ntestuser2\ntestpass2\nTest User 2\n1991-01-01\n".getBytes())));
        } catch (Exception e) {
            fail("Falha ao atualizar usuário: " + e.getMessage());
        }
    }

    @Test
    public void testListarTodosUsuarios() {
        try {
            Main.listarTodosUsuarios();
        } catch (Exception e) {
            fail("Falha ao listar usuários: " + e.getMessage());
        }
    }

    @Test
    public void testExcluirUsuario() {
        try {
            Main.excluirUsuario(new Scanner(new ByteArrayInputStream("1\n".getBytes())));
        } catch (Exception e) {
            fail("Falha ao excluir usuário: " + e.getMessage());
        }
    }

    @Test
    public void testCriarTabelaCliente() {
        Main.criarTabelaCliente();
    }

    @Test
    public void testCadastrarCliente() {
        provideInput("Test Client\n12345678900\n");
        try {
            Main.cadastrarCliente(new Scanner(System.in));
        } catch (Exception e) {
            Assert.assertFalse(Boolean.parseBoolean("Falha ao cadastrar cliente: " + e.getMessage()));
        }
    }

    @Test
    public void testAtualizarCliente() {
        try {
            Main.atualizarCliente(new Scanner(new ByteArrayInputStream("1\nNew Test Client\n98765432100\n".getBytes())));
        } catch (Exception e) {
            fail("Falha ao atualizar cliente: " + e.getMessage());
        }
    }

    @Test
    public void testListarTodosClientes() {
        try {
            Main.listarTodosClientes();
        } catch (Exception e) {
            fail("Falha ao listar clientes: " + e.getMessage());
        }
    }

    @Test
    public void testExcluirCliente() {
        try {
            Main.excluirCliente(new Scanner(new ByteArrayInputStream("1\n".getBytes())));
        } catch (Exception e) {
            fail("Falha ao excluir cliente: " + e.getMessage());
        }
    }

    @Test
    public void testCriarTabelaProduto() {
        Main.criarTabelaProduto();
    }

    @Test
    public void testCadastrarProduto() {
        try {
            Main.cadastrarProduto("Test Product", new BigDecimal("10.99"));
        } catch (Exception e) {
            fail("Falha ao cadastrar produto: " + e.getMessage());
        }
    }

    @Test
    public void testAtualizarProduto() {
        try {
            Main.atualizarProduto(new Scanner(new ByteArrayInputStream("1\nNew Test Product\n9.99\n".getBytes())));
        } catch (Exception e) {
            Assert.assertFalse(Boolean.parseBoolean("Falha ao atualizar produto: " + e.getMessage()));
        }
    }

    @Test
    public void testListarTodosProdutos() {
        try {
            Main.listarTodosProdutos();
        } catch (Exception e) {
            fail("Falha ao listar produtos: " + e.getMessage());
        }
    }

    @Test
    public void testExcluirProduto() {
        try {
            Main.excluirProduto(new Scanner(new ByteArrayInputStream("1\n".getBytes())));
        } catch (Exception e) {
            fail("Falha ao excluir produto: " + e.getMessage());
        }
    }

    @Test
    public void testCriarTabelaVenda() {
        Main.criarTabelaVenda();
    }

    @Test
    public void testCadastrarVenda() {
        try {
            Main.cadastrarVenda(new Scanner(new ByteArrayInputStream("1\n1\n".getBytes())));
        } catch (Exception e) {
            Assert.assertFalse(Boolean.parseBoolean("Falha ao cadastrar venda: " + e.getMessage()));
        }
    }

    @Test
    public void testListarVendas() {
        try {
            Main.listarVendas();
        } catch (Exception e) {
            fail("Falha ao listar vendas: " + e.getMessage());
        }
    }

    @Test
    public void testExcluirVenda() {
        try {
            Main.excluirVenda(new Scanner(new ByteArrayInputStream("1\n".getBytes())));
        } catch (Exception e) {
            fail("Falha ao excluir venda: " + e.getMessage());
        }
    }
}
