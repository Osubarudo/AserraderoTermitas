/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import vista.FormBienvenido;
import vista.FormMaquina;
import vista.FormOrdenTrabajo;
import vista.FormSolicitud;
import vista.FormTareas;
import vista.FormTrabajador;

/**
 *
 * @author Althon Vader
 */
public class ControladorBienvenido implements ActionListener {

    private FormBienvenido vistabien;

    public ControladorBienvenido(FormBienvenido vistabie) {
        this.vistabien = vistabie;
        this.vistabien.menuMaquina.addActionListener(this);
        this.vistabien.menuOrdenTra.addActionListener(this);
        this.vistabien.menuSolicitud.addActionListener(this);
        this.vistabien.menuTrabajador.addActionListener(this);
        this.vistabien.menuTarea.addActionListener(this);

    }

    public void iniciarFormBienvenida() {
        vistabien.setTitle("Menu de Bienvenida");
        vistabien.setLocationRelativeTo(null);
        vistabien.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (vistabien.menuMaquina == e.getSource()) {
            FormMaquina formaq = new FormMaquina();
            Maquina ma = new Maquina();
            DAOMaquina daoma = new DAOMaquina();
            ControladorMaquina ctrm = new ControladorMaquina(formaq, ma, daoma);
            ctrm.iniciarFormMaquina();
        }

        if (vistabien.menuOrdenTra == e.getSource()) {
            FormOrdenTrabajo formortra = new FormOrdenTrabajo();
            OrdenTrabajo ordtra = new OrdenTrabajo();
            DAOOrdenTrabajo daoordtra = new DAOOrdenTrabajo();
            ControladorOrdenTrabajo ctrordtra = new ControladorOrdenTrabajo(formortra, ordtra, daoordtra);
            try {
                ctrordtra.iniciarFormOrdenTrabajo();
            } catch (SQLException ex) {
                Logger.getLogger(ControladorBienvenido.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (vistabien.menuSolicitud == e.getSource()) {
            FormSolicitud formsolic = new FormSolicitud();
            Solicitud solic = new Solicitud();
            DAOSolicitud daosolic = new DAOSolicitud();
            ControladorSolicitud ctrsolic = new ControladorSolicitud(formsolic, solic, daosolic);
            try {
                ctrsolic.iniciarFormOrdenTrabajo();
            } catch (SQLException ex) {
                Logger.getLogger(ControladorBienvenido.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (vistabien.menuTrabajador == e.getSource()) {
                FormTrabajador formtrab = new FormTrabajador();
                Trabajador tra = new Trabajador();
                DAOTrabajador daot = new DAOTrabajador();
                ControladorTrabajador ctrtra = new ControladorTrabajador(formtrab, daot, formtrab);
                try {
                    ctrtra.iniciarFormTrabajadores();
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorBienvenido.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            if (vistabien.menuTarea == e.getSource()) {
                FormTareas formtare = new FormTareas();
                Tareas tare = new Tareas();
                DAOTareas daotare = new DAOTareas();
                ControladorTareas ctrtare = new ControladorTareas(formtare, tare, daotare);
                try {
                    ctrtare.iniciarFormTareas();
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorBienvenido.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
    }
}
