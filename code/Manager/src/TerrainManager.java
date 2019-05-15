import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

/**
 * Manages a terrain: allows modifications to its weight, start and end
 * and uses a solver to return a shortest path from start to end
 */
public class TerrainManager {
    private Terrain terrain;

    public TerrainManager(int width, int height){
        terrain = new Terrain(width, height);
    }
    
    public Path getShortestPath(String alg){
        String host = "localhost";
        Path response = null;
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            ShortestPathSolver stub = (ShortestPathSolver) registry.lookup("ComputationServer");
            response = stub.calculateShortestPath(alg, terrain);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
        return response;
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
