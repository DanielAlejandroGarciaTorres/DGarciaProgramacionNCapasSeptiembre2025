package com.digis01.DGarciaProgramacionNCapasSeptiembre2025.Controller;

import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.DAO.AlumnoDAOImplementation;
import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.DAO.EstadoDAOImplementation;
import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.DAO.SemestreDAOImplementation;
import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.ML.Alumno;
import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.ML.Result;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller  
@RequestMapping("alumno")
public class AlumnoController {
    
    @Autowired
    private AlumnoDAOImplementation alumnoDAOImplementation;
    
    @Autowired
    private SemestreDAOImplementation semestreDAOImplementation;
    
    @Autowired
    private EstadoDAOImplementation estadoDAOImplementation;
    
    @GetMapping
    public String Index(Model model){
        Result result = alumnoDAOImplementation.GetAll();
        
        model.addAttribute("alumnos", result.objects);
        
        return "AlumnoIndex";
    }
    
    @GetMapping("detail")
    public String Detail(){
        return "AlumnoDetail";
    }
    
    @GetMapping("add")
    public String Form(Model model){
        
        Alumno alumno = new Alumno();
//        alumno.Direcciones = new ArrayList<>();
        model.addAttribute("Alumno", alumno);
//        Result resultSemestres = semestreDAOImplementation.GetAll();
        model.addAttribute("semestres", semestreDAOImplementation.GetAll().objects);
        return "AlumnoForm";
    }
    
    @PostMapping("add")
    public String Form(@ModelAttribute("Alumno") Alumno alumno){
        
//        Result result = alumnoDAOImplementation.Add(alumno);
                // alumnoDAOImplementation
                        // java.util.date  -- > setDate()
                        
        return "redirect:/alumno";
    }
    
    @GetMapping("estado/{idPais}")
    @ResponseBody // retornara un dato estructurado 
    public Result GetEstadosByIdPais(@PathVariable("idPais") int idPais){
        return estadoDAOImplementation.GetByIdPais(idPais);
    }
}
