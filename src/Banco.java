import java.sql.*;
import java.util.ArrayList;

public class Banco {
    public ContaBancaria cadastrarConta(String titular){
        String sql = "INSERT INTO conta(titular, saldo) VALUES (?, 0.00) RETURNING id";
        try(Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, titular);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                int idGerado = rs.getInt(1);
                return new ContaBancaria(titular, idGerado, 0.00);
            }
            }catch(SQLException e){
            System.err.println("Erro ao cadastrar conta no banco: "+e.getMessage());
        }
        return null;
    }
    public ContaBancaria buscarConta(int numeroConta){
        String sql = "SELECT * FROM conta WHERE id = ?";
        try(Connection conn = ConexaoBanco.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, numeroConta);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                String titular = rs.getString("titular");
                double saldo = rs.getDouble("saldo");
                return new ContaBancaria(titular, numeroConta, saldo);
            }
        }catch(SQLException e){
            System.err.println("Erro ao buscar conta: "+e.getMessage());
        }
        return null;
    }

    public ContaBancaria removerConta(int numeroConta){
        ContaBancaria conta = buscarConta(numeroConta);
        if(conta == null){
            return null;
        }
        String sql = "DELETE FROM conta WHERE id = ?";
        try(Connection conn = ConexaoBanco.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, numeroConta);
            stmt.executeUpdate();
            return conta;
        }catch(SQLException e){
            System.err.println("Erro ao remover conta: "+e.getMessage());
            return null;
        }

    }

    public static ArrayList<ContaBancaria> carregarContas(){
        ArrayList<ContaBancaria> contas = new ArrayList<>();
        String sql = "SELECT * FROM conta";

        try(Connection conn = ConexaoBanco.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()){

            while(rs.next()){
                int id = rs.getInt("id");
                String titular = rs.getString("titular");
                double saldo = rs.getDouble("saldo");

                contas.add(new ContaBancaria(titular, id, saldo));
            }
        }catch(SQLException e){
            System.err.println("Erro ao carregar contas: "+e.getMessage());
        }
        return contas;
    }
    public static ContaBancaria cadastrarNovaConta(String titular) {
        String sql = "INSERT INTO conta (titular, saldo) VALUES (?, ?) RETURNING id";
        ContaBancaria novaConta = null;

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, titular);
            stmt.setDouble(2, 0.0); // Saldo inicial 0

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int idGerado = rs.getInt("id");
                novaConta = new ContaBancaria(titular, idGerado, 0.0);
                System.out.println("Conta criada com sucesso! O número da sua conta é: " + idGerado);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar conta no banco: " + e.getMessage());
        }

        return novaConta;
    }



}
