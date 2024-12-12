package com.alexandru.alten.service;

import com.alexandru.alten.model.db.StoreUser;
import com.alexandru.alten.model.db.StoreUserPrivilege;
import com.alexandru.alten.model.db.StoreUserRole;
import com.alexandru.alten.store.UserPrivilegeRepository;
import com.alexandru.alten.store.UserRepository;
import com.alexandru.alten.store.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class UserDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean setupDone;

    private final UserPrivilegeRepository userPrivilegeRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDataLoader(UserPrivilegeRepository userPrivilegeRepository,
                          UserRoleRepository userRoleRepository,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder
    ) {
        this.userPrivilegeRepository = userPrivilegeRepository;
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (setupDone) {
            return;
        }

        StoreUserPrivilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        StoreUserPrivilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        StoreUserRole adminRole = createRoleIfNotFound("ROLE_ADMIN", List.of(readPrivilege, writePrivilege));
        StoreUserRole userRole = createRoleIfNotFound("ROLE_USER", List.of(readPrivilege));

        StoreUser rootUser = new StoreUser("root", passwordEncoder.encode("root"), List.of(adminRole));
        StoreUser normalUser = new StoreUser("root", passwordEncoder.encode("root"), List.of(userRole));
        userRepository.saveAll(List.of(rootUser, normalUser));

        setupDone = true;
    }

    @Transactional
    private StoreUserPrivilege createPrivilegeIfNotFound(String name) {
        return userPrivilegeRepository
                .findByName(name)
                .orElse(userPrivilegeRepository.save(new StoreUserPrivilege(name)));
    }

    @Transactional
    private StoreUserRole createRoleIfNotFound(String name, List<StoreUserPrivilege> privileges) {
        return userRoleRepository
                .findByName(name)
                .orElse(userRoleRepository.save(new StoreUserRole(name, privileges)));
    }
}
