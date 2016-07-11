
public class Coordinates {
    protected int level,row,column;
    protected Coordinates parent;
    protected double heuristic;
    public Coordinates() {
    }
    public Coordinates(int level, int row, int column){
        this.level = level;
        this.row = row;
        this.column = column;
        parent = null;
    }
    public Coordinates(int level, int row, int column, Coordinates parent){
        this.level = level;
        this.row = row;
        this.column = column;
        this.parent = parent;
    }
    public Coordinates(int level, int row, int column, Coordinates parent, double heuristic){
        this.level = level;
        this.row = row;
        this.column = column;
        this.parent = parent;
        this.heuristic = heuristic;
    }

    public void adjustHeuristic(double x){
        this.heuristic += x;
    }
    public void setParent(Coordinates x){
        parent = x;
    }
    public void setLevel(int level){
        this.level = level;
    }
    public void setRow(int row){
        this.row = row;
    }
    public void setColumn(int column){
        this.column = column;
    }
    public void setHeuristic(double heuristic){
        this.heuristic = heuristic;
    }
    public Coordinates getParent(){
        return parent;
    }
    public int getLevel(){
        return level;
    }
    public int getRow(){
        return row;
    }
    public int getColumn(){
        return column;
    }
    public double getHeuristic(){
        return heuristic;
    }
}
