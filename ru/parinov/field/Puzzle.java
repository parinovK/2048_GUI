package ru.parinov.field;

import ru.parinov.ConfigGame;

import java.awt.*;

public class Puzzle {
    //будет рисовать в нужных координатах квадрат похожий на блок из 2048
    private static Puzzle instance;

    private Puzzle(){}

    public static Puzzle getInstance() {
        if (instance == null)
            instance = new Puzzle();
        return instance;
    }

    public void paintPuzzle(int x, int y, int blockSize, int digit, Graphics graphics){
        int CenterDrawDigitX = x + blockSize/2,
                CenterDrawDigitY = y + blockSize/2;
        graphics.setColor(Color.decode("#cdc1b4"));
        graphics.fillRect(x, y, blockSize, blockSize);
        graphics.setFont(ConfigGame.fontForBlock);
        int shift = 20;

        switch(digit){
            case 2 -> {
                graphics.setColor(Color.decode("#eee4da"));
                graphics.fillRoundRect(x, y, blockSize, blockSize, 20, 15);
                graphics.setColor(Color.decode("#796d66"));
                graphics.drawString(Integer.toString(digit), CenterDrawDigitX-shift, CenterDrawDigitY+shift);
            }
            case 4 -> {
                graphics.setColor(Color.decode("#eee1c9"));
                graphics.fillRoundRect(x, y, blockSize, blockSize, 20, 15);
                graphics.setColor(Color.decode("#796d66"));
                graphics.drawString(Integer.toString(digit), CenterDrawDigitX-shift, CenterDrawDigitY + shift);
            }
            case 8 -> {
                graphics.setColor(Color.decode("#f3b27a"));
                graphics.fillRoundRect(x, y, blockSize, blockSize, 20, 15);
                graphics.setColor(Color.decode("#F9F6F2"));
                graphics.drawString(Integer.toString(digit), CenterDrawDigitX-shift, CenterDrawDigitY + shift);
            }
            case 16 -> {
                graphics.setColor(Color.decode("#F69664"));
                graphics.fillRoundRect(x, y, blockSize, blockSize, 20, 15);
                graphics.setColor(Color.decode("#F9F6F2"));
                graphics.drawString(Integer.toString(digit), CenterDrawDigitX-shift*2, CenterDrawDigitY + shift);
            }
            case 32 -> {
                graphics.setColor(Color.decode("#F77C5F"));
                graphics.fillRoundRect(x, y, blockSize, blockSize, 20, 15);
                graphics.setColor(Color.decode("#F9F6F2"));
                graphics.drawString(Integer.toString(digit), CenterDrawDigitX-shift*2, CenterDrawDigitY + (int)(shift*1.5));
            }
            case 64 -> {
                graphics.setColor(Color.decode("#F75F3B"));
                graphics.fillRoundRect(x, y, blockSize, blockSize, 20, 15);
                graphics.setColor(Color.decode("#F9F6F2"));
                graphics.drawString(Integer.toString(digit), CenterDrawDigitX-shift*2, CenterDrawDigitY + (int)(shift*1.5));
            }
            case 128 -> {
                graphics.setColor(Color.decode("#EDD073"));
                graphics.fillRoundRect(x, y, blockSize, blockSize, 20, 15);
                graphics.setColor(Color.decode("#F9F6F2"));
                graphics.drawString(Integer.toString(digit), CenterDrawDigitX-(int)(shift*2.7), CenterDrawDigitY + (int)(shift*1.2));
            }
            case 256 -> {
                graphics.setColor(Color.decode("#EDCC62"));
                graphics.fillRoundRect(x, y, blockSize, blockSize, 20, 15);
                graphics.setColor(Color.decode("#F9F6F2"));
                graphics.drawString(Integer.toString(digit), CenterDrawDigitX-(int)(shift*2.7), CenterDrawDigitY + (int)(shift*1.2));
            }
            case 512 ->{//512
                graphics.setColor(Color.decode("#EDC950"));
                graphics.fillRoundRect(x, y, blockSize, blockSize, 20, 15);
                graphics.setColor(Color.decode("#F9F6F2"));
                graphics.drawString(Integer.toString(digit), CenterDrawDigitX-(int)(shift*2.7), CenterDrawDigitY + (int)(shift*1.2));
            }
            case 1024 ->{//1024
                graphics.setFont(new Font("TimesRoman", Font.BOLD, 60));
                graphics.setColor(Color.decode("#EDCC62"));
                graphics.fillRoundRect(x, y, blockSize, blockSize, 20, 15);
                graphics.setColor(Color.decode("#F9F6F2"));
                graphics.drawString(Integer.toString(digit), (int)(CenterDrawDigitX-blockSize/2.2), CenterDrawDigitY + (int)(shift*1.2));
            }
            case 2048 ->{//2048
                graphics.setFont(new Font("TimesRoman", Font.BOLD, 60));
                graphics.setColor(Color.decode("#EDCC62"));
                graphics.fillRoundRect(x, y, blockSize, blockSize, 20, 15);
                graphics.setColor(Color.decode("#F9F6F2"));
                graphics.drawString(Integer.toString(digit), (int)(CenterDrawDigitX-blockSize/2.2), CenterDrawDigitY + (int)(shift*1.2));
            }
        }
    }
}
