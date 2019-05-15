import javafx.util.Pair;

import java.io.Serializable;

/**
 * Simple wrapper class for a pair of Integers.
 */
public class Position extends Pair<Integer, Integer> implements Serializable {
    public Position(Integer key, Integer value) {
        super(key, value);
    }
}
