package tacos.data.jpa;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import tacos.model.Order;
import tacos.model.User;

public interface OrderRepositoryJPA extends CrudRepository<Order, Long> {
    /**
     * If we were to add custom query methods, we could define them here. 
     * For simple use cases:
     * We just need define the method signature, and Spring Data JPA will provide the implementation automatically.
     * For example:
     * ```
     * List<Order> findByDeliveryZip(String deliveryZip);
     * ```
     * Soring will provide the implementation based on the method name.
     * 
     * For complex use cases:
     * We can use the @Query annotation to define JPQL or native SQL queries.
     * For example:
     * You can use @Param to map method parameters to named parameters in your query.
     * ```
     * @Query("SELECT i FROM Ingredient i WHERE i.name LIKE CONCAT('%', :name, '%')")```
     * List<Ingredient> searchByNameLike(@Param("name") String name);
     * ```
     * 
     */

    List<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
}
