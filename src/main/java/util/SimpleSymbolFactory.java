package util;

import java_cup.runtime.Symbol;
import java_cup.runtime.SymbolFactory;

public class SimpleSymbolFactory implements SymbolFactory {
    @Override
    public Symbol newSymbol(String name, int id, Symbol left, Symbol right, Object value) {
        return new Symbol(id, value);
    }

    @Override
    public Symbol newSymbol(String name, int id, Symbol left, Symbol right) {
        return new Symbol(id);
    }

    @Override
    public Symbol newSymbol(String name, int id, Symbol left, Object value) {
        return new Symbol(id, value);
    }

    @Override
    public Symbol newSymbol(String name, int id, Object value) {
        return new Symbol(id, value);
    }

    @Override
    public Symbol newSymbol(String name, int id) {
        return new Symbol(id);
    }

    @Override
    public Symbol startSymbol(String name, int id, int state) {
        Symbol start = new Symbol(id);
        start.parse_state = state;
        return start;
    }
}
