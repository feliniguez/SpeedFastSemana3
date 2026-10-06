import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class VentanaEntregas extends JFrame {

    private JTextField txtId;
    private JTextField txtPedido;
    private JTextField txtRepartidor;
    private JTextField txtFecha;
    private JTextField txtHora;

    private JTable tabla;
    private DefaultTableModel modelo;

    private EntregaDAO dao = new EntregaDAO();

    public VentanaEntregas() {

        setTitle("SpeedFast - Gestión de Entregas");
        setSize(1000, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel principal = new JPanel(new BorderLayout(10, 10));

        principal.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        // =========================
        // PANEL SUPERIOR
        // =========================

        JPanel superior = new JPanel(
                new GridLayout(2, 1, 5, 5)
        );

        JPanel datos = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 8, 5)
        );

        JLabel lblId = new JLabel("ID:");
        txtId = new JTextField(5);

        JLabel lblPedido = new JLabel("Pedido:");
        txtPedido = new JTextField(7);

        JLabel lblRepartidor = new JLabel("Repartidor:");
        txtRepartidor = new JTextField(7);

        JLabel lblFecha = new JLabel("Fecha:");
        txtFecha = new JTextField(10);
        txtFecha.setToolTipText("AAAA-MM-DD");

        JLabel lblHora = new JLabel("Hora:");
        txtHora = new JTextField(8);
        txtHora.setToolTipText("HH:MM:SS");

        datos.add(lblId);
        datos.add(txtId);

        datos.add(lblPedido);
        datos.add(txtPedido);

        datos.add(lblRepartidor);
        datos.add(txtRepartidor);

        datos.add(lblFecha);
        datos.add(txtFecha);

        datos.add(lblHora);
        datos.add(txtHora);

        // =========================
        // BOTONES
        // =========================

        JPanel botones = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 15, 5)
        );

        JButton btnAgregar = new JButton("Agregar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnListar = new JButton("Listar");

        botones.add(btnAgregar);
        botones.add(btnActualizar);
        botones.add(btnEliminar);
        botones.add(btnListar);

        superior.add(datos);
        superior.add(botones);

        // =========================
        // TABLA
        // =========================

        modelo = new DefaultTableModel(
                new Object[]{
                        "ID",
                        "Pedido",
                        "Repartidor",
                        "Fecha",
                        "Hora"
                },
                0
        );

        tabla = new JTable(modelo);
        tabla.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(tabla);

        principal.add(superior, BorderLayout.NORTH);
        principal.add(scrollPane, BorderLayout.CENTER);

        add(principal);

        // =========================
        // EVENTOS
        // =========================

        btnAgregar.addActionListener(
                e -> agregarEntrega()
        );

        btnActualizar.addActionListener(
                e -> actualizarEntrega()
        );

        btnEliminar.addActionListener(
                e -> eliminarEntrega()
        );

        btnListar.addActionListener(
                e -> cargarTabla()
        );

        cargarTabla();
    }

    // =========================
    // AGREGAR
    // =========================

    private void agregarEntrega() {

        try {

            int idPedido = Integer.parseInt(
                    txtPedido.getText().trim()
            );

            int idRepartidor = Integer.parseInt(
                    txtRepartidor.getText().trim()
            );

            Date fecha = Date.valueOf(
                    txtFecha.getText().trim()
            );

            Time hora = Time.valueOf(
                    txtHora.getText().trim()
            );

            dao.create(
                    idPedido,
                    idRepartidor,
                    fecha,
                    hora
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Entrega registrada correctamente."
            );

            limpiarCampos();
            cargarTabla();

        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Revise los datos ingresados.\n"
                            + "Fecha: AAAA-MM-DD\n"
                            + "Hora: HH:MM:SS",
                    "Error de validación",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================
    // ACTUALIZAR
    // =========================

    private void actualizarEntrega() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione una entrega."
            );

            return;
        }

        try {

            int id = Integer.parseInt(
                    modelo.getValueAt(fila, 0).toString()
            );

            int idPedido = Integer.parseInt(
                    txtPedido.getText().trim()
            );

            int idRepartidor = Integer.parseInt(
                    txtRepartidor.getText().trim()
            );

            Date fecha = Date.valueOf(
                    txtFecha.getText().trim()
            );

            Time hora = Time.valueOf(
                    txtHora.getText().trim()
            );

            dao.update(
                    id,
                    idPedido,
                    idRepartidor,
                    fecha,
                    hora
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Entrega actualizada correctamente."
            );

            limpiarCampos();
            cargarTabla();

        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Revise los datos ingresados.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================
    // ELIMINAR
    // =========================

    private void eliminarEntrega() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione una entrega."
            );

            return;
        }

        int id = Integer.parseInt(
                modelo.getValueAt(fila, 0).toString()
        );

        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Desea eliminar la entrega?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (respuesta == JOptionPane.YES_OPTION) {

            dao.delete(id);

            JOptionPane.showMessageDialog(
                    this,
                    "Entrega eliminada correctamente."
            );

            cargarTabla();
        }
    }

    // =========================
    // LISTAR
    // =========================

    private void cargarTabla() {

        modelo.setRowCount(0);

        List<String> entregas = dao.readAll();

        for (String entrega : entregas) {

            String[] datos = entrega.split(
                    " - Pedido: | - Repartidor: | - Fecha: | - Hora: "
            );

            if (datos.length == 5) {

                modelo.addRow(
                        new Object[]{
                                datos[0],
                                datos[1],
                                datos[2],
                                datos[3],
                                datos[4]
                        }
                );
            }
        }
    }

    // =========================
    // LIMPIAR
    // =========================

    private void limpiarCampos() {

        txtId.setText("");
        txtPedido.setText("");
        txtRepartidor.setText("");
        txtFecha.setText("");
        txtHora.setText("");
    }

    // =========================
    // MAIN
    // =========================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            VentanaEntregas ventana =
                    new VentanaEntregas();

            ventana.setVisible(true);
        });
    }
}