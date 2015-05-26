package com.dekarti.gs;

/**
 * Класс описывает перечисление текущих целей автомобиля.
 */
enum Target {

    /**
     * выбрать колонку
     */
    CHOOSE_DISPENSER,

    /**
     * встать в очередь на колонку
     */
    GET_IN_LINE,

    /**
     * продвинуться в очереди
     */
    MOVE_IN_LINE,

    /**
     * дозаправиться
     */
    REFUEL,

    /**
     * уехать с колонки
     */
    GET_AWAY_FROM_DISPENSER,

    /**
     * уехать с заправки
     */
    GET_AWAY_FROM_GS



}
