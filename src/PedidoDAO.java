import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    public void create(Pedido pedido) {
        String sql = "INSERT INTO pedidos (id, direccion, tipo, estado) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, pedido.getIdPedido());
            ps.setString(2, pedido.getDireccionEntrega());
            ps.setString(3, obtenerTipo(pedido));
            ps.setString(4, pedido.getEstado());

            ps.executeUpdate();

            System.out.println("Pedido guardado en la BD.");

        } catch (SQLException e) {
            System.out.println("Error al guardar pedido: " + e.getMessage());
        }
    }

    public List<Pedido> readAll() {
        List<Pedido> pedidos = new ArrayList<>();

        String sql = "SELECT id, direccion, tipo, estado FROM pedidos";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                int id = rs.getInt("id");
                String direccion = rs.getString("direccion");
                String tipo = rs.getString("tipo");
                String estado = rs.getString("estado");

                Pedido pedido;

                if ("COMIDA".equals(tipo)) {
                    pedido = new PedidoComida(id, direccion, 0);
                } else if ("ENCOMIENDA".equals(tipo)) {
                    pedido = new PedidoEncomienda(id, direccion, 0);
                } else {
                    pedido = new PedidoExpress(id, direccion, 0);
                }

                pedido.setEstado(estado);
                pedidos.add(pedido);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar pedidos: " + e.getMessage());
        }

        return pedidos;
    }

    public void update(Pedido pedido) {
        String sql = "UPDATE pedidos SET direccion = ?, tipo = ?, estado = ? WHERE id = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pedido.getDireccionEntrega());
            ps.setString(2, obtenerTipo(pedido));
            ps.setString(3, pedido.getEstado());
            ps.setInt(4, pedido.getIdPedido());

            ps.executeUpdate();

            System.out.println("Pedido actualizado.");

        } catch (SQLException e) {
            System.out.println("Error al actualizar pedido: " + e.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM pedidos WHERE id = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

            System.out.println("Pedido eliminado.");

        } catch (SQLException e) {
            System.out.println("Error al eliminar pedido: " + e.getMessage());
        }
    }

    private String obtenerTipo(Pedido pedido) {

        if (pedido instanceof PedidoComida) {
            return "COMIDA";
        }

        if (pedido instanceof PedidoEncomienda) {
            return "ENCOMIENDA";
        }

        return "EXPRESS";
    }
}