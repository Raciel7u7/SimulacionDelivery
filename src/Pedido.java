import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>Clase Pedido que representa un pedido de un cliente en el restaurante.<p>
 * 
 * <p>Cada pedido incluye información sobre el cliente, los alimentos, 
 * la bebida y el helado que ha ordenado. Además, se registra la 
 * fecha y hora en que se realizó el pedido y la fecha y hora 
 * en que se entregó.</p>
 * 
 * <p>Esta clase utiliza {@link LocalDateTime} para manejar las fechas 
 * y {@link DateTimeFormatter} para formatearlas.</p>
 * 
 * <h2>Ejemplo de uso:</h2>
 * @code
 * Pedido pedido = new Pedido("Juan Pérez", "Desayuno", "Café", "Vainilla");
 * System.out.println(pedido); 
 * @endcode
 * 
 * <h2>Posibles mejoras:</h2>
 * <ul>
 *   <li>Agregar validaciones para los campos de entrada (nombre, alimento, bebida, helado).</li>
 *   <li>Implementar un sistema de estado más completo para el pedido (pendiente, en proceso, entregado).</li>
 *   <li>Permitir la modificación de los detalles del pedido antes de la entrega.</li>
 * </ul>
 * 
 * 
 * @author Cruz Bautista Mauricio Raciel 3SB
 * @author Enriquez Rodriguez Alejandro Guillermo 3SB
 * @see Repartidor
 */
public class Pedido {
    private String nombre;
    private String alimento;
    private String bebida;
    private String helado;
    private LocalDateTime fechaPedido;
    private LocalDateTime fechaEntrega;

    private static final DateTimeFormatter personalizado = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    /**
     * <p>Constructor de la clase Pedido.</p>
     * <p>Crea una nueva instancia de la clase Pedido con la información 
     * del cliente, el alimento, la bebida y el helado. La fecha 
     * y hora del pedido se establece automáticamente al momento 
     * de crear el objeto.</p>
     * 
     * @param nombre Nombre del cliente.
     * @param alimento Alimento del pedido (Desayuno, comida o cena).
     * @param bebida Bebida del pedido.
     * @param helado Helado del pedido.
     */
    public Pedido(String nombre, String alimento, String bebida, String helado) {
        this.nombre = nombre;
        this.alimento = alimento;
        this.bebida = bebida;
        this.helado = helado;
        this.fechaPedido = LocalDateTime.now();
    }

    /**
     * <p>Establece la fecha y hora de entrega del pedido.</p>
     * 
     * <p>La fecha de entrega se establece a la fecha y hora actual 
     * en el momento en que se llama a este método.</p>
     */

    public void setFechaEntrega() {
        this.fechaEntrega = LocalDateTime.now();
    }

    /**
     * <p>Obtiene la fecha de entrega del pedido.</p>
     *
     * @return Fecha de entrega del pedido.
     */
    public LocalDateTime getFechaEntrega() {
        return fechaEntrega;
    }

    /**
     * <p>Obtiene el nombre del cliente.</p>
     *
     * @return Nombre del cliente.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * <p>Establece el nombre del cliente.</p>
     *
     * @param nombre Nombre del cliente.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * <p>Obtiene el alimento del pedido.</p>
     *
     * @return Alimento del pedido.
     */
    public String getAlimento() {
        return alimento;
    }

    /**
     * <p>Establece el alimento del pedido.</p>
     *
     * @param alimento Alimento del pedido.
     */
    public void setAlimento(String alimento) {
        this.alimento = alimento;
    }

    /**
     * <p>Obtiene la bebida del pedido.</p>
     *
     * @return Bebida del pedido.
     */
    public String getBebida() {
        return bebida;
    }

    /**
     * <p>Establece la bebida del pedido.</p>
     *
     * @param bebida Bebida del pedido.
     */
    public void setBebida(String bebida) {
        this.bebida = bebida;
    }

    /**
     * <p>Obtiene el helado del pedido.</p>
     *
     * @return Helado del pedido.
     */
    public String getHelado() {
        return helado;
    }

    /**
     * <p>Establece el helado del pedido.</p>
     *
     * @param helado Helado del pedido.
     */
    public void setHelado(String helado) {
        this.helado = helado;
    }

    /**
     * <p>Devuelve una representación en cadena del pedido.</p>
     * <p>La cadena incluye el nombre del cliente, el alimento, 
     * la bebida, el helado, la fecha del pedido y, si está 
     * disponible, la fecha en que fué entregado el pedido.</p>
     * 
     * @return Representación en cadena del pedido.
     */
    @Override
    public String toString() {
        return "Pedido a nombre de " + nombre + "\nalimento "
                + alimento + "\nbebida " + bebida + "\nhelado "
                + helado + "\nFecha del pedido " + fechaPedido.format(personalizado)
                + (fechaEntrega != null ? "\nfechaEntrega " + fechaEntrega.format(personalizado) : "") + "\n"; // MOSTRAR LA FECHA DE ENTREGA SI NO ESTA VACIA
    }
}