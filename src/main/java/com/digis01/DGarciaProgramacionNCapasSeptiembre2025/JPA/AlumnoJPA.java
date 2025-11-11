package com.digis01.DGarciaProgramacionNCapasSeptiembre2025.JPA;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ALUMNO")
public class AlumnoJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idalumno")
    private int IdAlumno; 
    
    @Column(name = "nombre")
    private String Nombre;
    
    @Column(name = "apellidopaterno")
    private String ApellidoPaterno;
    
    @Column(name = "apellidomaterno")
    private String ApellidoMaterno;
    
    @ManyToOne
    @JoinColumn(name = "idsemestre")
    public SemestreJPA Semestre;
    
    @OneToMany(mappedBy = "AlumnoJPA", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<DireccionJPA> DireccionesJPA = new ArrayList<>();
}
