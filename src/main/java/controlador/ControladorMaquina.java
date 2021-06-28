/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.DAOMaquina;
import modelo.Maquina;
import vista.FormMaquina;

/**
 *
 * @author Althon Vader
 */
public class ControladorMaquina implements ActionListener, MouseListener {

    private Maquina maq;
    private DAOMaquina daomaq;
    private FormMaquina formaq;

    public ControladorMaquina(Maquina maq, DAOMaquina daomaq, FormMaquina formaq) {
        this.maq = maq;
        this.daomaq = daomaq;
        this.formaq = formaq;
        this.formaq.btnAgregarMaquina.addActionListener(this);
        this.formaq.btnModificarMaquina.addActionListener(this);
        this.formaq.btnEliminarMaquina.addActionListener(this);
        this.formaq.jtbMaquina.addMouseListener(this);
    }

    public void iniciarFormMaquina() {

        formaq.setTitle("Formulario Máquinas");// titulo
        formaq.setLocationRelativeTo(null);// ubicacion
        formaq.setVisible(true); // mostrar el formulario
        formaq.jtbMaquina.setModel(modelo); // muestra la tabla
        formaq.txtIdmaq.setEnabled(false);//bloquea el campo "txtIdMaquina"
        formaq.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //dispose , permite cerrar solo la ventana seleccionada
        llenarTabla();
    }
    
    String[] columnas = {"id", "Nombre", "ubicacion", "tipo"};
    ArrayList<Object[]> datos = new ArrayList<>();
    DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

    @Override
    public void actionPerformed(ActionEvent e) {
        
        //boton agregar
        if (formaq.btnAgregarMaquina == e.getSource()) { 

            maq.setNombreMaquina(formaq.txtNombreMaq.getText()); // toma el usuario ingresa en el formulario y lo guarda en el atributo nombre maquina
            maq.setUbicacionMaquina(formaq.txtUbicacionMaq.getText());
            maq.setTipoMaquina(formaq.txtTipoMaq.getText());

            if (daomaq.Agregar(maq)) { // se agraga el objaeto maquina "ma"
                JOptionPane.showMessageDialog(null, "agregado exitoso");

                llenarTabla();
                limpiar();

            } else {
                JOptionPane.showMessageDialog(null, "agregado fallida");
            }

        }
        
        
         //  btn Modificar o actualizar

        if (formaq.btnModificarMaquina == e.getSource()) {

            maq.setIdMaquina(Integer.parseInt(formaq.txtIdmaq.getText()));
            maq.setNombreMaquina(formaq.txtNombreMaq.getText());
            maq.setUbicacionMaquina(formaq.txtUbicacionMaq.getText());
            maq.setTipoMaquina(formaq.txtTipoMaq.getText());

            if (daomaq.Modificar(maq)) {
                JOptionPane.showMessageDialog(null, "Registro Actualizado");
                llenarTabla();
                limpiar();

            } else {
                JOptionPane.showMessageDialog(null, "Error al Eliminar");
                limpiar();
            }
        }
        
        // btn eliminar 
        if (e.getSource() == formaq.btnEliminarMaquina) {

            maq.setIdMaquina(Integer.parseInt(formaq.txtIdmaq.getText()));
            if (daomaq.Eliminar(maq)) {
                JOptionPane.showMessageDialog(null, "Eliminado");
                llenarTabla();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al Eliminar");
                limpiar();
            }

        }

    }

    public void llenarTabla() {  
        modelo.setRowCount(0);
        datos = daomaq.consultar();

        
        for (Object[] obj : datos) {
            modelo.addRow(obj);
        }
        formaq.jtbMaquina.setModel(modelo);
    
    }
    public void limpiar() {

        formaq.txtNombreMaq.setText("");
        formaq.txtUbicacionMaq.setText("");
        formaq.txtTipoMaq.setText("");
        formaq.txtIdmaq.setText("");
    }


@Override
        public void mouseClicked(MouseEvent e) {
            
        if (e.getSource() == formaq.jtbMaquina) {
            formaq.txtNombreMaq.setText(String.valueOf(formaq.jtbMaquina.getValueAt(formaq.jtbMaquina.getSelectedRow(), 1)));
            formaq.txtUbicacionMaq.setText(String.valueOf(formaq.jtbMaquina.getValueAt(formaq.jtbMaquina.getSelectedRow(), 2)));
            formaq.txtTipoMaq.setText(String.valueOf(formaq.jtbMaquina.getValueAt(formaq.jtbMaquina.getSelectedRow(), 3)));
            formaq.txtIdmaq.setText(String.valueOf(formaq.jtbMaquina.getValueAt(formaq.jtbMaquina.getSelectedRow(), 0)));

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

}
