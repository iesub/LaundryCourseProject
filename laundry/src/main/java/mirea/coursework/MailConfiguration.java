package mirea.coursework;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**Конфигурация для почты*/

@Configuration
public class MailConfiguration{

    /**Почта, с которой отправляются сообщения*/

    @Value("${application.mail}")
    String mail;

    /**Пароль от почты, с которой отправляются сообщения*/

    @Value("${application.mail.pass}")
    String pass;

    /**Класс, который хранит в себе настройку почты
     * В ней указан хост адреса (Google) и порт хоста,
     * Адресс почты и пароль,
     * Дополнительные настройик
     * @return Полностью настроенный объект mailSender*/

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
