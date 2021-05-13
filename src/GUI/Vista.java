package GUI;

import Base.Cliente;
import Base.Medico;
import Base.Perro;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * Contiene los datos de todos los componentes del panel gráfico.
 * @author Marc Ferrero Fernández
 */
public class Vista extends Component {
    JFrame frame;
    JPanel contentPane;
    JTabbedPane tabbedPane;
    JPanel panelClientes;
    JPanel panelPerros;
    JPanel panelDatosCliente;
    JPanel panelOtrosDatos;
    JTextField txtNombreCliente;
    JTextField txtApellidosCliente;
    JTextField txtDniCliente;
    JCheckBox chBoxNuevo;
    JCheckBox chBoxMenor;
    JList listaClientes;
    JScrollPane panelListaClientes;
    JButton btnAnadirCliente;
    JButton btnModificarCliente;
    JButton btnEliminarCliente;
    JPanel panelDatosPerro;
    JTextField txtNombrePerro;
    JTextField txtEdadPerro;
    JTextField txtPesoPerro;
    DatePicker datePickerPerro;
    JLabel lblClientes;
    JList listaPerros;
    JButton btnAnadirPerro;
    JButton btnModificarPerro;
    JButton btnEliminarPerro;
    JButton botAnadirImagen;
    JButton botEliminarImagen;
    JPanel panelBotones;
    JButton botCargarDatos;
    JButton botGuardarDatos;
    JLabel lblFotoPerro;
    JPanel panelDatosMedico;
    JPanel panelListaMedicos;
    JTextField txtNombreMedico;
    JTextField txtApellidosMedico;
    JTextField txtIdentificadorMedico;
    JTextField txtTelefonoMedico;
    JButton botEliminarMedico;
    JButton botModificarMedico;
    JButton botAnadirMedico;
    JList listMedicos;
    JPanel panelHorario;
    JPanel panelChBox;
    JPanel panelRelacionPerros;
    JList listRelacionPerros;
    JButton botRelacionarPerrosMedicos;
    JRadioButton RdBtnManana;
    JRadioButton RdBtnTarde;
    JPanel panelBotonesMedicos;
    JPanel panelBotonesCliente;
    JPanel panelRelacionPerrosClientes;
    JList listaRelacionPerrosClientes;
    JButton botRelacionPerrosClientes;
    JButton botIdioma;
    JButton botGrafica;
    JButton botGrafica2;
    JList listClientesPerros;
    JList listDuenos;
    JButton btnEliminarDuenos;
    JLabel lblDuenos;
    JButton btnAnadirDuenos;
    JButton botInforme;
    JButton botInforme2;
    private JButton manualDeUsuarioButton;
    DefaultListModel<Cliente> dlmClientes;
    DefaultListModel<Perro> dlmPerros;
    DefaultListModel<Medico> dlmMedicos;
    DefaultListModel<Perro> dlmPerrosDeMedico;
    DefaultListModel<Cliente> dlmDuenos;
    DefaultListModel<Cliente> dlmClientesPerros;
    DefaultListModel<Perro> dlmPerrosClientes;

    /**
     * Constructor de la clase
     */
    public Vista() {
        frame = new JFrame("Vista");
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        addAtajos();

        frame.setVisible(true);
        iniciarListas();
    }

    /**
     * Inicializa las listas y el comboBox
     */
    private void iniciarListas() {
        dlmClientes = new DefaultListModel<Cliente>();
        listaClientes.setModel(dlmClientes);

        dlmPerros = new DefaultListModel<Perro>();
        listaPerros.setModel(dlmPerros);

        dlmDuenos = new DefaultListModel<Cliente>();
        listDuenos.setModel(dlmDuenos);

        dlmClientesPerros = new DefaultListModel<Cliente>();
        listClientesPerros.setModel(dlmClientesPerros);

        dlmPerrosClientes = new DefaultListModel<Perro>();
        listaRelacionPerrosClientes.setModel(dlmPerrosClientes);

        dlmMedicos = new DefaultListModel<Medico>();
        listMedicos.setModel(dlmMedicos);

        dlmPerrosDeMedico = new DefaultListModel<Perro>();
        listRelacionPerros.setModel(dlmPerrosDeMedico);


    }

    /**
     * Añade atajos a los botones de la aplicación
     */
    public void addAtajos(){
        //Botones Clientes
        btnAnadirCliente.setMnemonic(KeyEvent.VK_A);
        btnEliminarCliente.setMnemonic(KeyEvent.VK_B);
        btnModificarCliente.setMnemonic(KeyEvent.VK_C);
        botRelacionPerrosClientes.setMnemonic(KeyEvent.VK_D);
        //Botones Perros
        btnAnadirPerro.setMnemonic(KeyEvent.VK_E);
        btnEliminarPerro.setMnemonic(KeyEvent.VK_F);
        btnModificarPerro.setMnemonic(KeyEvent.VK_G);
        botAnadirImagen.setMnemonic(KeyEvent.VK_H);
        botEliminarImagen.setMnemonic(KeyEvent.VK_I);
        btnAnadirDuenos.setMnemonic(KeyEvent.VK_W);
        btnEliminarDuenos.setMnemonic(KeyEvent.VK_X);
        //Botones Médicos
        botAnadirMedico.setMnemonic(KeyEvent.VK_J);
        botModificarMedico.setMnemonic(KeyEvent.VK_K);
        botEliminarMedico.setMnemonic(KeyEvent.VK_L);
        botRelacionarPerrosMedicos.setMnemonic(KeyEvent.VK_O);
        //Botones Menú
        botGuardarDatos.setMnemonic(KeyEvent.VK_P);
        botCargarDatos.setMnemonic(KeyEvent.VK_Q);
        botIdioma.setMnemonic(KeyEvent.VK_T);
        botGrafica.setMnemonic(KeyEvent.VK_U);
        botGrafica2.setMnemonic(KeyEvent.VK_V);

    }
}
