import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OperarioDAO {
    private Connection connection;

    public OperarioDAO() throws SQLException {
        this.connection = ConexaoBD.getInstance().getConnection();
    }

    public void inserirOperario(Operario operario) throws SQLException {
        String sql = "INSERT INTO Operario (Nome_Operario, Funcao) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, operario.getNomeOperario());
            stmt.setString(2, operario.getFuncao());
            stmt.executeUpdate();
        }
    }

    public void atualizarOperario(Operario operario) throws SQLException {
        String sql = "UPDATE Operario SET Nome_Operario = ?, Funcao = ? WHERE ID_Operario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, operario.getNomeOperario());
            stmt.setString(2, operario.getFuncao());
            stmt.setInt(3, operario.getIdOperario());
            stmt.executeUpdate();
        }
    }

    public void excluirOperario(int idOperario) throws SQLException {
        String sql = "DELETE FROM Operario WHERE ID_Operario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idOperario);
            stmt.executeUpdate();
        }
    }

    public List<Operario> listarOperarios() throws SQLException {
        String sql = "SELECT * FROM Operario";
        List<Operario> operarios = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Operario operario = new Operario(
                    rs.getInt("ID_Operario"),
                    rs.getString("Nome_Operario"),
                    rs.getString("Funcao")
                );
                operarios.add(operario);
            }
        }
        return operarios;
    }
}
