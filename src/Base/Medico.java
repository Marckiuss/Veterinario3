package Base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un médico con sus datos
 * @author Marc Ferrero Fernández
 */
public class Medico implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nombre;
    private String apellidos;
    private String identificador;
    private String telefono;
    private String horario;
    private List<Perro> perros;

    /**
     * Constructor de la clase
     * @param nombre nombre del médico
     * @param apellidos apellidos del médico
     * @param identificador identificador del médico
     * @param telefono telefono del médico
     * @param horario horario del médico
     */
    public Medico(String nombre, String apellidos, String identificador, String telefono, String horario) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.identificador = identificador;
        this.telefono = telefono;
        this.horario = horario;
        perros = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        identificador = identificador;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        telefono = telefono;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public List<Perro> getPerros() {
        return perros;
    }

    public void setPerros(List<Perro> perros) {
        this.perros = perros;
    }

    /**
     * Añade un Perro a la lista de perros asociados al médico.
     * @param perro perro que se desea añadir
     */
    public void anadirPerro(Perro perro){
        perros.add(perro);
    }

    @Override
    public String toString() {
        String aDevolver = "";
        aDevolver += nombre + " - " + apellidos + " - " + identificador + " - " + telefono + " - " + horario;
        return aDevolver;
    }

}
