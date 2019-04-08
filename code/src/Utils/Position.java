package Utils;

import javafx.util.Pair;

/**
 * Simple wrapper class for a pair of Integers.
 */
public class Position extends Pair<Integer, Integer> {
    /**
     * Creates a new pair
     *
     * @param key   The key for this pair
     * @param value The value to use for this pair
     */
    public Position(Integer key, Integer value) {
        super(key, value);
    }
}
