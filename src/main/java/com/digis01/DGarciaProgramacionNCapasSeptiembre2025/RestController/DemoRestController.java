package com.digis01.DGarciaProgramacionNCapasSeptiembre2025.RestController;

import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.JPA.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController// controller responsebody
@RequestMapping("api/demo") // define la ruta base del controlador
public class DemoRestController {

    @GetMapping("saludo")
    public String Saludo() {
        return "Hola Mundo, soy Alejandro"; // llamado a html 
    }

    @GetMapping("division")
    public ResponseEntity Division(@RequestParam("NumeroUno") int numeroUno, @RequestParam("NumeroDos") int numeroDos) {
        Result result = new Result();
        try {
            if (numeroDos == 0) {
                result.correct = false;
                result.errorMessage = "Syntax Error :c";
                result.status = 400;
            } else {
                int division = numeroUno / numeroDos;
                result.correct = true;
                result.status = 200;
            }

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }

        return ResponseEntity.status(result.status).body(result);
    }
    
    /*
    1. hola, saluda alguien 
    2. División
    3. Multiplicación con dos requestparams
    4. Suma de n Elementos de un arreglo
    
    *Analizar el sistema, y validar que es necesario para el servidor,
    y que no, ¿ por que ? 
    
    */
}
