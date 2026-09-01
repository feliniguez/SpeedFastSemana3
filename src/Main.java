import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        PedidoComida comida = new PedidoComida(
                101,
                "Av. Santa Rosa 567",
                4
        );

        PedidoEncomienda encomienda = new PedidoEncomienda(
                102,
                "Av. Independencia 123",
                6
        );

        PedidoExpress express = new PedidoExpress(
                103,
                "Av. Apoquindo 1500",
                7
        );

        System.out.println("===== PEDIDO COMIDA =====");

        comida.mostrarResumen();
        comida.asignarRepartidor();
        comida.asignarRepartidor("Luis Díaz");

        System.out.println("Tiempo estimado: "
                + comida.calcularTiempoEntrega() + " minutos");

        comida.despachar();

        System.out.println();


        System.out.println("===== PEDIDO ENCOMIENDA =====");

        encomienda.mostrarResumen();
        encomienda.asignarRepartidor();
        encomienda.asignarRepartidor("Daniela Tapia");

        System.out.println("Tiempo estimado: "
                + encomienda.calcularTiempoEntrega() + " minutos");

        encomienda.despachar();

        System.out.println();


        System.out.println("===== PEDIDO EXPRESS =====");

        express.mostrarResumen();
        express.asignarRepartidor();
        express.asignarRepartidor("Luis Díaz");

        System.out.println("Tiempo estimado: "
                + express.calcularTiempoEntrega() + " minutos");

        express.cancelar();

        System.out.println();


        System.out.println("===== HISTORIAL =====");

        ArrayList<Pedido> historial = new ArrayList<>();

        historial.add(comida);
        historial.add(encomienda);
        historial.add(express);

        for (Pedido pedido : historial) {

            System.out.println(
                    "Pedido #" + pedido.getIdPedido()
                            + " - " + pedido.getDireccionEntrega()
            );

        }
    }
}