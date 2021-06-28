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
public class DAOTrabajador implements CRUD {

    Trabajador tra = new Trabajador();

    //patron singleton
    Conexion conectar = Conexion.getInstance();
    Connection con;

    @Override
    public boolean Agregar(Object obj) {
        tra = (Trabajador) obj;
        String sql = "INSERT INTO trabajadores (rut, nombres, apellido_paterno, apellido_materno, cargo_fk) VALUES(?,?,?,?,(SELECT id_cargo FROM cargos WHERE nombre=?))";
        PreparedStatement pst;

        try {
            con = (Connection) conectar.conectar();
            pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, tra.getRut());
            pst.setString(2, tra.getNombres());
            pst.setString(3, tra.getPaterno());
            pst.setString(4, tra.getMaterno());
            pst.setString(5, tra.getCargos());

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
        tra = (Trabajador) obj;
        String sql = "UPDATE trabajadores  SET rut = ?, nombres = ?, apellido_paterno=?, apellido_materno=?, cargo_fk=(SELECT id_cargo FROM cargos WHERE nombre = ?) WHERE id_trabajador =?";
        Connection con;
        PreparedStatement pst;

        try {
            con = (Connection) conectar.conectar();
            pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, tra.getRut());
            pst.setString(2, tra.getNombres());
            pst.setString(3, tra.getPaterno());
            pst.setString(4, tra.getMaterno());
            pst.setString(5, tra.getCargos());
            pst.setInt(6, tra.getIdTrabajador());

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
        tra = (Trabajador) obj;
        String sql = "DELETE FROM trabajadores where id_trabajador=?";
        Connection con;
        PreparedStatement pst;

        try {
            con = (Connection) conectar.conectar();
            pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, tra.getIdTrabajador());
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
        String sql = "SELECT tra.id_trabajador, tra.rut, tra.nombres, tra.apellido_paterno, tra.apellido_materno, car.nombre\n"
                + "FROM cargos car\n"
                + "INNER JOIN trabajadores tra ON car.id_cargo=tra.cargo_fk\n"
                + "ORDER BY tra.id_trabajador ASC";
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

    public DefaultComboBoxModel obtenerCargo() throws SQLException {
        con = (Connection) conectar.conectar();
        Statement st = con.createStatement();
        DefaultComboBoxModel listaModelo = new DefaultComboBoxModel();
        listaModelo.addElement("Seleccione su Cargo");
        ResultSet rs = st.executeQuery("select nombre from cargos");
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

}
