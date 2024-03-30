package ru.parinov;

import ru.parinov.field.PaintGame;
import ru.parinov.field.Direction;
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
        createFieldCLI();
        createFieldGUI();
        if (Config.debugCLI) {
            System.out.println("Debug CLI is enabled.");
            System.out.println("init field success.");
            outputFieldCLI();
        }
    }

    public boolean validateGameOver(){
        for (int i = 0; i < Config.fieldSize; i++)
            for (int j = 0; j < Config.fieldSize; j++)
                if (matrixField[i][j] == 0)
                    return false;
        return true;
    }

    private void createFieldGUI() {
        CreateFieldGUI field = new CreateFieldGUI(
                Config.DESIRED_SIZE.height/ Config.fieldSize,
                Config.DESIRED_SIZE.height
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
        matrixField = new int[Config.fieldSize][Config.fieldSize];
        for (int i = 0; i < Config.fieldSize; i++)
            for (int j = 0; j < Config.fieldSize; j++)
                matrixField[i][j] = 0;
        setBeginPlacesToField(matrixField);
    }

    private void outputFieldCLI() {
        if (Config.debugCLI) {
            for (int i = 0; i < Config.fieldSize; i++) {
                for (int j = 0; j < Config.fieldSize; j++)
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
        int maxValue = (Config.fieldSize * Config.fieldSize) - 1;
        int minValue = 0;
        Set<Integer> places = new HashSet<>();

        while (places.size() != 3) {
            places.add(new Random().nextInt((maxValue - minValue) + 1) + minValue);
        }
        int line;
        for (var place : places) {
            line = place / Config.fieldSize;
            matrixField[line][place - (line * Config.fieldSize)] = 2;
        }
    }

    public void shiftMatrix(Direction direction, PaintGame game){
        if (validateGameOver())
            return;
        if (Config.debugCLI) {
            System.out.println("---------------------");
            System.out.println("Matrix before shift: ");
            outputFieldCLI();
            System.out.println("Shift direction: " + direction);
        }
        boolean addedDigitInField = moveByKeypress(direction);
        if (Config.debugCLI){
            System.out.println("Matrix before shift: ");
            outputFieldCLI();
        }
        addedDigitToMatrix(addedDigitInField);
        game.repaint();
    }

    private boolean moveByKeypress(Direction direction){
        boolean isMoved = false, isShift = false, isFindDuplicate = false;
        for (int i = 0; i < Config.fieldSize; i++) {
            for (int j = 0; j < Config.fieldSize; j++) {
                if (matrixField[i][j] != 0) {
                    isShift = findZeroInLine(direction, i, j);
                }
            }
        }
        if (isShift)
            isMoved = true;

        for (int i = 0; i < Config.fieldSize; i++) {
            for (int j = 0; j < Config.fieldSize; j++) {
                isFindDuplicate = findDuplicate(direction, i, j);
                if (isFindDuplicate)
                    isMoved = true;
            }
        }
        if (isFindDuplicate)
            isMoved = true;

        if (Config.debugCLI) {
            System.out.println("Added digit: " + isMoved);
        }
        return isMoved;
    }

    private boolean findZeroInLine(Direction direction, int row, int column){
        if (matrixField[row][column] == 0)
            return false;
        int temp;
        if (direction == Direction.LEFT){
            if (column == 0)
                return false;
            temp = column;
            while (temp-1 >= 0 && matrixField[row][temp-1] == 0){
                temp--;
            }
            if (temp != column) {
                matrixField[row][temp] = matrixField[row][column];
                matrixField[row][column] = 0;
                return true;
            }
        }
        if (direction == Direction.RIGHT){
            if (column == Config.fieldSize)
                return false;
            temp = column;
            while (temp+1 < Config.fieldSize && matrixField[row][temp+1] == 0){
                temp++;
            }
            if (temp != column) {
                matrixField[row][temp] = matrixField[row][column];
                matrixField[row][column] = 0;
                return true;
            }
        }

        if (direction == Direction.UP){
            if (row == 0)
                return false;
            temp = row;
            while (temp-1 >= 0 && matrixField[temp-1][column] == 0){
                temp--;
            }
            if (temp != row){
                matrixField[temp][column] = matrixField[row][column];
                matrixField[row][column] = 0;
                return true;
            }
        }

        if (direction == Direction.DOWN){
            if (row == Config.fieldSize)
                return false;
            temp = row;
            while (temp+1 < Config.fieldSize && matrixField[temp+1][column] == 0){
                temp++;
            }
            if (temp != row){
                matrixField[temp][column] = matrixField[row][column];
                matrixField[row][column] = 0;
                return true;
            }
        }
        return false;
    }

    private boolean findDuplicate(Direction direction, int line, int column){
        if (matrixField[line][column] == 0)
            return false;
        if (direction == Direction.LEFT) {
            if (column != 0 &&
                    matrixField[line][column] == matrixField[line][column - 1]) {
                matrixField[line][column - 1] += matrixField[line][column];
                matrixField[line][column] = 0;
                return true;
            }
        }
        if (direction == Direction.RIGHT) {
            if (column != Config.fieldSize-1 &&
                    matrixField[line][column] == matrixField[line][column + 1]) {
                matrixField[line][column + 1] += matrixField[line][column];
                matrixField[line][column] = 0;
                return true;
            }
        }
        if (direction == Direction.UP) {
            if (line != 0 &&
                    matrixField[line][column] == matrixField[line - 1][column]) {
                matrixField[line - 1][column] += matrixField[line][column];
                matrixField[line][column] = 0;
                return true;
            }
        }

        if (direction == Direction.DOWN) {
            if (line != Config.fieldSize -1 &&
                    (line + 1) < Config.fieldSize &&
                    matrixField[line][column] == matrixField[line + 1][column]) {
                matrixField[line + 1][column] += matrixField[line][column];
                matrixField[line][column] = 0;
                return true;
            }
        }
        return false;
    }

    private void addedDigitToMatrix(boolean addedDigitInField){
        if (!addedDigitInField) {//если добавление не разрешено
            if (Config.debugCLI)
                System.out.println("there are still moves. There is no need to add two. Pass.");
            return;
        }
        List<Coordinate> positionZero = new ArrayList<>();
        for (int i = 0; i < Config.fieldSize; i++) {
            for (int j = 0; j < Config.fieldSize; j++) {
                if (matrixField[i][j] == 0) {
                    positionZero.add(new Coordinate(i,j));
                }
            }
        }
        if (Config.debugCLI) {
            System.out.println("list of positions of found voids:");
            for (var element : positionZero)
                System.out.println(element.getX() + " " + element.getY());
        }
        Collections.shuffle(positionZero);
        matrixField[positionZero.get(0).getX()][positionZero.get(0).getY()] = 2;
        if (Config.debugCLI){
            System.out.println("a two was added to the position: ");
            System.out.println(positionZero.get(0).getX() + " " + positionZero.get(0).getY());
            System.out.println("There are no moves. Allowed to add two");
            System.out.println("matrix after adding two:");
            outputFieldCLI();
        }
    }
}
