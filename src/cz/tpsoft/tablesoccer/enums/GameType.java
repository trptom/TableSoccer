/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.tpsoft.tablesoccer.enums;

/**
 *
 * @author tomas.praslicak
 */
public enum GameType {
    SINGLE {
        @Override public int getId() { return 0; }
    },
    DOUBLE {
        @Override public int getId() { return 1; }
    },
    TWO_BALL {
        @Override public int getId() { return 2; }
    },
    FOUR_ON_FOUR {
        @Override public int getId() { return 3; }
    };
    
    public abstract int getId();
    
    public static GameType getById(int id) {
        for (GameType type : GameType.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }
}
