/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.DAOUsuario;
import modelo.Usuario;
import vista.FormBienvenido;
import vista.FormLogin;

/**
 *
 * @author osval
 */
public class ControladorLogin implements ActionListener {

    private FormLogin formLog;
    private Usuario modelUsu;
    private DAOUsuario modelDAOUsu;

    public ControladorLogin(FormLogin viewLog, Usuario modelUsu, DAOUsuario modelDAOUsu) {
        this.formLog = viewLog;
        this.modelUsu = modelUsu;
        this.modelDAOUsu = modelDAOUsu;
        this.formLog.btnIngresar.addActionListener(this);
    }

    public void iniciarFormLogin() {
        formLog.setTitle("LOGIN");
        formLog.setLocationRelativeTo(null);
        formLog.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (formLog.btnIngresar == e.getSource()) {
            
            modelUsu.setUsuario(formLog.txtUsuario.getText());
            modelUsu.setClave(formLog.txtContraseña.getText());

            if (modelDAOUsu.validarUsuario(modelUsu)) {
                JOptionPane.showMessageDialog(null, "Validacion de usuario exitosa");

                FormBienvenido formbien = new FormBienvenido();

                ControladorBienvenido ctrb = new ControladorBienvenido(formbien);
                ctrb.iniciarFormBienvenida();

            } else {
                JOptionPane.showMessageDialog(null, "Usuario o Contraseña Incorrecta");

            }

        }

    }

}
