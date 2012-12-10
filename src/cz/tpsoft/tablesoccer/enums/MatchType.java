/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.tpsoft.tablesoccer.enums;

/**
 *
 * @author tomas.praslicak
 */
public enum MatchType {
    FRIENDLY {
        @Override public int getId() { return 0; }
    },
    LEAGUE {
        @Override public int getId() { return 1; }
    };
    
    public abstract int getId();
    
    public static MatchType getById(int id) {
        for (MatchType type : MatchType.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }
}
