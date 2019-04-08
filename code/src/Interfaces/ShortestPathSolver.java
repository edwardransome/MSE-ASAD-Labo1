package Interfaces;

import Utils.Path;
import Utils.Terrain;

public interface ShortestPathSolver {
    Path calculateShortestPath(Terrain terrain);
}
