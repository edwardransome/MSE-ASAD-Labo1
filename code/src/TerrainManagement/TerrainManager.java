package TerrainManagement;

import Interfaces.ShortestPathSolver;
import Utils.Path;
import Utils.Position;
import Utils.Terrain;

public class TerrainManager {
    private Terrain terrain;
    private ShortestPathSolver solver;

    public TerrainManager(int width, int height){
        terrain = new Terrain(width, height);
    }
    
    public Path getShortestPath(Position start, Position end){
        terrain.start = start;
        terrain.end = end;
        return solver.calculateShortestPath(terrain);
    }

    public void setSolver(ShortestPathSolver solver) {
        this.solver = solver;
    }

    public void setWeight(Position p, double newValue) {
        terrain.setWeight(p, newValue);
    }
}
