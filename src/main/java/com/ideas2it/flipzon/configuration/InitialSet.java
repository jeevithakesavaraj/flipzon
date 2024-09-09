package com.ideas2it.flipzon.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.ideas2it.flipzon.service.RoleService;
import com.ideas2it.flipzon.service.UserService;

/**
 * <p>
 * This class implements commandLineRunner and is for Add roles and create admin.
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@Configuration
public class InitialSet implements CommandLineRunner {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        roleService.addRoles();
        userService.createAdmin();
    }
}
