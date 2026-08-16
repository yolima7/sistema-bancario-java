import java.sql.*;
import java.util.ArrayList;

public class ContaDAO {
        public ContaBancaria buscarPorId(int idConta) {
            String sql = "SELECT * FROM conta WHERE id = ?";

            try (Connection conn = ConexaoBanco.conectar();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, idConta);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String titular = rs.getString("titular");
                    double saldo = rs.getDouble("saldo");
                    return new ContaBancaria(titular, idConta, saldo);
                }
            } catch (SQLException e) {
                System.err.println("Erro ao buscar conta: " + e.getMessage());
            }
            return null;
        }

        // Cadastra uma nova conta no banco e retorna a conta com o ID gerado
        public ContaBancaria cadastrar(String titular) {
            String sql = "INSERT INTO conta (titular, saldo) VALUES (?, ?) RETURNING id";

            try (Connection conn = ConexaoBanco.conectar();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, titular);
                stmt.setDouble(2, 0.0);

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    int idGerado = rs.getInt("id");
                    return new ContaBancaria(titular, idGerado, 0.0);
                }
            } catch (SQLException e) {
                System.err.println("Erro ao cadastrar conta: " + e.getMessage());
            }
            return null;
        }
        public ArrayList<ContaBancaria> listarTodas() {
            ArrayList<ContaBancaria> contas = new ArrayList<>();
            String sql = "SELECT * FROM conta";

            try (Connection conn = ConexaoBanco.conectar();
                 PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String titular = rs.getString("titular");
                    double saldo = rs.getDouble("saldo");
                    contas.add(new ContaBancaria(titular, id, saldo));
                }
            } catch (SQLException e) {
                System.err.println("Erro ao listar contas: " + e.getMessage());
            }
            return contas;
        }
    }

