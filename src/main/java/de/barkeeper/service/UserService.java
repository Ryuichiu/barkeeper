package de.barkeeper.service;

import de.barkeeper.repository.UserRepository;
import de.barkeeper.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;
    private MailService mailService;
    private VerificationTokenService verificationTokenService;

    @Autowired
    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, MailService mailService, VerificationTokenService verificationTokenService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.mailService = mailService;
        this.verificationTokenService = verificationTokenService;
    }

    /**
     * User will be found by username from the database.
     *
     * @param username
     * @return
     */
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * User will be found by email from the database.
     *
     * @param mail
     * @return
     */
    public User findByMail(String mail) {
        return userRepository.findByEmail(mail);
    }

    /**
     * User will be found by email or by username from the database.
     *
     * @param user
     * @return
     */
    public User findByEmailOrUsername(User user) {
        return userRepository.findByEmailOrUsername(user.getEmail(), user.getUsername());
    }

    /**
     * Creates a user and a token that will be saved into the database and returns the user. Sends an e-mail.
     *
     * @param user
     * @return
     */
    public User registerNewUser(User user) throws MessagingException {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        verificationTokenService.createVerificationToken(user);
        List<String> recipients = new ArrayList<>();
        recipients.add(user.getEmail());
        mailService.sendRegistrationMail(recipients);
        return save(user);
    }

    /**
     * Confirm registration.
     *
     * @param user
     * @return
     */
    public User confirmRegistration(User user) {
        return changeStatus(user);
    }

    /**
     * Updates status from user in the database.
     *
     * @param user
     * @return
     */
    public User changeStatus(User user) {
        if (user.isEnabled()) {
            user.setEnabled(false);
        } else {
            user.setEnabled(true);
        }

        User savedUser = save(user);

        if (savedUser != null) {
            LOG.info("User with id {} has been changed the status enabled to {}.", user.getId(), user.isEnabled());
        }

        return savedUser;
    }

    /**
     * Creates a user to the database and returns it with additional information. The new user is printed on the console.
     *
     * @param user
     * @return
     */
    public User save(User user) {
        User savedUser = userRepository.save(user);
        if (savedUser != null) {
            LOG.info("User with id {} has been saved.", savedUser.getId());
        }
        return savedUser;
    }

    /**
     * Deletes user by id from the database.
     *
     * @param user
     */
    public void delete(User user) {
        LOG.info("User with id {} has been deleted.", user.getId());
        userRepository.deleteById(user.getId());
    }
}
