package GUI;

import Base.Cliente;
import Base.Medico;
import Base.Perro;
import Dialogos.DialogoPerroCliente;
import Dialogos.DialogoPerroMedico;
import Dialogos.DialogoSeleccionIdioma;
import Util.Util;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;


import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

/**
 * Clase encargada de controlar todas las acciones realizadas por los botones
 *
 * @author Marc Ferrero Fernández
 */
public class Controlador implements ActionListener, MouseListener, ListSelectionListener, WindowListener {

    private Vista vista;
    private Modelo modelo;
    private ResourceBundle resourceBundle;

    public Controlador(Vista vista, Modelo modelo) {
        this.vista = vista;
        this.modelo = modelo;
        addActionListeners(this);
        addListListeners(this);
        addWindowListeners(this);
        refrescarApp();
        resourceBundle = ResourceBundle.getBundle("idiomaResourceBundle", Locale.getDefault());
    }

    /**
     * Carga los listeners de los botones y los checkbox
     *
     * @param listener
     */
    private void addActionListeners(ActionListener listener) {
        //Botones Clientes
        vista.btnAnadirCliente.addActionListener(listener);
        vista.btnEliminarCliente.addActionListener(listener);
        vista.btnModificarCliente.addActionListener(listener);
        vista.botRelacionPerrosClientes.addActionListener(listener);
        //Botones Perros
        vista.btnAnadirPerro.addActionListener(listener);
        vista.btnEliminarPerro.addActionListener(listener);
        vista.btnModificarPerro.addActionListener(listener);
        vista.botAnadirImagen.addActionListener(listener);
        vista.botEliminarImagen.addActionListener(listener);
        vista.btnEliminarDuenos.addActionListener(listener);
        vista.btnAnadirDuenos.addActionListener(listener);
        //Botones Médicos
        vista.botAnadirMedico.addActionListener(listener);
        vista.botModificarMedico.addActionListener(listener);
        vista.botEliminarMedico.addActionListener(listener);
        vista.chBoxNuevo.addActionListener(listener);
        vista.chBoxMenor.addActionListener(listener);
        vista.botRelacionarPerrosMedicos.addActionListener(listener);
        //Botones Menú
        vista.botGuardarDatos.addActionListener(listener);
        vista.botCargarDatos.addActionListener(listener);
        vista.botIdioma.addActionListener(listener);
        vista.botIdioma.setActionCommand("SeleccionarIdioma");
        vista.botGrafica.addActionListener(listener);
        vista.botGrafica.setActionCommand("MostrarGrafica");
        vista.botGrafica2.addActionListener(listener);
        vista.botGrafica2.setActionCommand("MostrarGrafica2");
        vista.botInforme.addActionListener(listener);
        vista.botInforme.setActionCommand("Informe");
        vista.botInforme2.addActionListener(listener);
        vista.botInforme2.setActionCommand("Informe2");
    }

    /**
     * Carga los listeners de los JList
     *
     * @param listener
     */
    private void addListListeners(ListSelectionListener listener) {
        vista.listaClientes.addListSelectionListener(listener);
        vista.listaPerros.addListSelectionListener(listener);
        vista.listMedicos.addListSelectionListener(listener);
    }

    private void addWindowListeners(WindowListener listener) {
        vista.frame.addWindowListener(listener);
    }

    /**
     * Relaciona los comandos de acción especificados en los botones con los métodos que se han de realizar
     *
     * @param e ActionEvent. Pasa a tener el valor del action command del botón.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "AnadirCliente": {
                anadirCliente();
                modelo.setDatosModificados(true);
                refrescarApp();
            }
            break;
            case "ModificarCliente": {
                Cliente cliente = (Cliente) vista.listaClientes.getSelectedValue();
                modificarCliente(cliente);
                modelo.setDatosModificados(true);
                refrescarApp();

            }
            break;
            case "EliminarCliente": {
                Cliente cliente = (Cliente) vista.listaClientes.getSelectedValue();
                modelo.eliminarCliente(cliente);
                modelo.setDatosModificados(true);
                refrescarApp();
            }
            break;
            case "AnadirPerro": {
                anadirPerro();
                modelo.setDatosModificados(true);
                refrescarApp();
            }
            break;
            case "ModificarPerro": {
                Perro perro = (Perro) vista.listaPerros.getSelectedValue();
                modificarPerro(perro);
                modelo.setDatosModificados(true);
                refreshPerros();
            }
            break;
            case "EliminarPerro": {
                Perro perro = (Perro) vista.listaPerros.getSelectedValue();
                modelo.eliminarPerro(perro);
                modelo.setDatosModificados(true);
                refrescarApp();
            }
            break;
            case "Anadir Dueno Provisional": {
                anadirDuenosProvisionales();
            }
            break;
            case "Eliminar Dueno Provisional": {
                eliminarDuenosProvisionales();
            }
            break;
            case "AnadirFotoPerro": {
                anadirFotoPerro();
            }
            break;
            case "EliminarFotoPerro": {
                Perro perro = (Perro) vista.listaPerros.getSelectedValue();
                eliminarFotoPerro(perro);
                modelo.setDatosModificados(true);
                refrescarApp();
            }
            break;
            case "Eliminar Dueño": {
                Perro perro = (Perro) vista.listaPerros.getSelectedValue();
                eliminarDuenos(perro);
                modelo.setDatosModificados(true);
                refrescarApp();
            }
            break;
            case "AnadirMedico": {
                anadirMedico();
                modelo.setDatosModificados(true);
                refrescarApp();
            }
            break;
            case "ModificarMedico": {
                Medico medico = (Medico) vista.listMedicos.getSelectedValue();
                modificarMedico(medico);
                modelo.setDatosModificados(true);
                refrescarApp();
            }
            break;
            case "EliminarMedico": {
                Medico medico = (Medico) vista.listMedicos.getSelectedValue();
                modelo.eliminarMedico(medico);
                modelo.setDatosModificados(true);
                refrescarApp();
            }
            break;
            case "RelacionarPerros": {
                Medico medico = (Medico) vista.listMedicos.getSelectedValue();
                relacionarPerrosMedico(medico);
                refrescarApp();

            }
            break;
            case "RelacionarPerroCliente": {
                Cliente cliente = (Cliente) vista.listaClientes.getSelectedValue();
                relacionarPerrosCliente(cliente);
                refrescarApp();
            }
            break;
            case "Guardar": {
                guardarDatos();
                refrescarApp();
            }
            break;
            case "Cargar": {
                cargarDatos();
                refrescarApp();
            }
            break;
            case "SeleccionarIdioma": {
                int opt = DialogoSeleccionIdioma.mostrarDialogo();
                if (opt == 1) {
                    int opt2 = JOptionPane.showConfirmDialog(null, resourceBundle.getString("cambiosReiniciarApp"), resourceBundle.getString("tituloReiniciar"), JOptionPane.YES_NO_CANCEL_OPTION);
                    if (opt2 == JOptionPane.OK_OPTION) {
                        if (modelo.isDatosModificados()) {
                            int opt3 = Util.mostrarDialogoSiNo(resourceBundle.getString("cerrarVentana"));
                            if (opt3 == Util.ACEPTAR) {
                                guardarDatos();
                            }
                        }
                        System.exit(0);
                        modelo.setDatosModificados(true);
                    }
                }
            }
            break;
            case "MostrarGrafica": {
                mostrarGrafica();
            }
            break;
            case "MostrarGrafica2": {
                mostrarGraficaDos();
            }
            break;
            case "Informe": {
                mostrarInforme();
            }
            break;
            case "Informe2": {
                mostrarInformeDos();
            }
            break;
            case "Manual": {
                mostrarManual();
            }
        }
    }

    /**
     * Comprueba que una cadena está compuesta solo por números
     *
     * @param cadena Cadena a comparar
     * @return true si solo hay números o false si encuentra algún otro caracter
     */
    static boolean soloNumeros(String cadena) {

        for (int i = 0; i < cadena.length(); i++) {
            char caracter = cadena.toUpperCase().charAt(i);
            int valorASCII = (int) caracter;
            if (valorASCII < 46 || valorASCII > 57) {
                return false;
            }

        }
        return true;

    }

    /**
     * Refresca las listas del panel de clientes.
     */
    public void refreshClientes() {
        vista.dlmClientes.clear();

        for (Cliente clienteActual : modelo.getClientes()) {
            vista.dlmClientes.addElement(clienteActual);

        }

        vista.dlmClientesPerros.clear();
        for (Cliente cliente : modelo.getClientes()) {
            vista.dlmClientesPerros.addElement(cliente);
        }

    }

    /**
     * Refresca el JList de los perros
     */
    public void refreshPerros() {
        vista.dlmPerros.clear();
        for (Perro perroActual : modelo.getPerros()) {
            vista.dlmPerros.addElement(perroActual);
        }
    }

    /**
     * Refresca el JList de los dueños
     */
    public void refrescarDuenos() {
        vista.dlmClientesPerros.clear();
        for (Cliente cliente : modelo.getClientes()) {
            vista.dlmClientesPerros.addElement(cliente);
        }
        vista.dlmDuenos.clear();
        for (Cliente cliente : modelo.getDuenosProvisionales()) {
            vista.dlmDuenos.addElement(cliente);
            for (Cliente cliente2 : modelo.getClientes()) {
                if (cliente.getDni().equals(cliente2.getDni())) {
                    vista.dlmClientesPerros.removeElement(cliente);
                }
            }
        }
    }

    /**
     * Refresca el JList de los médicos
     */
    public void refreshMedicos() {
        vista.dlmMedicos.clear();
        for (Medico medicoActual : modelo.getMedicos()) {
            vista.dlmMedicos.addElement(medicoActual);
            refreshPerrosDeMedico(medicoActual);

        }
    }

    /**
     * Condensa todos los métodos de refresco de las listas principales
     */
    public void refrescarApp() {
        refreshClientes();
        refreshPerros();
        refreshMedicos();
    }

    /**
     * Refresca el JList de los Perros que pertenecen a cada médico
     *
     * @param medico Médico al que se desea asociarle los perros
     */
    private void refreshPerrosDeMedico(Medico medico) {
        vista.dlmPerrosDeMedico.clear();
        for (Perro perro : medico.getPerros()) {
            vista.dlmPerrosDeMedico.addElement(perro);
        }
    }

    /**
     * Refresca el JList de los perros que pertenencen a cada cliente
     *
     * @param cliente Cliente al que se desea asociarle los perros
     */
    private void refreshPerrosDeCliente(Cliente cliente) {
        vista.dlmPerrosClientes.clear();
        for (Perro perro : cliente.getPerros()) {
            vista.dlmPerrosClientes.addElement(perro);
        }
    }

    /**
     * Permite añadir un cliente con los datos introducidos en los campos de texto
     */
    private void anadirCliente() {
        String nombre = vista.txtNombreCliente.getText();
        String apellidos = vista.txtApellidosCliente.getText();
        String dni = vista.txtDniCliente.getText();
        boolean nuevoCliente = vista.chBoxNuevo.isSelected();
        boolean menorEdad = vista.chBoxMenor.isSelected();
        if (!checkCliente(dni, nombre, apellidos)) {
            Util.mostrarDialogoError(
                    resourceBundle.getString("errorDatosCliente"));
        } else if (clienteRepetido(nombre, apellidos, dni, nuevoCliente, menorEdad)) {
            Util.mostrarDialogoError(
                    resourceBundle.getString("errorExisteCliente"));
        } else {
            this.modelo.anadirCliente(nombre, apellidos, dni, nuevoCliente, menorEdad);
        }
    }


    /**
     * Modifica el Cliente seleccionado en el JList con los datos introducidos en los campos
     *
     * @param cliente al que se desea modificar los datos
     */
    private void modificarCliente(Cliente cliente) {
        if (cliente != null) {
            cliente.setNombre(vista.txtNombreCliente.getText());
            cliente.setApellidos(vista.txtApellidosCliente.getText());
            cliente.setDni(vista.txtDniCliente.getText());
            cliente.setNuevo(vista.chBoxNuevo.isSelected());
            cliente.setMenorEdad(vista.chBoxMenor.isSelected());
        } else {
            JOptionPane.showMessageDialog(null, resourceBundle.getString("errorClienteNoSeleccionado"));
        }
    }

    /**
     * Permite añadir un perro con los datos introducidos en los campos de texto
     */
    private void anadirPerro() {
        String nombre = vista.txtNombrePerro.getText();
        String edad = vista.txtEdadPerro.getText();
        String pesoPerro = vista.txtPesoPerro.getText();
        LocalDate fechaIngreso = vista.datePickerPerro.getDate();
        List<Cliente> duenos = modelo.getDuenosProvisionales();
        Icon fotoPerro = vista.lblFotoPerro.getIcon();
        if (checkPerro(nombre, edad, pesoPerro)) {
            Perro perro = new Perro(nombre, Integer.parseInt(edad), Double.parseDouble(pesoPerro), fechaIngreso, fotoPerro, duenos);
            this.modelo.anadirPerro(nombre, Integer.parseInt(edad), Double.parseDouble(pesoPerro), fechaIngreso, fotoPerro, duenos);
            for (Cliente dueno : duenos) {
                dueno.anadirPerro(perro);
            }

        } else {
            Util.mostrarDialogoError(
                    resourceBundle.getString("errorDatosCliente"));
        }


        vista.lblFotoPerro.setIcon(null);
    }

    /**
     * Modifica el Perro seleccionado en el JList con los datos introducidos en los campos de texto
     *
     * @param perro Perro al que se le desea modificar los datoss
     */
    private void modificarPerro(Perro perro) {
        if (perro != null) {
            perro.setNombre(vista.txtNombrePerro.getText());
            perro.setEdad(Integer.parseInt(vista.txtEdadPerro.getText()));
            perro.setPeso(Double.parseDouble(vista.txtPesoPerro.getText()));
            perro.setFechaIngreso(vista.datePickerPerro.getDate());
            List<Cliente> duenos = modelo.getDuenosProvisionales();
            //Limpiamos la lista de dueños del perro y quitamos al perro de la lista de sus dueños anteriores
            if (perro.getDuenos() != null) {
                for (Cliente dueno : perro.getDuenos()) {
                    dueno.eliminarPerro(perro);
                }
                perro.getDuenos().clear();
            }
            //Añadimos los dueños al perro
            for (Cliente dueno : duenos) {
                perro.addDueno(dueno);
            }
            //Añadimos el perro a sus dueños
            for (Cliente dueno : duenos) {
                dueno.anadirPerro(perro);
            }

            perro.setFotoPerro(vista.lblFotoPerro.getIcon());
        } else {
            JOptionPane.showMessageDialog(null, resourceBundle.getString("errorPerroNoSeleccionado"));
        }
    }

    /**
     * Añade dueños al JList de dueños
     */
    private void anadirDuenosProvisionales() {
        vista.dlmDuenos.clear();
        List<Cliente> duenosAnadir = new ArrayList<>(vista.listClientesPerros.getSelectedValuesList());
        for (Cliente cliente : duenosAnadir) {
            modelo.addDuenoProvisional(cliente);
            vista.dlmDuenos.addElement(cliente);
            vista.dlmClientesPerros.removeElement(cliente);
        }
        if (!vista.listDuenos.isSelectionEmpty()) {
            List<Cliente> duenos = vista.listDuenos.getSelectedValuesList();
            for (Cliente dueno : duenos) {
                vista.dlmDuenos.addElement(dueno);
            }
        }
        refrescarDuenos();
    }

    /**
     * Elimina dueños del JList de dueños y los introduce en el de clientes
     */
    private void eliminarDuenosProvisionales() {
        List<Cliente> duenosAEliminar = new ArrayList<>(vista.listDuenos.getSelectedValuesList());
        for (Cliente cliente : duenosAEliminar) {
            modelo.eliminarDuenoProvisional(cliente);
            vista.dlmDuenos.removeElement(cliente);
            vista.dlmClientesPerros.addElement(cliente);
        }
        refrescarDuenos();
    }

    /**
     * Permite seleccionar una foto para el perro seleccionado al pulsar el botón
     */
    private void anadirFotoPerro() {
        JFileChooser selector = new JFileChooser();
        int opt = selector.showOpenDialog(vista);
        if (opt == JFileChooser.APPROVE_OPTION) {
            File fotoPerro = selector.getSelectedFile();
            ImageIcon icono = new ImageIcon(fotoPerro.getPath());
            ImageIcon iconoEscalado = Util.escarlarImagen(icono, 90, 90);
            vista.lblFotoPerro.setIcon(iconoEscalado);
        }

    }

    /**
     * Permite eliminar la foto seleccionada del perro seleccionado al pulsar el botón
     *
     * @param perro
     */
    private void eliminarFotoPerro(Perro perro) {
        if (perro != null) {
            perro.setFotoPerro(null);
            vista.lblFotoPerro.setIcon(null);
        } else {
            JOptionPane.showMessageDialog(null, resourceBundle.getString("errorPerroNoSeleccionado"));
        }

    }

    /**
     * Elimina el dueño seleccionado del JList dueños al pulsar el botón
     *
     * @param perro Perro que desea eliminar
     */
    private void eliminarDuenos(Perro perro) {
        if (perro != null) {
            perro.setDuenos(null);
            List<Cliente> duenos = vista.listClientesPerros.getSelectedValuesList();
            for (Cliente duenoActual : duenos) {
                duenoActual.eliminarPerro(perro);
            }
            vista.lblFotoPerro.setIcon(null);
        } else {
            JOptionPane.showMessageDialog(null, resourceBundle.getString("errorPerroNoSeleccionado"));
        }
    }

    /**
     * Añade un médico con los datos introducidos en los campos de texto
     */
    private void anadirMedico() {
        String nombre = vista.txtNombreMedico.getText();
        String apellidos = vista.txtApellidosMedico.getText();
        String identificador = vista.txtIdentificadorMedico.getText();
        String telefono = vista.txtTelefonoMedico.getText();
        String horario = "";
        if (vista.RdBtnManana.isSelected()) {
            horario = "Mañana";
        } else {
            horario = "Tarde";
        }

        if (!checkMedico(nombre, apellidos, identificador, telefono)) {
            Util.mostrarDialogoError(
                    resourceBundle.getString("errorDatosCliente"));
        } else if (medicoRepetido(nombre, apellidos, identificador, telefono, horario)) {
            Util.mostrarDialogoError(
                    resourceBundle.getString("errorMedicoIdentificador"));
        } else {
            modelo.anadirMedico(nombre, apellidos, identificador, telefono, horario);
        }
    }

    /**
     * Modifica el médico seleccionado en el JList con los datos introducidos en los campos de texto
     *
     * @param medico Médico que se desea modificar
     */
    private void modificarMedico(Medico medico) {
        if (medico != null) {
            medico.setNombre(vista.txtNombreMedico.getText());
            medico.setApellidos(vista.txtApellidosMedico.getText());
            medico.setIdentificador(vista.txtIdentificadorMedico.getText());
            medico.setTelefono(vista.txtTelefonoMedico.getText());
            if (vista.RdBtnManana.isSelected()) {
                medico.setHorario("Mañana");
            } else {
                medico.setHorario("Tarde");
            }
        } else {
            JOptionPane.showMessageDialog(null, resourceBundle.getString("errorMedicoNoSeleccionado"));
        }
    }

    /**
     * Abre el cuadro diálogo de seleccion de perros de cada médico al pulsar el botón
     *
     * @param medico Médico al que desea asociar no quitar perros
     */
    private void relacionarPerrosMedico(Medico medico) {
        if (medico != null) {
            List<Perro> perrosTotales = modelo.getPerros();
            DialogoPerroMedico dialogo = new DialogoPerroMedico(medico, perrosTotales);
            refreshPerrosDeMedico(medico);
        } else {
            JOptionPane.showMessageDialog(null, resourceBundle.getString("errorMedicoNoSeleccionado"));
        }
    }

    /**
     * Abre el diálogo de seleccion de perros de cada cliente al pulsar el botón
     *
     * @param cliente
     */
    private void relacionarPerrosCliente(Cliente cliente) {
        if (cliente != null) {
            List<Perro> perrosTotales = modelo.getPerros();
            DialogoPerroCliente dialogo = new DialogoPerroCliente(cliente, perrosTotales);
            refreshPerrosDeCliente(cliente);
        } else {
            JOptionPane.showMessageDialog(null, resourceBundle.getString("errorClienteNoSeleccionado"));
        }
    }

    /**
     * Muestra en los componentes los valores del cliente seleccionado en el jList
     *
     * @param cliente Cliente seleccionado en el JList del que desea mostrar los datos
     */
    private void mostrarValoresCliente(Cliente cliente) {
        vista.txtNombreCliente.setText(cliente.getNombre());
        vista.txtApellidosCliente.setText(cliente.getApellidos());
        vista.txtDniCliente.setText(cliente.getDni());
        vista.chBoxNuevo.setSelected(cliente.getNuevo());
        vista.chBoxMenor.setSelected(cliente.getMenorEdad());
    }

    /**
     * Muestra en los componentes los valores del perro seleccionado en el jList
     *
     * @param perro Perro seleccionado en el JList del que desea mostrar los datos
     */
    private void mostrarValoresPerro(Perro perro) {
        modelo.getDuenosProvisionales().clear();
        vista.txtNombrePerro.setText(perro.getNombre());
        vista.txtEdadPerro.setText(String.valueOf(perro.getEdad()));
        vista.txtPesoPerro.setText(String.valueOf(perro.getPeso()));
        vista.datePickerPerro.setDate(perro.getFechaIngreso());
        vista.lblFotoPerro.setIcon(perro.getFotoPerro());
        //Si el perro tiene dueños divide los clientes en dos listas
        vista.dlmDuenos.clear();
        if (perro.getDuenos() != null) {
            vista.dlmClientesPerros.clear();
            for (Cliente cliente : modelo.getClientes()) {
                vista.dlmClientesPerros.addElement(cliente);
            }
            for (Cliente dueno : perro.getDuenos()) {
                modelo.getDuenosProvisionales().add(dueno);
                vista.dlmDuenos.addElement(dueno);
            }
            for (Cliente cliente : modelo.getClientes()) {
                for (Cliente dueno : modelo.getDuenosProvisionales()) {
                    if (cliente.getDni().equals(dueno.getDni())) {
                        vista.dlmClientesPerros.removeElement(cliente);
                    }
                }
            }
        }
        //si no, muestra solo los clientes
        else {
            vista.dlmClientesPerros.clear();
            for (Cliente cliente : modelo.getClientes()) {
                vista.dlmClientesPerros.addElement(cliente);
            }
        }
    }

    /**
     * Muestra en los componentes los valores del perro seleccionado en el jList
     *
     * @param medico Médico seleccionado en el JList del que desea mostrar los datos
     */
    private void mostrarValoresMedico(Medico medico) {
        vista.txtNombreMedico.setText(medico.getNombre());
        vista.txtApellidosMedico.setText(medico.getApellidos());
        vista.txtIdentificadorMedico.setText(medico.getIdentificador());
        vista.txtTelefonoMedico.setText(medico.getTelefono());
        if (medico.getHorario().equals("Mañana")) {
            vista.RdBtnManana.setSelected(true);
        } else {
            vista.RdBtnTarde.setSelected(true);
        }
        if (medico.getPerros() != null) {
            refreshPerrosDeMedico(medico);
        }
    }

    /**
     * Comprueba que los datos introducidos al añadir un nuevo cliente son válidos
     *
     * @param dni       del cliente del que desea comprobar los datos
     * @param nombre    del cliente del que desea comprobar los datos
     * @param apellidos del cliente del que desea comprobar los datos
     * @return true si los datos están bien introducidos y false si no.
     */
    private boolean checkCliente(String dni, String nombre, String apellidos) {
        boolean esCorrecto = true;
        if (!(nombre.length() > 2)) {
            esCorrecto = false;
        } else if (apellidos.length() <= 0) {
            esCorrecto = false;
        } else if (dni.length() < 8 || dni.length() > 9) {
            esCorrecto = false;
        }
        return esCorrecto;
    }

    /**
     * Comprueba si el cliente introducido ya existe
     *
     * @param nombre       del cliente del que desea comprobar los datos
     * @param apellidos    del cliente del que desea comprobar los datos
     * @param dni          del cliente del que desea comprobar los datos
     * @param nuevocliente del cliente del que desea comprobar los datos
     * @param menorEdad    del cliente del que desea comprobar los datos
     * @return
     */
    private boolean clienteRepetido(String nombre, String apellidos, String dni, boolean nuevocliente, boolean menorEdad) {
        boolean repetido = false;
        Cliente cliente = new Cliente(nombre, apellidos, dni, nuevocliente, menorEdad);
        for (Cliente clienteActual : modelo.getClientes()) {
            if (cliente.getDni().equals(clienteActual.getDni())) {
                repetido = true;
            }
        }
        return repetido;
    }


    private boolean checkPerro(String nombre, String edad, String peso) {
        boolean esCorrecto = true;
        try {
            Double.parseDouble(peso);
        } catch (NumberFormatException e) {
            esCorrecto = false;
        }
        if (!soloNumeros(edad) || edad.length() <= 0) {
            esCorrecto = false;
        } else if (nombre.length() < 1) {
            esCorrecto = false;
        }
        return esCorrecto;
    }

    /**
     * Comprueba que los datos introducidos del médico son correctos
     *
     * @param nombre        del perro del que desea comprobar los datos
     * @param apellidos     del perro del que desea comprobar los datos
     * @param identificador del perro del que desea comprobar los datos
     * @param telefono      del perro del que desea comprobar los datos
     * @return
     */
    private boolean checkMedico(String nombre, String apellidos, String identificador, String telefono) {
        boolean esCorrecto = true;

        if (!soloNumeros(telefono) || telefono.length() <= 0) {
            esCorrecto = false;
        } else if (!soloNumeros(identificador) || identificador.length() <= 0) {
            esCorrecto = false;
        } else if (nombre.length() < 1) {
            esCorrecto = false;
        } else if (apellidos.length() < 1) {
            esCorrecto = false;
        }
        return esCorrecto;
    }

    /**
     * Comprueba si el médico introducido está repetido
     *
     * @param nombre        del medico del que desea comprobar los datos
     * @param apellidos     del medico del que desea comprobar los datos
     * @param identificador del medico del que desea comprobar los datos
     * @param telefono      del medico del que desea comprobar los datos
     * @param horario       del medico del que desea comprobar los datos
     * @return
     */
    private boolean medicoRepetido(String nombre, String apellidos, String identificador, String telefono, String horario) {
        boolean repetido = false;
        Medico medico = new Medico(nombre, apellidos, identificador, telefono, horario);
        for (Medico medicoActual : modelo.getMedicos()) {
            if (medico.getIdentificador().equals(medicoActual.getIdentificador())) {
                repetido = true;
            }
        }
        return repetido;
    }

    /**
     * Permite seleccionar una ruta ara guardar los datos de la aplicación
     */
    private void guardarDatos() {
        JFileChooser selector = new JFileChooser();
        int opt = selector.showSaveDialog(vista);

        if (opt == JFileChooser.APPROVE_OPTION) {
            File fichero = selector.getSelectedFile();

            try {
                modelo.guardarDatos(fichero);

            } catch (IOException e1) {

                e1.printStackTrace();
                Util.mostrarDialogoError(resourceBundle.getString("errorAlGuardar"));
            }
        }
    }

    /**
     * Permite seleccionar un archivo de datos para cargarlos en la aplicación
     */
    private void cargarDatos() {
        JFileChooser selector = new JFileChooser();
        int opt = selector.showOpenDialog(vista);

        if (opt == JFileChooser.APPROVE_OPTION) {
            File fichero = selector.getSelectedFile();

            try {
                modelo.cargarDatos(fichero);


            } catch (IOException | ClassNotFoundException e1) {

                e1.printStackTrace();
                Util.mostrarDialogoError(resourceBundle.getString("errorAlAbrir"));
            }

        }
    }

    /**
     * muestra un gráfico de los perros clasificados por edad
     */
    private void mostrarGrafica() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Perro perro : modelo.getPerros()) {
            dataset.setValue(perro.getEdad(), resourceBundle.getString("grafica1Anos"), perro.getNombre());
        }

        JFreeChart grafica = ChartFactory.createBarChart(resourceBundle.getString("grafica1PerrosEdad"), resourceBundle.getString("grafica1Perro"), resourceBundle.getString("grafica1Edad"), dataset);

        ChartFrame frame = new ChartFrame(resourceBundle.getString("grafica1Diagrama"), grafica);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * muestra un gráfico de los perros que tienen y no tienen dueño
     */
    private void mostrarGraficaDos() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        int conDueno = 0;
        int sinDueno = 0;

        for (Perro perro : modelo.getPerros()) {
            if (perro.getDuenos().size() < 1) {
                sinDueno++;
            } else {
                conDueno++;
            }
        }

        dataset.setValue(MessageFormat.format(resourceBundle.getString("grafica2PerrosSinDueno"), sinDueno), sinDueno);
        dataset.setValue(MessageFormat.format(resourceBundle.getString("grafica2PerrosConDueno"), conDueno), conDueno);

        JFreeChart jFreeChart = ChartFactory.createPieChart(resourceBundle.getString("grafica2Perros"), dataset);

        ChartFrame frame = new ChartFrame(resourceBundle.getString("grafica1Diagrama"), jFreeChart);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Muestra un informe de los médicos y sus datos.
     */
    private void mostrarInforme() {
        try {
            JasperReport informe = (JasperReport) JRLoader.loadObject((getClass().getResource("/Medicos.jasper")));
            JRBeanCollectionDataSource coleccion = new JRBeanCollectionDataSource(modelo.getMedicos());
            JasperPrint print = JasperFillManager.fillReport(informe, null, coleccion);
            JasperViewer.viewReport(print, false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    /**
     * Muestra un informe con los datos de los perros y el médico que tienen asociado.
     */
    private void mostrarInformeDos() {
        try {
            JasperReport informe = (JasperReport) JRLoader.loadObject((getClass().getResource("/Perros.jasper")));
            JRBeanCollectionDataSource coleccion = new JRBeanCollectionDataSource(modelo.getPerros());
            JasperPrint print = JasperFillManager.fillReport(informe, null, coleccion);
            JasperViewer.viewReport(print, false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    private void mostrarManual() {

    }

    /**
     * Despliega un cuadro de diálogo al cerrar la aplicación
     *
     * @param arg0
     */
    public void windowClosing(WindowEvent arg0) {
        if (modelo.isDatosModificados()) {
            int opt = Util.mostrarDialogoSiNo(resourceBundle.getString("cerrarVentana"));
            if (opt == Util.ACEPTAR) {
                guardarDatos();
            }
            System.exit(0);
        }
    }

    /**
     * Muestra los datos del objeto seleccionado en cada una de las listas
     *
     * @param e
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        Cliente cliente = (Cliente) vista.listaClientes.getSelectedValue();
        if (cliente != null) {
            mostrarValoresCliente(cliente);
            refreshPerrosDeCliente(cliente);
        }
        Perro perro = (Perro) vista.listaPerros.getSelectedValue();
        if (perro != null) {
            mostrarValoresPerro(perro);
        }
        Medico medico = (Medico) vista.listMedicos.getSelectedValue();
        if (medico != null) {
            mostrarValoresMedico(medico);
            refreshPerrosDeMedico(medico);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {


    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
