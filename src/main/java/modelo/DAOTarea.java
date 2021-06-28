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
public class DAOTarea implements CRUD {

    Tarea ta = new Tarea();

    //patron singleton
    Conexion conectar = Conexion.getInstance();
    Connection con;

    @Override
    public boolean Agregar(Object obj) {
        ta = (Tarea) obj;
        String sql = "INSERT INTO tareas (descripcion, duracion, prioridad_fk, maquina_fk, periodo, tipo_tarea_fk, ot_fk, tipo_mantencion_fk) VALUES(?,?,(SELECT id_prioridad FROM prioridades WHERE nombre=?), (SELECT id_maquina FROM maquinas WHERE nombre=?),?,(SELECT id_tipo_tarea FROM tipo_tarea WHERE nombre=?),?,(SELECT id_tipo_mantencion FROM tipos_mantenciones WHERE nombre=?))";
        PreparedStatement pst;

        try {
            con = (Connection) conectar.conectar();
            pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, ta.getDescripcion());
            pst.setInt(2, ta.getDuracion());
            pst.setString(3, ta.getPrioridad());
            pst.setString(4, ta.getMaquina());
            pst.setInt(5, ta.getPeriodo());
            pst.setString(6, ta.getTipoTarea());
            pst.setString(7, ta.getOt());
            pst.setString(8, ta.getTipoMantencion());
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
        ta = (Tarea) obj;
        String sql = "UPDATE tareas SET descripcion = ?, duracion = ?, prioridad_fk =(SELECT id_prioridad FROM prioridades WHERE nombre = ?), maquina_fk = (SELECT id_maquina FROM maquinas WHERE nombre = ?), periodo = ?, tipo_tarea_fk= (SELECT id_tipo_tarea FROM tipo_tarea WHERE nombre = ?), ot_fk =?, tipo_mantencion_fk=(SELECT id_tipo_mantencion FROM tipos_mantenciones WHERE nombre = ?) WHERE id_tarea =?";
        Connection con;
        PreparedStatement pst;

        try {
            con = (Connection) conectar.conectar();
            pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, ta.getDescripcion());
            pst.setInt(2, ta.getDuracion());
            pst.setString(3, ta.getPrioridad());
            pst.setString(4, ta.getMaquina());
            pst.setInt(5, ta.getPeriodo());
            pst.setString(6, ta.getTipoTarea());
            pst.setString(7, ta.getOt());
            pst.setString(8, ta.getTipoMantencion());
            pst.setInt(9, ta.getIdTarea());
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
        ta = (Tarea) obj;
        String sql = "DELETE FROM tareas where id_tarea=?";
        Connection con;
        PreparedStatement pst;

        try {
            con = (Connection) conectar.conectar();
            pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, ta.getIdTarea());
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
        String sql = "SELECT ta.id_tarea, ta.descripcion, ta.duracion, pri.nombre, ma.nombre, ta.periodo, tt.nombre, ta.ot_fk, tm.nombre \n" +
"FROM tareas ta \n" +
"INNER JOIN prioridades pri ON pri.id_prioridad = ta.prioridad_fk \n" +
"INNER JOIN maquinas ma ON ma.id_maquina = ta.maquina_fk \n" +
"INNER JOIN tipo_tarea tt ON tt.id_tipo_tarea = ta.tipo_tarea_fk \n" +
"INNER JOIN ordenes_trabajo ot ON ot.id_ot = ta.ot_fk  \n" +
"INNER JOIN tipos_mantenciones tm ON tm.id_tipo_mantencion = ta.tipo_mantencion_fk \n" +
"ORDER BY ta.id_tarea ASC";
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

    public DefaultComboBoxModel obtenerMaquinas() throws SQLException {
        con = (Connection) conectar.conectar();
        Statement st = con.createStatement();
        DefaultComboBoxModel listaModelo = new DefaultComboBoxModel();
        listaModelo.addElement("Seleccione Maquina");
        ResultSet rs = st.executeQuery("select nombre from maquinas");
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

    public DefaultComboBoxModel obtenerTipoTarea() throws SQLException {
        con = (Connection) conectar.conectar();
        Statement st = con.createStatement();
        DefaultComboBoxModel listaModelo = new DefaultComboBoxModel();
        listaModelo.addElement("Seleccione Tipo Tarea");
        ResultSet rs = st.executeQuery("select nombre from tipo_tarea");
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

//    public DefaultComboBoxModel obtenerGeneraOrdenesTrabajo() throws SQLException {
//        con = (Connection) conectar.conectar();
//        Statement st = con.createStatement();
//        DefaultComboBoxModel listaModelo = new DefaultComboBoxModel();
//        listaModelo.addElement("Seleccione quien Genera");
//        ResultSet rs = st.executeQuery("select genera from ordenes_trabajo");
//        try {
//            while (rs.next()) {
//                listaModelo.addElement(rs.getString(1));
//            }
//            rs.close();
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, "Ocurrio un errror" + ex.getMessage());
//        }
//        return listaModelo;
//    }
//    public DefaultComboBoxModel obtenerResponsableOrdenesTrabajo() throws SQLException {
//        con = (Connection) conectar.conectar();
//        Statement st = con.createStatement();
//        DefaultComboBoxModel listaModelo = new DefaultComboBoxModel();
//        listaModelo.addElement("Seleccione Responsable");
//        ResultSet rs = st.executeQuery("select responsable from ordenes_trabajo");
//        try {
//            while (rs.next()) {
//                listaModelo.addElement(rs.getString(1));
//            }
//            rs.close();
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, "Ocurrio un errror" + ex.getMessage());
//        }
//        return listaModelo;
//    }
    public DefaultComboBoxModel obtenerTipoMantencion() throws SQLException {
        con = (Connection) conectar.conectar();
        Statement st = con.createStatement();
        DefaultComboBoxModel listaModelo = new DefaultComboBoxModel();
        listaModelo.addElement("Seleccione Tipo de Mantencion");
        ResultSet rs = st.executeQuery("select nombre from tipos_mantenciones");
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

    public DefaultComboBoxModel obtenerPrioridad() throws SQLException {
        con = (Connection) conectar.conectar();
        Statement st = con.createStatement();
        DefaultComboBoxModel listaModelo = new DefaultComboBoxModel();
        listaModelo.addElement("Seleccione Prioridad");
        ResultSet rs = st.executeQuery("select nombre from prioridades");
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

    public DefaultComboBoxModel obtenerOT() throws SQLException {
        con = (Connection) conectar.conectar();
        Statement st = con.createStatement();
        DefaultComboBoxModel listaModelo = new DefaultComboBoxModel();
        listaModelo.addElement("Seleccione OT");
        ResultSet rs = st.executeQuery("select id_ot from ordenes_trabajo");
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
