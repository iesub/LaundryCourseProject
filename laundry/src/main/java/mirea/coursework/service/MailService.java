package mirea.coursework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {
    @Autowired
    public JavaMailSender emailSender;

    public void sendActivationURL(String userMail, Long userId) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        String htmlMsg = "<h2>Вы зарегистрировались на сайте laundry</h2>" +
                "<h3>Перейдите по ссылке ниже, для активации вашего аккаунта.</h3>" +
                "<a href = 'http://89.179.247.172:13378/registration/activate/"+ userId +"'>Активация</a>";
        try {
            helper.setText(htmlMsg, true);
            helper.setTo(userMail);
            helper.setSubject("Регистрация в laundry");
            helper.setFrom("iesubutil@gmail.com");
            emailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
