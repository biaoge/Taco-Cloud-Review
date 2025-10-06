package tacos.data.jpa;

import org.springframework.data.repository.CrudRepository;

import tacos.model.Taco;

public interface TacoRepositoryJPA extends CrudRepository<Taco, Long> {
    
}
