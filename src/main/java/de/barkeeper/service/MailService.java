package de.barkeeper.service;

import de.barkeeper.model.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class MailService {

    private JavaMailSender javaMailSender;
    private TemplateEngine templateEngine;

    @Autowired
    public MailService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }


    @Async
    public void sendRegistrationMail(List<String> recipients) throws MessagingException {
        Mail mail = new Mail();
        mail.setTo(recipients);
        mail.setSubject("Registration");
        mail.setMessage("Confirm registration.");
        mail.setTemplate("mail");
        send(mail);
    }

    @Async
    public void send(final Mail mail) throws MessagingException {
        Context context = new Context();
        context.setVariable("message", mail.getMessage());
        String template = templateEngine.process(mail.getTemplate(), context);
        sendMail(mail, template);
    }

    public void sendMail(final Mail mail, final String template) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        if (mail.getTo() != null) {
            String[] recipients = new String[mail.getTo().size()];
            for (int i = 0; i < mail.getTo().size(); i++) {
                for (String to : mail.getTo()) {
                    recipients[i] = to;
                }
            }
            helper.setTo(recipients);
        }

        if (mail.getCc() != null) {
            String[] carbonCopies = new String[mail.getCc().size()];
            for (int i = 0; i < mail.getCc().size(); i++) {
                for (String cc : mail.getCc()) {
                    carbonCopies[i] = cc;
                }
            }
            helper.setCc(carbonCopies);
        }

        if (mail.getBcc() != null) {
            String[] blindCarbonCopies = new String[mail.getBcc().size()];
            for (int i = 0; i < mail.getBcc().size(); i++) {
                for (String bcc : mail.getBcc()) {
                    blindCarbonCopies[i] = bcc;
                }
            }
            helper.setBcc(blindCarbonCopies);
        }

        helper.setSubject(mail.getSubject());
        helper.setText(template, true);
        javaMailSender.send(message);
    }
}
