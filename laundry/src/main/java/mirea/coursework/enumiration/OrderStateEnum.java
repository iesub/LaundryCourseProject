package mirea.coursework.enumiration;

/**
Данный класс хранит возможные состояния заказов
0 - Заказ отправлен, в ожидании одобрения
1 - Заказ одобрен, в ожидании приезда клиента с вещами
2 - Вещи приняты и ожидают стирки
3 - Вещи в процессе стирки
4 - Вещи постираны, готовы возвращать
5 - Вещи возвращены, заказ закрыт
*/

public enum OrderStateEnum {
    AWAIT_TO_ACCEPT,
    ACCEPTED_READY_TO_TAKE,
    TAKEN,
    PROCESSING,
    READY_TO_RETURN,
    RETURNED;
}
