package de.barkeeper.service;

import de.barkeeper.model.User;
import de.barkeeper.model.VerificationToken;
import de.barkeeper.repository.VerificationTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenService {

    private static final Logger LOG = LoggerFactory.getLogger(VerificationTokenService.class);
    private static final java.util.UUID UUID = java.util.UUID.randomUUID();

    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public VerificationTokenService(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }


    /**
     * VerificationToken will be found by token from the database.
     *
     * @param token
     * @return
     */
    public VerificationToken findByToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    /**
     * VerificationToken will be found by user from the database.
     *
     * @param user
     * @return
     */
    public VerificationToken findByUser(User user) {
        return verificationTokenRepository.findByUser(user);
    }

    /**
     * Creates a VerificationToken that will be saved into the database and returns it.
     *
     * @param user
     * @return
     */
    public VerificationToken createVerificationToken(User user) {
        VerificationToken createdVerificationToken = new VerificationToken();
        createdVerificationToken.setToken(java.util.UUID.randomUUID().toString());
        createdVerificationToken.setUser(user);
        return save(createdVerificationToken);
    }

    /**
     * Saves a VerificationToken to the database and returns it with additional information. The new VerificationToken
     *      is printed on the console.
     *
     * @param verificationToken
     * @return
     */
    public VerificationToken save(VerificationToken verificationToken) {
        VerificationToken savedVerificationToken = verificationTokenRepository.save(verificationToken);
        if (savedVerificationToken != null) {
            LOG.info("Verification Token with id {} has been saved.", savedVerificationToken.getId());
        }
        return savedVerificationToken;
    }
}
