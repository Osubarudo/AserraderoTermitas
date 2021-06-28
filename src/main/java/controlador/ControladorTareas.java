/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.DAOTarea;
import modelo.Tarea;
import vista.FormTareas;

/**
 *
 * @author Althon Vader
 */
public class ControladorTareas implements ActionListener, MouseListener, KeyListener {

    private Tarea tare;
    private DAOTarea daotare;
    private FormTareas formtare;

    public ControladorTareas(Tarea tare, DAOTarea daotare, FormTareas formtare) {
        this.tare = tare;
        this.daotare = daotare;
        this.formtare = formtare;
        this.formtare.btnAñadirTarea.addActionListener(this); // agregar boton "agregar"
        this.formtare.btnModificarTarea.addActionListener(this);
        this.formtare.btnEliminatTarea.addActionListener(this);
        formtare.jtbTarea.addMouseListener(this);
        formtare.txtDescripcion.addKeyListener(this);
        formtare.txtDuracion.addKeyListener(this);
        formtare.cbxPrioridad.addKeyListener(this);
        formtare.txtPeriodo.addKeyListener(this);
        formtare.cbxMaquina.addKeyListener(this);
        formtare.cbxTipoTarea.addKeyListener(this);
        formtare.cbxOrdentTabajo.addKeyListener(this);
        formtare.cbxTipoMantencion.addKeyListener(this);
    }

    public void iniciarFormtareas() throws SQLException {

        formtare.setTitle("Formulario Tareas");// titulo
        formtare.setLocationRelativeTo(null);// ubicacion
        formtare.setVisible(true); // mostrar el formulario
        formtare.jtbTarea.setModel(modelo); // muestra la tabla
        formtare.txtId.setEnabled(false);//bloquea el campo "txtIdMaquina"
        formtare.jtbTarea.addMouseListener(this);
        formtare.cbxPrioridad.setModel(daotare.obtenerPrioridad());
        formtare.cbxMaquina.setModel(daotare.obtenerMaquinas());
        formtare.cbxTipoTarea.setModel(daotare.obtenerTipoTarea());
        formtare.cbxOrdentTabajo.setModel(daotare.obtenerOT());
        formtare.cbxTipoMantencion.setModel(daotare.obtenerTipoMantencion());
        formtare.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        llenarTabla();
    }
    String[] columnas = {"ID", "Descripción", "Duración", "Prioridad", "Máquina", "Periodo", "Tipo Tarea", "OT", "Tipo Mantención"};
    ArrayList<Object[]> datos = new ArrayList<>();
    DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

    @Override
    public void actionPerformed(ActionEvent e) {
        //boton agregar
        if (formtare.btnAñadirTarea == e.getSource()) { //detecta la pulsacion del boton agregar

            tare.setDescripcion(formtare.txtDescripcion.getText()); // toma el usuario ingresa en el formulario y lo guarda en el atributo nombre maquina
            tare.setDuracion(Integer.parseInt(formtare.txtDuracion.getText()));
            tare.setPrioridad(formtare.cbxPrioridad.getSelectedItem().toString());
            tare.setMaquina(formtare.cbxMaquina.getSelectedItem().toString());
            tare.setPeriodo(Integer.parseInt(formtare.txtPeriodo.getText()));
            tare.setTipoTarea(formtare.cbxTipoTarea.getSelectedItem().toString());
            tare.setOt(formtare.cbxOrdentTabajo.getSelectedItem().toString());
            tare.setTipoMantencion(formtare.cbxTipoMantencion.getSelectedItem().toString());

            if (tare.validarCamposVacios()) {
                if (daotare.Agregar(tare)) {
                    JOptionPane.showMessageDialog(null, "Agregado correctamente");
                    llenarTabla();
                    limpiar();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al Agregar");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No debe dejar campos  vacios");
            }
        }

        // boton modificar
        if (formtare.btnModificarTarea == e.getSource()) {

            tare.setDescripcion(formtare.txtDescripcion.getText());
            tare.setDuracion(Integer.parseInt(formtare.txtDuracion.getText()));
            tare.setPrioridad(formtare.cbxPrioridad.getSelectedItem().toString());
            tare.setMaquina(formtare.cbxMaquina.getSelectedItem().toString());
            tare.setPeriodo(Integer.parseInt(formtare.txtPeriodo.getText()));
            tare.setTipoTarea(formtare.cbxTipoTarea.getSelectedItem().toString());
            tare.setOt(formtare.cbxOrdentTabajo.getSelectedItem().toString());
            tare.setTipoMantencion(formtare.cbxTipoMantencion.getSelectedItem().toString());
            tare.setIdTarea(Integer.parseInt(formtare.txtId.getText()));

            if (daotare.Modificar(tare)) {
                JOptionPane.showMessageDialog(null, "Registro Actualizado");
                llenarTabla();
                limpiar();

            } else {
                JOptionPane.showMessageDialog(null, "Error al Modificar");
                limpiar();
            }
        }
        //boton eliminar
        if (e.getSource() == formtare.btnEliminatTarea) {

            tare.setIdTarea(Integer.parseInt(formtare.txtId.getText()));
            if (daotare.Eliminar(tare)) {
                JOptionPane.showMessageDialog(null, "Eliminado");
                llenarTabla();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al Eliminar");
                limpiar();
            }

        }
    }

    public void llenarTabla() {  //carga los datos de la bace de datos a la tabla
        modelo.setRowCount(0);
        datos = daotare.consultar();

        // for each
        for (Object[] obj : datos) {
            modelo.addRow(obj);
        }
        formtare.jtbTarea.setModel(modelo);

    }

    public void limpiar() {
        formtare.txtId.setText("");
        formtare.txtDescripcion.setText("");
        formtare.txtDuracion.setText("");
        formtare.txtPeriodo.setText("");
        formtare.cbxMaquina.setSelectedIndex(0);
        formtare.cbxPrioridad.setSelectedIndex(0);
        formtare.cbxTipoMantencion.setSelectedIndex(0);
        formtare.cbxTipoTarea.setSelectedIndex(0);
        formtare.cbxOrdentTabajo.setSelectedIndex(0);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == formtare.jtbTarea) {
            formtare.txtId.setText(String.valueOf(formtare.jtbTarea.getValueAt(formtare.jtbTarea.getSelectedRow(), 0)));
            formtare.txtDescripcion.setText(String.valueOf(formtare.jtbTarea.getValueAt(formtare.jtbTarea.getSelectedRow(), 1)));
            formtare.txtDuracion.setText(String.valueOf(formtare.jtbTarea.getValueAt(formtare.jtbTarea.getSelectedRow(), 2)));
            formtare.cbxPrioridad.setSelectedItem(String.valueOf(formtare.jtbTarea.getValueAt(formtare.jtbTarea.getSelectedRow(), 3)));
            formtare.cbxMaquina.setSelectedItem(String.valueOf(formtare.jtbTarea.getValueAt(formtare.jtbTarea.getSelectedRow(), 4)));
            formtare.txtPeriodo.setText(String.valueOf(formtare.jtbTarea.getValueAt(formtare.jtbTarea.getSelectedRow(), 5)));
            formtare.cbxTipoTarea.setSelectedItem(String.valueOf(formtare.jtbTarea.getValueAt(formtare.jtbTarea.getSelectedRow(), 6)));
            formtare.cbxOrdentTabajo.setSelectedItem(String.valueOf(formtare.jtbTarea.getValueAt(formtare.jtbTarea.getSelectedRow(), 7)));
            formtare.cbxTipoMantencion.setSelectedItem(String.valueOf(formtare.jtbTarea.getValueAt(formtare.jtbTarea.getSelectedRow(), 8)));

        }
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

    @Override
    public void keyTyped(KeyEvent e) {

        if (e.getSource() == formtare.txtDescripcion) {

        }

        if (e.getSource() == formtare.txtDuracion) {
            char c = e.getKeyChar();
            if (e.getKeyChar() == com.sun.glass.events.KeyEvent.VK_ENTER) {//Al presionar enter pasa el foco al siguiente campo
                //txtPeso.requestFocus();//hay que definir a que componente pasa el foco
            } else if (e.getKeyChar() == com.sun.glass.events.KeyEvent.VK_BACKSPACE || e.getKeyChar() == com.sun.glass.events.KeyEvent.VK_DELETE) {//lo utilizo para que no haga el sonido del beep al borrar
                //
            } else if (c < '0' || c > '9') {
                formtare.getToolkit().beep();
                e.consume();
            }

        }
        if (e.getSource() == formtare.txtPeriodo) {
            char c = e.getKeyChar();
            if (e.getKeyChar() == com.sun.glass.events.KeyEvent.VK_ENTER) {//Al presionar enter pasa el foco al siguiente campo
                //txtPeso.requestFocus();//hay que definir a que componente pasa el foco
            } else if (e.getKeyChar() == com.sun.glass.events.KeyEvent.VK_BACKSPACE || e.getKeyChar() == com.sun.glass.events.KeyEvent.VK_DELETE) {//lo utilizo para que no haga el sonido del beep al borrar
                //
            } else if (c < '0' || c > '9') {
                formtare.getToolkit().beep();
                e.consume();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
