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
public class DAOOrdenTrabajo implements CRUD {

    OrdenTrabajo ot = new OrdenTrabajo();

    //patron singleton
    Conexion conectar = Conexion.getInstance();
    Connection con;

    @Override
    public boolean Agregar(Object obj) {

        ot = (OrdenTrabajo) obj;
        String sql = "INSERT INTO ordenes_trabajos (notas, genera_fk, responsable_fk) VALUES(?,?,?)";
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
        String sql = "UPDATE ordenes_trabajos SET notas = ?, genera_fk =(SELECT id_trabajador FROM trabajadores WHERE apellido_paterno = ?), responsable_fk=(SELECT id_trabajador FROM trabajadores WHERE apellido_paterno = ?) WHERE id_ot =?";
        Connection con;
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
        String sql = "DELETE FROM tareas where id_ordenes_trabajos=?";
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
        String sql = "SELECT ot.id_ot, ge_fk, re_fk\n"
                + "FROM trabajadores tra\n"
                + "INNER JOIN ordenes_trabajos ot ON tra.id_trabajador= ot.genera_fk\n"
                + "INNER JOIN ordenes_trabajos ON tra.id_trabajador= ot.responsable_fk\n"
                + "ORDER BY ot.id_ot ASC";
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
