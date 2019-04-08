package Utils;

public class Terrain {
    private double[][] grid;
    private Position start;
    private Position end;

    public Terrain(int width, int height){
        grid = new double[width][height];
    }
}
