package mirea.coursework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main класс приложения
 */
@SpringBootApplication()
public class LaundryApplication {

	/**
	 *
	 * @param args - аргументы, которые передаются в программу через консоль
	 */
	public static void main(String[] args) {
		SpringApplication.run(LaundryApplication.class, args);
	}

}
