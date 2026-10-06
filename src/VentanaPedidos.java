import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaPedidos extends JFrame {

    private JTextField txtId;
    private JTextField txtDireccion;

    private JComboBox<String> comboTipo;
    private JComboBox<String> comboEstado;

    private JTable tabla;
    private DefaultTableModel modelo;

    private PedidoDAO dao = new PedidoDAO();

    public VentanaPedidos() {

        setTitle("SpeedFast - Gestión de Pedidos");

        setSize(950, 550);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));

        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        // =====================================
        // PANEL SUPERIOR
        // =====================================

        JPanel panelSuperior = new JPanel(
                new GridLayout(2, 1, 5, 5)
        );

        // =====================================
        // FILA 1 - DATOS
        // =====================================

        JPanel panelDatos = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 10, 5)
        );

        JLabel lblId = new JLabel("ID:");

        txtId = new JTextField(6);

        JLabel lblDireccion = new JLabel("Dirección:");

        txtDireccion = new JTextField(20);

        JLabel lblTipo = new JLabel("Tipo:");

        comboTipo = new JComboBox<>(
                new String[]{
                        "COMIDA",
                        "ENCOMIENDA",
                        "EXPRESS"
                }
        );

        JLabel lblEstado = new JLabel("Estado:");

        comboEstado = new JComboBox<>(
                new String[]{
                        "PENDIENTE",
                        "EN_REPARTO",
                        "ENTREGADO"
                }
        );

        panelDatos.add(lblId);
        panelDatos.add(txtId);

        panelDatos.add(lblDireccion);
        panelDatos.add(txtDireccion);

        panelDatos.add(lblTipo);
        panelDatos.add(comboTipo);

        panelDatos.add(lblEstado);
        panelDatos.add(comboEstado);

        // =====================================
        // FILA 2 - BOTONES
        // =====================================

        JPanel panelBotones = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 15, 5)
        );

        JButton btnAgregar = new JButton("Agregar");

        JButton btnActualizar = new JButton("Actualizar");

        JButton btnEliminar = new JButton("Eliminar");

        JButton btnListar = new JButton("Listar");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnListar);

        panelSuperior.add(panelDatos);
        panelSuperior.add(panelBotones);

        // =====================================
        // TABLA
        // =====================================

        modelo = new DefaultTableModel(
                new Object[]{
                        "ID",
                        "Dirección",
                        "Tipo",
                        "Estado"
                },
                0
        );

        tabla = new JTable(modelo);

        tabla.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(tabla);

        // =====================================
        // AGREGAR COMPONENTES
        // =====================================

        panelPrincipal.add(
                panelSuperior,
                BorderLayout.NORTH
        );

        panelPrincipal.add(
                scrollPane,
                BorderLayout.CENTER
        );

        add(panelPrincipal);

        // =====================================
        // EVENTOS
        // =====================================

        btnAgregar.addActionListener(
                e -> agregarPedido()
        );

        btnActualizar.addActionListener(
                e -> actualizarPedido()
        );

        btnEliminar.addActionListener(
                e -> eliminarPedido()
        );

        btnListar.addActionListener(
                e -> cargarTabla()
        );

        cargarTabla();
    }

    // =====================================
    // AGREGAR PEDIDO
    // =====================================

    private void agregarPedido() {

        try {

            int id = Integer.parseInt(
                    txtId.getText().trim()
            );

            String direccion =
                    txtDireccion.getText().trim();

            String tipo =
                    comboTipo.getSelectedItem().toString();

            String estado =
                    comboEstado.getSelectedItem().toString();

            if (direccion.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe ingresar una dirección.",
                        "Validación",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            Pedido pedido;

            if (tipo.equals("COMIDA")) {

                pedido = new PedidoComida(
                        id,
                        direccion,
                        0
                );

            } else if (tipo.equals("ENCOMIENDA")) {

                pedido = new PedidoEncomienda(
                        id,
                        direccion,
                        0
                );

            } else {

                pedido = new PedidoExpress(
                        id,
                        direccion,
                        0
                );
            }

            pedido.setEstado(estado);

            dao.create(pedido);

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido registrado correctamente."
            );

            limpiarCampos();

            cargarTabla();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "El ID debe ser un número.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =====================================
    // ACTUALIZAR PEDIDO
    // =====================================

    private void actualizarPedido() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un pedido de la tabla.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            int id = Integer.parseInt(
                    modelo.getValueAt(fila, 0).toString()
            );

            String direccion =
                    txtDireccion.getText().trim();

            String tipo =
                    comboTipo.getSelectedItem().toString();

            String estado =
                    comboEstado.getSelectedItem().toString();

            if (direccion.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe ingresar una dirección."
                );

                return;
            }

            Pedido pedido;

            if (tipo.equals("COMIDA")) {

                pedido = new PedidoComida(
                        id,
                        direccion,
                        0
                );

            } else if (tipo.equals("ENCOMIENDA")) {

                pedido = new PedidoEncomienda(
                        id,
                        direccion,
                        0
                );

            } else {

                pedido = new PedidoExpress(
                        id,
                        direccion,
                        0
                );
            }

            pedido.setEstado(estado);

            dao.update(pedido);

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido actualizado correctamente."
            );

            limpiarCampos();

            cargarTabla();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al actualizar el pedido: "
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =====================================
    // ELIMINAR PEDIDO
    // =====================================

    private void eliminarPedido() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un pedido de la tabla.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int id = Integer.parseInt(
                modelo.getValueAt(fila, 0).toString()
        );

        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Desea eliminar el pedido seleccionado?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (respuesta == JOptionPane.YES_OPTION) {

            dao.delete(id);

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido eliminado correctamente."
            );

            cargarTabla();
        }
    }

    // =====================================
    // LISTAR PEDIDOS
    // =====================================

    private void cargarTabla() {

        modelo.setRowCount(0);

        List<Pedido> pedidos = dao.readAll();

        for (Pedido pedido : pedidos) {

            String tipo;

            if (pedido instanceof PedidoComida) {

                tipo = "COMIDA";

            } else if (pedido instanceof PedidoEncomienda) {

                tipo = "ENCOMIENDA";

            } else {

                tipo = "EXPRESS";
            }

            modelo.addRow(
                    new Object[]{
                            pedido.getIdPedido(),
                            pedido.getDireccionEntrega(),
                            tipo,
                            pedido.getEstado()
                    }
            );
        }
    }

    // =====================================
    // LIMPIAR CAMPOS
    // =====================================

    private void limpiarCampos() {

        txtId.setText("");

        txtDireccion.setText("");

        comboTipo.setSelectedIndex(0);

        comboEstado.setSelectedIndex(0);
    }

    // =====================================
    // MAIN
    // =====================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            VentanaPedidos ventana =
                    new VentanaPedidos();

            ventana.setVisible(true);
        });
    }
}