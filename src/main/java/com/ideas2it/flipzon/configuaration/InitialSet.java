package com.ideas2it.flipzon.configuaration;

import com.ideas2it.flipzon.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitialSet implements CommandLineRunner {

    @Autowired
    private RoleService roleService;

    @Override
     public void run(String... args) throws Exception {
        roleService.addRoles();
    }
}
