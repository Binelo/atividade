import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
    private static ConexaoBD instance;
    private Connection connection;

    private ConexaoBD() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/seubanco";
        String username = "seuusuario";
        String password = "suasenha";
        this.connection = DriverManager.getConnection(url, username, password);
    }

    public static ConexaoBD getInstance() throws SQLException {
        if (instance == null) {
            instance = new ConexaoBD();
        } else if (instance.getConnection().isClosed()) {
            instance = new ConexaoBD();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
