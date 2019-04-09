package Utils;

public class Terrain {
    private double[][] grid;
    private Position start;
    private Position end;

    public Terrain(int width, int height){
        grid = new double[width][height];
    }

    public void setWeight(Position p, double weight){
        grid[p.getKey()][ p.getValue()] = weight;
    }

    public double getWeight(Position p){
        return grid[p.getKey()][ p.getValue()];
    }
}
