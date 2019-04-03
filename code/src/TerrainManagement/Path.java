package TerrainManagement;

import javafx.util.Pair;

import java.util.Iterator;
import java.util.List;

public class Path {
    private List<Position> path;
    public Iterator<Position> iterator(){
        return path.iterator();
    }
}
