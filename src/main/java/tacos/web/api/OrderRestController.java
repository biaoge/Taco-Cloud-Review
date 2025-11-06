package tacos.web.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tacos.data.jpa.OrderRepositoryJPA;
import tacos.model.Order;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(path="/api/orders", produces="application/json")
@CrossOrigin(origins="*")
public class OrderRestController {

    private OrderRepositoryJPA repo;

    public OrderRestController(OrderRepositoryJPA repo) {
        this.repo = repo;
    }

    @PutMapping("/{orderId}")
    public Order putOrder(@RequestBody Order order) {
        return repo.save(order);
    }

    @PatchMapping(path="/{orderId}", consumes="application/json")
    public Order patchOrder(@PathVariable("orderId") Long orderId, @RequestBody Order patch) {
        Order order = repo.findById(orderId).orElse(null);
        if (order != null) {
            if (patch.getName() != null) {
                order.setName(patch.getName());
            }
            if (patch.getStreet() != null) {
                order.setStreet(patch.getStreet());
            }
            if (patch.getCity() != null) {
                order.setCity(patch.getCity());
            }
            if (patch.getState() != null) {
                order.setState(patch.getState());
            }
            if (patch.getZip() != null) {
                order.setZip(patch.getZip());
            }
            if (patch.getCcNumber() != null) {
                order.setCcNumber(patch.getCcNumber());
            }
            if (patch.getCcExpiration() != null) {
                order.setCcExpiration(patch.getCcExpiration());
            }
            if (patch.getCcCVV() != null) {
                order.setCcCVV(patch.getCcCVV());
            }
            if (patch.getTacos() != null) {
                order.setTacos(patch.getTacos());
            }
        }
        return repo.save(order);
    }
}
