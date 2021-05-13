package Util;

import javax.swing.*;
import java.awt.*;

/**
 * Clase que maneja utilidades de la aplicación como las acciones realizadas al pulsar los botones de los diálogos
 * o redimensionar el tamaño de las imágenes
 * @author Marc Ferrero Fernández
 */

public class Util {

    public static final int ACEPTAR = JOptionPane.OK_OPTION;
    public static final int CANCELAR = JOptionPane.CANCEL_OPTION;

    public static void mostrarDialogoError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static int mostrarDialogoSiNo(String mensaje) {
        return JOptionPane.showConfirmDialog(null, mensaje, "Confirmación", JOptionPane.YES_NO_OPTION);
    }

    // Redimensiona la imagen introducida a un tamaño óptimo para su representación dentro de la aplicación
    public static ImageIcon escarlarImagen(ImageIcon icon, int alto, int ancho){
        Image imagen = icon.getImage();

        Image imagenEscalada = imagen.getScaledInstance(ancho, alto, Image.SCALE_FAST);

        ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);

        return iconoEscalado;
    }
}