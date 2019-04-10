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

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    public Path getShortestPath(Position start, Position end){
        //solver.getShortestPath()
        return null;
    }

    public void setSolver(ShortestPathSolver solver) {
        this.solver = solver;
    }

    public void setWeight(Position p, double newValue) {
        terrain.setWeight(p, newValue);
    }
}
