public class PedidoExpress extends Pedido implements Despachable, Cancelable, Rastreable {

    public PedidoExpress(int idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, distanciaKm);
    }

    @Override
    public int calcularTiempoEntrega() {

        int tiempo = 10;

        if (getDistanciaKm() > 5) {
            tiempo = tiempo + 5;
        }

        return tiempo;
    }

    @Override
    public void asignarRepartidor() {
        System.out.println("Pedido express: buscando repartidor más cercano...");
        System.out.println("Repartidor disponible encontrado.");
    }

    @Override
    public void asignarRepartidor(String nombre) {
        System.out.println("Pedido express asignado a " + nombre);
        System.out.println("Repartidor con disponibilidad inmediata confirmado.");
    }

    @Override
    public void despachar() {
        System.out.println("Pedido express despachado correctamente.");
    }

    @Override
    public void cancelar() {
        System.out.println("Pedido express cancelado exitosamente.");
    }

    @Override
    public void verHistorial() {
        System.out.println("Historial del Pedido express #" + getIdPedido());
    }
}