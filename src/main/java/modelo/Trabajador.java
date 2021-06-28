/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author acer
 */
public class Trabajador {

    private int idTrabajador;
    private String rut;
    private String nombres;
    private String Paterno;
    private String Materno;
    private String cargos;

    public Trabajador() {
    }

    public int getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(int idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getPaterno() {
        return Paterno;
    }

    public void setPaterno(String Paterno) {
        this.Paterno = Paterno;
    }

    public String getMaterno() {
        return Materno;
    }

    public void setMaterno(String Materno) {
        this.Materno = Materno;
    }

    public String getCargos() {
        return cargos;
    }

    public void setCargos(String cargos) {
        this.cargos = cargos;
    }



    public boolean validarRut(String rut) {

        boolean validacion = false;
        try {
            rut = rut.toUpperCase();
            rut = rut.replace(".", "");
            rut = rut.replace("-", "");
            int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

            char dv = rut.charAt(rut.length() - 1);

            int m = 0, s = 1;
            for (; rutAux != 0; rutAux /= 10) {
                s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
            }
            if (dv == (char) (s != 0 ? s + 47 : 75)) {
                validacion = true;
            }

        } catch (java.lang.NumberFormatException e) {
        } catch (Exception e) {
        }
        return validacion;

    }

    public boolean validarCamposVacios() {
        boolean ok = true;
        if (this.getRut().isEmpty()) {
            ok = false;
            JOptionPane.showMessageDialog(null, "El campo Rut no puede estar vacío");
        }
        if (this.getNombres().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El campo Nombres no puede estar vacío");
            ok = false;
        }
        if (this.getPaterno().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El campo Apellido Paterno no puede estar vacío");
            ok = false;
        }
        if (this.getMaterno().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El campo Apellido Materno no puede estar vacío");
            ok = false;
        }
        if (this.getCargos() == "Seleccione Cargo") {
            JOptionPane.showMessageDialog(null, "El campo Cargo no puede estar vacío");
            ok = false;
        }
        return ok;
    }
    
    
    public boolean validarTodo() {
        boolean ok = true;

        ArrayList<Boolean> lista = new ArrayList<>();

        if (validarRut(this.getRut())) {
        } else {
            lista.add(false);
            JOptionPane.showMessageDialog(null, "Rut No Válido", "Atención", 2);
        }

        if (validarNombre(this.getNombres())) {
        } else {
            lista.add(false);
            JOptionPane.showMessageDialog(null, "Nombre No Válido", "Atención", 2);
        }
        if (validarNombre(this.getPaterno())) {
        } else {
            lista.add(false);
            JOptionPane.showMessageDialog(null, "Apellido Paterno No Válido", "Atención", 2);
        }

        if (validarNombre(this.getMaterno())) {
        } else {
            lista.add(false);
            JOptionPane.showMessageDialog(null, "Apellido Materno No Válido", "Atención", 2);
        }

        int contador = 0;
        for (Boolean bool : lista) {
            if (bool == false) {
                contador++;
            }
        }
        
        if (contador > 0) {
            ok = false;
        }
        //JOptionPane.showMessageDialog(null, "El numero de validaciones falladas es: "+ contador);
        return ok;
    }

    public static boolean validarNombre(String txt) {
        String regx = "^[\\p{L} .'-]+$";
        Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(txt);
        return matcher.find();
    }

}
