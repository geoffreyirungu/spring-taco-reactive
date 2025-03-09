package sia.tacoreactive.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sia.tacoreactive.entity.Taco;
import sia.tacoreactive.entity.TacoOrder;
import sia.tacoreactive.repository.OrderRepository;
import sia.tacoreactive.repository.TacoRepository;


@Slf4j
@Service
@RequiredArgsConstructor
public class TacoOrderAggregateService {
    
    private final TacoRepository tacoRepository;
    private final OrderRepository orderRepository;

    public Mono<TacoOrder> save(TacoOrder tacoOrder){ // wont work since nested objects are not supported

         
        return Mono.just(tacoOrder)
                .flatMap(order -> {
                    List<Taco> tacos = order.getTacos();
                    order.setTacos(new ArrayList<>());
                    return tacoRepository.saveAll(tacos)
                        .map(taco -> {
                            order.addTaco(taco);
                            return order;
                        })
                        .last();
                })
                .flatMap(orderRepository::save)
                .doOnError(error -> log.error("Error when saving: {}",error.getMessage()));
        

    }

    public Mono<TacoOrder> findById(Long id){
        return orderRepository.findById(id)
                    .flatMap(order -> 
                        tacoRepository.findAllById(order.getTacoIds())
                                .map(taco -> {
                                    order.addTaco(taco);
                                    return order;
                                })
                                .last()
                    );
    }


}
