import java.util.Random;
import java.util.Stack;
import java.util.concurrent.Semaphore;

/**
 * <p>Clase Repartidor que representa a un repartidor que entrega pedidos en el restaurante.</p>
 * <p>Cada repartidor tiene un nombre, un ID único y una pila de pedidos que debe entregar.</p>
 * <p>Esta clase implementa la interfaz {@link Runnable} para simular la entrega concurrente de pedidos.</p>
 * 
 * 
 * <h2>Ejemplo de uso:</h2>
 * @code
 * Semaphore semaforo = new Semaphore(1);
 * Repartidor repartidor = new Repartidor("Repartidor1", 1, semaforo);
 * Pedido pedido = new Pedido("Juan Perez", "Desayuno", "Café", "Vainilla");
 * repartidor.agregarPedido(pedido);
 * new Thread(repartidor).start();
 * @endcode
 * 
 * <h2>Posibles errores:</h2>
 * <ul>
 *   <li>No manejar correctamente las excepciones al trabajar con los hilos.</li>
 *   <li>No sincronizar adecuadamente el acceso a la pila de pedidos.</li>
 * </ul>
 * 
 * @author Cruz Bautista Mauricio Raciel 3SB
 * @author Enriquez Rodriguez Alejandro Guillermo 3SB
 * @see Runnable
 * @see Pedido
 */
public class Repartidor implements Runnable {
    private String nombre;
    private int idRepartidor;
    private Stack<Pedido> pedidos;
    private boolean estado;
    private Semaphore semaforo;

    /**
     * <p>Constructor de la clase Repartidor.</p>
     * <p>Crea una nueva instancia de la clase Repartidor con el nombre, 
     * el ID y el semáforo especificado. Inicializa la pila de pedidos 
     * del repartidor (vacio).</p>
     * 
     * @param nombre Nombre del repartidor.
     * @param idRepartidor ID del repartidor.
     * @param semaforo Semaforo para controlar el acceso concurrente.
     */
    public Repartidor(String nombre, int idRepartidor, Semaphore semaforo) {
        this.nombre = nombre;
        this.idRepartidor = idRepartidor;
        this.pedidos = new Stack<>();
        this.estado = false;
        this.semaforo = semaforo;
    }

    /**
     * <p>Método que ejecuta el hilo del repartidor.</p>
     * 
     * <p>El repartidor toma un pedido de la pila, espera un tiempo 
     * aleatorio que simula el tiempo de entrega y luego "entrega" 
     * el pedido, es decir, lo elimina de la pila.</p>
     */
    @Override
    public void run() {
        entregarPedido();
    }

    /**
     * <p>Método sincronizado para entregar los pedidos.</p>
     * 
     * <p>Adquiere un permiso del semáforo, simula la entrega de 
     * pedidos con un tiempo de espera aleatorio y libera el 
     * permiso del semáforo al finalizar.</p>
     */
    public synchronized void entregarPedido() {
        try {
            semaforo.acquire();
            while (!pedidos.isEmpty()) {
                try {
                    Random random = new Random();
                    Thread.sleep(1000);
                    //Thread.sleep(5000 + random.nextInt(25000));

                    Pedido pedidoTemporal = pedidos.pop();
                    pedidoTemporal.setFechaEntrega();
                    System.out.println(this.nombre + " entrego el " + pedidoTemporal);

                    estado = !pedidos.isEmpty();
                } catch (Exception e) {
                    // e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            // e.printStackTrace();
        } finally {
            semaforo.release();
        }
    }

    /**
     * <p>Metodo sincronizado que verifica si el repartidor tiene pedidos.</p>
     *
     * @return true si tiene pedidos, y false si no los tiene.
     */
    public synchronized boolean tienePedido() {
        return !pedidos.isEmpty();
    }

    /**
     * <p>Método sincronizado para agregar un pedido a la pila de pedidos del repartidor.</p>
     * <p>Si la pila tiene menos de 3 pedidos, se agrega el nuevo pedido. 
     * Si la pila alcanza los 3 pedidos, se inicia el hilo del repartidor 
     * para que comience a entregar los pedidos.</p>
     *
     * @param pedido Pedido a agregar a la pila del repartidor.
     */
    public synchronized void agregarPedido(Pedido pedido) {
        if (pedidos.size() < 3) {
            pedidos.push(pedido);
            estado = true;
            if (pedidos.size() == 3) {
                new Thread(this).start();
            }
        }
    }

    /**
     * <p>Metodo para obtener el nombre del repartidor.</p>
     *
     * @return Nombre del repartidor.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * <p>Metodo para obtener el ID del repartidor.</p>
     *
     * @return ID del repartidor.
     */
    public int getIdRepartidor() {
        return idRepartidor;
    }

    /**
     * <p>Metodo para obtener el estado del repartidor.</p>
     * <p>Indica si el repartidor está ocupado entregando pedidos o no.</p>
     * @return true si el repartidor está ocupado, false en caso contrario.
     */
    public boolean getEstado() {
        return estado;
    }

    /**
     * <p>Metodo sincronizado para obtener la pila de pedidos del repartidor.</p>
     *
     * @return Pila de pedidos.
     */
    public synchronized Stack<Pedido> getPedidos() {
        return pedidos;
    }
}