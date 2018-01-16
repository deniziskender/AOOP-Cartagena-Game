package model;

import java.io.Serializable;

public class Face implements Serializable{
	private static final long serialVersionUID = 1L;
	private Symbol symbol;
    
    public Face(Symbol symbol) {
    	this.symbol = symbol;
    }

    public Symbol getSymbol() {
        return symbol;
    }
}
