package Principal;
import Dialogos.DialogoSeleccionIdioma;
import GUI.Controlador;
import GUI.Modelo;
import GUI.Vista;

import java.io.*;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Clase que arranca la aplicación
 * @author Marc Ferrero Fernández
 */
public class Principal {

    /**
     * Método main
     * @param args
     */
    public static void main(String[] args) {
        try {
            Locale.setDefault(DialogoSeleccionIdioma.cargarIdioma());
        } catch (IOException e) {
            System.out.println("No se ha podido cargar el idioma");
        }
        ResourceBundle bundle = ResourceBundle.getBundle("idiomaResourceBundle",Locale.getDefault());
        Vista vista = new Vista();
        Modelo modelo = new Modelo();

        if (comprobarDatosGuardados()){
            Properties propiedades = new Properties();
            try {
                propiedades.load(new FileReader("guardado.props"));
                File f = new File(propiedades.getProperty("ubicacion"));
                Locale.setDefault(DialogoSeleccionIdioma.cargarIdioma());
                modelo.cargarDatos(f);
            } catch (IOException e) {
                System.out.println("Fichero props no encontrado");
            } catch (ClassNotFoundException e) {
                System.out.println("No se han podido cargar los datos");
            }

        }
        Controlador controlador = new Controlador(vista, modelo);

    }

    /**
     * Comprueba si existen datos guardados
     * @return
     */
    public static boolean comprobarDatosGuardados(){
        boolean aDevolver = true;
        Properties propiedades = new Properties();
        try {
            propiedades.load(new FileReader("guardado.props"));
            String ubicacion = propiedades.getProperty("ubicacion");
            File fichero = new File(ubicacion);
            FileInputStream input = new FileInputStream(ubicacion);
        } catch (IOException e) {
            aDevolver = false;
            System.out.println("No se pudo encontrar el archivo de propiedades");
        }

        return aDevolver;
    }

}
