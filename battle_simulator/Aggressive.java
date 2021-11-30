package simulator.rabotiaev_makar;

public interface Aggressive {
	
	// Move towards weaker symbols only - within the sightDistance
	public abstract void attackSmart();
	
}