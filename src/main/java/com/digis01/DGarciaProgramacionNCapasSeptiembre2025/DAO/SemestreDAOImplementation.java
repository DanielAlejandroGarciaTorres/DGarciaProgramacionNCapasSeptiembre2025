package com.digis01.DGarciaProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.ML.Result;
import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.ML.Semestre;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SemestreDAOImplementation implements ISemestreDAO{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetAll() {
        Result result = jdbcTemplate.execute("{CALL SemestreGetAll(?)}", (CallableStatementCallback<Result>) callableStatement -> {
            Result resultSP = new Result();
            try {
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.execute();
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                resultSP.objects = new ArrayList<>();
                while(resultSet.next()){
                    Semestre semestre = new Semestre();
                    semestre.setIdSemestre(resultSet.getInt("IdSemestre"));
                    semestre.setNombre(resultSet.getString("Nombre"));
                    resultSP.objects.add(semestre);
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
