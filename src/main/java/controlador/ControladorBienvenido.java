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
import modelo.DAOMaquina;
import modelo.DAOOrdenTrabajo;
import modelo.DAOTarea;
import modelo.DAOTrabajador;
import modelo.Maquina;
import modelo.OrdenTrabajo;
import modelo.Tarea;
import modelo.Trabajador;
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

    private FormBienvenido formbien;

    public ControladorBienvenido(FormBienvenido vistabie) {
        this.formbien = formbien;
        this.formbien.menuMaquina.addActionListener(this);
        this.formbien.menuOrdenTra.addActionListener(this);
        this.formbien.menuSolicitud.addActionListener(this);
        this.formbien.menuTrabajador.addActionListener(this);
        this.formbien.menuTarea.addActionListener(this);

    }

    public void iniciarFormBienvenida() {
        formbien.setTitle("Menu de Bienvenida");
        formbien.setLocationRelativeTo(null);
        formbien.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (formbien.menuMaquina == e.getSource()) {
            FormMaquina formaq = new FormMaquina();
            Maquina ma = new Maquina();
            DAOMaquina daoma = new DAOMaquina();
            ControladorMaquina ctrm = new ControladorMaquina( ma, daoma,formaq);
            ctrm.iniciarFormMaquina();
        }

//        if (formbien.menuOrdenTra == e.getSource()) {
//            FormOrdenTrabajo formortra = new FormOrdenTrabajo();
//            OrdenTrabajo ordtra = new OrdenTrabajo();
//            DAOOrdenTrabajo daoordtra = new DAOOrdenTrabajo();
//            ControladorOrdenTrabajo ctrordtra = new ControladorOrdenTrabajo(ordtra, formortra, daoordtra);
//            try {
//                ctrordtra.iniciarFormOrdenTrabajo();
//            } catch (SQLException ex) {
//                Logger.getLogger(ControladorBienvenido.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }

//        if (vistabien.menuSolicitud == e.getSource()) {
//            FormSolicitud formsolic = new FormSolicitud();
//            Solicitud solic = new Solicitud();
//            DAOSolicitud daosolic = new DAOSolicitud();
//            ControladorSolicitud ctrsolic = new ControladorSolicitud(formsolic, solic, daosolic);
//            try {
//                ctrsolic.iniciarFormOrdenTrabajo();
//            } catch (SQLException ex) {
//                Logger.getLogger(ControladorBienvenido.class.getName()).log(Level.SEVERE, null, ex);
//            }

            if (formbien.menuTrabajador == e.getSource()) {
                FormTrabajador formtrab = new FormTrabajador();
                Trabajador tra = new Trabajador();
                DAOTrabajador daot = new DAOTrabajador();
                ControladorTrabajador ctrtra = new ControladorTrabajador(tra, daot,formtrab);
                try {
                    ctrtra.iniciarFormulaTrabajadores();
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorBienvenido.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            if (formbien.menuTarea == e.getSource()) {
                FormTareas formtare = new FormTareas();
                Tarea tare = new Tarea();
                DAOTarea daotare = new DAOTarea();
                ControladorTareas ctrtare = new ControladorTareas(tare, daotare, formtare);
                try {
                    ctrtare.iniciarFormtareas();
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorBienvenido.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
    }

