/**
 * Implementation of the {@link Strategy} interface that selects a move
 * completely at random. This strategy does not analyze player patterns
 * or history, providing a baseline level of unpredictability.
 * * @author Van Diep
 * @version 1.0
 */

public class RandomStrategy implements Strategy {
    public String getMove(String playerMove) {
        int r = (int)(Math.random() * 3);
        return (r == 0 ? "R" : r == 1 ? "P" : "S");
    }
}

