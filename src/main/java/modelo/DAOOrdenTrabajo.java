/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author acer
 */
public class DAOOrdenTrabajo implements CRUD {

    OrdenTrabajo ot = new OrdenTrabajo();

    //patron singleton
    Conexion conectar = Conexion.getInstance();
    Connection con;

    @Override
    public boolean Agregar(Object obj) {

        ot = (OrdenTrabajo) obj;
        String sql = "INSERT INTO ordenes_trabajo (notas, genera_fk, responsable_fk) VALUES(?,(SELECT id_trabajador FROM trabajadores WHERE apellido_paterno=?),(SELECT id_trabajador FROM trabajadores WHERE apellido_paterno=?))";
        PreparedStatement pst;

        try {
            con = (Connection) conectar.conectar();
            pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, ot.getNotas());
            pst.setString(2, ot.getGenera());
            pst.setString(3, ot.getResponsable());

            int filas = pst.executeUpdate();
            if (filas > 0) {

                conectar.cerrarConexion();
                return true;
            } else {

                conectar.cerrarConexion();
                return true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocurrio un errror : " + e.getMessage());
        }
        return false;

    }

    @Override
    public boolean Modificar(Object obj) {
        ot = (OrdenTrabajo) obj;
        String sql = "UPDATE ordenes_trabajo SET notas = ?, genera_fk =(SELECT id_trabajador FROM trabajadores WHERE apellido_paterno = ?), responsable_fk=(SELECT id_trabajador FROM trabajadores WHERE apellido_paterno = ?) WHERE id_ot =?";
        Connection con;
        PreparedStatement pst;

        try {
            con = (Connection) conectar.conectar();
            pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, ot.getNotas());
            pst.setString(2, ot.getGenera());
            pst.setString(3, ot.getResponsable());
            pst.setInt(4, ot.getIdOt());

            int filas = pst.executeUpdate();
            if (filas > 0) {

                conectar.cerrarConexion();
                return true;
            } else {

                conectar.cerrarConexion();
                return false;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocurrio un errror : " + e.getMessage());
        }
        return false;

    }

    @Override
    public boolean Eliminar(Object obj) {
        ot = (OrdenTrabajo) obj;
        String sql = "DELETE FROM ordenes_trabajo WHERE id_ot = ?";
        Connection con;
        PreparedStatement pst;

        try {
            con = (Connection) conectar.conectar();
            pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, ot.getIdOt());
            int filas = pst.executeUpdate();
            con.close();

            if (filas > 0) {
                con.close();
                return true;

            } else {
                return false;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocurrio un errror :" + e.getMessage());
            return false;
        }
    }

    @Override
    public ArrayList<Object[]> consultar() {
        String sql = "SELECT ot.id_ot, ot.notas, tra.apellido_paterno, tr.apellido_paterno \n" +
"FROM ordenes_trabajo ot \n" +
"INNER JOIN trabajadores tra ON tra.id_trabajador = ot.genera_fk \n" +
"INNER JOIN trabajadores tr ON tr.id_trabajador = ot.responsable_fk \n" +
"ORDER BY ot.id_ot DESC";

        //String sql = "SELECT id_ot, notas, genera_fk, responsable_fk FROM ordenes_trabajo";
        Connection con;
        PreparedStatement pst;
        ResultSet rs;
        ResultSetMetaData meta;
        ArrayList<Object[]> datos = new ArrayList<>();

        try {
            con = (Connection) conectar.conectar();
            pst = (PreparedStatement) con.prepareStatement(sql);

            rs = pst.executeQuery();
            meta = rs.getMetaData();
            while (rs.next()) {
                Object[] fila = new Object[meta.getColumnCount()];
                for (int i = 0; i < fila.length; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                datos.add(fila);

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocurrio un errror" + e.getMessage());

        }
        return datos;
    }

    public DefaultComboBoxModel obtenerGenerador() throws SQLException {
        con = (Connection) conectar.conectar();
        Statement st = con.createStatement();
        DefaultComboBoxModel listaModelo = new DefaultComboBoxModel();
        listaModelo.addElement("Seleccione Quien Genera");
        ResultSet rs = st.executeQuery("select apellido_paterno from trabajadores");
        try {
            while (rs.next()) {
                listaModelo.addElement(rs.getString(1));
            }
            rs.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocurrio un errror" + ex.getMessage());
        }
        return listaModelo;
    }

    public DefaultComboBoxModel obtenerResponsable() throws SQLException {
        con = (Connection) conectar.conectar();
        Statement st = con.createStatement();
        DefaultComboBoxModel listaModelo = new DefaultComboBoxModel();
        listaModelo.addElement("Seleccione Responsable");
        ResultSet rs = st.executeQuery("select apellido_paterno from trabajadores");
        try {
            while (rs.next()) {
                listaModelo.addElement(rs.getString(1));
            }
            rs.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocurrio un errror" + ex.getMessage());
        }
        return listaModelo;
    }

    public String enviarACombo(String id) throws SQLException {
        String dato="";
        String sql = "SELECT apellido_paterno FROM trabajadores WHERE id_trabajador=?";
        PreparedStatement pst;
        con = (Connection) conectar.conectar();
        pst = (PreparedStatement) con.prepareStatement(sql);
        pst.setString(1, id);
        
        ResultSet rs = pst.executeQuery();
         while (rs.next()) {
                dato = rs.getString(1);
            }
        
        return dato;
    }

}
