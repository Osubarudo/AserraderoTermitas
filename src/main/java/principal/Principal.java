/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import com.jtattoo.plaf.acryl.AcrylLookAndFeel;
import com.jtattoo.plaf.bernstein.BernsteinLookAndFeel;
import controlador.ControladorLogin;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import modelo.DAOUsuario;
import modelo.Usuario;
import vista.FormLogin;

/**
 *
 * @author osval
 */
public class Principal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new AcrylLookAndFeel());
        FormLogin log = new FormLogin();
        Usuario usu = new Usuario();
        DAOUsuario dusu = new DAOUsuario();

        ControladorLogin  ctrl = new ControladorLogin (log, usu, dusu);
        ctrl.iniciarFormLogin();
    }
    
}
