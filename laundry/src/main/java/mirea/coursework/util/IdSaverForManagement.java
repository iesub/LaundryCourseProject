package mirea.coursework.util;

import lombok.Getter;
import lombok.Setter;

/*
Класс, используется для получения id пользователя
из формы на странице сайта
в контроллере OrderOpsController
 */

@Getter
@Setter
public class IdSaverForManagement {
    private String id;

    public IdSaverForManagement(){}
}
