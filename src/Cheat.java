


public class Cheat implements Strategy{


    /**
     * Determines the winning move based on the player's current selection.
     * * @param playerMove The current move chosen by the player ("R", "P", or "S").
     * @return A String representing the winning counter move, or "X" if
     * the input move is unrecognized.
     */

    @Override
    public String getMove(String playerMove) {
        String computerMove = "";
        switch (playerMove) {
            case "R":
                computerMove = "P";
                break;
            case "P":
                computerMove = "S";
                break;
            case "S":
                computerMove = "R";
                break;
            default:
                computerMove = "X";
                break;
        }
        return computerMove;
    }
}