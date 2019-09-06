package de.barkeeper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class MailConfig {

    private String MAIL_HOST = "localhost";
    private int MAIL_PORT = 587;
    private String MAIL_PROTOCOL = "smtp";
    private String MAIL_USERNAME = "noreply@barkeeper.de";
    private String MAIL_PASSWORD = "barkeeper";

    /**
     * The 'org.springframework.mail' package is the root level package for the email support. The central interface
     *      for sending emails is the MailSender interface; a simple value object encapsulating the properties of a
     *      simple mail such as from and to (plus many others) is the SimpleMailMessage class. This package also
     *      contains a hierarchy of checked exceptions which provide a higher level of abstraction over the lower
     *      level mail system exceptions with the root exception beeing MailException.
     *
     * @return
     */
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setJavaMailProperties(mailSenderProperties());
        javaMailSender.setHost(MAIL_HOST);
        javaMailSender.setPort(MAIL_PORT);
        javaMailSender.setProtocol(MAIL_PROTOCOL);
        javaMailSender.setUsername(MAIL_USERNAME);
        javaMailSender.setPassword(MAIL_PASSWORD);
        return javaMailSender;
    }

    /**
     * Properties of JavaMailSender
     *
     * @return
     */
    protected Properties mailSenderProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.starttls.required", true);
        properties.put("mail.smtp.socketFactory.port", 465);
        properties.put("mail.smtp.debug", true);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", false);
        return properties;
    }
}
