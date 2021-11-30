package simulator.rabotiaev_makar;

import java.util.*;

public class Simulator extends WorldController{

    /*
    So first of all, we initialise arrays of different symbols.
     */

    public static List<Symbol> all_symbols = new ArrayList<>();

    public static List<CapitalCase> capital_symbols = new ArrayList<>();
    public static List<SmallCase> small_symbols = new ArrayList<>();

    public static List<Aggressive> aggressive_symbols = new ArrayList<>();
    public static List<Passive> passive_symbols = new ArrayList<>();


    public static void main(String[] args) {
        
        Simulator app = new Simulator();

        world = new HashMap<>();

        // This loop simply spawns new elements.
        for (int i = 0; i < 4; i++) {
            spawnSymbol(1);
            spawnSymbol(2);
            spawnSymbol(3);
            spawnSymbol(4);
            spawnSymbol(5);
            spawnSymbol(6);
        }

        int simulation_time = 1000;

        while (simulation_time != 0){

            app.symbolsMove(all_symbols);
            app.symbolsDie(all_symbols);
            app.smallCaseUpgrade(small_symbols);
            app.capitalCaseJump(capital_symbols);
            app.passiveEscape(passive_symbols);
            //app.passiveBreed(passive_symbols);
            app.aggressiveAttackSmart(aggressive_symbols);


            for (int i = 0; i < all_symbols.size(); i++)
            {
                LinkedList<Symbol> specific_symbols = new LinkedList<Symbol>();
                specific_symbols.add(all_symbols.get(i));
                if(world.containsKey(all_symbols.get(i).getPosition()))
                {
                    world.get(all_symbols.get(i).getPosition()).add(all_symbols.get(i));
                }
                else{
                    world.put(all_symbols.get(i).getPosition(), specific_symbols);
                }

            }

            app.plotWorld();


            simulation_time--;
        }


    }

    public String plotWorld(){
        //public static HashMap<Position, LinkedList<Symbol>> world;
        //


        for (int i = 0; i < MAX_ROWS; i++) {
            for (int j = 0; j < MAX_COLS; j++)
            {
                Position current_position = new Position(i,j);
                if(world.containsKey(current_position))
                {
                    if(world.get(current_position).getFirst() instanceof SymbolCapitalR){
                        System.out.printf(" %c ", CAPITAL_R);
                    }
                    else if(world.get(current_position).getFirst() instanceof  SymbolCapitalS){
                        System.out.printf(" %c ", CAPITAL_S);
                    }
                    else if(world.get(current_position).getFirst() instanceof SymbolCapitalP){
                        System.out.printf(" %c ", CAPITAL_P);
                    }
                    else if(world.get(current_position).getFirst() instanceof SymbolSmallR){
                        System.out.printf(" %c ", SMALL_R);
                    }
                    else if(world.get(current_position).getFirst() instanceof  SymbolSmallS){
                        System.out.printf(" %c ", SMALL_S);
                    }
                    else{
                        System.out.printf(" %c ", SMALL_P);
                    }

                }

                else{
                    System.out.print(" # ");
                }
            }
            System.out.println(" ");
        }
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");

        return " -  -  -  -  -  -  -  -  -  -  ";
    }
    
    /**
     * Depending on option parameter, entity of a specific symbol is created and placed in a desired list.
     * @param option
     */
    public static void spawnSymbol(int option){
        Random rand = new Random();
        Position random_position = new Position(rand.nextInt(11), rand.nextInt(11));
        Symbol.COUNT_SYMBOLS++;

        if (option == 1){
            SymbolCapitalS symbol = new SymbolCapitalS();

            symbol.idSymbol = Symbol.COUNT_SYMBOLS;
            symbol.setPosition(random_position);

            all_symbols.add(symbol);
            capital_symbols.add(symbol); // ok, so we can add a class, which is implementing this interface.
            aggressive_symbols.add(symbol);

        }

        else if(option == 2){
            SymbolCapitalP symbol = new SymbolCapitalP();

            symbol.idSymbol = Symbol.COUNT_SYMBOLS;
            symbol.setPosition(random_position);

            all_symbols.add(symbol);
            capital_symbols.add(symbol);
            aggressive_symbols.add(symbol);

        }

        else if(option == 3){
            SymbolCapitalS symbol = new SymbolCapitalS();

            symbol.idSymbol = Symbol.COUNT_SYMBOLS;
            symbol.setPosition(random_position);

            all_symbols.add(symbol);
            capital_symbols.add(symbol);
            aggressive_symbols.add(symbol);


        }

        else if(option == 4){
            SymbolSmallP symbol = new SymbolSmallP();

            symbol.idSymbol = Symbol.COUNT_SYMBOLS;
            symbol.setPosition(random_position);

            all_symbols.add(symbol);
            small_symbols.add(symbol);
            passive_symbols.add(symbol);

        }

        else if(option == 5){
            SymbolSmallR symbol = new SymbolSmallR();

            symbol.idSymbol = Symbol.COUNT_SYMBOLS;
            symbol.setPosition(random_position);

            all_symbols.add(symbol);
            small_symbols.add(symbol);
            passive_symbols.add(symbol);

        }

        else{
            SymbolSmallS symbol = new SymbolSmallS();

            symbol.idSymbol = Symbol.COUNT_SYMBOLS;
            symbol.setPosition(random_position);

            all_symbols.add(symbol);
            small_symbols.add(symbol);
            passive_symbols.add(symbol);

        }

    }
    
    public void symbolsMove(List<Symbol> symbols){
        for (int i = 0; i < symbols.size(); i++) {
            symbols.get(i).move();
        }
    }

    public void symbolsDie(List<Symbol> symbols){
        for (int i = 0; i < symbols.size(); i++) {
            symbols.get(i).die();
        }

    }

    public void smallCaseUpgrade(List<SmallCase> symbols){
        for (int i = 0; i < symbols.size(); i++) {
            symbols.get(i).upgrade();
        }

    }

    public void capitalCaseJump(List<CapitalCase> symbols){
        for (int i = 0; i < symbols.size(); i++) {
            symbols.get(i).jump();
        }
    }

    public void passiveEscape(List<Passive> symbols){
        for (int i = 0; i < symbols.size(); i++) {
            symbols.get(i).escape();
        }

    }

    public  void passiveBreed(List<Passive> symbols){
        for (int i = 0; i < symbols.size(); i++) {
            symbols.get(i).moveBreed();
        }
    }

    public  void aggressiveAttackSmart(List<Aggressive> symbols){
        for (int i = 0; i < symbols.size(); i++) {
            symbols.get(i).attackSmart();
        }

    }
    

    // Returns a 10x10 string with the symbols after the updates of the interation


}
