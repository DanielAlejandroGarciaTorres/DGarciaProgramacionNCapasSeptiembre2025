package com.digis01.DGarciaProgramacionNCapasSeptiembre2025.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller 
@RequestMapping("demo")
public class DemoController {

    //pathvariable -- ruta/cristobal
    //requestparam -- ruta?nombre=cristobal
    
//    @GetMapping("saludo/{nombre}")
//    public String Saludo(@PathVariable("nombre") String nombre, Model model){
//        model.addAttribute("nombre", nombre);
//        return "HolaMundo";
//    }
    
    @GetMapping("saludo")
    public String Saludo(@RequestParam("nombre") String nombre, Model model){
        model.addAttribute("nombre", nombre);
        return "HolaMundo";
    }
    
    
    
}
