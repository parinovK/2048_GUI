package ru.parinov.field;

import ru.parinov.field.SquaresCreate.Square;
import ru.parinov.Config;
import ru.parinov.Game;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PaintGame extends JPanel{
    @Override
    public Dimension getPreferredSize() {
        return Config.DESIRED_SIZE;
    }

    public PaintGame(){
        setBackground(Color.lightGray);
        setFocusable(true);
        addKeyListener(new FieldKeyListener(this));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!Game.getInstance().validateGameOver()){
            paintForField(g);
            paintPuzzles(g);
        }
        else{
            paintForField(g);
            paintPuzzles(g);
            System.out.println("Game over!");
        }
    }

    private void paintForField(Graphics g) {
        g.setColor(Color.decode("#bbada0"));
        for (List<Square> squareList : Game.getInstance().getAllCoordinates()) {
            for (Square square : squareList) {
                int[][] squarePoints = square.getSquarePoints();
                for (int i = 0; i < Config.fieldSize; i++) {
                    g.drawRoundRect(squarePoints[i][0], squarePoints[i][1],
                            Config.DESIRED_SIZE.height / Config.fieldSize,
                            Config.DESIRED_SIZE.height / Config.fieldSize, 20, 15);
                }
            }
        }
    }

    private void paintPuzzles(Graphics g){
        int shift = 2;
        for (int i = 0; i < Config.fieldSize; i++)
            for (int j = 0; j < Config.fieldSize; j++)
                if (i == Game.getInstance().getAllCoordinates().get(i).get(j).getPositionInMatrix().getX() &&
                        j == Game.getInstance().getAllCoordinates().get(i).get(j).getPositionInMatrix().getY()) {
                    Puzzle.getInstance().paintPuzzle(
                            Game.getInstance().getAllCoordinates().get(i).get(j).getSquarePoints()[0][0] + shift,
                            Game.getInstance().getAllCoordinates().get(i).get(j).getSquarePoints()[0][1] + shift,
                            Config.DESIRED_SIZE.height / Config.fieldSize - shift,
                            Game.getInstance().getMatrixField()[i][j],
                            g);
                }
    }
}
