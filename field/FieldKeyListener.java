package field;

import mainCode.Game;

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
            case KeyEvent.VK_LEFT -> Game.getInstance().shiftMatrix(Shift.LEFT, paintGame);
            case KeyEvent.VK_RIGHT -> Game.getInstance().shiftMatrix(Shift.RIGHT, paintGame);
            case KeyEvent.VK_UP -> Game.getInstance().shiftMatrix(Shift.UP, paintGame);
            case KeyEvent.VK_DOWN -> Game.getInstance().shiftMatrix(Shift.DOWN, paintGame);
        }
    }
}
