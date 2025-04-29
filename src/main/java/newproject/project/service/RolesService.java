package newproject.project.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import newproject.project.model.Role;
import newproject.project.repository.RoleRepository;

@Service
public class RolesService {
    @Autowired
    private RoleRepository roleRepository;
    
    
}
