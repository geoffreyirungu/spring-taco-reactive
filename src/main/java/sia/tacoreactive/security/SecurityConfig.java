package sia.tacoreactive.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.web.server.SecurityWebFilterChain;

import sia.tacoreactive.repository.UserRepository;



@Configuration
public class SecurityConfig {
    
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){
        return http.authorizeExchange(authorizeExchange -> authorizeExchange.pathMatchers("/api/tacos","/orders")
                                                                        .hasAuthority("USER")
                                                                        .anyExchange().permitAll()
                )
                .build();      
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService(UserRepository userRepo){

        return username -> userRepo.findByUsername(username).map(user -> user.toUserDetails());
    }

}
