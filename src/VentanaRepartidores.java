import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaRepartidores extends JFrame {

    private JTextField txtNombre;
    private JTable tabla;
    private DefaultTableModel modelo;

    private ClienteDAO dao = new ClienteDAO();

    public VentanaRepartidores() {

        setTitle("SpeedFast - Gestión de Repartidores");

        // Dimensiones de la ventana
        setSize(900, 500);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Centrar ventana en la pantalla
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));

        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        // ==========================
        // PANEL SUPERIOR
        // ==========================

        JPanel panelDatos = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JLabel lblNombre = new JLabel("Nombre:");

        txtNombre = new JTextField(25);

        JButton btnAgregar = new JButton("Agregar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnListar = new JButton("Listar");

        panelDatos.add(lblNombre);
        panelDatos.add(txtNombre);
        panelDatos.add(btnAgregar);
        panelDatos.add(btnActualizar);
        panelDatos.add(btnEliminar);
        panelDatos.add(btnListar);

        // ==========================
        // TABLA
        // ==========================

        modelo = new DefaultTableModel(
                new Object[]{"ID", "Nombre"}, 0
        );

        tabla = new JTable(modelo);

        tabla.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(tabla);

        // ==========================
        // AGREGAR COMPONENTES
        // ==========================

        panelPrincipal.add(panelDatos, BorderLayout.NORTH);

        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        add(panelPrincipal);

        // ==========================
        // EVENTOS
        // ==========================

        btnAgregar.addActionListener(e -> agregarRepartidor());

        btnActualizar.addActionListener(e -> actualizarRepartidor());

        btnEliminar.addActionListener(e -> eliminarRepartidor());

        btnListar.addActionListener(e -> cargarTabla());

        // Cargar datos al iniciar
        cargarTabla();
    }

    // ==========================
    // AGREGAR REPARTIDOR
    // ==========================

    private void agregarRepartidor() {

        String nombre = txtNombre.getText().trim();

        if (nombre.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar un nombre.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        boolean guardado = dao.create(nombre);

        if (guardado) {

            JOptionPane.showMessageDialog(
                    this,
                    "Repartidor registrado correctamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
            );

            txtNombre.setText("");

            cargarTabla();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo registrar el repartidor.\n"
                            + "Verifique la conexión con la base de datos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==========================
    // ACTUALIZAR REPARTIDOR
    // ==========================

    private void actualizarRepartidor() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un repartidor de la tabla.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String nombre = txtNombre.getText().trim();

        if (nombre.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar un nombre.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int id = Integer.parseInt(
                modelo.getValueAt(fila, 0).toString()
        );

        boolean actualizado = dao.update(id, nombre);

        if (actualizado) {

            JOptionPane.showMessageDialog(
                    this,
                    "Repartidor actualizado correctamente."
            );

            txtNombre.setText("");

            cargarTabla();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo actualizar el repartidor.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==========================
    // ELIMINAR REPARTIDOR
    // ==========================

    private void eliminarRepartidor() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un repartidor de la tabla.",
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
                "¿Desea eliminar el repartidor seleccionado?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (respuesta == JOptionPane.YES_OPTION) {

            boolean eliminado = dao.delete(id);

            if (eliminado) {

                JOptionPane.showMessageDialog(
                        this,
                        "Repartidor eliminado correctamente."
                );

                cargarTabla();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo eliminar el repartidor.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    // ==========================
    // LISTAR REPARTIDORES
    // ==========================

    private void cargarTabla() {

        modelo.setRowCount(0);

        List<String> repartidores = dao.readAll();

        for (String repartidor : repartidores) {

            String[] datos = repartidor.split(" - ", 2);

            if (datos.length == 2) {

                modelo.addRow(
                        new Object[]{
                                datos[0],
                                datos[1]
                        }
                );
            }
        }
    }

    // ==========================
    // MAIN
    // ==========================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            VentanaRepartidores ventana =
                    new VentanaRepartidores();

            ventana.setVisible(true);
        });
    }
}