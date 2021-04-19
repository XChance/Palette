package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Palette {
    private ColorGrab colorGrab;
    private ArrayList<Pixel> pixels;
    private ArrayList<ArrayList<Pixel>> buckets;
    private Color[] palette;

    private int paletteSize;

    public Palette(BufferedImage image, int paletteSize) {
        this.paletteSize = paletteSize;

        colorGrab = new ColorGrab(image);
        pixels = colorGrab.getPixels();

        buckets = new ArrayList<>();
        palette = new Color[paletteSize];

        palette = new Color[paletteSize];
    }

    public Color[] getPalette() {
        buckets = new ArrayList<>();

        mergeSort(pixels, 0, pixels.size() - 1, getHighestChannelRange());
        colorSplit(0, pixels.size() - 1, paletteSize);
        averageBuckets();

        return palette;
    }

    private void averageBuckets(){
        int r = 0, g = 0, b = 0;

        for(int i = 0; i < buckets.size(); i++){
            for(int j = 0; j < buckets.get(i).size(); j++){
                r += buckets.get(i).get(j).getR() * buckets.get(i).get(j).getR();
                g += buckets.get(i).get(j).getG() * buckets.get(i).get(j).getG();
                b += buckets.get(i).get(j).getB() * buckets.get(i).get(j).getB();
            }

            r /= buckets.get(i).size();
            g /= buckets.get(i).size();
            b /= buckets.get(i).size();

            r = (int)Math.sqrt(r);
            g = (int)Math.sqrt(g);
            b = (int)Math.sqrt(b);

            palette[i] = new Color(r, g, b);
        }
    }

    public int getHighestChannelRange(){
        int rangeR, rangeG, rangeB;
        int minR = 255, minG = 255, minB = 255, maxR = 0, maxG = 0, maxB = 0;

        for (Pixel pixel : pixels) {
            if (pixel.getR() < minR) {
                minR = pixel.getR();
            }

            if (pixel.getR() > maxR) {
                maxR = pixel.getR();
            }

            if (pixel.getG() < minG) {
                minG = pixel.getG();
            }

            if (pixel.getG() > maxG) {
                maxG = pixel.getG();
            }

            if (pixel.getB() < minB) {
                minB = pixel.getB();
            }

            if (pixel.getB() > maxB) {
                maxB = pixel.getB();
            }
        }

        rangeR = maxR - minR;
        rangeG = maxG - minG;
        rangeB = maxB - minB;

        if(rangeR > rangeG && rangeR > rangeB){
            return 0;
        }else if(rangeG > rangeB){
            return 1;
        }else{
            return 2;
        }

    }

    public void mergeSort(ArrayList<Pixel> array, int left, int right, int colorChannel) {
        if (right <= left) return;
        int mid = (left + right)/2;

        mergeSort(array, left, mid, colorChannel);
        mergeSort(array, mid + 1, right, colorChannel);
        merge(array, left, mid, right, colorChannel);
    }

    public void merge(ArrayList<Pixel> array, int left, int mid, int right, int colorChannel){
        int lengthLeft = mid - left + 1;
        int lengthRight = right - mid;

        // temporary sub arrays
        Pixel[] leftArray = new Pixel[lengthLeft];
        Pixel[] rightArray = new Pixel[lengthRight];

        // copying sorted arrays into temp sub arrays
        for (int i = 0; i < lengthLeft; i++)
            leftArray[i] = array.get(left + i);
        for (int i = 0; i < lengthRight; i++)
            rightArray[i] = array.get(mid+i+1);

        // sub array iterators
        int leftIndex = 0;
        int rightIndex = 0;

        // copying left array and right array back into original array
        for (int i = left; i < right + 1; i++) {
            // if there are still uncopied elements in l and r, copy minimum of the two
            if (leftIndex < lengthLeft && rightIndex < lengthRight) {
                if (leftArray[leftIndex].getChannel(colorChannel) < rightArray[rightIndex].getChannel(colorChannel)) {
                    array.set(i, leftArray[leftIndex]);
                    leftIndex++;
                }
                else {
                    array.set(i, rightArray[rightIndex]);
                    rightIndex++;
                }
            }
            // if all the elements have been copied from rightArray, copy the rest of leftArray
            else if (leftIndex < lengthLeft) {
                array.set(i, leftArray[leftIndex]);
                leftIndex++;
            }
            // if all the elements have been copied from leftArray, copy the rest of rightArray
            else if (rightIndex < lengthRight) {
                array.set(i, rightArray[rightIndex]);
                rightIndex++;
            }
        }
    }

    private void colorSplit(int left, int right, int depth){
        int mid = (left + right)/2;
        ArrayList<Pixel> leftHalf, rightHalf;

        leftHalf = new ArrayList<>();
        rightHalf = new ArrayList<>();

        for(int i = left; i < mid; i++){
            leftHalf.add(pixels.get(i));
        }

        for(int i = mid + 1; i < right; i++){
            rightHalf.add(pixels.get(i));
        }

        if(depth == 2){
            buckets.add(leftHalf);
            buckets.add(rightHalf);
            return;
        }

        colorSplit(left, mid, depth / 2);
        colorSplit(mid + 1, right, depth / 2);
    }
}
