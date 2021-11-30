package simulator.rabotiaev_makar;


import java.util.*;

public class SymbolCapitalP extends Symbol implements CapitalCase, Aggressive{

    /**
     * This function checks all possible moves a symbol can make, and then randomly chooses one.
     */
    public void move(){
        Position future_position = new Position(0,0);
        List<Position> validMoves = new ArrayList<Position>();
        for (int i = (this.position.row - 1); i < this.position.row + 2; i++) {
            for (int j = (this.position.column - 1); j < this.position.column + 2; j++) {
                if((i <= 10) & (j <= 10)){
                    future_position.row = i;
                    future_position.column = j;
                    validMoves.add(future_position);
                }
            }
        }
        Random rd = new Random();
        future_position = validMoves.get(rd.nextInt(validMoves.size()));

        this.setPosition(future_position);
    }

    /**
     * I expect a big symbol to live for 50 iterations.
     * While small symbol leaving for 25 iterations.
     * If it's the "last day" of the symbol, then i delete from two lists : "all_symbol" and "capital_symb" lists
     *
     */
    public void die(){
        if(this.numberIterationsAlive == 50){
            for (int i = 0; i < Simulator.all_symbols.size(); i++) {
                if(Simulator.all_symbols.get(i).idSymbol == this.idSymbol){
                    Simulator.all_symbols.remove(i);
                }
            }
            for (int i = 0; i < Simulator.capital_symbols.size(); i++) {
                if(((Symbol) Simulator.capital_symbols.get(i)).idSymbol == this.idSymbol){
                    Simulator.capital_symbols.remove(i);
                    Simulator.aggressive_symbols.remove(i);
                }

            }
        }
        else{
            this.numberIterationsAlive ++;
        }


    }

    /**
     * I suppose that jumping, is essentially just jumping over one cell.
     */
    @Override
    public void jump() {
        List<Integer> row_list = new ArrayList<Integer>();
        List<Integer> column_list = new ArrayList<Integer>();
        List<Position> jump_positions = new ArrayList<Position>();

        row_list.add(this.position.row - 2);
        row_list.add(this.position.row + 2);
        column_list.add(this.position.column + 2);
        column_list.add(this.position.column - 2);

        for (int i = 0; i < row_list.size(); i++) {
            if((row_list.get(i) <= 10) & (row_list.get(i) >= 0)){
                for (int j = 0; j < column_list.size(); j++) {
                    if((column_list.get(j) <= 10) & (column_list.get(j) >= 0)){
                        Position possible_position = new Position(row_list.get(i),column_list.get(j));
                        jump_positions.add(possible_position);
                    }
                }
            }
        }

        Random rand = new Random();
        this.setPosition(jump_positions.get(rand.nextInt(jump_positions.size())));
    }

    @Override
    public void attackSmart() {

        int smallest_distance = 1000;

        SymbolSmallR small_victim = new SymbolSmallR();
        SymbolCapitalR big_victim = new SymbolCapitalR();


        for (int i = 0; i < Simulator.all_symbols.size(); i++)
        {
            if(Simulator.all_symbols.get(i).idSymbol != this.idSymbol)
            {
                if((Simulator.all_symbols.get(i) instanceof SymbolCapitalR))
                {
                    if(smallest_distance > this.position.manhattanDistance(Simulator.all_symbols.get(i).getPosition()))
                    {

                        smallest_distance = this.position.manhattanDistance(Simulator.all_symbols.get(i).getPosition());
                        big_victim = (SymbolCapitalR) Simulator.all_symbols.get(i);

                    }
                }
                else if((Simulator.all_symbols.get(i) instanceof SymbolSmallR))
                {
                    if (smallest_distance > this.position.manhattanDistance(Simulator.all_symbols.get(i).getPosition()))
                    {

                        smallest_distance = this.position.manhattanDistance(Simulator.all_symbols.get(i).getPosition());
                        small_victim = (SymbolSmallR) Simulator.all_symbols.get(i);

                    }
                }
            }
        }

        Position future_position = new Position(0,0);
        int move_distance = smallest_distance;

        for (int i = (this.position.row - 1); i < this.position.row + 2; i++)
        {
            for (int j = (this.position.column - 1); j < this.position.column + 2; j++)
            {
                if((i <= 10) & (j <= 10))
                {

                    future_position.row = i;
                    future_position.column = j;

                    if(big_victim.getPosition() != null){
                        if(this.position.manhattanDistance(big_victim.getPosition()) == smallest_distance)
                        {
                            if(future_position.manhattanDistance(big_victim.getPosition()) < move_distance)
                            {

                                move_distance = future_position.manhattanDistance(big_victim.getPosition());
                                this.position = future_position;

                            }

                        }

                    }

                    else if (small_victim.getPosition() != null){
                        if(this.position.manhattanDistance(small_victim.getPosition()) == smallest_distance)
                        {
                            if(future_position.manhattanDistance(small_victim.getPosition()) < move_distance)
                            {

                                move_distance = future_position.manhattanDistance(small_victim.getPosition());
                                this.position = future_position;

                            }

                        }

                    }


                }

            }

        }

        for (int i = 0; i < Simulator.all_symbols.size(); i++)
        {
            if(Simulator.all_symbols.get(i).position.hashCode() == this.position.hashCode())
            {
                if(Simulator.all_symbols.get(i) instanceof SymbolSmallR || Simulator.all_symbols.get(i) instanceof SymbolCapitalR)
                {
                    for (int j = 0; j < Simulator.small_symbols.size(); j++)
                    {
                        if(Simulator.small_symbols.get(j).hashCode() == Simulator.all_symbols.get(i).hashCode())
                        {

                            Simulator.small_symbols.remove(i);
                            Simulator.passive_symbols.remove(i);

                        }
                    }
                    for (int j = 0; j < Simulator.capital_symbols.size(); j++)
                    {
                        if(Simulator.capital_symbols.get(j).hashCode() == Simulator.all_symbols.get(i).hashCode())
                        {

                            Simulator.capital_symbols.remove(j);
                            Simulator.aggressive_symbols.remove(j);

                        }

                    }

                }

            }

        }

    }

}
