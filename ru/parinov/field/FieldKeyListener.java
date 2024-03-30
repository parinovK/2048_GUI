package ru.parinov.field;

import ru.parinov.Game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FieldKeyListener extends KeyAdapter {
    private final PaintGame paintGame;

    FieldKeyListener(PaintGame paintGame){
        this.paintGame = paintGame;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT -> Game.getInstance().shiftMatrix(Direction.LEFT, paintGame);
            case KeyEvent.VK_RIGHT -> Game.getInstance().shiftMatrix(Direction.RIGHT, paintGame);
            case KeyEvent.VK_UP -> Game.getInstance().shiftMatrix(Direction.UP, paintGame);
            case KeyEvent.VK_DOWN -> Game.getInstance().shiftMatrix(Direction.DOWN, paintGame);
        }
    }
}
