import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public boolean create(String nombre) {

        String sql = "INSERT INTO repartidores (nombre) VALUES (?)";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.executeUpdate();

            System.out.println("Repartidor guardado.");
            return true;

        } catch (SQLException e) {

            System.out.println("Error al guardar: " + e.getMessage());
            return false;
        }
    }

    public List<String> readAll() {

        List<String> lista = new ArrayList<>();

        String sql = "SELECT id, nombre FROM repartidores";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                lista.add(
                        rs.getInt("id") +
                                " - " +
                                rs.getString("nombre")
                );
            }

        } catch (SQLException e) {

            System.out.println("Error al listar: " + e.getMessage());
        }

        return lista;
    }

    public boolean update(int id, String nombre) {

        String sql = "UPDATE repartidores SET nombre = ? WHERE id = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setInt(2, id);

            ps.executeUpdate();

            System.out.println("Registro actualizado.");
            return true;

        } catch (SQLException e) {

            System.out.println("Error al actualizar: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {

        String sql = "DELETE FROM repartidores WHERE id = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.executeUpdate();

            System.out.println("Registro eliminado.");
            return true;

        } catch (SQLException e) {

            System.out.println("Error al eliminar: " + e.getMessage());
            return false;
        }
    }
}