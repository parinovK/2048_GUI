package ru.parinov.field;

import ru.parinov.ConfigGame;

import java.awt.*;

public class Puzzle {
    private static Puzzle instance;

    private Puzzle(){}

    public static Puzzle getInstance() {
        if (instance == null)
            instance = new Puzzle();
        return instance;
    }

    public void paintPuzzle(int x, int y, int blockSize, int digit, Graphics graphics){

        graphics.setFont(ConfigGame.fontForBlock);
        int shift = 20;
        final String[] hexColorForPuzzle = {"#eee4da","#eee1c9","#f3b27a","#F69664","#F77C5F","#F75F3B","#EDD073","#EDCC62","#EDC950"};

        switch(digit){
            case 2 -> graphics.setColor(Color.decode(hexColorForPuzzle[0]));
                /*Integer.toString(digit), CenterDrawDigitX-shift, CenterDrawDigitY+shift*/
            case 4 -> graphics.setColor(Color.decode(hexColorForPuzzle[1]));
                /*CenterDrawDigitX-shift, CenterDrawDigitY + shift*/
            case 8 -> graphics.setColor(Color.decode(hexColorForPuzzle[2]));
                /*CenterDrawDigitX-shift, CenterDrawDigitY + shift*/
            case 16 -> graphics.setColor(Color.decode(hexColorForPuzzle[3]));
               /*CenterDrawDigitX-shift*2, CenterDrawDigitY + shift*/
            case 32 -> graphics.setColor(Color.decode(hexColorForPuzzle[4]));
               /*CenterDrawDigitX-shift*2, CenterDrawDigitY + (int)(shift*1.5)*/
            case 64 -> graphics.setColor(Color.decode(hexColorForPuzzle[5]));
               /*CenterDrawDigitX-shift*2, CenterDrawDigitY + (int)(shift*1.5)*/
            case 128 -> graphics.setColor(Color.decode(hexColorForPuzzle[6]));
                /*CenterDrawDigitX-(int)(shift*2.7), CenterDrawDigitY + (int)(shift*1.2*/
            case 256 -> graphics.setColor(Color.decode(hexColorForPuzzle[7]));
                /*CenterDrawDigitX-(int)(shift*2.7), CenterDrawDigitY + (int)(shift*1.2)*/
            case 512 -> graphics.setColor(Color.decode(hexColorForPuzzle[8]));
               /*CenterDrawDigitX-(int)(shift*2.7), CenterDrawDigitY + (int)(shift*1.2)*/
            case 1024 ->{
                graphics.setFont(new Font("TimesRoman", Font.BOLD, 60));
                graphics.setColor(Color.decode(hexColorForPuzzle[7]));
                /*(int)(CenterDrawDigitX-blockSize/2.2), CenterDrawDigitY + (int)(shift*1.2)*/
            }
            case 2048 ->{
                graphics.setFont(new Font("TimesRoman", Font.BOLD, 60));
                graphics.setColor(Color.decode(hexColorForPuzzle[7]));
                /*(int)(CenterDrawDigitX-blockSize/2.2), CenterDrawDigitY + (int)(shift*1.2)*/
            }
        }

        graphics.fillRoundRect(x, y, blockSize, blockSize, 20, 15);
        if (digit == 0){
            graphics.setColor(Color.decode("#cdc1b4"));
            graphics.fillRect(x, y, blockSize, blockSize);
        }
        else if (digit == 2 || digit == 4){
            graphics.setColor(Color.decode("#796d66"));
            graphics.drawString(Integer.toString(digit), (x + blockSize/2)-shift, (y + blockSize/2)+shift);
        }
        else{
            graphics.setColor(Color.decode("#F9F6F2"));
            graphics.drawString(Integer.toString(digit), (x + blockSize/2)-shift*2, (y + blockSize/2) + shift);
        }
    }
}
