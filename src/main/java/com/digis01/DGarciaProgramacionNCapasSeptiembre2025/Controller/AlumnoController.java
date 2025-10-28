package com.digis01.DGarciaProgramacionNCapasSeptiembre2025.Controller;

import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.DAO.AlumnoDAOImplementation;
import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.DAO.EstadoDAOImplementation;
import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.DAO.SemestreDAOImplementation;
import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.ML.Alumno;
import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.ML.Result;
import jakarta.validation.Valid;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String Index(Model model) {
        Result result = alumnoDAOImplementation.GetAll();

        model.addAttribute("alumnos", result.objects);

        return "AlumnoIndex";
    }

    @GetMapping("detail/{idAlumno}")
    public String Detail(@PathVariable("idAlumno") int idAlumno, Model model) {
        model.addAttribute("alumno", alumnoDAOImplementation.GetById(idAlumno).object);
        
        return "AlumnoDetail";
    }

    @GetMapping("add")
    public String Form(Model model) {

        Alumno alumno = new Alumno();
//        alumno.Direcciones = new ArrayList<>();
        model.addAttribute("Alumno", alumno);
//        Result resultSemestres = semestreDAOImplementation.GetAll();
        model.addAttribute("semestres", semestreDAOImplementation.GetAll().objects);
        return "AlumnoForm";
    }

    @PostMapping("add")
    public String Form(@Valid @ModelAttribute("Alumno") Alumno alumno,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("Alumno", alumno);
            model.addAttribute("semestres", semestreDAOImplementation.GetAll().objects);
            // model.addAttribute("paises", paisDAOImplementation.GetAll())
            if (alumno.Direcciones.get(0).Colonia.Municipio.Estado.Pais.getIdPais() > 0) {
                model.addAttribute("estados",estadoDAOImplementation.GetByIdPais(alumno.Direcciones.get(0).Colonia.Municipio.Estado.Pais.getIdPais()).objects);
                if(alumno.Direcciones.get(0).Colonia.Municipio.Estado.getIdEstado() > 0) {
                    // recupero la información 
//                    if()
                }
            }
            
                return "AlumnoForm";
        }
//        Result result = alumnoDAOImplementation.Add(alumno);
        // alumnoDAOImplementation
        // java.util.date  -- > setDate()

        return "redirect:/alumno";
    }

    @PostMapping("/detail")
    public String UpdateAlumno(@ModelAttribute("alumno") Alumno alumno){
        Result result = alumnoDAOImplementation.Update(alumno);
        return "redirect:/alumno/detail/" + alumno.getIdAlumno();
    }
    
    @GetMapping("estado/{idPais}")
    @ResponseBody // retornara un dato estructurado 
    public Result GetEstadosByIdPais(@PathVariable("idPais") int idPais) {
        return estadoDAOImplementation.GetByIdPais(idPais);
    }
}
