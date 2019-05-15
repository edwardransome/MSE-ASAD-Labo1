import java.rmi.Remote;
import java.util.List;

/**
 * Interface for a Shortest Path Solver, receives a Terrain as parameter and
 * returns the shortest path from its start to its end
 */
public interface ShortestPathSolver extends Remote {
    Path calculateShortestPath(String algorithm, Terrain terrain);
    List<String> getAlgorithms();
}

