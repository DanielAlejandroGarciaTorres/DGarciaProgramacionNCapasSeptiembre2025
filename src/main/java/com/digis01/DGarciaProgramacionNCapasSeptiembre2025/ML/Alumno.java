package com.digis01.DGarciaProgramacionNCapasSeptiembre2025.ML;

import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.JPA.AlumnoJPA;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public class Alumno {

    private int IdAlumno; //Propiedad
    // null  vs ""
    @NotNull(message = "Campo no puede ser nulo")
    @NotBlank(message = "Campo debe contener datos")
    @Size(min = 2, max = 17, message = "entre 2 y 17")
    private String Nombre;
    @NotBlank(message = "Campo debe contener datos")
    @Size(min = 2, max = 17, message = "entre 2 y 17")
    private String ApellidoPaterno;
    private String ApellidoMaterno;
    private String UserName;
    private String Telefono;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date FechaNacimiento;
    public Semestre Semestre; // Propiedad de navegación
    public List<Direccion> Direcciones;
    private String Imagen;
    
    public Alumno(){
        
    }
    
    //JPA - ML
    public Alumno(AlumnoJPA alumnoJPA){
        this.IdAlumno = alumnoJPA.getIdAlumno();
        this.Nombre = alumnoJPA.getNombre();
    }
    
    
    public Alumno(String Nombre, String ApellidoPaterno){
        this.Nombre = Nombre;
        this.ApellidoPaterno = ApellidoPaterno;
    }
    
    public void setIdAlumno(int IdAlumno){
        this.IdAlumno = IdAlumno;
    }
    
    public int getIdAlumno(){
        return IdAlumno;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellidoPaterno() {
        return ApellidoPaterno;
    }

    public void setApellidoPaterno(String ApellidoPaterno) {
        this.ApellidoPaterno = ApellidoPaterno;
    }

    public String getApellidoMaterno() {
        return ApellidoMaterno;
    }

    public void setApellidoMaterno(String ApellidoMaterno) {
        this.ApellidoMaterno = ApellidoMaterno;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public Date getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(Date FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }

    public Semestre getSemestre() {
        return Semestre;
    }

    public void setSemestre(Semestre Semestre) {
        this.Semestre = Semestre;
    }

    public List<Direccion> getDirecciones() {
        return Direcciones;
    }

    public void setDirecciones(List<Direccion> Direcciones) {
        this.Direcciones = Direcciones;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String Imagen) {
        this.Imagen = Imagen;
    }
    
    
    
}
