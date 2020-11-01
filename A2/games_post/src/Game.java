import java.util.Set;

public abstract class Game {
	private State currentState;

	abstract public boolean isWinState(State state);
	abstract public boolean isStuckState(State state);
	abstract public Set<State> getSuccessors(State state);
	abstract public double eval(State state);

	public State getCurrentState() {
		return currentState;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}
}

//no node class because we don't need to go back and produce a solution