package com.digis01.DGarciaProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.ML.Alumno;
import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.ML.Direccion;
import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.ML.Result;

public interface IAlumnoDAO {

    Result GetAll();
    
    Result GetById(int idAlumno);
    
    Result Update(Alumno alumno);
    
//    Result ActionDireccion(Direccion direccion, int idUsuario);
    
    Result AddDireccion(Direccion direccion, int idUsuario);
    
    Result UpdateDireccion(Direccion direccion, int idUsuario);
}
