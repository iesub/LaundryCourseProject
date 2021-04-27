package mirea.coursework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/*
Сервис для контроля отправки писем.
 */

@Service
public class MailService {
    @Autowired
    public JavaMailSender emailSender;

    //Отправляет новому пользователю письмо с ссылкой на активацию аккаунта
    public void sendActivationURL(String userMail, Long userId) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        String htmlMsg = "<h2>Вы зарегистрировались на сайте laundry</h2>" +
                "<h3>Перейдите по ссылке ниже, для активации вашего аккаунта.</h3>" +
                "<a href = 'http://{YOUR_IP_OR_DOMAIN}/registration/activate/"+ userId +"'>Активация</a>";
        try {
            helper.setText(htmlMsg, true);
            helper.setTo(userMail);
            helper.setSubject("Регистрация в laundry");
            helper.setFrom({YOUR_MAIL});
            emailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
