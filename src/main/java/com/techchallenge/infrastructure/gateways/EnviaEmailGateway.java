package com.techchallenge.infrastructure.gateways;

import com.techchallenge.application.gateway.EmailGateway;
import com.techchallenge.core.exceptions.BusinessException;
import com.techchallenge.domain.entity.Email;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class EnviaEmailGateway implements EmailGateway {

    private final JavaMailSender mailSender;
    public EnviaEmailGateway(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void send(Email email) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(email.getRemetente());
            helper.setTo(email.getDestinatari());
            helper.setSubject(email.getAssunto());
            helper.setText(email.getTexto());
            FileSystemResource resource = new FileSystemResource(new File(email.getAnexo()));
            helper.addAttachment(email.getAnexo(), resource);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new BusinessException("Falha ao enviar e-mail",e);
        }
    }
}
