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
    
    public Path getShortestPath(){
        return solver.calculateShortestPath(terrain);
    }

    public void setSolver(ShortestPathSolver solver) {
        this.solver = solver;
    }

    public void setWeight(Position p, double newValue) {
        terrain.setWeight(p, newValue);
    }

    public void setStart(Position p) {
        this.terrain.setStart(p);
    }

    public void setEnd(Position p) {
        this.terrain.setStart(p);
    }
}
