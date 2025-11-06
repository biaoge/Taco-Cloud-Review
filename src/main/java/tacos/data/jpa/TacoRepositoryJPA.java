package tacos.data.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import tacos.model.Taco;

public interface TacoRepositoryJPA extends PagingAndSortingRepository<Taco, Long>, CrudRepository<Taco, Long> {
    
}
