/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author acer
 */
public class Maquina {

    private int idMaquina;
    private String nombreMaquina;
    private String ubicacionMaquina;
    private String tipoMaquina;

    public Maquina() {
    }

    public int getIdMaquina() {
        return idMaquina;
    }

    public void setIdMaquina(int idMaquina) {
        this.idMaquina = idMaquina;
    }

    public String getNombreMaquina() {
        return nombreMaquina;
    }

    public void setNombreMaquina(String nombreMaquina) {
        this.nombreMaquina = nombreMaquina;
    }

    public String getUbicacionMaquina() {
        return ubicacionMaquina;
    }

    public void setUbicacionMaquina(String ubicacionMaquina) {
        this.ubicacionMaquina = ubicacionMaquina;
    }

    public String getTipoMaquina() {
        return tipoMaquina;
    }

    public void setTipoMaquina(String tipoMaquina) {
        this.tipoMaquina = tipoMaquina;
    }
   
    
    
}
