import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ComputationServer implements ShortestPathSolver{
    private enum Algorithms {ASTAR, DIJKSTRA};


    public static void main(String[] args) {
        try {
            ComputationServer obj = new ComputationServer();
            ShortestPathSolver stub = (ShortestPathSolver) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("ComputationServer", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }

    }

    @Override
    public Path calculateShortestPath(String algorithm, Terrain terrain) {
        ShortestPathAlgorithm a;
        switch(algorithm){
            case("ASTAR"):
                a = new AStarShortestPathSolver();
                break;

            case("DIJKSTRA"):
                a = null;
                break;
            default:
                throw new RuntimeException("Invalid algorithm");
        }
        return a.calculateShortestPath(terrain);
    }

    @Override
    public List<String> getAlgorithms() {
        return Arrays.stream(Algorithms.values()).map(x->x.toString()).collect(Collectors.toList());
    }


}
