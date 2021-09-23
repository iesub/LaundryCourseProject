package mirea.coursework.util;

import lombok.Getter;
import lombok.Setter;
import mirea.coursework.controller.OrderControlController;

/*
Класс, используется для получения id пользователя
из формы на странице сайта
в контроллере OrderOpsController
 */

/**
 * Утилитный класс, нужен для хранения id заказа, которому нужно сменить состояние
 * Просмотреть точное место использование
 * {@link mirea.coursework.controller.OrderControlController#updateState(IdSaverForManagement)}
 */
@Getter
@Setter
public class IdSaverForManagement {
    private String id;

    /**
     *
     */
    public IdSaverForManagement(){}
}
