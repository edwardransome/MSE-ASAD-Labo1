package Interfaces;

import Utils.Path;
import Utils.Terrain;

/**
 * Interface for a Shortest Path Solver, receives a Terrain as parameter and
 * returns the shortest path from its start to its end
 */
public interface ShortestPathSolver {
    Path calculateShortestPath(Terrain terrain);
}
