package simulator.rabotiaev_makar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SymbolSmallR extends Symbol implements SmallCase, Passive{

    public void move(){
        Position future_position = null;
        List<Position> validMoves = new ArrayList<Position>();
        for (int i = (this.position.row - 1); i < this.position.row + 2; i++) {
            for (int j = (this.position.column - 1); j < this.position.column + 2; j++) {
                if((i <= 10) & (j <= 10)){
                    future_position = new Position(i,j);
                    validMoves.add(future_position);
                }
            }
        }
        Random rd = new Random();
        future_position = validMoves.get(rd.nextInt(validMoves.size()));

        this.setPosition(future_position);
    }

    public void die(){
        if (this.numberIterationsAlive == 20) {
            for (int i = 0; i < Simulator.all_symbols.size(); i++) {

                if (Simulator.all_symbols.get(i).idSymbol == this.idSymbol) {
                    Simulator.all_symbols.remove(i);
                    SymbolCapitalR newborn = new SymbolCapitalR();
                    newborn.idSymbol = this.idSymbol;
                    newborn.setPosition(this.getPosition());
                    Simulator.all_symbols.add(newborn);
                }
            }

            for (int j = 0; j < Simulator.small_symbols.size(); j++) {
                if (((Symbol) Simulator.small_symbols.get(j)).idSymbol == this.idSymbol) {
                    Simulator.small_symbols.remove(j);
                    Simulator.passive_symbols.remove(j);
                    SymbolCapitalR newborn = new SymbolCapitalR();
                    newborn.idSymbol = this.idSymbol;
                    newborn.setPosition(this.getPosition());
                    Simulator.capital_symbols.add(newborn);
                    Simulator.aggressive_symbols.add(newborn);
                }
            }
            this.numberIterationsAlive++;
        }
    }

    @Override
    public void upgrade() {
        for (int i = 0; i < Simulator.small_symbols.size(); i++) {

            if(((Symbol) Simulator.small_symbols.get(i)).idSymbol == this.idSymbol){
                Simulator.small_symbols.remove(i);
                Simulator.passive_symbols.remove(i);
                SymbolCapitalP new_symbol = new SymbolCapitalP();
                new_symbol.setPosition(this.position);
                new_symbol.idSymbol = this.idSymbol;

                Simulator.capital_symbols.add(new_symbol);
                Simulator.aggressive_symbols.add(new_symbol);
            }
        }
        for (int i = 0; i < Simulator.all_symbols.size(); i++) {
            if(Simulator.all_symbols.get(i).idSymbol == this.idSymbol){
                Simulator.all_symbols.remove(i);
                SymbolCapitalP new_symbol = new SymbolCapitalP();
                new_symbol.setPosition(this.position);
                new_symbol.idSymbol = this.idSymbol;
                Simulator.all_symbols.add(new_symbol);
            }
        }

    }

    @Override
    public void escape(){
        List<Position> validMoves = new ArrayList<Position>();
        int closest_distance = 1000;
        Symbol closest_enemy = new SymbolCapitalS();
        for (int i = 0; i < Simulator.all_symbols.size(); i++) {
            Symbol current_symbol = Simulator.all_symbols.get(i);
            if (current_symbol instanceof SymbolCapitalP){
                if((this.position.manhattanDistance(current_symbol.getPosition()) < closest_distance) & this.position.manhattanDistance(current_symbol.getPosition()) <= this.sightDistance){
                    closest_distance = this.position.manhattanDistance(current_symbol.getPosition());
                    closest_enemy = current_symbol;
                }
            }
        }
        for (int i = (this.position.row - 1); i < this.position.row + 2; i++) {
            for (int j = (this.position.column - 1); j < this.position.column + 2; j++) {
                if((i <= 10 & i > 0) & (j <= 10 & j > 0)){
                    Position future_position = new Position(i, j);
                    if (closest_enemy.getPosition() != null){
                        if(future_position.manhattanDistance(closest_enemy.getPosition()) > closest_distance){
                            this.setPosition(future_position);
                        }
                    }
                }
            }
        }


    }

    @Override
    public void moveBreed() {
        /*
        first we set unrealistic furtherness, so any other further element will seem close
         */
        Position temporary = new Position(100,100);

        SymbolSmallR potential_parent = new SymbolSmallR();
        potential_parent.setPosition(temporary);


        // Here we check all the elements, in the passive_symbols, for being reachable and of our type
        for (int i = 0; i < Simulator.all_symbols.size(); i++) {
            if(this.position.hashCode() == Simulator.all_symbols.get(i).position.hashCode()){
                SymbolSmallR newborn = new SymbolSmallR();
                Symbol.COUNT_SYMBOLS++;
                newborn.setPosition(this.getPosition());
                newborn.idSymbol = Symbol.COUNT_SYMBOLS;
                Simulator.all_symbols.add(newborn);
                Simulator.passive_symbols.add(newborn);
                Simulator.small_symbols.add(newborn);
            }

        }
        for (int i = 0; i < Simulator.passive_symbols.size(); i++) {
            if(Simulator.passive_symbols.get(i).equals(this)){
                if(((this.position.manhattanDistance(((SymbolSmallR) Simulator.passive_symbols.get(i)).position)) < this.sightDistance)){
                    if(this.position.manhattanDistance(potential_parent.getPosition()) > this.position.manhattanDistance(((SymbolSmallR) Simulator.passive_symbols.get(i)).getPosition())){
                        potential_parent = (SymbolSmallR) Simulator.passive_symbols.get(i);
                    }
                }
            }
        }

        Position future_position = null;
        List<Position> validMoves = new ArrayList<Position>();

        for (int i = (this.position.row - 1); i < this.position.row + 2; i++) {
            for (int j = (this.position.column - 1); j < this.position.column + 2; j++) {
                if((i <= 10) & (j <= 10)){
                    future_position = new Position(i,j);
                    validMoves.add(future_position);
                }
            }
        }

        for (int i = 0; i < validMoves.size(); i++) {
            if(validMoves.get(i).manhattanDistance(potential_parent.getPosition()) < this.position.manhattanDistance(potential_parent.getPosition())){
                future_position = validMoves.get(i);
            }
        }

        this.setPosition(future_position);

    }

    }
