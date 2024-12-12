package com.alexandru.alten.service;

import com.alexandru.alten.model.db.StoreUser;
import com.alexandru.alten.model.db.StoreUserPrivilege;
import com.alexandru.alten.model.db.StoreUserRole;
import com.alexandru.alten.store.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public StoreUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        StoreUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found: %s", username)));

        return new User(
                user.getUsername(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                getAuthorities(user.getRoles())
        );
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.fromHierarchy("ROLE_ADMIN > ROLE_STAFF \n ROLE_STAFF > ROLE_USER");
    }

    @Bean
    public DefaultWebSecurityExpressionHandler customWebSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy());
        return handler;
    }

    // TODO Finish implementation
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
//
//        });
//    }

    private List<? extends GrantedAuthority> getAuthorities(List<StoreUserRole> roles) {
        return getGrantedAuthorities(getPrivilegesAsNames(roles));
    }

    private List<String> getPrivilegesAsNames(List<StoreUserRole> roles) {
        return roles.stream()
                .map(StoreUserRole::getPrivileges)
                .flatMap(List::stream)
                .map(StoreUserPrivilege::getName)
                .toList();
    }

    private List<SimpleGrantedAuthority> getGrantedAuthorities(List<String> privilegeNames) {
        return privilegeNames.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}
