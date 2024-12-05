package com.debasis.SecureRBAC.initializer;

import com.debasis.SecureRBAC.model.Role;
import com.debasis.SecureRBAC.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Objects;

@Component
public class RoleInitializer  implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public RoleInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        String []roles ={"USER", "MODERATOR", "ADMIN"};
        for (String rolName:roles){
            Role existingRole = roleRepository.findByName(rolName);

            if (Objects.isNull(existingRole)){
                Role role = new Role();
                role.setName(rolName);
                roleRepository.save(role);
            }

        }
        System.out.println("Role added Successfully");

    }
}

