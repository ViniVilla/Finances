package br.edu.ifsp.finances.factory.entity;

import br.com.leonardoferreira.jbacon.JBacon;
import br.edu.ifsp.finances.domain.entity.User;
import br.edu.ifsp.finances.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserFactory extends JBacon<User> {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected User getDefault() {
        return new User("testUser", "testUsername", "test@email.com", "123");
    }

    @Override
    protected User getEmpty() {
        return new User();
    }

    @Override
    protected void persist(User user) {
        userRepository.save(user);
    }
}
