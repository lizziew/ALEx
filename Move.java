public class Move {
    private int l;
    private int r;

    public Move(int l, int r){
        this.l = l;
        this.r = r;
    }

    public int getL(){ return l; }

    public int getR(){ return r; }

    public void setL(int l){ this.l = l; }

    public void setR(int r){ this.r = r; }
}