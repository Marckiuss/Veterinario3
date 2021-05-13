package Dialogos;

import javax.swing.*;
import java.awt.event.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

/**
 * Cuadro de diálogo que permite seleccionar el idioma de la aplicación
 * @author Marc Ferrero Fernández
 */
public class DialogoSeleccionIdioma extends JDialog {
    private JPanel contentPane;
    private JRadioButton RdBtnEsp;
    private JRadioButton RdBtnEng;
    private JLabel lblEspanol;
    private JLabel lblIngles;
    private JButton botAceptar;
    private JButton botCancelar;
    public static final int ACEPTAR = 1;
    public static final int CANCELAR = 0;
    private int estado;

    //Constructor de la clase
    public DialogoSeleccionIdioma() {
        estado = CANCELAR;
        this.contentPane = contentPane;
        setModal(true);
        setLocationRelativeTo(null);
        abrirVentana();
    }

    /**
     * Ejecuta los métodos necesarios para realizar los cambios realizados al pulsar el botón ACEPTAR
     *
     * @throws IOException
     */
    private void onOK() throws IOException {
        realizarCambios();
        dispose();
    }

    /**
     * Guarda los cambios efectuados
     * @throws IOException
     */
    private void realizarCambios() throws IOException {
        Locale locale = new Locale("es","ES");
        if(RdBtnEng.isSelected()){
            locale = Locale.UK;
        }
        guardarDatosIdioma(locale);
        estado = ACEPTAR;
    }

    /**
     * Guarda los datos del idioma seleccionado
     * @param locale
     * @throws IOException
     */
    private void guardarDatosIdioma(Locale locale) throws IOException {
        Properties props = new Properties();
        props.load(new FileReader("guardado.props"));
        props.setProperty("idioma",locale.getLanguage());
        props.setProperty("pais", locale.getCountry());
        props.store(new FileWriter("guardado.props"), "fichero configuracion");
    }

    /**
     * Cancela los cambios efectuados al pulsar el botón CANCELAR
     */
    private void onCancel() {
        dispose();
    }

    /**
     * Carga los datos del idioma guardado en el archivo de propiedades
     * @return
     * @throws IOException
     */
    public static Locale cargarIdioma() throws IOException {
        Locale locale = new Locale("es","ES");
        Properties props = new Properties();
        props.load(new FileReader("guardado.props"));
        String idioma = props.getProperty("idioma");
        String pais = props.getProperty("pais");
        locale = new Locale(idioma,pais);
        return locale;
    }

    /**
     * Muestra el cuadro de diálogo de seleccion de idioma y devuelvo el número necesario para guardar
     * el idioma en el archivo de propiedades
     * @return
     */
    public static int mostrarDialogo(){
        DialogoSeleccionIdioma dialogo = new DialogoSeleccionIdioma();
        dialogo.setVisible(true);
        return dialogo.estado;
    }


    /**
     * Muestra el cuadro de diálogo de selección de idioma
     */
    private void abrirVentana() {
        setContentPane(contentPane);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        getRootPane().setDefaultButton(botCancelar);

        botAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onOK();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        botCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        /**
         * Llama onCancel() cuando pulsas la cruz
         */
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        /**
         * Llama onCancel() cuando pulsas escape
         */
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


        pack();
    }

}
