package mirea.coursework;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;
import mirea.coursework.entity.Order;
import mirea.coursework.entity.User;
import mirea.coursework.enumiration.OrderStateEnum;
import mirea.coursework.repository.OrderRepository;
import mirea.coursework.repository.UserRepository;
import mirea.coursework.service.MailService;
import mirea.coursework.service.OrderService;
import mirea.coursework.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class LaundryApplicationTests {

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserService userService;

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private OrderService orderService;

	@Captor
	ArgumentCaptor<Order> orderCaptor;

	@Captor
	ArgumentCaptor<User> userCaptor;

	@Resource
	private JavaMailSenderImpl emailSender;

	private GreenMail testSmtp;

	/**
	 *  Тест на создание пользователя
	 * */

	@Test
	void checkUserCreation() {
		User user = new User();

		user.setMail("example@mail.com");
		user.setPassword("123");
		userService.registerUser(user);

		Mockito.verify(userService).registerUser(userCaptor.capture());
		User capturedUser = userCaptor.getValue();

		assertEquals("example@mail.com", capturedUser.getUsername());
		assertEquals("123", capturedUser.getPassword());
	}

	/**
	 *  Тест на поиск пользователя по имени
	 * */

	@Test
	void checkUserSearch(){
		User user1 = new User();
		UserService userService = new UserService(userRepository);

		user1.setMail("name1");
		user1.setId(1L);

		Mockito.when(userRepository.findByMail(user1.getUsername())).thenReturn(user1);
		Mockito.when(userRepository.findById(user1.getId())).thenReturn(java.util.Optional.of(user1));


		assertEquals("name1", userService.loadUserByUsername(user1.getUsername()).getUsername());
		assertEquals(1L, userService.findUserById(user1.getId()).getId());
	}

	/**
	 *  Тест на создания заказа
	 * */

	@Test
	void orderCreation(){
		Order order = new Order();
		User user = new User();

		user.setMail("name");
		order.setUsers(user);
		order.setState(OrderStateEnum.ACCEPTED_READY_TO_TAKE);
		orderService.insertOrder(order);

		Mockito.verify(orderService).insertOrder(orderCaptor.capture());
		Order capturedOrder = orderCaptor.getValue();

		assertEquals(OrderStateEnum.ACCEPTED_READY_TO_TAKE, capturedOrder.getState());
		assertEquals("name", capturedOrder.getUsers().getUsername());
	}

	/**
	 *  Тест на поиск заказа по состоянию
	 * */

	@Test
	void checkOrderSearch(){
		Order order1 = new Order();
		Order order2 = new Order();
		Order order3 = new Order();
		OrderService orderService = new OrderService(orderRepository);

		order1.setState(OrderStateEnum.ACCEPTED_READY_TO_TAKE);
		order2.setState(OrderStateEnum.RETURNED);
		order3.setState(OrderStateEnum.RETURNED);
		order3.setId(1L);

		Mockito.when(orderRepository.findByState(order1.getState())).thenReturn(List.of(order1));
		Mockito.when(orderRepository.findByState(OrderStateEnum.RETURNED)).thenReturn(List.of(order2, order3));
		Mockito.when(orderRepository.findById(order3.getId())).thenReturn(java.util.Optional.of(order3));

		assertEquals(OrderStateEnum.ACCEPTED_READY_TO_TAKE, orderService.getOrderByState(order1.getState())
				.get(0).getState());
		assertEquals(OrderStateEnum.RETURNED, orderService.getOrderByState(OrderStateEnum.RETURNED).get(1)
				.getState());
		assertEquals(1L, orderService.findOrderById(order3.getId()).getId());
	}


	/**
	 * Метод, проверяющий, правильно ли собираются и отправляются письма пользователям
	 * @throws InterruptedException
	 * @throws MessagingException
	 * @throws IOException
	 */
	@Test
	public void testEmail() throws MessagingException, IOException {

		testSmtp = new GreenMail(ServerSetupTest.SMTP);
		testSmtp.start();

		emailSender.setPort(3025);
		emailSender.setHost("localhost");

		MailService mailService = new MailService(emailSender);
		mailService.setMail("from@smb.com");
		mailService.setIp("localhost");
		mailService.setPort("13378");

		mailService.sendActivationURL("example@mail.com", 1L);
		mailService.updateMail("example@mail.com");

		Message[] messages = testSmtp.getReceivedMessages();
		assertEquals(2, messages.length);
		assertEquals("Регистрация в laundry", messages[0].getSubject());
		assertEquals("<h2>Вы зарегистрировались на сайте laundry</h2><h3>Перейдите по ссылке ниже, для активации вашего аккаунта.</h3><a href = 'http://localhost:13378/registration/activate/1'>Активация</a>",
				messages[0].getContent().toString());

		assertEquals("Ваш заказ в laundry обновлен", messages[1].getSubject());
		assertEquals("<h2>Ваш заказ сменил свой статус</h2><h3>Перейдите по ссылке ниже, чтобы просмотреть изменения.</h3><a href = 'http://localhost:13378/show-orders'> Список заказов </a>",
				messages[1].getContent().toString());
	}
}
