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
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author acer
 */
public class DAOMaquina implements CRUD {

    Maquina ma = new Maquina();// instanciando una clase maquina utilizando el constructor vacio

    //patron singleton
    Conexion conectar = Conexion.getInstance(); // instancia una clase pero con un metodo getInstance
    Connection con;

    @Override
        public boolean Agregar(Object obj) {
        ma = (Maquina) obj;
        String sql = "INSERT INTO maquinas (nombre,ubicacion,tipo) VALUES(?,?,?)";
        PreparedStatement pst;

        try {
            con = (Connection) conectar.conectar();
            pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, ma.getNombreMaquina());
            pst.setString(2, ma.getUbicacionMaquina());
            pst.setString(3, ma.getTipoMaquina());
            int filas = pst.executeUpdate();
            if (filas > 0) {

                //con.close();
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
                ma = (Maquina) obj;
        String sql = "UPDATE maquinas SET nombre = ?,ubicacion =?, tipo = ? WHERE id_maquina=? ";
        Connection con;
        PreparedStatement pst;

        try {
            con = (Connection) conectar.conectar();
            pst = (PreparedStatement) con.prepareStatement(sql);
            // pst.setInt(4, ma.getIdMaquina());
            pst.setString(1, ma.getNombreMaquina());
            pst.setString(2, ma.getUbicacionMaquina());
            pst.setString(3, ma.getTipoMaquina());
            pst.setInt(4, ma.getIdMaquina());
            int filas = pst.executeUpdate();
            if (filas > 0) {

                con.close();
                //conectar.cerrarConexion();
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
                ma = (Maquina) obj;
        String sql = "DELETE FROM maquinas where id_maquina=?";
        Connection con;
        PreparedStatement pst;

        try {
            con = (Connection) conectar.conectar();
            pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, ma.getIdMaquina());
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
               String sql = "SELECT * FROM maquinas";
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
    
}

