package sia.tacoreactive.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import sia.tacoreactive.entity.Taco;

public interface TacoRepository extends ReactiveCrudRepository<Taco,Long>{}
