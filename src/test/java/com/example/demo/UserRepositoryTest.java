package com.example.demo;

import com.example.demo.entities.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;


import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void test() {
        User user = new User();
        user.setName("Jan");
        user.setSurname("Oleksik");
        user.setPassword("giazda");
        user.setEmail("xd@xd.qp");
        User savedUser = userRepository.save(user);
        User foundUser = entityManager.find(User.class,savedUser.getId());
        Assertions.assertEquals(user.getEmail(),foundUser.getEmail());
    }
}
