package com.alexandru.alten.store;

import com.alexandru.alten.model.db.StoreUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<StoreUserRole, Long> {

    Optional<StoreUserRole> findByName(String name);
}
