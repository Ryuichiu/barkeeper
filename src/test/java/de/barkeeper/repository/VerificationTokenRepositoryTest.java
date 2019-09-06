package de.barkeeper.repository;

import de.barkeeper.model.User;
import de.barkeeper.model.VerificationToken;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@DataJpaTest
public class VerificationTokenRepositoryTest {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;


    @Test
    public void findByTokenTest() {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setId(1L);
        verificationToken.setToken(UUID.randomUUID().toString());

        User user = new User();
        user.setId(1L);
        user.setUsername("MaxMustermann");
        user.setEmail("max.mustermann@example.com");
        user.setPassword(bCryptPasswordEncoder.encode("password"));
        user.setEnabled(true);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);

        VerificationToken foundVerificationToken = verificationTokenRepository.findByToken(verificationToken.getToken());
        Assertions.assertThat(verificationToken.getToken()).isEqualTo(foundVerificationToken.getToken());
    }
}
