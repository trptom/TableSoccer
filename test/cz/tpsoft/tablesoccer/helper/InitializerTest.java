/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.tpsoft.tablesoccer.helper;

import cz.tpsoft.tablesoccer.data.MySQL;
import cz.tpsoft.tablesoccer.data.SQLite;
import cz.tpsoft.tablesoccer.enums.GameType;
import cz.tpsoft.tablesoccer.model.Game;
import cz.tpsoft.tablesoccer.model.Match;
import cz.tpsoft.tablesoccer.model.MatchGame;
import cz.tpsoft.tablesoccer.model.Player;
import cz.tpsoft.tablesoccer.model.Team;
import java.io.File;
import java.util.Calendar;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tomas.praslicak
 */
public class InitializerTest {
    public InitializerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testCreateTablesSQLite() {
        // nove pripojeni
        SQLite db = new SQLite();
        db.connect("db", "Test.sql");
        
        // vytvoreni dat
        Initializer.createTablesSQLite(db);
        
        assertEquals("ukladani hrace 1", true, new Player("Hrac Prvni", 0).save(db));
        assertEquals("ukladani hrace 2", true, new Player("Hrac Druhy", 0).save(db));
        assertEquals("ukladani hry 1", true, new Game(0, GameType.DOUBLE).save(db));
        assertEquals("ukladani hry 2", true, new Game(0, GameType.DOUBLE).save(db));
        assertEquals("ukladani tymu 1", true, new Team("Mortal", "MOR", null, null).save(db));
        assertEquals("ukladani tymu 2", true, new Team("Inarabu", "INA", 3, 1).save(db));
        assertEquals("ukladani mistraku 1", true, new Match(2012, null, 0, Calendar.getInstance(), null, null, 1, 2).save(db));
        assertEquals("ukladani mistraku 2", true, new Match(2012, 1, 1, Calendar.getInstance(), 6, 5, 1, 2).save(db));
        assertEquals("ukladani mistraku 3 (null datum)", false, new Match(2012, 1, 1, null, 6, 5, 1, 2).save(db));
        
        assertEquals("parovani mistrak:hra 1", true, new MatchGame(1, 1).save(db));
        assertEquals("parovani mistrak:hra 2", true, new MatchGame(2, 2).save(db));
        
        // konec pripojeni
        db.close();
        
        // smazani test DB
        System.out.println("mazani testovaci db");
        File f = new File("db\\Test.sql");
        assertEquals("Existence souboru s testovaci DB", true, f.exists());
        assertEquals("Mazani testovaci SQLite DB", true, f.delete());
        
        System.out.println("hotovo");
    }
}
