package com.digis01.DGarciaProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.ML.Estado;
import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository // esteorito que indica la logica de la base de datos
public class EstadoDAOImplementation implements IEstadoDAO{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetByIdPais(int idPais) {
        return jdbcTemplate.execute("{CALL GetEstadosByIdPais(?,?)}", (CallableStatementCallback<Result>) callableStatement -> {
            Result result = new Result();
            try{
                callableStatement.setInt(1, idPais);
                callableStatement.registerOutParameter(2, java.sql.Types.REF_CURSOR);
                
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                
                result.objects = new ArrayList<>();
                
                while (resultSet.next()) {                    
                    Estado estado = new Estado();
                    estado.setIdEstado(resultSet.getInt("IdEstado"));
                    estado.setNombre(resultSet.getString("Nombre"));
                    
                    result.objects.add(estado);
                }
                result.correct = true;
            } catch (Exception ex){
                result.correct = false;
                result.errorMessage = ex.getLocalizedMessage();
                result.ex = ex; 
                result.objects = null; //limpiar
            }
            return result;
        });
    }
}
