package de.barkeeper.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
public class VerificationTokenTest {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Before
    public void givenUserWithVerificationToken() {
        User user = new User();
        user.setId(1L);
        user.setUsername("Max Mustermann");
        user.setEmail("max.mustermann@example.com");
        user.setPassword(bCryptPasswordEncoder.encode("hkU7cF3Zru@RcYRc"));
        user.setEnabled(false);

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setId(1L);
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationToken.setUser(user);
    }

    @Test
    public void calculateExpiryDateTest() {

    }
}
