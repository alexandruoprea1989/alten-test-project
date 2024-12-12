package com.alexandru.alten.store;

import com.alexandru.alten.model.db.StoreUserPrivilege;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPrivilegeRepository extends JpaRepository<StoreUserPrivilege, Long> {

    Optional<StoreUserPrivilege> findByName(String name);
}
