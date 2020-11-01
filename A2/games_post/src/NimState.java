public class NimState extends State {

    int coins_pile = 13;

    NimState() {}

    NimState(NimState state) {
        this.coins_pile = state.coins_pile;
        player = state.player;
    }

    public String toString() {
        return "" + this.coins_pile;
    }
}