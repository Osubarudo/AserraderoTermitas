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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.DAOOrdenTrabajo;
import modelo.OrdenTrabajo;
import vista.FormOrdenTrabajo;

/**
 *
 * @author Althon Vader
 */
public class ControladorOrdenTrabajo implements ActionListener, MouseListener {

    private FormOrdenTrabajo vista;
    private OrdenTrabajo model;
    private DAOOrdenTrabajo dao;

    public ControladorOrdenTrabajo(FormOrdenTrabajo vista, OrdenTrabajo model, DAOOrdenTrabajo dao) {
        this.vista = vista;
        this.model = model;
        this.dao = dao;
        this.vista.btnAñadirOrden.addActionListener(this);
        this.vista.btnModificarOrden.addActionListener(this);
        this.vista.btnEliminarOrden.addActionListener(this);
        this.vista.jtbOrdenTrabajo.addMouseListener(this);
    }

    public void inciarForm() throws SQLException {
        vista.setTitle("Formulario Ordenes de Trabajo");
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
        vista.txtIdOt.setEnabled(false);
        vista.cbxGeneradoOt.setModel(dao.obtenerGenerador());
        vista.cbxResponsableOt.setModel(dao.obtenerResponsable());
        vista.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        llenarTabla();
    }

    String[] columnas = {"ID", "Notas", "Generador", "Responsable"};
    ArrayList<Object[]> datos = new ArrayList<>();
    DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

    @Override
    public void actionPerformed(ActionEvent e) {

        if (vista.btnAñadirOrden == e.getSource()) {
            model.setNotas(vista.txtNota.getText());
            model.setGenera(vista.cbxGeneradoOt.getSelectedItem().toString());
            model.setResponsable(vista.cbxResponsableOt.getSelectedItem().toString());

            if (model.validarCamposVacios()) {
                if (dao.Agregar(model)) {
                    JOptionPane.showMessageDialog(null, "Agregado correctamente");
                    llenarTabla();
                    limpiar();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al agregar");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Rellene campos vacios");
            }

        }

        if (vista.btnModificarOrden == e.getSource()) {
            model.setIdOt(Integer.parseInt(vista.txtIdOt.getText()));
            model.setNotas(vista.txtNota.getText());
            model.setGenera(vista.cbxGeneradoOt.getSelectedItem().toString());
            model.setResponsable(vista.cbxResponsableOt.getSelectedItem().toString());

            if (model.validarCamposVacios()) {
                if (dao.Modificar(model)) {
                    JOptionPane.showMessageDialog(null, "Registro Actualizado");
                    llenarTabla();
                    limpiar();

                } else {
                    JOptionPane.showMessageDialog(null, "Error al Modificar");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Rellene campos vacios");
            }

        }

        if (vista.btnEliminarOrden == e.getSource()) {
            model.setIdOt(Integer.parseInt(vista.txtIdOt.getText()));
            if (dao.Eliminar(model)) {
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
        datos = dao.consultar();

        // for each
        for (Object[] obj : datos) {
            modelo.addRow(obj);
        }
        vista.jtbOrdenTrabajo.setModel(modelo);

    }

    public void limpiar() {
        vista.txtIdOt.setText("");
        vista.txtNota.setText("");
        vista.cbxGeneradoOt.setSelectedIndex(0);
        vista.cbxResponsableOt.setSelectedIndex(0);
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        
        vista.txtIdOt.setText(String.valueOf(vista.jtbOrdenTrabajo.getValueAt(vista.jtbOrdenTrabajo.getSelectedRow(), 0)));
        vista.txtNota.setText(String.valueOf(vista.jtbOrdenTrabajo.getValueAt(vista.jtbOrdenTrabajo.getSelectedRow(), 1)));
        vista.cbxGeneradoOt.setSelectedItem(String.valueOf(vista.jtbOrdenTrabajo.getValueAt(vista.jtbOrdenTrabajo.getSelectedRow(), 2)));
        vista.cbxResponsableOt.setSelectedItem(String.valueOf(vista.jtbOrdenTrabajo.getValueAt(vista.jtbOrdenTrabajo.getSelectedRow(), 3)));
        
//        String idGen = String.valueOf(vista.jtbOrdenTrabajo.getValueAt(vista.jtbOrdenTrabajo.getSelectedRow(), 2));
//        try {
//            vista.cbxGeneradoOt.setSelectedItem(dao.enviarACombo(idGen));
//        } catch (SQLException ex) {
//            Logger.getLogger(ControladorOrdenTrabajo.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        String idRes = String.valueOf(vista.jtbOrdenTrabajo.getValueAt(vista.jtbOrdenTrabajo.getSelectedRow(), 3));
//        try {
//            vista.cbxResponsableOt.setSelectedItem(dao.enviarACombo(idRes));
//        } catch (SQLException ex) {
//            Logger.getLogger(ControladorOrdenTrabajo.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
    }

    @Override
    public void mousePressed(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
