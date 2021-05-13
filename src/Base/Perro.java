package Base;

import javax.swing.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un perro con sus datos.
 *
 * @author Marc Ferrero Fernández
 */
public class Perro implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nombre;
    private int edad;
    private double peso;
    private LocalDate fechaIngreso;
    private Icon fotoPerro;
    private List<Cliente> duenos;
    private Medico medico;

    /**
     * Constructor de la clase
     * @param nombre nombre del perro
     * @param edad edad del perro
     * @param peso peso del perro
     * @param fechaIngreso fecha de ingreso del perro
     * @param fotoPerro foto del perro
     * @param duenos lista de dueños del perro
     */
    public Perro(String nombre, int edad, double peso, LocalDate fechaIngreso, Icon fotoPerro, List<Cliente> duenos) {
        super();
        this.nombre = nombre;
        this.edad = edad;
        this.peso = peso;
        this.fechaIngreso = fechaIngreso;
        this.duenos = new ArrayList(duenos);
        this.fotoPerro = fotoPerro;
    }

    //MÉTODOS GETTER Y SETTER
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public List<Cliente> getDuenos() {
        return duenos;
    }

    public Icon getFotoPerro() {
        return fotoPerro;
    }

    public void setFotoPerro(Icon fotoPerro) {
        this.fotoPerro = fotoPerro;
    }

    public Medico getMedico() {
        return medico;
    }

    @Override
    public String toString() {
        String aDevolver = "";
        if (getDuenos() == null || getDuenos().size() < 1) {
            aDevolver += nombre + " - " + edad + " años" + " | perro sin dueño";
        } else {
            aDevolver += nombre + " - " + edad + " años" + " | " + duenos;
        }
        return aDevolver;
    }

    /**
     * Asocia un médico al perro actual. Si ya tenía uno asociado, lo reemplaza
     * Param medico: Médico que desea asociar al perro.
     */
    public void setMedico(Medico medico) {
        //Si antes ya tenia profesor, lo elimino de su lista
        if (this.medico != null) {
            this.medico.getPerros().remove(this);
        }
        this.medico = medico;
        //Tambien annado este alumno a la lista de alumnos de su profesor
        if (medico != null) {
            medico.getPerros().add(this);
        }
    }

    /**
     * Actualiza la lista de dueños del perro actual
     *
     * @param duenos lista de tipo cliente con los clientes a introducir como dueños.
     */
    public void setDuenos(List<Cliente> duenos) {

        if (duenos != null) {
            for (Cliente dueno : duenos) {
                dueno.getPerros().remove(this);
                dueno.getPerros().add(this);
            }
        }
    }

    /**
     * Añade un único dueño a la lista de dueños del perro actual
     *
     * @param cliente que se desea añadir a la lista de dueños.
     */
    public void addDueno(Cliente cliente){
        this.duenos.add(cliente);
    }

    /**
     * Elimina un cliente de la lista de dueños
     *
     * @param dueno Cliente que desea ser eliminado de la lista de dueños.
     */
    public void eliminarDueno(Cliente dueno){
        duenos.remove(dueno);
    }
}
