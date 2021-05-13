package GUI;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import javax.swing.*;

import Base.Medico;
import Base.Perro;
import Base.Cliente;

/**
 * Contiene las listas principales de la aplicación y los métodos para gestionarlas.
 * @author Marc Ferrero Fernández
 */
public class Modelo {
    private List<Cliente> clientes;
    private List<Perro> perros;
    private List<Medico> medicos;
    private List<Cliente> duenosProvisionales;
    private ResourceBundle resourceBundle;
    private boolean datosModificados;

    /**
     * Constructor de la clase
     */
    public Modelo() {
        clientes = new ArrayList<>();
        perros = new ArrayList<>();
        medicos = new ArrayList<>();
        duenosProvisionales = new ArrayList<>();
        resourceBundle = ResourceBundle.getBundle("idiomaResourceBundle", Locale.getDefault());
        datosModificados = false;

    }

    // MÉTODOS GETTER PARA LOS HASHSET
    public List<Cliente> getClientes() { return clientes; }

    public List<Perro> getPerros() {
        return perros;
    }

    public List<Medico> getMedicos() {
        return medicos;
    }

    public List<Cliente> getDuenosProvisionales() {return duenosProvisionales;}

    public void addDuenoProvisional(Cliente duenoProvisional) {this.duenosProvisionales.add(duenoProvisional);}

    public void eliminarDuenoProvisional(Cliente cliente) {this.duenosProvisionales.remove(cliente);}

    public boolean isDatosModificados() {return datosModificados;}

    public void setDatosModificados(boolean datosModificados) {this.datosModificados = datosModificados;}


    /**
     * Añade clientes al array cliente
     * @param nombre del cliente
     * @param apellidos del cliente
     * @param dni del cliente
     * @param nuevoCliente del cliente
     * @param menorEdad del cliente
     */
    public void anadirCliente(String nombre, String apellidos, String dni, boolean nuevoCliente, boolean menorEdad) {
        clientes.add(new Cliente(nombre, apellidos, dni, nuevoCliente, menorEdad));
    }

    /**
     * Elimina clientes del array clientes
     * @param cliente
     */
    public void eliminarCliente(Cliente cliente) {
        if (cliente != null) {
            clientes.remove(cliente);
        } else {
            JOptionPane.showMessageDialog(null, resourceBundle.getString("errorClienteNoSeleccionado"));
        }
    }

    /**
     * añade perros al array perros
     * @param nombre del perro
     * @param edad del perro
     * @param pesoPerro del perro
     * @param fechaIngreso del perro
     * @param fotoPerro del perro
     * @param duenos del perro
     * @return
     */
    public Perro anadirPerro(String nombre, int edad, double pesoPerro, LocalDate fechaIngreso, Icon fotoPerro, List duenos) {
        Perro perro = new Perro(nombre, edad, pesoPerro, fechaIngreso, fotoPerro, duenos);
        perros.add(perro);
        return perro;

    }

    /**
     * Elimina perros del array perros
     * @param perro Perro que se desea eliminar
     */
    public void eliminarPerro(Perro perro) {
        if(perro != null){
            if(perro.getDuenos() != null){
                for(Cliente dueno : perro.getDuenos()){
                    dueno.eliminarPerro(perro);
                }
            }
            perros.remove(perro);
        }
        else {
            JOptionPane.showMessageDialog(null, resourceBundle.getString("errorPerroNoSeleccionado"));
        }

    }

    /**
     * añade Médicos al array medicos
     * @param nombre nombre del médico
     * @param apellidos apellidos del médico
     * @param identificador id del médico
     * @param telefono teléfono del médico
     * @param horario horario de trabajo del médico
     */
    public void anadirMedico(String nombre, String apellidos, String identificador, String telefono, String horario) {
        medicos.add(new Medico(nombre, apellidos, identificador, telefono, horario));

    }

    /**
     * Elimina médicos del array médicos
     * @param medico que se desea eliminar
     */
    public void eliminarMedico(Medico medico) {
        if(medico != null){
            medicos.remove(medico);
        }
        else {
            JOptionPane.showMessageDialog(null, resourceBundle.getString("errorMedicoNoSeleccionado"));
        }

    }

    /**
     * genera un archivo .dat que almacena los datos guardados actualemtne en la aplicación
     * @param fichero ruta donde se desea guardar el fichero
     * @throws IOException
     */
    public void guardarDatos(File fichero) throws IOException {
        FileOutputStream fos = new FileOutputStream(fichero);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(clientes);
        oos.writeObject(perros);
        oos.writeObject(medicos);

        oos.close();

        Properties propiedades = new Properties();
        propiedades.load(new FileReader("guardado.props"));
        propiedades.setProperty("ubicacion", fichero.getPath());
        propiedades.store(new FileWriter("guardado.props"), "fichero configuracion");

        /**
         * Al arrancar la app comprobará si existe un guardado anterior a través de este fichero.
         * Si existe el fichero es porque hay datos, así que se arrancará los datos automáticamente
         */
    }

    /**
     * Permite cargar un fichero de datos
     * @param fichero ruta del fichero que se desea cargar
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public void cargarDatos(File fichero) throws ClassNotFoundException, IOException {
        FileInputStream fis = new FileInputStream(fichero);
        ObjectInputStream ois = new ObjectInputStream(fis);
        clientes = (List<Cliente>) ois.readObject();
        perros = (List<Perro>) ois.readObject();
        medicos = (List<Medico>) ois.readObject();
        ois.close();
    }
}

