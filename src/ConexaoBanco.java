import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {
    private static final String URL = "jdbc:postgresql://localhost:5432/sistema_bancario";
    private static final String USUARIO = "postgres";

    private static final String SENHA = System.getenv("DB_PASSWORD") != null ? System.getenv("DB_PASSWORD") : "postgres";

    public static Connection conectar(){
        try{
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        }catch(SQLException e){
            System.out.println("Erro ao conectar com o banco de dados: "+e.getMessage());
            return null;
        }
    }


}
