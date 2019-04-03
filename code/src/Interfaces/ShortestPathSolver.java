package Interfaces;

import TerrainManagement.Path;
import TerrainManagement.Terrain;

public interface ShortestPathSolver {
    Path calculateShortestPath(Terrain terrain);
}
