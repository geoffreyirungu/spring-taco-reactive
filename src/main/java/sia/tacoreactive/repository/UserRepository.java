package sia.tacoreactive.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Mono;
import sia.tacoreactive.entity.User;

public interface UserRepository extends ReactiveCrudRepository<User,String>{
    Mono<User> findByUsername(String username);
}
