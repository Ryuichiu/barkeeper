package de.barkeeper.service;

import de.barkeeper.model.User;
import de.barkeeper.model.VerificationToken;
import de.barkeeper.repository.VerificationTokenRepository;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
public class VerificationTokenServiceTest {

    @Rule
    public MethodRule methodRule = MockitoJUnit.rule();

    @InjectMocks
    private VerificationTokenService verificationTokenService;

    @Mock
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


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

        Mockito.when(verificationTokenRepository.findByToken(Mockito.anyString())).thenReturn(verificationToken);
        VerificationToken foundToken = verificationTokenService.findByToken(verificationToken.getToken());
        Assertions.assertThat(verificationToken.getToken()).isEqualTo(foundToken.getToken());
    }

    @Test
    public void createVerificationTokenTest() {
        User user = new User();
        user.setId(1L);
        user.setUsername("MaxMustermann");
        user.setEmail("max.mustermann@example.com");
        user.setPassword(bCryptPasswordEncoder.encode("password"));
        user.setEnabled(true);

        VerificationTokenService verificationTokenService = mock(VerificationTokenService.class);
        Mockito.doNothing().when(verificationTokenService).createVerificationToken(isA(User.class));
        verificationTokenService.createVerificationToken(user);
        Mockito.verify(verificationTokenService, times(1)).createVerificationToken(isA(User.class));
    }
}
