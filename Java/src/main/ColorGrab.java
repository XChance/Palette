package main;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;

public class ColorGrab {
    private int width;
    private int height;
    private boolean hasAlphaChannel;
    private int pixelLength;
    private byte[] pixels;

    public ColorGrab(BufferedImage image){
        pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        width = image.getWidth();
        height = image.getHeight();
        hasAlphaChannel = image.getAlphaRaster() != null;
        pixelLength = 3;

        if (hasAlphaChannel) {
            pixelLength = 4;
        }
    }

    public int getRGB(int x, int y) {
        int pos = (y * pixelLength * width) + (x * pixelLength);

        int argb = -16777216; // 255 alpha
        if (hasAlphaChannel)
        {
            argb = (((int) pixels[pos++] & 0xff) << 24); // alpha
        }

        argb += ((int) pixels[pos++] & 0xff); // blue
        argb += (((int) pixels[pos++] & 0xff) << 8); // green
        argb += (((int) pixels[pos++] & 0xff) << 16); // red
        return argb;
    }

    public Pixel getPixel(int x, int y){
        Pixel pixel = new Pixel();
        int argb = getRGB(x, y);

        pixel.setB(argb & 255);
        pixel.setG((argb>> 8) & 255);
        pixel.setR((argb>> 16) & 255);
        
        return pixel;
    }

    public ArrayList<Pixel> getPixels(){
        ArrayList<Pixel> pixels = new ArrayList<>();
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                pixels.add(getPixel(i, j));
            }
        }
        return pixels;
    }

}
