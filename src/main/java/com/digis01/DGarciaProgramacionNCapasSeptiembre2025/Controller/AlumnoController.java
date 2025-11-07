package com.digis01.DGarciaProgramacionNCapasSeptiembre2025.Controller;

import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.DAO.AlumnoDAOImplementation;
import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.DAO.EstadoDAOImplementation;
import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.DAO.SemestreDAOImplementation;
import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.ML.Alumno;
import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.ML.Direccion;
import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.ML.ErrorCarga;
import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.ML.Result;
import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.Service.ValidationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
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
    private ValidationService validationService;

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

        return "redirect:/usuario/detail/" + idAlumno;

    }

    @PostMapping("/detail")
    public String UpdateAlumno(@ModelAttribute("alumno") Alumno alumno) {
        Result result = alumnoDAOImplementation.Update(alumno);
        return "redirect:/alumno/detail/" + alumno.getIdAlumno();
    }

    @GetMapping("/cargamasiva")
    public String CargaMasiva() {
        return "CargaMasiva";
    }
    
    @GetMapping("/cargamasiva/procesar")
    public String CargaMasiva( HttpSession session) {
        String Path = session.getAttribute("archivoCargaMasiva").toString();
        session.removeAttribute("archivoCargaMasiva");
        
        //inserción con carga masiva
        
        // 
        
        return "CargaMasiva";
    }

    @PostMapping("/cargamasiva")
    public String CargaMasiva(@RequestParam("archivo") MultipartFile archivo, Model model, HttpSession session) throws IOException {
        String extension = archivo.getOriginalFilename().split("\\.")[1];

        String path = System.getProperty("user.dir");
        String pathArchivo = "src/main/resources/archivosCarga";
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS"));
        String pathDefinitvo = path + "/" + pathArchivo + "/" + fecha + archivo.getOriginalFilename();

        archivo.transferTo(new File(pathDefinitvo));

        List<Alumno> alumnos;
        if (extension.equals("txt")) {
            alumnos = LecturaArchivoTXT(new File(pathDefinitvo));
            // validator
        } else if (extension.equals("xlsx")) {
            alumnos = LecturaArchivoXLSX(new File(pathDefinitvo));
        } else {
            // error
            model.addAttribute("errroMessage","Manda un archivo que sea valido :@");
            return "CargaMasiva";
        }

        List<ErrorCarga> errores = ValidarDatosArchivo(alumnos); //--> retorna una lista de errores

        if (errores.isEmpty()) { // todo cul
            // pintar un boton que diga procesar
            model.addAttribute("errores", false);
            session.setAttribute("archivoCargaMasiva", pathDefinitvo);
        } else { // no cul
            // pintar la lista de errores
            model.addAttribute("errores", true);
            model.addAttribute("errores", errores);
        }

        return "CargaMasiva";
    }

    public List<ErrorCarga> ValidarDatosArchivo(List<Alumno> alumnos) {

        List<ErrorCarga> erroresCarga = new ArrayList<>();
        int lineaError = 0;

        for (Alumno alumno : alumnos) {
            lineaError++;
            BindingResult bindingResult = validationService.validateObject(alumno);
            List<ObjectError> errors = bindingResult.getAllErrors();
            for (ObjectError error : errors) {
                FieldError fieldError = (FieldError) error;
                ErrorCarga errorCarga = new ErrorCarga();
                errorCarga.campo = fieldError.getField();
                errorCarga.descripcion = fieldError.getDefaultMessage();
                errorCarga.linea = lineaError;
                erroresCarga.add(errorCarga);
            }
        }

        return erroresCarga;
    }

    public List<Alumno> LecturaArchivoTXT(File archivo) {
        //SCOPE 
        // --
        List<Alumno> alumnos = new ArrayList<>();
        
        
        try (InputStream fileInputStream = new FileInputStream(archivo); BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));) {
            String linea = "";

            while ((linea = bufferedReader.readLine()) != null) {

                String[] campos = linea.split("\\|");
                Alumno alumno = new Alumno();
                alumno.setNombre(campos[0]);
                alumno.setApellidoPaterno(campos[1]);
                alumno.setApellidoMaterno(campos[2]);

                alumnos.add(alumno);
            }

        } catch (Exception ex) {
            return null;
        }

        return alumnos;
    }

    public List<Alumno> LecturaArchivoXLSX(File archivo) {
        List<Alumno> alumnos = new ArrayList<>();
        
        try (InputStream fileInputStream = new FileInputStream(archivo);
                XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream)) {
            XSSFSheet workSheet = workbook.getSheetAt(0);
            for (Row row : workSheet) {
                Alumno alumno = new Alumno();
                alumno.setNombre(row.getCell(0).toString());
                alumno.setApellidoPaterno(row.getCell(1).toString());
                alumno.setApellidoPaterno(row.getCell(2).toString());
                alumnos.add(alumno);
            }
        } catch (Exception ex) {
            return null;
        }
        return alumnos;
    }

    /*
    
    Valdar
    
    if alumno.getNombre == "" || null 
    
    
     */
    @GetMapping("estado/{idPais}")
    @ResponseBody // retornara un dato estructurado 
    public Result GetEstadosByIdPais(@PathVariable("idPais") int idPais) {
        return estadoDAOImplementation.GetByIdPais(idPais);
    }
}
