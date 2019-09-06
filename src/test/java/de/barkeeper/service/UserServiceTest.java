package de.barkeeper.service;

import de.barkeeper.model.User;
import de.barkeeper.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Rule
    public MethodRule methodRule = MockitoJUnit.rule();

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User givenUser;

    @Before
    public void givenUser() {
        givenUser = new User();
        givenUser.setId(1L);
        givenUser.setUsername("Max Mustermann");
        givenUser.setEmail("max.mustermann@example.com");
        givenUser.setPassword("hkU7cF3Zru@RcYRc");
        givenUser.setEnabled(true);
    }

    @Test
    public void findByEmailTest() {
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(givenUser);
        User foundUser = userService.findByMail(givenUser.getEmail());
        Assertions.assertThat(givenUser).isEqualTo(foundUser);
    }

    @Test
    public void findByEmailOrUsernameTest() {
        Mockito.when(userRepository.findByEmailOrUsername(Mockito.anyString(), Mockito.anyString())).thenReturn(givenUser);
        User foundUser = userService.findByEmailOrUsername(givenUser);
        Assertions.assertThat(givenUser).isEqualTo(foundUser);
    }

    @Test
    public void registerNewUserTest() {
        /*
        UserService userService = mock(UserService.class);
        Mockito.doNothing().when(userService).registerNewUser(isA(User.class));
        userService.registerNewUser(givenUser);
        Mockito.verify(userService, times(1)).registerNewUser(givenUser);*/
    }
}
