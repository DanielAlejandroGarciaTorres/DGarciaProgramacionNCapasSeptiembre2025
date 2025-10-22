package com.digis01.DGarciaProgramacionNCapasSeptiembre2025.ML;

public class Semestre {

    private int IdSemestre;
    private String Nombre;

    public Semestre(){}
    
    public Semestre(int IdSemestre, String Nombre) {
        this.IdSemestre = IdSemestre;
        this.Nombre = Nombre;
    }
    
    public int getIdSemestre() {
        return IdSemestre;
    }

    public void setIdSemestre(int IdSemestre) {
        this.IdSemestre = IdSemestre;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
    
    
    
}
