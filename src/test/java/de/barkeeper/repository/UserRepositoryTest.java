package de.barkeeper.repository;

import de.barkeeper.model.User;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User givenUser;

    @Before
    public void givenUser() {
        givenUser = new User();
        givenUser.setId(1L);
        givenUser.setUsername("MaxMustermann");
        givenUser.setEmail("max.mustermann@example.com");
        givenUser.setPassword("hkU7cF3Zru@RcYRc");
        givenUser.setEnabled(true);
        userRepository.save(givenUser);
    }

    @Test
    public void findByEmailTest() {
        User foundUser = userRepository.findByEmail(givenUser.getEmail());
        Assertions.assertThat(givenUser.getEmail()).isEqualTo(foundUser.getEmail());
    }

    @Test
    public void findByUsernameTest() {
        User foundUser = userRepository.findByUsername(givenUser.getUsername());
        Assertions.assertThat(givenUser.getUsername()).isEqualTo(foundUser);
    }

    @Test
    public void findByEmailOrUsernameTest() {
        User foundUser = userRepository.findByEmailOrUsername(givenUser.getEmail(), givenUser.getUsername());
        Assertions.assertThat(givenUser).isEqualTo(foundUser);
    }
}
