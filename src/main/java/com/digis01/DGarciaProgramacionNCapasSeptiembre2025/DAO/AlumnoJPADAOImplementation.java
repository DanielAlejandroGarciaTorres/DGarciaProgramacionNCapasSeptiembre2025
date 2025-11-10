package com.digis01.DGarciaProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.JPA.AlumnoJPA;
import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AlumnoJPADAOImplementation implements IAlumnoJPA{

    @Autowired
    private EntityManager entityManager; // DataSource
    
    @Override
    public Result GetAll() {
        Result result = new Result();
        try{
            
            TypedQuery<AlumnoJPA> queryAlumno = entityManager.createQuery("FROM AlumnoJPA", AlumnoJPA.class);
            List<AlumnoJPA> alumnos = queryAlumno.getResultList();
            
            //rowmapper
            
            result.correct = true;
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.objects = null;
        }
        return result;
    }

}
