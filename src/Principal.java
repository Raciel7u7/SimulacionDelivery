import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * <p>Clase Principal que gestiona la simulación del restaurante.</p>
 * <p>Esta clase se encarga de crear los repartidores, mostrar el menú, 
 * leer los pedidos del usuario, asignar los pedidos a los repartidores 
 * e iniciar los hilos de los repartidores para que comiencen a 
 * entregar los pedidos.</p>
 *
 * <p>La clase utiliza un semáforo para controlar el acceso concurrente 
 * a los recursos compartidos por los repartidores, como la cola de pedidos.</p>
 * 
 * @author Cruz Bautista Mauricio Raciel 3SB
 * @author Enriquez Rodriguez Alejandro Guillermo 3SB
 * @see Repartidor
 * @see Pedido
 */
public class Principal {
    private LinkedList<Repartidor> repartidores;
    private Semaphore semaforo;
    private Scanner sc;

    /**
     * <p>Constructor de la clase Principal.</p>
     * 
     * <p>Inicializa la lista de repartidores, el semáforo y el escáner 
     * para la entrada de datos por consola.</p>
     */
    public Principal() {
        repartidores = new LinkedList<>();
        semaforo = new Semaphore(1);
        sc = new Scanner(System.in);
    }

    /**
     * <p>Crea los repartidores del restaurante.</p>
     * 
     * <p>Crea tres repartidores con nombres "Repartidor1", "Repartidor2" 
     * y "Repartidor3", y los añade a la lista de repartidores.</p>
     */
    private void crearRepartidores() {
        for (int i = 0; i < 3; i++) {
            repartidores.add(new Repartidor("Repartidor" + (i + 1), i, semaforo));
        }
    }

    /**
     * <p>Muestra el menú del restaurante en la consola.</p>
     * 
     * <p>Imprime un menú con las opciones de alimentos, bebidas 
     * y helados disponibles para el usuario.</p>
     */
    private void mostrarMenu() {
        String[][] menu = {
                {"Desayuno", "Omelette", "Huevos rancheros", "Hot cakes", "Pan tostado"},
                {"Comida", "Milanesa de pollo", "Ensalada cesar", "Hamburguesa", "Enchiladas"},
                {"Cena", "Pizza", "Pasta a la Bolognesa", "Lasagna", "Filete de pescado"},
                {"Bebidas", "Refresco", "Jugo de naranja", "Limonada", "Cafe"},
                {"Helado oreo", "Helado napolitano", "Helado triple chocolate", "Helado de vainilla", "Helado de cafe"}
        };

        System.out.println("-----------------------------------------------------------------------------------------------");
        System.out.println("Menu:");
        for (int i = 0; i < 5; i++) {
            for (String[] filas : menu) {
                System.out.format("%25s", filas[i]);
            }
            System.out.println();
        }
        System.out.println("-----------------------------------------------------------------------------------------------");
    }

    /**
     * <p>Lee los pedidos del usuario desde la consola.</p>
     * 
     * <p>Solicita al usuario que ingrese el número de pedidos 
     * y la información de cada pedido (nombre del cliente, 
     * alimento, bebida y helado). Los pedidos se almacenan en 
     * una cola.</p>
     * 
     * <h2>Posibles mejoras:</h2>
     * <ul>
     *  <li>Hacer que el usuario elija libremente qué comer, actualmente tiene que escoger forzosamente un alimento, una bebida y un helado.</li>
     *  <li>Permitir que el usuario pueda elegir la cantidad de comida que desee.</li>
     *  <li>Agregar una interfaz gráfica de usuario (GUI) para mejorar la interacción con el usuario.</li>
     * </ul>
     * 
     * <h2>Errores comunes:</h2>
     * <ul>
     *  <li>No validar correctamente la entrada del usuario, lo que puede llevar a errores en la creación de pedidos.</li>
     *  <li>No manejar excepciones al leer la entrada, lo que podría causar que el programa se "rompa".</li>
     * </ul>
     *  
     * @return Una cola de pedidos.
     */
    private Queue<Pedido> leerPedidos() {
        System.out.println("Número de pedidos:");
        int n = sc.nextInt();
        sc.nextLine();
        Queue<Pedido> ordenes = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            System.out.println("Ingrese el alimento (DESAYUNO, COMIDA, O CENA):");
            String alimento = sc.nextLine();
            System.out.println("Ingrese la bebida:");
            String bebida = sc.nextLine();
            System.out.println("Ingrese el helado:");
            String helado = sc.nextLine();
            System.out.println("A nombre de quién es el pedido:");
            String nombre = sc.nextLine();
            ordenes.add(new Pedido(nombre, alimento, bebida, helado));
        }
        return ordenes;
    }

    /**
     * <p>Asigna los pedidos a los repartidores.</p>
     * <p>Recorre la cola de pedidos y asigna cada pedido a un repartidor. 
     * Cada repartidor tiene una capacidad máxima de 3 pedidos. Si un 
     * repartidor ya tiene 3 pedidos, se asigna el pedido al siguiente 
     * repartidor disponible.</p>
     * 
     * @param ordenes La cola de pedidos a asignar.
     */
    private void asignarPedidos(Queue<Pedido> ordenes) {
        int cont = 0;
        while (!ordenes.isEmpty()) {
            Repartidor repartidor = repartidores.get(cont);
            repartidor.agregarPedido(ordenes.poll());

            if (repartidor.getPedidos().size() == 3) {
                cont = (cont + 1) % repartidores.size();
            }
        }
    }

    /**
     * <p>Inicia los hilos de los repartidores para que comiencen a entregar pedidos.</p>
     * <p>Recorre la lista de repartidores y, para cada repartidor que 
     * tenga pedidos en su pila, inicia un nuevo hilo para que comience 
     * a entregar los pedidos.</p>
     *
     * <h2>Posibles mejoras:</h2>
     * <ul>
     *  <li>No tener que recorrer toda la lista de repartidores para verificar quien tiene o no pedidos</li>
     * </ul>
     */
    private void empezarRepartir() {
        for (Repartidor repartidor : repartidores) {
            if (repartidor.tienePedido()) {
                new Thread(repartidor).start();
            }
        }
    }

    /**
     * <p>Cierra el escáner utilizado para la entrada de datos por consola.</p>
     */
    private void cerrarScanner() {
        sc.close();
    }

    /**
     * <p>Método principal que ejecuta el flujo del programa.</p>
     * <p>Crea una instancia de la clase Principal, crea los repartidores, 
     * muestra el menú, lee los pedidos del usuario, asigna los pedidos 
     * a los repartidores e inicia los hilos de los repartidores.</p>
     */
    public static void main(String[] args) {
        Principal principal = new Principal();
        principal.crearRepartidores();
        principal.mostrarMenu();
        Queue<Pedido> ordenes = principal.leerPedidos();
        principal.asignarPedidos(ordenes);
        principal.empezarRepartir();
        principal.cerrarScanner();
    }
}