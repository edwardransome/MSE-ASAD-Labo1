package TerrainManagement;

import Utils.Path;
import Utils.Position;
import Utils.Terrain;

public class TerrainManager {
    private Terrain terrain;

    public TerrainManager(int width, int height){
        terrain = new Terrain(width, height);
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    public Path getShortestPath(Position start, Position end){
        return null;
    }
}
