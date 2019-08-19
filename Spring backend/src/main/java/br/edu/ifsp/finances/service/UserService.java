package br.edu.ifsp.finances.service;

import br.edu.ifsp.finances.domain.entity.User;
import br.edu.ifsp.finances.exception.ResourceNotFoundException;
import br.edu.ifsp.finances.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("User not found"));
    }

}
