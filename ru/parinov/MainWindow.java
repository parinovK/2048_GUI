package ru.parinov;

import ru.parinov.field.PaintGame;

import javax.swing.*;

/*
2)Оптимизировать класс Block, чтобы была логика, а не просто много свитчей
4)Если поле заполнено - проиграно, если набрано 2048 - выиграно
6)При перемещении иногда блок остается отдельно - пофиксить нужно
7)придумать с анимацией что-то

Что-то заменяет элементы. Либо добавление двойки при каждом ходе? Проверить.
шестой пункт - возможный фикс еще раз пройти по массиву и переместить квадраты через пустые ячейки
*/

public class MainWindow extends JFrame {

    MainWindow(){
        setTitle("2048: The Game");
        add(new PaintGame());
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args){
        MainWindow window = new MainWindow();
    }
}
