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
public void iniciarFormulaTrabajadores() throws SQLException {

        formtare.setTitle("Formulario Trabajadores");// titulo
        formtare.setLocationRelativeTo(null);// ubicacion
        formtare.setVisible(true); // mostrar el formulario
        formtare.jtbTarea.setModel(modelo); // muestra la tabla
        formtare.txtId.setEnabled(false);//bloquea el campo "txtIdMaquina"
        formtare.jtbTarea.addMouseListener(this);
       // formtare.cbxPrioridad.setModel(daotare.());
        formtare.cbxMaquina.setModel(daotare.obtenerMaquinas());
        formtare.cbxTipoTarea.setModel(daotare.obtenerTipoTarea());
        formtare.cbxOrdentTabajo.setModel(daotare.obtenerGeneraOrdenesTrabajo());
        formtare.cbxTipoMantencion.setModel(daotare.obtenerTipoMantencion());
        formtare.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //dispose , permite cerrar solo la ventana seleccionada
        llenarTabla();
    }
 String[] columnas = {"id_tarea", "rut", "nombres", "apellido_paterno", "apellido_materno", "cargo_fk"};
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
            char C = e.getKeyChar();

            if (Character.isDigit(C)) {

                formtare.getToolkit().beep();
                e.consume();
                JOptionPane.showMessageDialog(null, "Ingrese solo Letras");
                formtare.txtDescripcion.setCursor(null); // nombre del campo

            } else if ((int) e.getKeyChar() > 32 && (int) e.getKeyChar() <= 47
                    || (int) e.getKeyChar() >= 58 && (int) e.getKeyChar() <= 64
                    || (int) e.getKeyChar() >= 91 && (int) e.getKeyChar() <= 96
                    || (int) e.getKeyChar() >= 123 && (int) e.getKeyChar() <= 255) {

                formtare.getToolkit().beep();
                e.consume();
                JOptionPane.showMessageDialog(null, "Ingrese solo Letras");
                formtare.txtDescripcion.setCursor(null); // nombre del campo

            }

        }

        if (e.getSource() == formtare.txtDuracion) {
            char C = e.getKeyChar();

            if (Character.isDigit(C)) {

                formtare.getToolkit().beep();
                e.consume();
                JOptionPane.showMessageDialog(null, "Ingrese solo Letras");
                formtare.txtDuracion.setCursor(null); // nombre del campo

            } else if ((int) e.getKeyChar() > 32 && (int) e.getKeyChar() <= 47
                    || (int) e.getKeyChar() >= 58 && (int) e.getKeyChar() <= 64
                    || (int) e.getKeyChar() >= 91 && (int) e.getKeyChar() <= 96
                    || (int) e.getKeyChar() >= 123 && (int) e.getKeyChar() <= 255) {

                formtare.getToolkit().beep();
                e.consume();
                JOptionPane.showMessageDialog(null, "Ingrese solo Letras");
                formtare.txtDuracion.setCursor(null); // nombre del campo

            }

        }
        if (e.getSource() == formtare.txtPeriodo) {
            char C = e.getKeyChar();

            if (Character.isDigit(C)) {

                formtare.getToolkit().beep();
                e.consume();
                JOptionPane.showMessageDialog(null, "Ingrese solo Letras");
                formtare.txtPeriodo.setCursor(null); // nombre del campo

            } else if ((int) e.getKeyChar() > 32 && (int) e.getKeyChar() <= 47
                    || (int) e.getKeyChar() >= 58 && (int) e.getKeyChar() <= 64
                    || (int) e.getKeyChar() >= 91 && (int) e.getKeyChar() <= 96
                    || (int) e.getKeyChar() >= 123 && (int) e.getKeyChar() <= 255) {

                formtare.getToolkit().beep();
                e.consume();
                JOptionPane.showMessageDialog(null, "Ingrese solo Letras");
                formtare.txtPeriodo.setCursor(null); // nombre del campo

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
