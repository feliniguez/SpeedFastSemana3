public class PedidoEncomienda extends Pedido implements Despachable, Cancelable, Rastreable {

    public PedidoEncomienda(int idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, distanciaKm);
    }

    @Override
    public int calcularTiempoEntrega() {
        return (int) (20 + (1.5 * getDistanciaKm()));
    }

    @Override
    public void asignarRepartidor() {
        System.out.println("Pedido de encomienda: validando peso y embalaje...");
        System.out.println("Peso y embalaje OK.");
    }

    @Override
    public void asignarRepartidor(String nombre) {
        System.out.println("Pedido de encomienda asignado a " + nombre);
        System.out.println("Validando peso y embalaje... OK");
    }

    @Override
    public void despachar() {
        System.out.println("Pedido de encomienda despachado correctamente.");
    }

    @Override
    public void cancelar() {
        System.out.println("Pedido de encomienda cancelado exitosamente.");
    }

    @Override
    public void verHistorial() {
        System.out.println("Historial del Pedido de encomienda #" + getIdPedido());
    }
}