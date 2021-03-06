package mirea.coursework.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.net.InetAddress;

/**
Сервис для контроля отправки писем.
 */

@Service
@Getter
@Setter
public class MailService {

    /**Объект класса JavaMailSender, который выполняет задачи по отправке писем*/

    @Autowired
    public JavaMailSender emailSender;

    /**Здесь храниться ip адрес, на котором запущено приложения.
     * Используется при формирование ссылки на ресурс, для активации аккаунта*/

    @Value("${server.ip}")
    String ip;

    /**Здесь храниться порт, на котором запущено приложения.
     * Используется при формирование ссылки на ресурс, для активации аккаунта*/

    @Value("${server.port}")
    String port;

    /**Почта, с которой отправляется письмо пользователю.*/

    @Value("${application.mail}")
    String mail;

    /**
     * Пустой конструктор класса
     */
    public MailService(){}

    /**
     * Конструктор с указанием нового объекта класса JavaMailSender
     * Создан для проведения Unit тестов
     * @param emailSender - объект класса JavaMailSender
     */
    public MailService(JavaMailSender emailSender){
        this.emailSender = emailSender;
    }

    /**Отправляет новому пользователю письмо с ссылкой на активацию аккаунта
     * Письмо отправляется с заголовоком "Ваш заказ в laundry обновлен"
     * Содержание письма - html страница:
     * <h2>Вы зарегистрировались на сайте laundry</h2>
     * <h3>Перейдите по ссылке ниже, для активации вашего аккаунта.</h3>
     * <a href = 'http://"+{ip адрес сайта}+":"+{port сайта}+"/registration/activate/"+ {userId пользователя} +"'>Активация</a>
     * @param userMail Адрес клиента, на который отправляется письмо
     * @param userId Id пользователя. Использется при формировании ссылки для активации аккаунта*/
    public boolean sendActivationURL(String userMail, Long userId) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        String htmlMsg = "<h2>Вы зарегистрировались на сайте laundry</h2>" +
                "<h3>Перейдите по ссылке ниже, для активации вашего аккаунта.</h3>" +
                "<a href = 'http://"+ip+":"+port+"/registration/activate/"+ userId +"'>Активация</a>";
        try {
            helper.setText(htmlMsg, true);
            helper.setTo(userMail);
            helper.setSubject("Регистрация в laundry");
            helper.setFrom(mail);
            emailSender.send(mimeMessage);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**Отправляет пользователю сообщение о смене состояния заказа
     * Письмо отправляется с заголовоком "Ваш заказ в laundry обновлен"
     * Содержание письма - html страница:
     * <h2>Ваш заказ сменил свой статус</h2>
     * <h3>Перейдите по ссылке ниже, чтобы просмотреть изменения.</h3>
     * <a href = 'http://"+{ip адрес сайта}+":"+{port сайта}+"/show-orders'> Список заказов</a>
     * @param userMail Адрес клиента, на который отправляется письмо */

    public boolean updateMail(String userMail) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        String htmlMsg = "<h2>Ваш заказ сменил свой статус</h2>" +
                "<h3>Перейдите по ссылке ниже, чтобы просмотреть изменения.</h3>" +
                "<a href = 'http://"+ip+":"+port+"/show-orders'> Список заказов </a>";
        try {
            helper.setText(htmlMsg, true);
            helper.setTo(userMail);
            helper.setSubject("Ваш заказ в laundry обновлен");
            helper.setFrom(mail);
            emailSender.send(mimeMessage);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
