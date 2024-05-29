//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import br.unipar.Main.Main;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestJDBC {
    private static final String url = "jdbc:postgresql://localhost:5432/Exemplo1";
    private static final String user = "postgres";
    private static final String password = "admin123";

    public TestJDBC() {
    }

    @BeforeEach
    public void setUp() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Exemplo1", "postgres", "admin123");

            try {
                Statement statement = conn.createStatement();

                try {
                    statement.executeUpdate("DROP TABLE IF EXISTS usuarios");
                    Main.criarTabelaUsuario();
                } catch (Throwable var7) {
                    if (statement != null) {
                        try {
                            statement.close();
                        } catch (Throwable var6) {
                            var7.addSuppressed(var6);
                        }
                    }

                    throw var7;
                }

                if (statement != null) {
                    statement.close();
                }
            } catch (Throwable var8) {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Throwable var5) {
                        var8.addSuppressed(var5);
                    }
                }

                throw var8;
            }

            if (conn != null) {
                conn.close();
            }
        } catch (SQLException var9) {
            var9.printStackTrace();
        }

    }

    @Test
    public void testInserirUsuario() {
        String userInput = "testeUser\ntesteSenha\ntesteNome\n2000-01-01\n";
        this.simulateUserInput(userInput);
        Main.inserirUsuario(new Scanner(System.in));

        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Exemplo1", "postgres", "admin123");

            try {
                Statement statement = conn.createStatement();

                try {
                    ResultSet rs = statement.executeQuery("SELECT * FROM usuarios WHERE username = 'testeUser'");

                    try {
                        Assertions.assertTrue(rs.next(), "Usuário não foi inserido corretamente no banco de dados.");
                        Assertions.assertEquals("testeUser", rs.getString("username"));
                        Assertions.assertEquals("testeNome", rs.getString("nome"));
                    } catch (Throwable var10) {
                        if (rs != null) {
                            try {
                                rs.close();
                            } catch (Throwable var9) {
                                var10.addSuppressed(var9);
                            }
                        }

                        throw var10;
                    }

                    if (rs != null) {
                        rs.close();
                    }
                } catch (Throwable var11) {
                    if (statement != null) {
                        try {
                            statement.close();
                        } catch (Throwable var8) {
                            var11.addSuppressed(var8);
                        }
                    }

                    throw var11;
                }

                if (statement != null) {
                    statement.close();
                }
            } catch (Throwable var12) {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Throwable var7) {
                        var12.addSuppressed(var7);
                    }
                }

                throw var12;
            }

            if (conn != null) {
                conn.close();
            }
        } catch (SQLException var13) {
            Assertions.fail("Exceção ao testar inserirUsuario: " + var13.getMessage());
        }

    }

    @Test
    public void testAtualizarUsuario() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Exemplo1", "postgres", "admin123");

            try {
                PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO usuarios (username, password, nome, nascimento) VALUES (?, ?, ?, ?)");

                try {
                    preparedStatement.setString(1, "userToUpdate");
                    preparedStatement.setString(2, "senha");
                    preparedStatement.setString(3, "nomeAntigo");
                    preparedStatement.setDate(4, Date.valueOf("1990-01-01"));
                    preparedStatement.executeUpdate();
                } catch (Throwable var16) {
                    if (preparedStatement != null) {
                        try {
                            preparedStatement.close();
                        } catch (Throwable var11) {
                            var16.addSuppressed(var11);
                        }
                    }

                    throw var16;
                }

                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (Throwable var17) {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Throwable var10) {
                        var17.addSuppressed(var10);
                    }
                }

                throw var17;
            }

            if (conn != null) {
                conn.close();
            }
        } catch (SQLException var18) {
            var18.printStackTrace();
        }

        String userInput = "userToUpdate\nnovaSenha\nnovoNome\n2000-01-01\n";
        this.simulateUserInput(userInput);
        Main.atualizarUsuario(new Scanner(System.in));

        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Exemplo1", "postgres", "admin123");

            try {
                Statement statement = conn.createStatement();

                try {
                    ResultSet rs = statement.executeQuery("SELECT * FROM usuarios WHERE username = 'userToUpdate'");

                    try {
                        Assertions.assertTrue(rs.next(), "Usuário não foi atualizado corretamente no banco de dados.");
                        Assertions.assertEquals("novaSenha", rs.getString("password"));
                        Assertions.assertEquals("novoNome", rs.getString("nome"));
                    } catch (Throwable var12) {
                        if (rs != null) {
                            try {
                                rs.close();
                            } catch (Throwable var9) {
                                var12.addSuppressed(var9);
                            }
                        }

                        throw var12;
                    }

                    if (rs != null) {
                        rs.close();
                    }
                } catch (Throwable var13) {
                    if (statement != null) {
                        try {
                            statement.close();
                        } catch (Throwable var8) {
                            var13.addSuppressed(var8);
                        }
                    }

                    throw var13;
                }

                if (statement != null) {
                    statement.close();
                }
            } catch (Throwable var14) {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Throwable var7) {
                        var14.addSuppressed(var7);
                    }
                }

                throw var14;
            }

            if (conn != null) {
                conn.close();
            }
        } catch (SQLException var15) {
            Assertions.fail("Exceção ao testar atualizarUsuario: " + var15.getMessage());
        }

    }

    @Test
    public void testListarTodosUsuarios() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Exemplo1", "postgres", "admin123");

            try {
                Statement statement = conn.createStatement();

                try {
                    statement.executeUpdate("INSERT INTO usuarios (username, password, nome, nascimento) VALUES ('user1', 'senha1', 'Nome1', '1990-01-01')");
                    statement.executeUpdate("INSERT INTO usuarios (username, password, nome, nascimento) VALUES ('user2', 'senha2', 'Nome2', '1990-01-02')");
                } catch (Throwable var7) {
                    if (statement != null) {
                        try {
                            statement.close();
                        } catch (Throwable var6) {
                            var7.addSuppressed(var6);
                        }
                    }

                    throw var7;
                }

                if (statement != null) {
                    statement.close();
                }
            } catch (Throwable var8) {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Throwable var5) {
                        var8.addSuppressed(var5);
                    }
                }

                throw var8;
            }

            if (conn != null) {
                conn.close();
            }
        } catch (SQLException var9) {
            var9.printStackTrace();
        }

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Main.listarTodosUsuarios();
        String expectedOutput = "===========================\nCódigo do Usuário: 1\nUsuário: user1\nNome: Nome1\nNascimento: 1990-01-01\n===========================\n===========================\nCódigo do Usuário: 2\nUsuário: user2\nNome: Nome2\nNascimento: 1990-01-02\n===========================\n";
        Assertions.assertEquals(expectedOutput, outContent.toString(), "A listagem de usuários está incorreta.");
    }

    @Test
    public void testExcluirUsuario() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Exemplo1", "postgres", "admin123");

            try {
                PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO usuarios (username, password, nome, nascimento) VALUES (?, ?, ?, ?)");

                try {
                    preparedStatement.setString(1, "userToDelete");
                    preparedStatement.setString(2, "senha");
                    preparedStatement.setString(3, "Nome");
                    preparedStatement.setDate(4, Date.valueOf("1990-01-01"));
                    preparedStatement.executeUpdate();
                } catch (Throwable var16) {
                    if (preparedStatement != null) {
                        try {
                            preparedStatement.close();
                        } catch (Throwable var11) {
                            var16.addSuppressed(var11);
                        }
                    }

                    throw var16;
                }

                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (Throwable var17) {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Throwable var10) {
                        var17.addSuppressed(var10);
                    }
                }

                throw var17;
            }

            if (conn != null) {
                conn.close();
            }
        } catch (SQLException var18) {
            var18.printStackTrace();
        }

        String userInput = "1\n";
        this.simulateUserInput(userInput);
        Main.excluirUsuario(new Scanner(System.in));

        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Exemplo1", "postgres", "admin123");

            try {
                Statement statement = conn.createStatement();

                try {
                    ResultSet rs = statement.executeQuery("SELECT * FROM usuarios");

                    try {
                        Assertions.assertFalse(rs.next(), "Usuário não foi excluído corretamente do banco de dados.");
                    } catch (Throwable var12) {
                        if (rs != null) {
                            try {
                                rs.close();
                            } catch (Throwable var9) {
                                var12.addSuppressed(var9);
                            }
                        }

                        throw var12;
                    }

                    if (rs != null) {
                        rs.close();
                    }
                } catch (Throwable var13) {
                    if (statement != null) {
                        try {
                            statement.close();
                        } catch (Throwable var8) {
                            var13.addSuppressed(var8);
                        }
                    }

                    throw var13;
                }

                if (statement != null) {
                    statement.close();
                }
            } catch (Throwable var14) {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Throwable var7) {
                        var14.addSuppressed(var7);
                    }
                }

                throw var14;
            }

            if (conn != null) {
                conn.close();
            }
        } catch (SQLException var15) {
            Assertions.fail("Exceção ao testar excluirUsuario: " + var15.getMessage());
        }

    }

    private void simulateUserInput(@NotNull String input) {
        if (input == null) {
            $$$reportNull$$$0(0);
        }

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
    }

    private void $$$reportNull$$$0(int i) {
    }
}
