public class RandomStrategy implements Strategy {
    public String getMove(String playerMove) {
        int r = (int)(Math.random() * 3);
        return (r == 0 ? "R" : r == 1 ? "P" : "S");
    }
}

