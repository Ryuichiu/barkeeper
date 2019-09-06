package de.barkeeper.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
/**
 * Signals that the annotated class can be used to configure a global instance of AuthenticationManagerBuilder.
 */
@EnableGlobalAuthentication
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private DataSource dataSource;

    @Autowired
    public WebSecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private String USERS_BY_USERNAME_QUERY = "SELECT email, password, enabled FROM user WHERE email=?";


    /**
     * Allows for easily building in memory authentication, JDBC based authentication etc.
     *
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .usersByUsernameQuery(USERS_BY_USERNAME_QUERY)
                .dataSource(dataSource);
    }

    /**
     * Only contains information about how to authenticate the users.
     *
     * @param httpSecurity
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();

        httpSecurity.authorizeRequests()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/home").permitAll()
                .antMatchers("/control").permitAll()
                .antMatchers("/control/**").permitAll();

        /*
        httpSecurity.authorizeRequests().and()
                .formLogin()
                .loginPage("/")
                .failureForwardUrl("/")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .passwordParameter("password")
                .and()
                .logout()
                .logoutUrl("/")
                .logoutSuccessUrl("/");*/

        httpSecurity.authorizeRequests().anyRequest().authenticated();
    }

    /**
     * The abstraction used by PersistentTokenBasedRememberMeServices to store the persistent login tokens for a user
     *      of the selected database.
     *
     * @return
     */
    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    /**
     * Returns an authentication if it can verify that the input represents a valid principal.
     *
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authentication() throws Exception {
        return authenticationManager();
    }

    /**
     * Implementation of PasswordEncoder that uses the BCrypt strong hashing function. Clients can optionally
     *      supply a strength and a 'SecureRandom' instance. The larger the strength parameter the more work will have
     *      to done (exponentially) to hash the passwords. The default value is 10.
     *
     * @return
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
