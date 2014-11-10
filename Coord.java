public class Coord {
    private int x;
    private int y;
    
    public Coord(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getL(){ return x; }

    public int getR(){ return y; }

    public void setL(int x){ this.x = x; }

    public void setR(int y){ this.y = y; }
}