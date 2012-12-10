/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.tpsoft.tablesoccer.model;

import cz.tpsoft.tablesoccer.data.SQLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author tomas.praslicak
 */
public class MatchGame extends Model {
    public static final Model.Column<Integer> COL_GAME_ID = new Column<>("gameId", Model.Column.Type.NUMBER, false);
    public static final Model.Column<Integer> COL_MATCH_ID = new Column<>("matchId", Model.Column.Type.NUMBER, false);
    
    private int gameId,  matchId;

    public MatchGame(int gameId, int matchId) {
        this.gameId = gameId;
        this.matchId = matchId;
    }
    
    public MatchGame(int id, SQLConnection connection) {
        super(id, connection);
    }
    
    public int getGameId() {
        return gameId;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }
    
    public Game getGame(SQLConnection connection) {
        return new Game(gameId, connection);
    }

    public Match getMatch(SQLConnection connection) {
        return new Match(getMatchId(), connection);
    }

    public void setGame(Game game) {
        this.gameId = game.getId();
    }

    public void setMatch(Match match) {
        this.matchId = match.getId();
    }
    
    @Override
    protected void parseResult(ResultSet rs) throws SQLException {
        matchId = rs.getInt(COL_MATCH_ID.getName());
        gameId = rs.getInt(COL_GAME_ID.getName());
    }

    @Override
    protected ColVal[] getColVal() {
        return new ColVal[] {
            new ColVal(COL_MATCH_ID, getMatchId()),
            new ColVal(COL_GAME_ID, getGameId())
        };
    }
    
}
