/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.tpsoft.tablesoccer;

import cz.tpsoft.tablesoccer.data.SQLite;
import cz.tpsoft.tablesoccer.enums.GameType;
import cz.tpsoft.tablesoccer.helper.Initializer;
import cz.tpsoft.tablesoccer.model.Game;
import cz.tpsoft.tablesoccer.model.Player;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author tomas.praslicak
 */
public class TableSoccer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SQLite db = new SQLite();
        db.connect("d:\\Dokumenty\\NetBeansProjects\\TableSoccer\\db", "TableSoccer.sql");
        
        Initializer.deleteTablesSQLite(db);
        Initializer.createTablesSQLite(db);
        Initializer.fillInTablesSQLite(db);
        
        /*try {
            LinkedList<Player> pls = Player.getAll(db, null, null, null);
            for (Player p : pls) {
                p.setTeamId((int)Math.round(Math.random()));
                p.save(db);
                System.out.println(p.toString());
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        
        System.out.println("-----");*/

        try {
            LinkedList<Player> pls = Player.getAll(db, null, null, null);
            for (Player p : pls) {
                System.out.println(p.toString() + " - " + (p.getPercentage(db)) + "%");
                LinkedList<Game> games = p.getGames(db);
                for (Game game : games) {
                    System.out.println(game.toString());
                }
                System.out.println(p.countPercentage(p.getGames(db, GameType.DOUBLE)) + "%");
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        
//        Initializer.createTablesSQLite(db);
        
//        new Team("Mortal", "MOR", null, null).save(db);
//        Team t = new Team(1, db);
//        t.setName("Mortal");
//        t.save(db);
        
//        MySQL db = new MySQL();
//        db.connect("localhost", null, "root", "", "TableSoccer");
        
        db.close();
    }
}
