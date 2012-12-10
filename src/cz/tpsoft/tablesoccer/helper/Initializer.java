/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.tpsoft.tablesoccer.helper;

import cz.tpsoft.tablesoccer.data.MySQL;
import cz.tpsoft.tablesoccer.data.SQLConnection;
import cz.tpsoft.tablesoccer.data.SQLite;
import cz.tpsoft.tablesoccer.enums.GameType;
import cz.tpsoft.tablesoccer.enums.PlayerInGame;
import cz.tpsoft.tablesoccer.enums.TeamType;
import cz.tpsoft.tablesoccer.model.Game;
import cz.tpsoft.tablesoccer.model.Match;
import cz.tpsoft.tablesoccer.model.MatchGame;
import cz.tpsoft.tablesoccer.model.Player;
import cz.tpsoft.tablesoccer.model.Team;
import java.util.Calendar;

/**
 *
 * @author tomas.praslicak
 */
public class Initializer {
    private static String ID_COL_STRING_SQLITE = "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT";
    private static String ID_COL_STRING_MYSQL = "id INTEGER NOT NULL PRIMARY KEY auto_increment";
    
    private static String getIdColString(SQLConnection connection) {
        if (connection instanceof SQLite) {
            return ID_COL_STRING_SQLITE;
        }
        if (connection instanceof MySQL) {
            return ID_COL_STRING_MYSQL;
        }
        return null;
    }
    
    private static String getSQLIntTypeString(SQLConnection connection) {
        if (connection instanceof SQLite) {
            return "INTEGER";
        }
        if (connection instanceof MySQL) {
            return "int";
        }
        return null;
    }
    
    private static void createSQLTablePlayer(SQLConnection connection) {
        connection.executeUpdate("CREATE TABLE Player ("
                + getIdColString(connection) + ","
                + "name TEXT NOT NULL,"
                + "teamId " + getSQLIntTypeString(connection) + " NOT NULL)");
    }
    
    private static void createSQLTableGame(SQLConnection connection) {
        connection.executeUpdate("CREATE TABLE Game ("
                + getIdColString(connection) + ","
                + "number " + getSQLIntTypeString(connection) + " NOT NULL,"
                + "type " + getSQLIntTypeString(connection) + " NOT NULL,"
                + "home_player_1_id " + getSQLIntTypeString(connection) + " NULL DEFAULT NULL,"
                + "home_player_2_id " + getSQLIntTypeString(connection) + " NULL DEFAULT NULL,"
                + "home_player_3_id " + getSQLIntTypeString(connection) + " NULL DEFAULT NULL,"
                + "home_player_4_id " + getSQLIntTypeString(connection) + " NULL DEFAULT NULL,"
                + "away_player_1_id " + getSQLIntTypeString(connection) + " NULL DEFAULT NULL,"
                + "away_player_2_id " + getSQLIntTypeString(connection) + " NULL DEFAULT NULL,"
                + "away_player_3_id " + getSQLIntTypeString(connection) + " NULL DEFAULT NULL,"
                + "away_player_4_id " + getSQLIntTypeString(connection) + " NULL DEFAULT NULL,"
                + "home_score " + getSQLIntTypeString(connection) + " NULL DEFAULT NULL,"
                + "away_score " + getSQLIntTypeString(connection) + " NULL DEFAULT NULL)");
    }
    
    private static void createSQLTableMatch(SQLConnection connection) {
        connection.executeUpdate("CREATE TABLE Match ("
                + getIdColString(connection) + ","
                + "season " + getSQLIntTypeString(connection) + " NOT NULL,"
                + "round " + getSQLIntTypeString(connection) + " NULL DEFAULT NULL,"
                + "type " + getSQLIntTypeString(connection) + " NOT NULL,"
                + "date DATETIME NOT NULL,"
                + "result_home " + getSQLIntTypeString(connection) + " NULL DEFAULT NULL,"
                + "result_away " + getSQLIntTypeString(connection) + " NULL DEFAULT NULL,"
                + "home_team_id " + getSQLIntTypeString(connection) + " NOT NULL,"
                + "away_team_id " + getSQLIntTypeString(connection) + " NOT NULL)");
    }
    
    private static void createSQLTableTeam(SQLConnection connection) {
        connection.executeUpdate("CREATE TABLE Team ("
                + getIdColString(connection) + ","
                + "name TEXT NOT NULL,"
                + "shortcut TEXT NOT NULL,"
                + "league_level " + getSQLIntTypeString(connection) + " NULL DEFAULT NULL,"
                + "league_division " + getSQLIntTypeString(connection) + " NULL DEFAULT NULL)");
    }
    
    private static void createSQLTableMatchGame(SQLConnection connection) {
        connection.executeUpdate("CREATE TABLE MatchGame ("
                + getIdColString(connection) + ","
                + "gameId " + getSQLIntTypeString(connection) + " NOT NULL,"
                + "matchId " + getSQLIntTypeString(connection) + " NOT NULL)");
    }
    
    private static void dropSQLTables(SQLConnection connection) {
        connection.executeUpdate("DROP TABLE Player");
        connection.executeUpdate("DROP TABLE Game");
        connection.executeUpdate("DROP TABLE Match");
        connection.executeUpdate("DROP TABLE Team");
        connection.executeUpdate("DROP TABLE MatchGame");
    }
    
    private static void fillInSQLTables(SQLConnection connection) {
        Team[] teams = {
            new Team("Mortal", "MOR", 3, 1),
            new Team("Inarabu", "INA", 3, 1),
            new Team("FB Beercelona", "FCB", 3, 1)
        };
        
        for (Team team : teams) {
            team.save(connection);
        }
        
        Player[] players = {
            new Player("Tomáš Přasličák", teams[0].getId()),
            new Player("Pavel Jarolímek", teams[0].getId()),
            new Player("Marek Bečka", teams[0].getId()),
            new Player("Tomáš Filip", teams[0].getId()),
            new Player("Vladimír Novotný", teams[0].getId()),
        };
        
        for (Player player : players) {
            player.save(connection);
        }
        
        Match[] matches = {
            new Match(2012, 1, 1, Calendar.getInstance(), null, null, teams[0].getId(), teams[1].getId()),
            new Match(2012, 2, 1, Calendar.getInstance(), null, null, teams[0].getId(), teams[2].getId()),
            new Match(2012, 3, 1, Calendar.getInstance(), null, null, teams[1].getId(), teams[2].getId())
        };
        
        for (Match match : matches) {
            match.save(connection);
        }
        
        Game[] games = {
            new Game(1, GameType.DOUBLE),
            new Game(2, GameType.DOUBLE),
            new Game(3, GameType.SINGLE),
            new Game(4, GameType.SINGLE),
            new Game(5, GameType.FOUR_ON_FOUR)
        };
        
        for (Game game : games) {
            game.setPlayerId(TeamType.HOME, PlayerInGame.FIRST, players[0].getId());
            game.setPlayerId(TeamType.HOME, PlayerInGame.SECOND, players[1].getId());
            switch((int)Math.round(Math.random()* 3)) {
                case 0: game.setScore(TeamType.HOME, 2); game.setScore(TeamType.AWAY, 0); break;
                case 1: game.setScore(TeamType.HOME, 2); game.setScore(TeamType.AWAY, 1); break;
                case 2: game.setScore(TeamType.HOME, 1); game.setScore(TeamType.AWAY, 2); break;
                case 3: game.setScore(TeamType.HOME, 0); game.setScore(TeamType.AWAY, 2); break;
            }
            game.save(connection);
        }
        
        MatchGame[] matchGames = {
            new MatchGame(games[0].getId(), matches[0].getId()),
            new MatchGame(games[1].getId(), matches[0].getId()),
            new MatchGame(games[2].getId(), matches[0].getId()),
            new MatchGame(games[3].getId(), matches[0].getId()),
            new MatchGame(games[4].getId(), matches[0].getId()),
        };
        
        for (MatchGame matchGame : matchGames) {
            matchGame.save(connection);
        }
    }
    
    public static void createTablesSQLite(SQLite connection) {
        createSQLTablePlayer(connection);
        createSQLTableGame(connection);
        createSQLTableMatch(connection);
        createSQLTableTeam(connection);
        
        // m:n tabulky
        createSQLTableMatchGame(connection);
    }
    
    public static void deleteTablesSQLite(SQLite connection) {
        dropSQLTables(connection);
    }

    public static void fillInTablesSQLite(SQLite connection) {
        fillInSQLTables(connection);
    }
}
