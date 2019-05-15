/**
 * Utility class that represents a terrain. A terrain is a grid with weights
 * at each node, as well as a start and end node.
 */
public class Terrain {
    private double[][] grid;
    private Position start;
    private Position end;

    public Terrain(int width, int height){
        grid = new double[width][height];
        start = new Position(0,0);
        end = new Position(width - 1, height - 1);
    }

    public int getSizeX() {
        return grid.length;
    }

    public int getSizeY() {
        return grid[0].length;
    }

    public void setWeight(Position p, double weight){
        grid[p.getKey()][ p.getValue()] = weight;
    }

    public double getWeight(Position p){
        return grid[p.getKey()][ p.getValue()];
    }

    public void setStart(Position start) {
        this.start = start;
    }

    public double[][] getGrid() {
        return grid;
    }

    public Position getStart() {
        return start;
    }

    public Position getEnd() {
        return end;
    }

    public void setEnd(Position end) {
        this.end = end;
    }
}
