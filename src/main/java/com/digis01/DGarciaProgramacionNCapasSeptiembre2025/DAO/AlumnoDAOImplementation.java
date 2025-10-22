package com.digis01.DGarciaProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.ML.Alumno;
import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.ML.Direccion;
import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.ML.Result;
import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.ML.Semestre;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository // Esto es una clase que maneja base de datos
public class AlumnoDAOImplementation implements IAlumnoDAO{

    //Inyección de dependencias 
    @Autowired //inyección de campo - field injection
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Result GetAll() {
        
        Result result =  jdbcTemplate.execute("{CALL AlumnoDireccionesGetAll(?)}", (CallableStatementCallback<Result>) callableStatement -> {
            Result resultSP = new Result();
            try{
                callableStatement.registerOutParameter(1, Types.REF_CURSOR);    
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                resultSP.objects  = new ArrayList<>();
                
                int idAlumno = 0;
                
                while (resultSet.next()){
                    
                    idAlumno = resultSet.getInt("IdAlumno");
                    // el alumno ya esta en mi lista 
                    if (!resultSP.objects.isEmpty() && idAlumno == ((Alumno)(resultSP.objects.get(resultSP.objects.size()-1))).getIdAlumno() ) {
                        // direccion
                        Direccion direccion = new Direccion();
                        
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                        
                        // result.objects.get(n) --> permite traer un elemento espevcifico de la lista
                        // result.objects.size()-1 --> trae la ultima posición agregada 
                        Alumno alumno = ((Alumno)(resultSP.objects.get(resultSP.objects.size()-1)));
                        alumno.Direcciones.add(direccion);
                    } else {
                        // el alumno no esta en mi lista 
                        Alumno alumno =  new Alumno();
                        alumno.setIdAlumno(resultSet.getInt("IdAlumno"));
                        alumno.setNombre(resultSet.getString("NombreAlumno"));
                        alumno.Semestre = new Semestre();
                        alumno.Semestre.setIdSemestre(resultSet.getInt("IdSemestre"));
                        alumno.Semestre.setNombre(resultSet.getString("NombreSemestre"));
                        
                        alumno.Direcciones = new ArrayList<>();
                        
                        Direccion direccion = new Direccion();
                        
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                        
                        alumno.Direcciones.add(direccion);
                        resultSP.objects.add(alumno);
                    }
                }
                resultSP.correct = true;
                
            }catch(Exception ex){
              resultSP.correct = false;
              resultSP.errorMessage = ex.getLocalizedMessage();
              resultSP.ex = ex;
            }
            return resultSP;
        });
        
        return result;
    }

}
