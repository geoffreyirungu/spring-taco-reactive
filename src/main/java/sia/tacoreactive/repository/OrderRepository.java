package sia.tacoreactive.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import sia.tacoreactive.entity.TacoOrder;

public interface OrderRepository extends ReactiveCrudRepository<TacoOrder,Long>{}
