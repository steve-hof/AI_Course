import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Nim extends Game {

    private Nim() {
        setCurrentState(new NimState());
    }

    // necessary condition for win
    public boolean isWinState(State state) {
        NimState state_n = (NimState) state;
        return state_n.coins_pile == 1;
    }

    // necessary for abstract class
    public boolean isStuckState(State state) {
        return false;
    }

    public Set<State> getSuccessors(State state) {
        if (isWinState(state))
            return null;

        Set<State> successors = new HashSet<>();
        NimState state_n = (NimState) state;

        // remove 1 coin
        NimState successor_state = new NimState(state_n);
        successor_state.coins_pile -= 1;
        successor_state.player = (state.player == 0 ? 1 : 0);
        checkState(successor_state);
        successors.add(successor_state);

        // remove 2 coins
        successor_state = new NimState(state_n);
        successor_state.coins_pile -= 2;
        successor_state.player = (state.player == 0 ? 1 : 0);
        checkState(successor_state);
        successors.add(successor_state);

        // remove 3 coins
        successor_state = new NimState(state_n);
        successor_state.coins_pile -= 3;
        successor_state.player = (state.player == 0 ? 1 : 0);
        checkState(successor_state);
        successors.add(successor_state);


        return successors;
    }

    // is current state valid
    private void checkState(State state) {
        NimState state_n = (NimState) state;
    }

    public double eval(State state) {
        if (isWinState(state)) {
            // most recent player
            int previous_player = (state.player == 0 ? 1 : 0);

            int winningScore = 10;
            int losingScore = -10;
            if (previous_player == 0) //computer wins
                return winningScore;
            else // human wins
                return losingScore;
        }
        return 0;
    }


    public static void main(String[] args) throws Exception {

        Game game = new Nim();
        Search search = new Search(game);
        int depth = 13;

        // collect human's move
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        while (true) {

            NimState nextState = null;

            switch (game.getCurrentState().player) {
                case 1:
                    // human's turn
                    System.out.print("Enter your *valid* move> ");

                    // number of coins taken by the user
                    int coins_taken = Integer.parseInt(in.readLine());
                    if (coins_taken > 3) {
                        System.out.println("You must choose between 1 - 3 coins\n");
                        System.out.print("Enter your *valid* move> ");
                        coins_taken = Integer.parseInt(in.readLine());
                    }
                    if (coins_taken < 1) {
                        System.out.print("You must choose between 1 - 3 coins\n");
                        System.out.print("Enter your *valid* move> ");
                        coins_taken = Integer.parseInt(in.readLine());
                    }

                    nextState = new NimState((NimState) game.getCurrentState());
                    nextState.player = 1;
                    nextState.coins_pile -= coins_taken;
                    System.out.println("Human just played: " + nextState + " coins left \n");
                    break;

                case 0: // Computer's turn
                    nextState = (NimState) search.bestSuccessorState(depth);
                    nextState.player = 0;
                    System.out.println("Computer Just Played: " + nextState + " coins left \n");
                    break;
            }

            game.setCurrentState(nextState);
            // change player
            assert game.getCurrentState() != null;
            game.getCurrentState().player = (game.getCurrentState().player == 0 ? 1 : 0);

            // Figure out who wins
            if (game.isWinState(game.getCurrentState())) {

                if (game.getCurrentState().player == 1)
                    System.out.println("Computer wins!");
                else
                    System.out.println("You win!");

                break;
            }

            if (game.isStuckState(game.getCurrentState())) {
                System.out.println("Tie game!");
                break;
            }
        }
    }
}

