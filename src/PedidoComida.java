public class PedidoComida extends Pedido implements Despachable, Cancelable, Rastreable {

    public PedidoComida(int idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, distanciaKm);
    }

    @Override
    public int calcularTiempoEntrega() {
        return (int) (15 + (2 * getDistanciaKm()));
    }

    @Override
    public void asignarRepartidor() {
        System.out.println("Pedido de comida: verificando mochila térmica...");
        System.out.println("Mochila térmica OK.");
    }

    @Override
    public void asignarRepartidor(String nombre) {
        System.out.println("Pedido de comida asignado a " + nombre);
        System.out.println("Verificando mochila térmica... OK");
    }

    @Override
    public void despachar() {
        System.out.println("Pedido de comida despachado correctamente.");
    }

    @Override
    public void cancelar() {
        System.out.println("Pedido de comida cancelado exitosamente.");
    }

    @Override
    public void verHistorial() {
        System.out.println("Historial del Pedido de comida #" + getIdPedido());
    }
}