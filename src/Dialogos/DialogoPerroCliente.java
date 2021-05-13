package Dialogos;

import Base.Cliente;
import Base.Perro;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Cuadro de diálogo utilizado para relacionar a perros con sus dueños
 *
 * @author Marc Ferrero Fernández
 */
public class DialogoPerroCliente extends JDialog {
    private JPanel contentPane;
    private JLabel lblDatosCliente;
    private JList <Perro> listaPerrosRelacionados;
    private JList <Perro> listaPerrosNoRelacionados;
    private DefaultListModel <Perro> relacionadosDlm;
    private DefaultListModel <Perro> noRelacionadosDlm;
    private JButton botRelacionarPerro;
    private JButton botDesrelacionarPerro;
    private JButton botAceptarRelacion;
    private JButton botCancelarRelacion;
    private List <Perro> listaTemporalPerrosRelacionados;
    private List <Perro> listaTemporalPerrosNoRelacionados;
    private Cliente cliente;

    /**
     * Constructor de la clase.
     * @param cliente Cliente al que desea añadir perros
     * @param perros  Lista que posee todos los perros guardados en la aplicación.
     */
    public DialogoPerroCliente(Cliente cliente, List<Perro> perros) {
        this.contentPane = contentPane;
        this.cliente = cliente;

        listaTemporalPerrosRelacionados = new ArrayList<>(cliente.getPerros());
        listaTemporalPerrosNoRelacionados = new ArrayList<>(perros);
        listaTemporalPerrosNoRelacionados.removeAll(listaTemporalPerrosRelacionados);

        relacionadosDlm = new DefaultListModel<>();
        noRelacionadosDlm = new DefaultListModel<>();
        lblDatosCliente.setText(cliente.toString());
        listaPerrosRelacionados.setModel(relacionadosDlm);
        listaPerrosNoRelacionados.setModel(noRelacionadosDlm);

        listarPerrosRelacionados();
        listarPerrosNoRelacionados();
        abrirVentana();
    }

    /**
     * Actualiza el JList con los perros que vamos a asociar al dueño
     */
    private void listarPerrosRelacionados(){
        relacionadosDlm.clear();
        for(Perro perro : listaTemporalPerrosRelacionados){
            relacionadosDlm.addElement(perro);
        }

    }

    /**
     * Actualiza el JList con los perros no relacionados al cliente
     */
    private void listarPerrosNoRelacionados(){
        noRelacionadosDlm.clear();
        for(Perro perro : listaTemporalPerrosNoRelacionados){
            noRelacionadosDlm.addElement(perro);
        }
    }

    /**
     * Método que relaciona los perros seleccionados al cliente en cuestión
     */
    private void relacionarPerrosSeleccionados() {

        List<Perro> seleccionados = listaPerrosNoRelacionados.getSelectedValuesList();
        listaTemporalPerrosNoRelacionados.removeAll(seleccionados);
        listaTemporalPerrosRelacionados.addAll(seleccionados);

        listarPerrosRelacionados();
        listarPerrosNoRelacionados();
    }

    /**
     * Método que desrelaciona los perros seleccionados del cliente en cuestión
     */
    private void desRelacionarSeleccionados() {
        List<Perro> seleccionados = listaPerrosRelacionados.getSelectedValuesList();
        for(Perro perroActual : seleccionados){
            perroActual.eliminarDueno(cliente);
            cliente.eliminarPerro(perroActual);
        }
        listaTemporalPerrosRelacionados.removeAll(seleccionados);
        listaTemporalPerrosNoRelacionados.addAll(seleccionados);

        listarPerrosRelacionados();
        listarPerrosNoRelacionados();
    }

    /**
     * Método que llama a los métodos necesarios para realizar los cambios al pulsar el botón aceptar del cuadro de diálogo.
     */
    private void onOK() {
        realizarCambios();
        dispose();
    }

    /**
     * Método que realiza los cambios al pulsar el botón Aceptar
     */
    private void realizarCambios() {

        if(cliente.getPerros() != null){
            for(Perro perro : cliente.getPerros()){
                perro.eliminarDueno(cliente);
            }
        }

        for(Perro perro : listaTemporalPerrosRelacionados){
            perro.addDueno(cliente);
            cliente.anadirPerro(perro);
        }

        cliente.setPerros(listaTemporalPerrosRelacionados);
    }

    /**
     * Método que cancela todas las operaciones realizadas al pulsar el botón Cancel
     */
    private void onCancel() {
        dispose();
    }

    /**
     * Método que permite mostrar la ventana de diálogo
     */
    private void abrirVentana() {
        setVisible(true);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(botCancelarRelacion);
        setTitle(cliente.getDni() + " - " + cliente.getNombre() + " " + cliente.getApellidos());

        lblDatosCliente.setText(cliente.toString());

        botAceptarRelacion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        botCancelarRelacion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        /**
         * llama onCancel() al pulsar la cruz
         */
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        /**
         * llama onCancel() al pulsar escape
         */
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        botDesrelacionarPerro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desRelacionarSeleccionados();
            }
        });
        botRelacionarPerro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                relacionarPerrosSeleccionados();
            }
        });

        pack();
        setVisible(true);
    }

}