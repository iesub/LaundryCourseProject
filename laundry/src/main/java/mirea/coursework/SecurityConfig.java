package mirea.coursework;

import mirea.coursework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/** Конфигурация SpringSecurity
* Данная конфигурация ограничивает функцианал незарегистрированнхы
* пользователей, также не дает пользователю, не имеющему роль
* менеджера доступ к странице менеджмента*/

@Configuration
@EnableWebSecurity
@EnableJpaRepositories
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**UserService который использует кодировку паролей*/

    @Autowired
    UserService userService;

    /**Метод, который возвращает метод кодировки
     * @return Метод кодировки*/

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    /**Метод, что устанавливает все правила подключения и области доступа для различных пользователей
     * @param http Сетевой протокол*/

    @Override
    protected void configure(HttpSecurity http) throws
            Exception {
        http.csrf().disable().cors().disable()
                .authorizeRequests()
                .antMatchers("/management/order-control").hasAuthority("ROLE_MANAGER")
                .antMatchers("/registration/activate/*", "/login", "/registration", "/login-error")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login-error")
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/");
    }

    /**Метод, который устанавливает метод кодировки на авторизацию
     * @param auth Класс SpringSecurity, отвечающий за аквторизацию*/

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(encoder());
    }
}
