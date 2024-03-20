package ru.parinov.field;

import ru.parinov.field.SquaresCreate.Square;
import ru.parinov.GameConfig;
import ru.parinov.Game;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PaintGame extends JPanel{
    @Override
    public Dimension getPreferredSize() {
        return GameConfig.DESIRED_SIZE;
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
                for (int i = 0; i < GameConfig.fieldSize; i++) {
                    g.drawRoundRect(squarePoints[i][0], squarePoints[i][1],
                            GameConfig.DESIRED_SIZE.height / GameConfig.fieldSize,
                            GameConfig.DESIRED_SIZE.height / GameConfig.fieldSize, 20, 15);
                }
            }
        }
    }

    private void paintPuzzles(Graphics g){
        int shift = 2;
        for (int i = 0; i < GameConfig.fieldSize; i++)
            for (int j = 0; j < GameConfig.fieldSize; j++)
                if (i == Game.getInstance().getAllCoordinates().get(i).get(j).getPositionInMatrix().getX() &&
                        j == Game.getInstance().getAllCoordinates().get(i).get(j).getPositionInMatrix().getY()) {
                    Puzzle.getInstance().paintPuzzle(
                            Game.getInstance().getAllCoordinates().get(i).get(j).getSquarePoints()[0][0] + shift,
                            Game.getInstance().getAllCoordinates().get(i).get(j).getSquarePoints()[0][1] + shift,
                            GameConfig.DESIRED_SIZE.height / GameConfig.fieldSize - shift,
                            Game.getInstance().getMatrixField()[i][j],
                            g);
                }
    }
}
