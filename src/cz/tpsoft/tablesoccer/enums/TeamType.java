/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.tpsoft.tablesoccer.enums;

/**
 *
 * @author tomas.praslicak
 */
public enum TeamType {
    HOME {
        @Override public int getId() { return 0; }
    },
    AWAY {
        @Override public int getId() { return 1; }
    };
    
    public abstract int getId();
}
