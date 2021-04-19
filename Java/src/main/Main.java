package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("file"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Palette palette = new Palette(image, 8);
        Color[] colors = palette.getPalette();

        for(int i = 0; i < colors.length; i++){
            System.out.println(colors[i].toString());
        }

        long end = System.currentTimeMillis();
        System.out.println((end - start) + "ms");
    }
}
