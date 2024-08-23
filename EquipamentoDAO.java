import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipamentoDAO {
    private Connection connection;

    public EquipamentoDAO() throws SQLException {
        this.connection = ConexaoBD.getInstance().getConnection();
    }

    public void inserirEquipamento(Equipamento equipamento) throws SQLException {
        String sql = "INSERT INTO Equipamento (Nome_Equipamento, Tipo) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, equipamento.getNomeEquipamento());
            stmt.setString(2, equipamento.getTipo());
            stmt.executeUpdate();
        }
    }

    public void atualizarEquipamento(Equipamento equipamento) throws SQLException {
        String sql = "UPDATE Equipamento SET Nome_Equipamento = ?, Tipo = ? WHERE ID_Equipamento = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, equipamento.getNomeEquipamento());
            stmt.setString(2, equipamento.getTipo());
            stmt.setInt(3, equipamento.getIdEquipamento());
            stmt.executeUpdate();
        }
    }

    public void excluirEquipamento(int idEquipamento) throws SQLException {
        String sql = "DELETE FROM Equipamento WHERE ID_Equipamento = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idEquipamento);
            stmt.executeUpdate();
        }
    }

    public List<Equipamento> listarEquipamentos() throws SQLException {
        String sql = "SELECT * FROM Equipamento";
        List<Equipamento> equipamentos = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Equipamento equipamento = new Equipamento(
                    rs.getInt("ID_Equipamento"),
                    rs.getString("Nome_Equipamento"),
                    rs.getString("Tipo")
                );
                equipamentos.add(equipamento);
            }
        }
        return equipamentos;
    }
}
