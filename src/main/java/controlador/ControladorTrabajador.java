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
import modelo.DAOTrabajador;
import modelo.Trabajador;
import vista.FormTrabajador;

/**
 *
 * @author Althon Vader
 */
public class ControladorTrabajador implements ActionListener, MouseListener, KeyListener {

    private Trabajador tra;
    private DAOTrabajador daot;
    private FormTrabajador formtra;

    public ControladorTrabajador(Trabajador tra, DAOTrabajador daot, FormTrabajador formtra) {
        this.tra = tra;
        this.daot = daot;
        this.formtra = formtra;
        this.formtra.btnAgregarTrabajador.addActionListener(this); // agregar boton "agregar"
        this.formtra.btnModificarTrabajador.addActionListener(this);
        this.formtra.btnEliminarTrabajador.addActionListener(this);
        formtra.jttbTrabajador.addMouseListener(this);
        formtra.txtnombreTrabajador.addKeyListener(this);
        formtra.txtApellidoPat.addKeyListener(this);
        formtra.txtApellidoMat.addKeyListener(this);
    }

    public void iniciarFormulaTrabajadores() throws SQLException {

        formtra.setTitle("Formulario Trabajadores");// titulo
        formtra.setLocationRelativeTo(null);// ubicacion
        formtra.setVisible(true); // mostrar el formulario
        formtra.jttbTrabajador.setModel(modelo); // muestra la tabla
        formtra.txtId.setEnabled(false);//bloquea el campo "txtIdMaquina"
        formtra.jttbTrabajador.addMouseListener(this);
        formtra.cbxCargo.setModel(daot.obtenerCargo());
        formtra.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //dispose , permite cerrar solo la ventana seleccionada
        llenarTabla();
    }

    String[] columnas = {"id_trabajador", "rut", "nombres", "apellido_paterno", "apellido_materno", "cargo_fk"};
    ArrayList<Object[]> datos = new ArrayList<>();
    DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

    @Override
    public void actionPerformed(ActionEvent e) {
        //boton agregar
        if (formtra.btnAgregarTrabajador == e.getSource()) { //detecta la pulsacion del boton agregar

            tra.setRut(formtra.txtRut.getText()); // toma el usuario ingresa en el formulario y lo guarda en el atributo nombre maquina
            tra.setNombres(formtra.txtnombreTrabajador.getText());
            tra.setPaterno(formtra.txtApellidoPat.getText());
            tra.setMaterno(formtra.txtApellidoMat.getText());
            tra.setCargos(formtra.cbxCargo.getSelectedItem().toString());
            
            if (tra.validarRut(formtra.txtRut.getText())) {
                if (daot.Agregar(tra)) { // se agraga el objaeto maquina "ma"
                    JOptionPane.showMessageDialog(null, "agregado exitoso");

                    llenarTabla();
                    limpiar();

                } else {
                    JOptionPane.showMessageDialog(null, "agregado fallida");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Rut Invalido");
            }

            if (tra.validarCamposVacios()) {

            } else {
                JOptionPane.showMessageDialog(null, "No debe dejar campos  vacios");

            }

        }

        // boton modificar
        if (formtra.btnModificarTrabajador == e.getSource()) {

            tra.setRut(formtra.txtRut.getText());
            tra.setNombres(formtra.txtnombreTrabajador.getText());
            tra.setPaterno(formtra.txtApellidoPat.getText());
            tra.setMaterno(formtra.txtApellidoMat.getText());
            tra.setCargos(formtra.cbxCargo.getSelectedItem().toString());
            tra.setIdTrabajador(Integer.parseInt(formtra.txtId.getText()));
            if (daot.Modificar(tra)) {
                JOptionPane.showMessageDialog(null, "Registro Actualizado");
                llenarTabla();
                limpiar();

            } else {
                JOptionPane.showMessageDialog(null, "Error al Modificar");
                limpiar();
            }
        }

        //boton eliminar
        if (e.getSource() == formtra.btnEliminarTrabajador) {

            tra.setIdTrabajador(Integer.parseInt(formtra.txtId.getText()));
            if (daot.Eliminar(tra)) {
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
        datos = daot.consultar();

        // for each
        for (Object[] obj : datos) {
            modelo.addRow(obj);
        }
        formtra.jttbTrabajador.setModel(modelo);

    }

    public void limpiar() {

        formtra.txtId.setText("");
        formtra.txtRut.setText("");
        formtra.txtnombreTrabajador.setText("");
        formtra.txtApellidoPat.setText("");
        formtra.txtApellidoMat.setText("");
        formtra.cbxCargo.setSelectedIndex(0);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == formtra.jttbTrabajador) {
            formtra.txtId.setText(String.valueOf(formtra.jttbTrabajador.getValueAt(formtra.jttbTrabajador.getSelectedRow(), 0)));
            formtra.txtRut.setText(String.valueOf(formtra.jttbTrabajador.getValueAt(formtra.jttbTrabajador.getSelectedRow(), 1)));
            formtra.txtnombreTrabajador.setText(String.valueOf(formtra.jttbTrabajador.getValueAt(formtra.jttbTrabajador.getSelectedRow(), 2)));
            formtra.txtApellidoPat.setText(String.valueOf(formtra.jttbTrabajador.getValueAt(formtra.jttbTrabajador.getSelectedRow(), 3)));
            formtra.txtApellidoMat.setText(String.valueOf(formtra.jttbTrabajador.getValueAt(formtra.jttbTrabajador.getSelectedRow(), 4)));
            formtra.cbxCargo.setSelectedItem(String.valueOf(formtra.jttbTrabajador.getValueAt(formtra.jttbTrabajador.getSelectedRow(), 5)));
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
    public void keyTyped(KeyEvent evt) {

        if (evt.getSource() == formtra.txtnombreTrabajador) {
            char C = evt.getKeyChar();

            if (Character.isDigit(C)) {

                formtra.getToolkit().beep();
                evt.consume();
                JOptionPane.showMessageDialog(null, "Ingrese solo Letras");
                formtra.txtnombreTrabajador.setCursor(null); // nombre del campo

            } else if ((int) evt.getKeyChar() > 32 && (int) evt.getKeyChar() <= 47
                    || (int) evt.getKeyChar() >= 58 && (int) evt.getKeyChar() <= 64
                    || (int) evt.getKeyChar() >= 91 && (int) evt.getKeyChar() <= 96
                    || (int) evt.getKeyChar() >= 123 && (int) evt.getKeyChar() <= 255) {

                formtra.getToolkit().beep();
                evt.consume();
                JOptionPane.showMessageDialog(null, "Ingrese solo Letras");
                formtra.txtnombreTrabajador.setCursor(null); // nombre del campo

            }

        }

        if (evt.getSource() == formtra.txtApellidoPat) {
            char C = evt.getKeyChar();

            if (Character.isDigit(C)) {

                formtra.getToolkit().beep();
                evt.consume();
                JOptionPane.showMessageDialog(null, "Ingrese solo Letras");
                formtra.txtApellidoPat.setCursor(null); // nombre del campo

            } else if ((int) evt.getKeyChar() > 32 && (int) evt.getKeyChar() <= 47
                    || (int) evt.getKeyChar() >= 58 && (int) evt.getKeyChar() <= 64
                    || (int) evt.getKeyChar() >= 91 && (int) evt.getKeyChar() <= 96
                    || (int) evt.getKeyChar() >= 123 && (int) evt.getKeyChar() <= 255) {

                formtra.getToolkit().beep();
                evt.consume();
                JOptionPane.showMessageDialog(null, "Ingrese solo Letras");
                formtra.txtApellidoPat.setCursor(null); // nombre del campo

            }

        }

        if (evt.getSource() == formtra.txtApellidoMat) {
            char C = evt.getKeyChar();

            if (Character.isDigit(C)) {

                formtra.getToolkit().beep();
                evt.consume();
                JOptionPane.showMessageDialog(null, "Ingrese solo Letras");
                formtra.txtApellidoMat.setCursor(null); // nombre del campo

            } else if ((int) evt.getKeyChar() > 32 && (int) evt.getKeyChar() <= 47
                    || (int) evt.getKeyChar() >= 58 && (int) evt.getKeyChar() <= 64
                    || (int) evt.getKeyChar() >= 91 && (int) evt.getKeyChar() <= 96
                    || (int) evt.getKeyChar() >= 123 && (int) evt.getKeyChar() <= 255) {

                formtra.getToolkit().beep();
                evt.consume();
                JOptionPane.showMessageDialog(null, "Ingrese solo Letras");
                formtra.txtApellidoMat.setCursor(null); // nombre del campo

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
