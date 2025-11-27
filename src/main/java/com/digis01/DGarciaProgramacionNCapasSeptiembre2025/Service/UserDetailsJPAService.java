package com.digis01.DGarciaProgramacionNCapasSeptiembre2025.Service;

import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.DAO.IAlumnoRepositoryDAO;
import com.digis01.DGarciaProgramacionNCapasSeptiembre2025.JPA.AlumnoJPA;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsJPAService  implements UserDetailsService{

    private final IAlumnoRepositoryDAO iAlumnoRepositoryDAO;

    public UserDetailsJPAService (IAlumnoRepositoryDAO iAlumnoRepositoryDAO){
        this.iAlumnoRepositoryDAO = iAlumnoRepositoryDAO;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AlumnoJPA alumno = iAlumnoRepositoryDAO.findByUserName(username);
        
        return User.withUsername(alumno.getUserName())
                .password(alumno.getPassword())
                .roles(alumno.Semestre.getNombre())
//                .accountExpired(true)
//                .disabled(true)
                .build();
    }
    
    
    
}
