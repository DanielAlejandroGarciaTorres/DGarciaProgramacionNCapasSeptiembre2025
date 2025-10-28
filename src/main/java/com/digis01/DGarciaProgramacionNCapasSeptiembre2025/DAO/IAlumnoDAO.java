package com.digis01.DGarciaProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.ML.Alumno;
import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.ML.Result;

public interface IAlumnoDAO {

    Result GetAll();
    
    Result GetById(int idAlumno);
    
    Result Update(Alumno alumno);
}
