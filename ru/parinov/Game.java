//логика игры

package ru.parinov;

import ru.parinov.field.PaintGame;
import ru.parinov.field.Shift;
import ru.parinov.field.SquaresCreate.Coordinate;
import ru.parinov.field.CreateFieldGUI;
import ru.parinov.field.SquaresCreate.Square;

import java.util.*;
import java.util.List;

public class Game{
    private static Game instance;
    private List<List<Square>> allCoordinates;
    private int[][] matrixField;

    public static Game getInstance(){
        if (instance == null){
            instance = new Game();
        }
        return instance;
    }

    public List<List<Square>> getAllCoordinates() {
        return allCoordinates;
    }

    public int[][] getMatrixField() {
        return matrixField;
    }

    private Game() {
        if (GameConfig.debugCLI)
            System.out.println("Debug CLI is enabled.");
        createFieldCLI();
        createFieldGUI();
        outputFieldCLI();
    }

    public boolean validateGameOver(){
        for (int i = 0; i < GameConfig.fieldSize; i++)
            for (int j = 0; j < GameConfig.fieldSize; j++)
                if (matrixField[i][j] == 0)
                    return false;
        return true;
    }

    private void createFieldGUI() {
        CreateFieldGUI field = new CreateFieldGUI(
                GameConfig.DESIRED_SIZE.height/ GameConfig.fieldSize,
                GameConfig.DESIRED_SIZE.height
        );
        allCoordinates = field.calculateCoordinates();
        for (int i = 0; i < allCoordinates.size(); i++) {
            for (int j = 0; j < allCoordinates.get(i).size(); j++) {
                allCoordinates.get(i).get(j).concatenationPoints();
                allCoordinates.get(i).get(j).setPositionInMatrix(new Coordinate(i, j));
            }
        }
        allCoordinates = ReverseMatrix();
    }

    private void createFieldCLI() {
        matrixField = new int[GameConfig.fieldSize][GameConfig.fieldSize];
        for (int i = 0; i < GameConfig.fieldSize; i++)
            for (int j = 0; j < GameConfig.fieldSize; j++)
                matrixField[i][j] = 0;
        setBeginPlacesToField(matrixField);
    }

    private void outputFieldCLI() {
        if (GameConfig.debugCLI) {
            System.out.println("\nMatrix for debug: ");
            for (int i = 0; i < GameConfig.fieldSize; i++) {
                for (int j = 0; j < GameConfig.fieldSize; j++)
                    System.out.print(matrixField[i][j] + "\t");
                System.out.println();
            }
        }
    }

    private List<List<Square>> ReverseMatrix() {
        List<List<Square>> reverseMatrix = new ArrayList<>();
        for (int i = 0; i < allCoordinates.size(); i++) {
            for (int j = 0; j < allCoordinates.get(i).size(); j++) {
                reverseMatrix.add(allCoordinates.get(j));
            }
            allCoordinates.clear();
        }
        return reverseMatrix;
    }

    private void setBeginPlacesToField(int[][] matrixField) {
        int maxValue = (GameConfig.fieldSize * GameConfig.fieldSize) - 1;
        int minValue = 0;
        Set<Integer> places = new HashSet<>();

        while (places.size() != 3) {
            places.add(new Random().nextInt((maxValue - minValue) + 1) + minValue);
        }
        int line;
        for (var place : places) {
            line = place / GameConfig.fieldSize;
            matrixField[line][place - (line * GameConfig.fieldSize)] = 2;
        }
    }

    public void shiftMatrix(Shift shift, PaintGame game){
        if (validateGameOver())
            return;
        boolean addedDigitInField = false;
        switch (shift) {
            case LEFT -> addedDigitInField = shiftLeft();
            case RIGHT -> addedDigitInField = shiftRight();
            case UP -> addedDigitInField = shiftUp();
            case DOWN -> addedDigitInField = shiftDown();
        }
        addedDigitToMatrix(addedDigitInField);
        outputFieldCLI();
        game.repaint();
    }

    private void addedDigitToMatrix(boolean addedDigitInField){
        if (!addedDigitInField)//если добавление разрешено
            return;
        List<Integer> positionZero = new ArrayList<>();
        outputFieldCLI();
        for (int i = 0; i < GameConfig.fieldSize; i++) {
            for (int j = 0; j < GameConfig.fieldSize; j++) {
                if (matrixField[i][j] == 0) {
                    positionZero.add(i);
                    positionZero.add(j);
                }
            }
        }
        int randomPlace = (int)(Math.random()*(positionZero.size()-1));
        matrixField[positionZero.get(randomPlace)][positionZero.get(randomPlace+1)] = 2;
    }

    private boolean shiftLeft(){
        boolean addedDigitInField = false;
        boolean isShift = true;
        while (isShift) {
            for (int i = GameConfig.fieldSize - 1; i >= 0; i--) {
                for (int j = GameConfig.fieldSize - 1; j >= 0; j--) {
                    if (matrixField[i][j] != 0) {
                        isShift = findZeroInLine(Shift.LEFT, i, j);//максимально сдвигаем по нулям
                        if (isShift)
                            addedDigitInField = true;

                    }
                }
            }
        }
        for (int i = GameConfig.fieldSize - 1; i >= 0; i--) {
            for (int j = GameConfig.fieldSize - 1; j >= 1; j--) {
                isShift = findDuplicate(Shift.LEFT, i, j);
                if (isShift)
                    addedDigitInField = true;
            }
        }
        return addedDigitInField;
    }

    private boolean shiftRight(){
        boolean addedDigitInField = false;
        boolean isShift = true;
        while (isShift) {
            for (int i = GameConfig.fieldSize - 1; i >= 0; i--) {
                for (int j = GameConfig.fieldSize - 1; j >= 0; j--) {
                    if (matrixField[i][j] != 0) {
                        isShift = findZeroInLine(Shift.RIGHT, i, j);//максимально сдвигаем по нулям
                        if (isShift)
                            addedDigitInField = true;
                    }
                }
            }
        }
        for (int i = GameConfig.fieldSize - 1; i >= 0; i--) {
            for (int j = (GameConfig.fieldSize - 1) - 1; j >= 0; j--) {
                isShift = findDuplicate(Shift.RIGHT, i, j);
                if (isShift)
                    addedDigitInField = true;
            }
        }
        return addedDigitInField;
    }

    private boolean shiftUp(){
        boolean addedDigitInField = false;
        boolean isShift = true;
        while (isShift) {
            for (int i = GameConfig.fieldSize; i >= 0; i--) {
                for (int j = GameConfig.fieldSize - 1; j >= 0; j--) {
                    if (matrixField[i][j] != 0) {
                        isShift = findZeroInLine(Shift.UP, i, j);//максимально сдвигаем по нулям
                        if (isShift)
                            addedDigitInField = true;
                    }
                }
            }
        }
        for (int i = GameConfig.fieldSize - 1; i >= 1; i--) {
            for (int j = GameConfig.fieldSize - 1; j >= 0; j--) {
                isShift = findDuplicate(Shift.UP, i, j);
                if (isShift)
                    addedDigitInField = true;
            }
        }
        return addedDigitInField;
    }

    private boolean shiftDown(){
        boolean addedDigitInField = false;
        boolean isShift = true;
        while (isShift) {
            for (int i = GameConfig.fieldSize - 1; i >= 0; i--) {
                for (int j = GameConfig.fieldSize - 1; j >= 0; j--) {
                    if (matrixField[i][j] != 0) {
                        isShift = findZeroInLine(Shift.DOWN, i, j);//максимально сдвигаем по нулям
                        if (isShift)
                            addedDigitInField = true;
                    }
                }
            }
        }
        for (int i = (GameConfig.fieldSize - 1) - 1; i >= 0; i--) {
            for (int j = GameConfig.fieldSize - 1; j >= 0; j--) {
                isShift = findDuplicate(Shift.DOWN, i, j);
                if (isShift)
                    addedDigitInField = true;
            }
        }
        return addedDigitInField;
    }

    private boolean findZeroInLine(Shift shift, int index_line, int index_digit_in_line){ //вернет позицию первого нуля в строке
        if (matrixField[index_line][index_digit_in_line] == 0)
            return false;
        int temp;
        if (shift == Shift.LEFT){
            temp = index_digit_in_line;
            while (temp-1 >= 0 && matrixField[index_line][temp-1] == 0){
                temp--;
            }
            if (temp != index_digit_in_line) {
                matrixField[index_line][temp] = matrixField[index_line][index_digit_in_line];
                matrixField[index_line][index_digit_in_line] = 0;
                return true;
            }
        }
        if (shift == Shift.RIGHT){
            temp = index_digit_in_line;
            while (temp+1 < GameConfig.fieldSize && matrixField[index_line][temp+1] == 0){
                temp++;
            }
            if (temp != index_digit_in_line) {
                matrixField[index_line][temp] = matrixField[index_line][index_digit_in_line];
                matrixField[index_line][index_digit_in_line] = 0;
                return true;
            }
        }

        if (shift == Shift.UP){
            temp = index_line;
            while (temp-1 >= 0 && matrixField[temp-1][index_digit_in_line] == 0){
                temp--;
            }
            if (temp != index_line){
                matrixField[temp][index_digit_in_line] = matrixField[index_line][index_digit_in_line];
                matrixField[index_line][index_digit_in_line] = 0;
                return true;
            }
        }

        if (shift == Shift.DOWN){
            temp = index_line;
            while (temp+1 < GameConfig.fieldSize && matrixField[temp+1][index_digit_in_line] == 0){
                temp++;
            }
            if (temp != index_line){
                matrixField[temp][index_digit_in_line] = matrixField[index_line][index_digit_in_line];
                matrixField[index_line][index_digit_in_line] = 0;
                return true;
            }
        }
        return false;
    }

    private boolean findDuplicate(Shift shift, int line, int indexLine){
        if (shift == Shift.LEFT) {
            if (matrixField[line][indexLine] == matrixField[line][indexLine - 1]) {
                matrixField[line][indexLine - 1] += matrixField[line][indexLine];
                matrixField[line][indexLine] = 0;
                return true;
            }
        }
        if (shift == Shift.RIGHT)
            if (matrixField[line][indexLine] == matrixField[line][indexLine + 1]) {
                matrixField[line][indexLine + 1] += matrixField[line][indexLine];
                matrixField[line][indexLine] = 0;
                return true;
            }
        if (shift == Shift.UP)
            if (matrixField[line][indexLine] == matrixField[line - 1][indexLine]) {
                matrixField[line - 1][indexLine] += matrixField[line][indexLine];
                matrixField[line][indexLine] = 0;
                return true;
            }

        if (shift == Shift.DOWN)
            if ((line+1) < GameConfig.fieldSize && matrixField[line][indexLine] == matrixField[line+1][indexLine]) {
                matrixField[line + 1][indexLine] += matrixField[line][indexLine];
                matrixField[line][indexLine] = 0;
                return true;
            }
        return false;
    }
}
