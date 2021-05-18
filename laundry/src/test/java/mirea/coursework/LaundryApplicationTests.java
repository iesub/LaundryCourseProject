package mirea.coursework;

import mirea.coursework.entity.Order;
import mirea.coursework.entity.User;
import mirea.coursework.enumiration.OrderStateEnum;
import mirea.coursework.repository.UserRepository;
import mirea.coursework.service.OrderService;
import mirea.coursework.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class LaundryApplicationTests {

	@Mock
	private UserService userService;

	@Mock
	private OrderService orderService;

	@Captor
	ArgumentCaptor<Order> orderCaptor;

	@Captor
	ArgumentCaptor<User> userCaptor;

	@Test
	void checkUserCreation() {
		User user = new User();

		user.setUsername("example@mail.com");
		user.setPassword("123");
		userService.registerUser(user);

		Mockito.verify(userService).registerUser(userCaptor.capture());
		User capturedUser = userCaptor.getValue();

		Assertions.assertEquals("example@mail.com", capturedUser.getUsername());
		Assertions.assertEquals("123", capturedUser.getPassword());
	}

	@Test
	void checkUserSearch(){
		User user1 = new User();
		User user2 = new User();

		user1.setUsername("name1");
		user2.setUsername("name2");

		Mockito.when(userService.loadUserByUsername(user1.getUsername())).thenReturn(user1);
		Mockito.when(userService.loadUserByUsername(user2.getUsername())).thenReturn(user2);

		Assertions.assertEquals("name1", userService.loadUserByUsername(user1.getUsername()).getUsername());
		Assertions.assertEquals("name2", userService.loadUserByUsername(user2.getUsername()).getUsername());
	}

	@Test
	void orderCreation(){
		Order order = new Order();
		User user = new User();

		user.setUsername("name");
		order.setUsers(user);
		order.setState(OrderStateEnum.ACCEPTED_READY_TO_TAKE);
		orderService.insertOrder(order);

		Mockito.verify(orderService).insertOrder(orderCaptor.capture());
		Order capturedOrder = orderCaptor.getValue();

		Assertions.assertEquals(OrderStateEnum.ACCEPTED_READY_TO_TAKE, capturedOrder.getState());
		Assertions.assertEquals("name", capturedOrder.getUsers().getUsername());
	}

	@Test
	void checkOrderSearch(){
		Order order1 = new Order();
		Order order2 = new Order();
		Order order3 = new Order();

		order1.setState(OrderStateEnum.ACCEPTED_READY_TO_TAKE);
		order2.setState(OrderStateEnum.RETURNED);
		order3.setState(OrderStateEnum.RETURNED);

		Mockito.when(orderService.getOrderByState(order1.getState())).thenReturn(List.of(order1));
		Mockito.when(orderService.getOrderByState(OrderStateEnum.RETURNED)).thenReturn(List.of(order2, order3));

		Assertions.assertEquals(OrderStateEnum.ACCEPTED_READY_TO_TAKE, orderService.getOrderByState(order1.getState())
				.get(0).getState());
		Assertions.assertEquals(OrderStateEnum.RETURNED, orderService.getOrderByState(OrderStateEnum.RETURNED).get(1)
				.getState());
	}

}
