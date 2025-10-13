package tacos.data.jpa;

import org.springframework.data.repository.CrudRepository;

import tacos.model.User;

public interface UserRepositoryJPA extends CrudRepository<User, Long> {

    User findByUsername(String username);
    
}
