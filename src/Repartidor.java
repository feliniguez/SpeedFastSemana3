public class Repartidor implements Runnable {

    private String nombre;
    private ZonaDeCarga zonaDeCarga;

    public Repartidor(String nombre, ZonaDeCarga zonaDeCarga) {
        this.nombre = nombre;
        this.zonaDeCarga = zonaDeCarga;
    }

    @Override
    public void run() {

        while (true) {

            Pedido pedido = zonaDeCarga.retirarPedido();

            if (pedido == null) {
                break;
            }

            pedido.setEstado("EN_REPARTO");

            System.out.println(
                    "[Repartidor - " + nombre + "] Retirando pedido #"
                            + pedido.getIdPedido()
            );

            System.out.println(
                    "[Repartidor - " + nombre + "] Estado: EN_REPARTO"
            );

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            pedido.setEstado("ENTREGADO");

            System.out.println(
                    "[Repartidor - " + nombre + "] Entregando pedido #"
                            + pedido.getIdPedido()
            );

            System.out.println(
                    "[Repartidor - " + nombre + "] Estado: ENTREGADO"
            );
        }
    }
}