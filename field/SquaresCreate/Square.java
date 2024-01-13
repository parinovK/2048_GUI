//получение квадрата по четырем точкам, каждая точка - положение в координатах

package field.SquaresCreate;

import java.util.ArrayList;
import java.util.List;

public class Square {
    private List<Coordinate> allPoints;
    private int[][] squarePoints = new int[4][2];
    private Coordinate positionInMatrix; //позиция блока в матрице. Хранит номер и столбец строки самого квадрата с отображением на матрицу

    public Coordinate getPositionInMatrix(){
        return positionInMatrix;
    }
    public void setPositionInMatrix(Coordinate coordinate){
        positionInMatrix = coordinate;
    }

    public Square(Coordinate point){
        allPoints = new ArrayList<>();
        addPoint(point);
    }

    public void addPoint(Coordinate point){
        allPoints.add(point);
    }

    public void concatenationPoints(){
            for (int i = 0; i < 4; i++) {
               squarePoints[i][0] = allPoints.getFirst().getX();
               squarePoints[i][1] = allPoints.getFirst().getY();
               allPoints.removeFirst();
            }
    }

    public int[][] getSquarePoints(){
        return squarePoints;
    }

    public void outputPoints(){
            for (int i = 0; i < 4; i++){
                System.out.println(squarePoints[i][0] +"\t" + squarePoints[i][1]);
            }
    }
}
