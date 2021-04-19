package main;

public class Pixel {
    private int r, g, b;

    public Pixel(){
        r = 0;
        g = 0;
        b = 0;
    }

    public int getChannel(int i){
        int value;

        switch(i){
            case 0:
                value = getR();
                break;
            case 1:
                value = getG();
                break;
            case 2:
                value = getB();
                break;
            default:
                value = 3;
        }

        return value;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }
}
