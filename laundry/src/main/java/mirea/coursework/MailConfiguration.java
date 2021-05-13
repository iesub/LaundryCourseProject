package mirea.coursework;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

//Конфигурация для почты

@Configuration
public class MailConfiguration{
    @Value("${application.mail}")
    String mail;

    @Value("${application.mail.pass}")
    String pass;
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com"); //Хост почтового сервиса
        mailSender.setPort(587);

        mailSender.setUsername(mail); //Почтовый ящик
        mailSender.setPassword(pass); //Пароль

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
