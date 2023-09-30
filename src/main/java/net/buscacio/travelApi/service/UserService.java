package net.buscacio.travelApi.service;

import jakarta.transaction.Transactional;
import net.buscacio.travelApi.entity.User;
import net.buscacio.travelApi.exception.ResourceException;
import net.buscacio.travelApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    public User saveUser(User user) {
       return userRepository.save(user);

    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User findById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new ResourceException("User not found"));
    }

    @Transactional
    public void deleteUser(UUID userId) {
        //User user =
        findById(userId);
        userRepository.deleteById(userId);
    }

}
