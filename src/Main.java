import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main() {

        setTitle("SpeedFast - Sistema de Gestión");

        setSize(500, 300);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        JPanel panel = new JPanel(
                new GridLayout(4, 1, 10, 10)
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(25, 50, 25, 50)
        );

        JLabel titulo = new JLabel(
                "SPEEDFAST",
                SwingConstants.CENTER
        );

        titulo.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        JButton btnRepartidores =
                new JButton("Gestionar Repartidores");

        JButton btnPedidos =
                new JButton("Gestionar Pedidos");

        JButton btnEntregas =
                new JButton("Gestionar Entregas");

        panel.add(titulo);
        panel.add(btnRepartidores);
        panel.add(btnPedidos);
        panel.add(btnEntregas);

        add(panel);

        // Abrir ventana de repartidores
        btnRepartidores.addActionListener(e -> {

            VentanaRepartidores ventana =
                    new VentanaRepartidores();

            ventana.setVisible(true);
        });

        // Abrir ventana de pedidos
        btnPedidos.addActionListener(e -> {

            VentanaPedidos ventana =
                    new VentanaPedidos();

            ventana.setVisible(true);
        });

        // Abrir ventana de entregas
        btnEntregas.addActionListener(e -> {

            VentanaEntregas ventana =
                    new VentanaEntregas();

            ventana.setVisible(true);
        });
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            Main ventana =
                    new Main();

            ventana.setVisible(true);
        });
    }
}