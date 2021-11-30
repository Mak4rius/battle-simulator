package simulator.rabotiaev_makar;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public abstract class WorldController {

	public static final int MAX_ROWS = 10;
	public static final int MAX_COLS = 10;
	// Rock
	public static final char CAPITAL_R = 'R';
	public static final char SMALL_R = 'r';
	// Paper
	public static final char CAPITAL_P = 'P';
	public static final char SMALL_P = 'p';
	// Scissors
	public static final char CAPITAL_S = 'S';
	public static final char SMALL_S = 's';
	
	// Created to describe which symbols moved into the same position in the world.
	// We declare a hash map, where position is key, and LinkedList<symbol> is a value
	public static HashMap<Position, LinkedList<Symbol>> world;

	// So, what i saw here, is that we can group all existing elements in three groups, assigning actions
	// to every single one of them.
	// 1) Main group, with all the elements in existance. Their only action is going to be move. And death.
	// 2) Capital group,

	/**
	 * First all symbols in the existance move, one by one.
	 * @param symbols
	 */

	public abstract void symbolsMove(List<Symbol> symbols);

	public abstract void symbolsDie(List<Symbol> symbols);

	public abstract void smallCaseUpgrade(List<SmallCase> symbols);

	public abstract void capitalCaseJump(List<CapitalCase> symbols);

	public abstract void passiveEscape(List<Passive> symbols);

	public abstract void passiveBreed(List<Passive> symbols);
	
	public abstract void aggressiveAttackSmart(List<Aggressive> symbols);
	
	// Returns a 10x10 string with the symbols after the updates of the interation
	public abstract String plotWorld();

}
