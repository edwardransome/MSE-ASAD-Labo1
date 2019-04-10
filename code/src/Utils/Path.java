package Utils;

import java.util.Iterator;
import java.util.List;

/**
 * Utility method representing a Path. Provides an Iterator<Position> to
 * get the path node by node
 */
public class Path {
    private List<Position> path;
    public Path(List<Position> path) {
        this.path = path;
    }
    public Iterator<Position> iterator(){
        return path.iterator();
    }
}
