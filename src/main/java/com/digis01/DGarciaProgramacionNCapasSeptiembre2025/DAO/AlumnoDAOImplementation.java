package com.digis01.DGarciaProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.ML.Alumno;
import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.ML.Direccion;
import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.ML.Result;
import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.ML.Semestre;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository // Esto es una clase que maneja base de datos
public class AlumnoDAOImplementation implements IAlumnoDAO {

    //Inyección de dependencias 
    @Autowired //inyección de campo - field injection
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetAll() {

        Result result = jdbcTemplate.execute("{CALL AlumnoDireccionesGetAll(?)}", (CallableStatementCallback<Result>) callableStatement -> {
            Result resultSP = new Result();
            try {
                callableStatement.registerOutParameter(1, Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                resultSP.objects = new ArrayList<>();

                int idAlumno = 0;

                while (resultSet.next()) {

                    idAlumno = resultSet.getInt("IdAlumno");
                    // el alumno ya esta en mi lista 
                    if (!resultSP.objects.isEmpty() && idAlumno == ((Alumno) (resultSP.objects.get(resultSP.objects.size() - 1))).getIdAlumno()) {
                        // direccion
                        Direccion direccion = new Direccion();

                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                        // result.objects.get(n) --> permite traer un elemento espevcifico de la lista
                        // result.objects.size()-1 --> trae la ultima posición agregada 
                        Alumno alumno = ((Alumno) (resultSP.objects.get(resultSP.objects.size() - 1)));
                        alumno.Direcciones.add(direccion);
                    } else {
                        // el alumno no esta en mi lista 
                        Alumno alumno = new Alumno();
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

            } catch (Exception ex) {
                resultSP.correct = false;
                resultSP.errorMessage = ex.getLocalizedMessage();
                resultSP.ex = ex;
            }
            return resultSP;
        });

        return result;
    }

    @Override
    public Result GetById(int idAlumno) {
        return jdbcTemplate.execute("{CALL AlumnoDireccionGetByIdAlumno(?,?,?)}", (CallableStatementCallback<Result>) callableStatment -> {

            Result result = new Result();
            try {
                callableStatment.setInt(1, idAlumno);
                callableStatment.registerOutParameter(2, java.sql.Types.REF_CURSOR);
                callableStatment.registerOutParameter(3, java.sql.Types.REF_CURSOR);

                callableStatment.execute();

                ResultSet resultSetAlumno = (ResultSet) callableStatment.getObject(2);
                ResultSet resultSetDirecciones = (ResultSet) callableStatment.getObject(3);

                if (resultSetAlumno.next()) {
                    Alumno alumno = new Alumno();
                    alumno.setIdAlumno(resultSetAlumno.getInt("IdAlumno"));
                    alumno.setNombre(resultSetAlumno.getString("Nombre"));
                    alumno.setApellidoPaterno(resultSetAlumno.getString("ApellidoPaterno"));
                    alumno.setApellidoMaterno(resultSetAlumno.getString("ApellidoMaterno"));
                    alumno.setUserName(resultSetAlumno.getString("UserName"));
                    alumno.setTelefono(resultSetAlumno.getString("Telefono"));
                    alumno.Semestre = new Semestre();
                    alumno.Semestre.setIdSemestre(resultSetAlumno.getInt("IdSemestre"));
//                    alumno.setFechaNacimiento(resultSetAlumno.getDate("FechaNacimiento"));
                    /*recuperar también las direcciones*/

                    result.object = alumno;
                    result.correct = true;
                }

            } catch (Exception ex) {
                result.correct = false;
                result.errorMessage = ex.getLocalizedMessage();
                result.ex = ex;
            }

            return result;
        });
    }

    @Override
    public Result Update(Alumno alumno) {
        return jdbcTemplate.execute("{CALL AlumnoUpdate(?,?,?,?)}", (CallableStatementCallback<Result>) callableStatment -> {

            Result result = new Result();
            try {
                callableStatment.setInt(1, alumno.getIdAlumno());
                callableStatment.setString(2, alumno.getNombre());
                callableStatment.setString(3, alumno.getApellidoPaterno());
                callableStatment.setString(4, alumno.getApellidoMaterno());
                
                int rowAffected =  callableStatment.executeUpdate();
                
                result.correct = true;
                

            } catch (Exception ex) {
                result.correct = false;
                result.errorMessage = ex.getLocalizedMessage();
                result.ex = ex;
            }

            return result;
        });
    }

    @Override
    public Result AddDireccion(Direccion direccion, int idUsuario) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Result UpdateDireccion(Direccion direccion, int idUsuario) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    //update -- SQl Directo
    
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result AddAll(List<Alumno> alumnos) {
        
        Result result = new Result();
        
        try{
            //parametricedtypereference
            jdbcTemplate.batchUpdate("{CALL AlumnoAdd(?,?,?,?,?,?,?)}", 
                    alumnos, 
                    alumnos.size(), 
                    (callableStatement, alumno) -> {
                        
                        callableStatement.setString(1, alumno.getNombre());
                        callableStatement.setString(2, alumno.getApellidoPaterno());
                        callableStatement.setString(3, alumno.getApellidoMaterno());
                        callableStatement.setString(4, alumno.getUserName());
                        callableStatement.setString(5, alumno.getTelefono());
                        callableStatement.setDate(6, new java.sql.Date(alumno.getFechaNacimiento().getTime()));
                        callableStatement.setInt(7, alumno.Semestre.getIdSemestre());
                    
                    });
            
            result.correct = true;
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
        
    }

    

}
