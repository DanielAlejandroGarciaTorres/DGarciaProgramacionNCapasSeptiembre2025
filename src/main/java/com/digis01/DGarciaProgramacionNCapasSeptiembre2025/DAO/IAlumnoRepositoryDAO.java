package com.digis01.DGarciaProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.JPA.AlumnoJPA;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IAlumnoRepositoryDAO extends JpaRepository<AlumnoJPA, Integer>{

    AlumnoJPA findByUserName(String userName);
}
