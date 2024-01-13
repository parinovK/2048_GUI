package field;

import field.SquaresCreate.Coordinate;
import field.SquaresCreate.Square;

import java.util.ArrayList;
import java.util.List;

public class CreateFieldGUI {
    private int partOfField;
    private int maxHeightField;

    public CreateFieldGUI(int partOfField, int maxHeightField){
        this.partOfField = partOfField;
        this.maxHeightField = maxHeightField;
    }

    public List<List<Square>> calculateCoordinates(){
        List<List<Square>> allSquaresInLine = new ArrayList<>();
        for (int line = 0; line < maxHeightField/partOfField; line++) {
            List<Square> squares = new ArrayList<>();
            //левый нижний угол всех квадратов первой линии
            for (int step = 0; step < maxHeightField; step += partOfField) {
                squares.add(new Square(new Coordinate(step, line*partOfField)));
            }
            //левый верхний угол всех квадратов первой линии
            for (int step = 0; step < maxHeightField; step += partOfField) {
                squares.get(step / partOfField).addPoint(new Coordinate(step, (line+1)*partOfField));
            }
            //правый верхний угол всех квадратов первой линии
            for (int step = partOfField; step <= maxHeightField; step += partOfField) {
                squares.get(step / partOfField - 1).addPoint(new Coordinate(step, (line+1)*partOfField));
            }
            //правый нижний угол всех квадратов первой линии
            for (int step = partOfField; step <= maxHeightField; step += partOfField) {
                squares.get(step / partOfField - 1).addPoint(new Coordinate(step, line*partOfField));
            }
            allSquaresInLine.add(squares);
        }
        return allSquaresInLine;
    }
}
