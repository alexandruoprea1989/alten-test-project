package com.alexandru.alten.store;

import com.alexandru.alten.model.db.StoreUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<StoreUser, Long> {

    Optional<StoreUser> findByUsername(String username);
}
