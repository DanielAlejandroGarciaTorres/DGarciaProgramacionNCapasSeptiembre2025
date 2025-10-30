package com.digis01.DGarciaProgramacionNCapasSeptiembre2025.Controller;

import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.DAO.AlumnoDAOImplementation;
import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.DAO.EstadoDAOImplementation;
import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.DAO.SemestreDAOImplementation;
import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.ML.Alumno;
import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.ML.Direccion;
import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.ML.Result;
import jakarta.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        model.addAttribute("direccion", new Direccion());
//        model.addAttribute("paises", direccionDAOImplementation.GetAll().objects);
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
            Model model,
            RedirectAttributes redirectAttributes,
            @RequestParam("imagenFile") MultipartFile imagenFile) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("Alumno", alumno);
            model.addAttribute("semestres", semestreDAOImplementation.GetAll().objects);
            // model.addAttribute("paises", paisDAOImplementation.GetAll())
            if (alumno.Direcciones.get(0).Colonia.Municipio.Estado.Pais.getIdPais() > 0) {
                model.addAttribute("estados", estadoDAOImplementation.GetByIdPais(alumno.Direcciones.get(0).Colonia.Municipio.Estado.Pais.getIdPais()).objects);
                if (alumno.Direcciones.get(0).Colonia.Municipio.Estado.getIdEstado() > 0) {
                    // recupero la información 
//                    if()
                }
            }

            return "AlumnoForm";

        }

//        Result result = alumnoDAOImplementation.Add(alumno);
        // alumnoDAOImplementation
        // java.util.date  -- > setDate()
//            if (result.correct) {
//                
//            } else {
//                
//            }
        if (imagenFile != null) {
            try {
                //vuelvo a asegurarme que es jpg o png

                String extension = imagenFile.getOriginalFilename().split("\\.")[1];
                if (extension.equals("jpg") || extension.equals("png")) {

                    byte[] byteImagen = imagenFile.getBytes();
                    String imagenBase64 = Base64.getEncoder().encodeToString(byteImagen);
                    alumno.setImagen(imagenBase64);
                }
            } catch (IOException ex) {
                Logger.getLogger(AlumnoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        redirectAttributes.addFlashAttribute("successMessage", "El usuario " + alumno.getUserName() + "se creo con exito.");

        return "redirect:/alumno";
    }

    @PostMapping("actiondireccion/{idAlumno}")
    public String ActionDireccion(@PathVariable("idAlumno") int idAlumno, @ModelAttribute("direccion") Direccion direccion, Model model) {

        if (direccion.getIdDireccion() == 0) { // agregar direccion a usuario
            alumnoDAOImplementation.AddDireccion(direccion, idAlumno);
        } else { // editar la direccion a usuario
            alumnoDAOImplementation.UpdateDireccion(direccion, idAlumno);
        }
        
        return "redirect:/usuario/detail/"+idAlumno;

    }

    @PostMapping("/detail")
    public String UpdateAlumno(@ModelAttribute("alumno") Alumno alumno) {
        Result result = alumnoDAOImplementation.Update(alumno);
        return "redirect:/alumno/detail/" + alumno.getIdAlumno();
    }

    @GetMapping("estado/{idPais}")
    @ResponseBody // retornara un dato estructurado 
    public Result GetEstadosByIdPais(@PathVariable("idPais") int idPais) {
        return estadoDAOImplementation.GetByIdPais(idPais);
    }
}
