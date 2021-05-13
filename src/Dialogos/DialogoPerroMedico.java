package Dialogos;

import Base.Medico;
import Base.Perro;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Cuadro de diálogo utilizado para asociar perros a un médico
 *
 * @author Marc Ferrero Fernández
 */
public class DialogoPerroMedico extends JDialog {
    private JPanel contentPane;
    private JLabel lblDatosMedico;
    private JList <Perro> listaRelacionados;
    private JList <Perro> listaNoRelacionados;
    private DefaultListModel <Perro> relacionadosDlm;
    private DefaultListModel <Perro> noRelacionadosDlm;
    private JButton botRelacionar;
    private JButton botDesrelacionar;
    private JButton botAceptarRelacion;
    private JButton botCancelarRelacion;
    private List <Perro> listaTemporalRelacionados;
    private List <Perro> listaTemporalNoRelacionados;
    private Medico medico;

    /**
     * Constructor de la clase
     * @param medico al que desea añadir perros
     * @param perros Lista de todos los perros guardados en la app
     */
    public DialogoPerroMedico(Medico medico, List<Perro> perros) {
        this.contentPane = contentPane;
        this.medico = medico;

        listaTemporalRelacionados = new ArrayList<>(medico.getPerros());
        listaTemporalNoRelacionados = new ArrayList<>(perros);
        listaTemporalNoRelacionados.removeAll(listaTemporalRelacionados);

        relacionadosDlm = new DefaultListModel<>();
        noRelacionadosDlm = new DefaultListModel<>();
        lblDatosMedico.setText(medico.toString());
        listaRelacionados.setModel(relacionadosDlm);
        listaNoRelacionados.setModel(noRelacionadosDlm);

        listarPerrosRelacionados();
        listarPerrosNoRelacionados();
        abrirVentana();
    }

    /**
     * Actualiza el JList con los perros que hemos decidido relacionar
     */
    private void listarPerrosRelacionados(){
        relacionadosDlm.clear();
        for(Perro perro : listaTemporalRelacionados){
            relacionadosDlm.addElement(perro);
        }
    }

    /**
     * Actualiza el JList con los perros que hemos decidido desrelacionar
     */
    private void listarPerrosNoRelacionados(){
        noRelacionadosDlm.clear();
        for(Perro perro : listaTemporalNoRelacionados){
            noRelacionadosDlm.addElement(perro);
        }
    }

    /**
     * Método que relaciona los perros seleccionados
     */
    private void relacionarPerrosSeleccionados() {

        List<Perro> seleccionados = listaNoRelacionados.getSelectedValuesList();

        listaTemporalNoRelacionados.removeAll(seleccionados);

        listaTemporalRelacionados.addAll(seleccionados);


        listarPerrosRelacionados();
        listarPerrosNoRelacionados();
    }

    /**
     * Método que desrelaciona los perros seleccionados
     */
    private void desRelacionarSeleccionados() {
        List<Perro> seleccionados = listaRelacionados.getSelectedValuesList();
        listaTemporalRelacionados.removeAll(seleccionados);
        listaTemporalNoRelacionados.addAll(seleccionados);

        listarPerrosRelacionados();
        listarPerrosNoRelacionados();
    }

    /**
     * Ejecuta los métodos necesarior para realizar los cambios al pulsar el botón ACEPTAR
     */
    private void onOK() {
        realizarCambios();
        dispose();
    }

    /**
     * Realiza los cambios efectuados
     */
    private void realizarCambios() {

        for(Perro perro : medico.getPerros()){
            perro.setMedico(null);
        }

        for(Perro perro : listaTemporalRelacionados){
            perro.setMedico(medico);
        }

        medico.setPerros(listaTemporalRelacionados);
    }

    /**
     * Método que cancela todos los cambios realizados
     */
    private void onCancel() {
        dispose();
    }

    /**
     * Método que permite mostrar el cuadro de diálogo
     */
    private void abrirVentana() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(botCancelarRelacion);
        setTitle(medico.getIdentificador() + " - " + medico.getNombre() + " " + medico.getApellidos());

        lblDatosMedico.setText(medico.toString());

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
        botDesrelacionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desRelacionarSeleccionados();
            }
        });
        botRelacionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                relacionarPerrosSeleccionados();
            }
        });

        pack();
        setVisible(true);
    }

}
