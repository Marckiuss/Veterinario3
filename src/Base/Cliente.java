package Base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un cliente con sus datos.
 *
 * @author Marc Ferrero Fernández
 */
public class Cliente implements Serializable{
    private static final long serialVersionUID = 1L;
    private String dni;
    private String nombre;
    private String apellidos;
    private boolean nuevoCliente;
    private boolean menorEdad;
    private List<Perro> perros;

    /**
     * Constructor de la clase
     * @param nombre nombre del cliente
     * @param apellidos apellidos del cliente
     * @param dni dni del cliente
     * @param nuevo boolean que indica si es un nuevo cliente o no
     * @param menorEdad boolean que indica si el cliente es menor de edad o no
     */
    public Cliente(String nombre, String apellidos, String dni, boolean nuevo, boolean menorEdad) {
        super();
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nuevoCliente = nuevo;
        this.menorEdad = menorEdad;
        perros = new ArrayList<>();
    }

    //MÉTODOS GETTER Y SETTER
    public String getDni() {return dni;}

    public void setDni(String dni) {this.dni = dni;}

    public String getNombre() {return nombre;}

    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getApellidos() {return apellidos;}

    public void setApellidos(String apellidos) {this.apellidos = apellidos;}

    public boolean getNuevo() {return nuevoCliente;}

    public void setNuevo(boolean nuevo) {this.nuevoCliente = nuevo;}

    public boolean getMenorEdad() {return menorEdad;}

    public void setMenorEdad(boolean menorEdad) {this.menorEdad = menorEdad;}

    public List<Perro> getPerros() {return perros;}

    public void setPerros(List<Perro> perros) {this.perros = perros;}

    @Override
    public String toString() {
        return nombre + " " + apellidos + " - " + dni;
    }

    /**
     * Añade un perro a la lista de perros de la aplicación.
     * @param perro: Perro que deseas añadir
     */
    public void anadirPerro(Perro perro){
        perros.add(perro);
    }

    /**
     * Elimina un perro a la lista de perros de la aplicación.
     * @param perro: Perro que deseas eliminar
     */
    public void eliminarPerro(Perro perro){
        perros.remove(perro);
    }



}