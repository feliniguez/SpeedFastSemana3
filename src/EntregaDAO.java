import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntregaDAO {

    public void create(int idPedido, int idRepartidor,
                       Date fecha, Time hora) {

        String sql = "INSERT INTO entregas " +
                "(id_pedido, id_repartidor, fecha, hora) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPedido);
            ps.setInt(2, idRepartidor);
            ps.setDate(3, fecha);
            ps.setTime(4, hora);

            ps.executeUpdate();

            System.out.println("Entrega registrada correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al registrar entrega: " + e.getMessage());
        }
    }

    public List<String> readAll() {

        List<String> entregas = new ArrayList<>();

        String sql = "SELECT id, id_pedido, id_repartidor, fecha, hora " +
                "FROM entregas";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                String entrega =
                        rs.getInt("id") + " - Pedido: " +
                                rs.getInt("id_pedido") +
                                " - Repartidor: " +
                                rs.getInt("id_repartidor") +
                                " - Fecha: " +
                                rs.getDate("fecha") +
                                " - Hora: " +
                                rs.getTime("hora");

                entregas.add(entrega);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar entregas: " + e.getMessage());
        }

        return entregas;
    }

    public void update(int id, int idPedido, int idRepartidor,
                       Date fecha, Time hora) {

        String sql = "UPDATE entregas SET " +
                "id_pedido = ?, id_repartidor = ?, fecha = ?, hora = ? " +
                "WHERE id = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPedido);
            ps.setInt(2, idRepartidor);
            ps.setDate(3, fecha);
            ps.setTime(4, hora);
            ps.setInt(5, id);

            ps.executeUpdate();

            System.out.println("Entrega actualizada correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al actualizar entrega: " + e.getMessage());
        }
    }

    public void delete(int id) {

        String sql = "DELETE FROM entregas WHERE id = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.executeUpdate();

            System.out.println("Entrega eliminada correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al eliminar entrega: " + e.getMessage());
        }
    }
}