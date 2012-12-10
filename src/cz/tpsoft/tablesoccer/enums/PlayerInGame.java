/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.tpsoft.tablesoccer.enums;

/**
 *
 * @author tomas.praslicak
 */
public enum PlayerInGame {
    FIRST {
        @Override public int getId() { return 0; }
    },
    SECOND {
        @Override public int getId() { return 1; }
    },
    THIRD {
        @Override public int getId() { return 2; }
    },
    FOURTH {
        @Override public int getId() { return 3; }
    };
    
    public abstract int getId();
}
