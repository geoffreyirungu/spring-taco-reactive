package sia.tacoreactive.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;

import reactor.test.StepVerifier;
import sia.tacoreactive.entity.Ingredient;
import sia.tacoreactive.entity.Ingredient.Type;
import sia.tacoreactive.entity.Taco;
import sia.tacoreactive.entity.TacoOrder;

@DataR2dbcTest
public class OrderRepositoryTest {
    
    @Autowired
    OrderRepository orderRepo;

    @BeforeEach
    void setUp(){
        orderRepo.deleteAll().subscribe();
    }

    @Test
    void shouldSaveAndFetchOrders(){
        TacoOrder order = createOrder();
        TacoOrder persistedOrder = savedOrder();

        StepVerifier.create(orderRepo.save(order)) //order id is initially null but updated when the order is saved
            .expectNext(order)
            .verifyComplete();

        StepVerifier.create(orderRepo.findById(order.getId()))
                .expectNext(persistedOrder)
                .verifyComplete();
        
        StepVerifier.create(orderRepo.findAll())
                .expectNext(persistedOrder)
                .verifyComplete();
    }

    TacoOrder createOrder() {
        TacoOrder order = new TacoOrder();
        order.setDeliveryName("Test Customer");
        order.setDeliveryStreet("1234 North Street");
        order.setDeliveryCity("Notrees");
        order.setDeliveryState("TX");
        order.setDeliveryZip("79759");
        order.setCcNumber("4111111111111111");
        order.setCcExpiration("12/23");
        order.setCcCVV("123");
        Taco taco1 = new Taco();
        taco1.setName("Test Taco One");

        taco1.addIngredient(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
        taco1.addIngredient(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
        taco1.addIngredient(new Ingredient("CHED", "Cheddar Cheese", Type.CHEESE));
        order.addTaco(taco1);

        Taco taco2 = new Taco();
        taco2.addIngredient(new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
        taco2.addIngredient(new Ingredient("CARN", "Carnitas", Type.PROTEIN));
        taco2.addIngredient(new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
        taco2.setName("Test Taco Two");
        order.addTaco(taco2);
        return order;
    }

    TacoOrder savedOrder() {
        TacoOrder order = new TacoOrder();
        order.setId(1L);
        order.setDeliveryName("Test Customer");
        order.setDeliveryStreet("1234 North Street");
        order.setDeliveryCity("Notrees");
        order.setDeliveryState("TX");
        order.setDeliveryZip("79759");
        order.setCcNumber("4111111111111111");
        order.setCcExpiration("12/23");
        order.setCcCVV("123");
        return order;
    }

}
