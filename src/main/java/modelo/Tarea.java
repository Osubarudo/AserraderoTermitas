/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import static modelo.Trabajador.validarNombre;

/**
 *
 * @author acer
 */
public class Tarea {

    private int idTarea;
    private String descripcion;
    private int duracion;
    private String prioridad;
    private int periodo;
    private String maquina;
    private String tipoTarea;
    private String ot;
    private String tipoMantencion;

    public Tarea() {
    }

    public int getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(int idTarea) {
        this.idTarea = idTarea;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public String getMaquina() {
        return maquina;
    }

    public void setMaquina(String maquina) {
        this.maquina = maquina;
    }

    public String getTipoTarea() {
        return tipoTarea;
    }

    public void setTipoTarea(String tipoTarea) {
        this.tipoTarea = tipoTarea;
    }

    public String getOt() {
        return ot;
    }

    public void setOt(String ot) {
        this.ot = ot;
    }

    public String getTipoMantencion() {
        return tipoMantencion;
    }

    public void setTipoMantencion(String tipoMantencion) {
        this.tipoMantencion = tipoMantencion;
    }

    public boolean validarCamposVacios() {
        boolean ok = true;
        if (this.getDescripcion().isEmpty()) {
            ok = false;
            JOptionPane.showMessageDialog(null, "El campo Descripcion no puede estar vacío");
        }
        if (this.getPrioridad().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El campo Prioridad no puede estar vacío");
            ok = false;
        }
        if (this.getMaquina().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El campo Apellido Paterno no puede estar vacío");
            ok = false;
        }
        if (this.getTipoTarea().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El campo Apellido Materno no puede estar vacío");
            ok = false;
        }
        if (this.getOt() == "Seleccione Cargo") {
            JOptionPane.showMessageDialog(null, "El campo Cargo no puede estar vacío");
            ok = false;

        }
        if (this.getTipoMantencion() == "Seleccione Cargo") {
            JOptionPane.showMessageDialog(null, "El campo Cargo no puede estar vacío");
            ok = false;
            return ok;
        }
        return ok;

    }
        public boolean validarTodo() {
        boolean ok = true;

        ArrayList<Boolean> lista = new ArrayList<>();

        if (validarDuracion(this.getDuracion())) {
        } else {
            lista.add(false);
            JOptionPane.showMessageDialog(null, "Duracion No Válida", "Atención", 2);
        }

        if (validarPeriodo(this.getPeriodo())) {
        } else {
            lista.add(false);
            JOptionPane.showMessageDialog(null, "Periodo No Válido", "Atención", 2);
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
    
    public boolean validarDuracion(int duracion) {
        boolean ok = true;
        if (duracion < 1) {
            ok = false;
        }
        return ok;
        
    }
    
    public boolean validarPeriodo(int Periodo) {
        boolean ok = true;
        if (periodo < 1 ) {
            ok = false;
        }
        return ok;
    }
    
  
    
}
