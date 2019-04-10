package Utils;

public class Terrain {
    public double[][] grid;
    public Position start;
    public Position end;

    public Terrain(int width, int height){
        grid = new double[width][height];
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
}
