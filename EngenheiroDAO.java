import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EngenheiroDAO {
    private Connection connection;

    public EngenheiroDAO() throws SQLException {
        this.connection = ConexaoBD.getInstance().getConnection();
    }

    public void inserirEngenheiro(Engenheiro engenheiro) throws SQLException {
        String sql = "INSERT INTO Engenheiro (Nome_Engenheiro, Especialidade) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, engenheiro.getNomeEngenheiro());
            stmt.setString(2, engenheiro.getEspecialidade());
            stmt.executeUpdate();
        }
    }

    public void atualizarEngenheiro(Engenheiro engenheiro) throws SQLException {
        String sql = "UPDATE Engenheiro SET Nome_Engenheiro = ?, Especialidade = ? WHERE ID_Engenheiro = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, engenheiro.getNomeEngenheiro());
            stmt.setString(2, engenheiro.getEspecialidade());
            stmt.setInt(3, engenheiro.getIdEngenheiro());
            stmt.executeUpdate();
        }
    }

    public void excluirEngenheiro(int idEngenheiro) throws SQLException {
        String sql = "DELETE FROM Engenheiro WHERE ID_Engenheiro = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idEngenheiro);
            stmt.executeUpdate();
        }
    }

    public List<Engenheiro> listarEngenheiros() throws SQLException {
        String sql = "SELECT * FROM Engenheiro";
        List<Engenheiro> engenheiros = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Engenheiro engenheiro = new Engenheiro(
                    rs.getInt("ID_Engenheiro"),
                    rs.getString("Nome_Engenheiro"),
                    rs.getString("Especialidade")
                );
                engenheiros.add(engenheiro);
            }
        }
        return engenheiros;
    }
}
