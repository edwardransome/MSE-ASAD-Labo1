import Interfaces.ShortestPathSolver;
import Path;
import Position;
import Terrain;

/**
 * Manages a terrain: allows modifications to its weight, start and end
 * and uses a solver to return a shortest path from start to end
 */
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
        this.terrain.setEnd(p);
    }
}
