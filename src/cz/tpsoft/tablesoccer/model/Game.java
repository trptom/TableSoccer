/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.tpsoft.tablesoccer.model;

import cz.tpsoft.tablesoccer.data.SQLConnection;
import cz.tpsoft.tablesoccer.enums.GameType;
import cz.tpsoft.tablesoccer.enums.PlayerInGame;
import cz.tpsoft.tablesoccer.enums.TeamType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author tomas.praslicak
 */
public class Game extends Model {
    public static LinkedList<Game> getAll(SQLConnection connection,
            String where, SortCriteria[] sortCriteria, Integer limit)
            throws SQLException {
        ResultSet set = processSelect(connection, "Game", where, sortCriteria, limit);
        LinkedList<Game> ret = new LinkedList<>();
        while (set.next()) {
            ret.add(new Game(set));
        }
        return ret;
    }
    
    public static LinkedList<Game> getByPlayer(SQLConnection connection,
            int playerId, SortCriteria[] sortCriteria, Integer limit)
            throws SQLException{
        return getAll(connection,
                "(" + COL_HOME_PLAYER_1_ID.getName() + " = " + playerId + ")OR("
                + COL_HOME_PLAYER_2_ID.getName() + " = " + playerId + ")OR("
                + COL_HOME_PLAYER_3_ID.getName() + " = " + playerId + ")OR("
                + COL_HOME_PLAYER_4_ID.getName() + " = " + playerId + ")OR("
                + COL_AWAY_PLAYER_1_ID.getName() + " = " + playerId + ")OR("
                + COL_AWAY_PLAYER_2_ID.getName() + " = " + playerId + ")OR("
                + COL_AWAY_PLAYER_3_ID.getName() + " = " + playerId + ")OR("
                + COL_AWAY_PLAYER_4_ID.getName() + " = " + playerId + ")",
                sortCriteria, limit);
    }
    
    public static LinkedList<Game> getByPlayerAndType(SQLConnection connection,
            int playerId, GameType gameType, SortCriteria[] sortCriteria,
            Integer limit) throws SQLException{
        return getAll(connection,
                "((" + COL_HOME_PLAYER_1_ID.getName() + " = " + playerId + ")OR("
                + COL_HOME_PLAYER_2_ID.getName() + " = " + playerId + ")OR("
                + COL_HOME_PLAYER_3_ID.getName() + " = " + playerId + ")OR("
                + COL_HOME_PLAYER_4_ID.getName() + " = " + playerId + ")OR("
                + COL_AWAY_PLAYER_1_ID.getName() + " = " + playerId + ")OR("
                + COL_AWAY_PLAYER_2_ID.getName() + " = " + playerId + ")OR("
                + COL_AWAY_PLAYER_3_ID.getName() + " = " + playerId + ")OR("
                + COL_AWAY_PLAYER_4_ID.getName() + " = " + playerId + "))AND("
                + COL_TYPE.getName() + " = " + gameType.getId() +")",
                sortCriteria, limit);
    }
    
    public static final Model.Column<Integer> COL_NUMBER = new Column<>("number", Model.Column.Type.NUMBER, false);
    public static final Model.Column<Integer> COL_TYPE = new Column<>("type", Model.Column.Type.NUMBER, false);
    public static final Model.Column<Integer> COL_HOME_PLAYER_1_ID = new Column<>("home_player_1_id", Model.Column.Type.NUMBER, true);
    public static final Model.Column<Integer> COL_HOME_PLAYER_2_ID = new Column<>("home_player_2_id", Model.Column.Type.NUMBER, true);
    public static final Model.Column<Integer> COL_HOME_PLAYER_3_ID = new Column<>("home_player_3_id", Model.Column.Type.NUMBER, true);
    public static final Model.Column<Integer> COL_HOME_PLAYER_4_ID = new Column<>("home_player_4_id", Model.Column.Type.NUMBER, true);
    public static final Model.Column<Integer> COL_AWAY_PLAYER_1_ID = new Column<>("away_player_1_id", Model.Column.Type.NUMBER, true);
    public static final Model.Column<Integer> COL_AWAY_PLAYER_2_ID = new Column<>("away_player_2_id", Model.Column.Type.NUMBER, true);
    public static final Model.Column<Integer> COL_AWAY_PLAYER_3_ID = new Column<>("away_player_3_id", Model.Column.Type.NUMBER, true);
    public static final Model.Column<Integer> COL_AWAY_PLAYER_4_ID = new Column<>("away_player_4_id", Model.Column.Type.NUMBER, true);
    public static final Model.Column<Integer> COL_HOME_SCORE = new Column<>("home_score", Model.Column.Type.NUMBER, true);
    public static final Model.Column<Integer> COL_AWAY_SCORE = new Column<>("away_score", Model.Column.Type.NUMBER, true);

    private int number;
    private GameType type;
    private Integer[][] playerId;
    private Integer[] score;

    public Game(ResultSet from) {
        super(from);
    }
    
    public Game(int number, GameType type) {
        this.number = number;
        this.type = type;
        playerId = new Integer[2][4];
        score = new Integer[2];
    }
    
    public Game(int id, SQLConnection connection) {
        super(id, connection);
    }

    public int getNumber() {
        return number;
    }

    public GameType getType() {
        return type;
    }

    public Integer getPlayerId(TeamType team, PlayerInGame player) {
        return playerId[team.getId()][player.getId()];
    }
    
    public Integer getScore(TeamType team) {
        return score[team.getId()];
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setType(GameType type) {
        this.type = type;
    }

    public void setPlayerId(TeamType team, PlayerInGame player, Integer playerId) {
        this.playerId[team.getId()][player.getId()] = playerId;
    }

    public void setScore(TeamType team, Integer score) {
        this.score[team.getId()] = score;
    }
    
    @Override
    protected void parseResult(ResultSet rs) throws SQLException {
        playerId = new Integer[2][4];
        score = new Integer[2];
        
        number = rs.getInt(COL_NUMBER.getName());
        type = GameType.getById(rs.getInt(COL_TYPE.getName()));
        score[TeamType.HOME.getId()] = SQLConnection.getInteger(rs, COL_HOME_SCORE.getName());
        score[TeamType.AWAY.getId()] = SQLConnection.getInteger(rs, COL_AWAY_SCORE.getName());
        playerId[TeamType.HOME.getId()][PlayerInGame.FIRST.getId()] = SQLConnection.getInteger(rs, COL_HOME_PLAYER_1_ID.getName());
        playerId[TeamType.HOME.getId()][PlayerInGame.SECOND.getId()] = SQLConnection.getInteger(rs, COL_HOME_PLAYER_2_ID.getName());
        playerId[TeamType.HOME.getId()][PlayerInGame.THIRD.getId()] = SQLConnection.getInteger(rs, COL_HOME_PLAYER_3_ID.getName());
        playerId[TeamType.HOME.getId()][PlayerInGame.FOURTH.getId()] = SQLConnection.getInteger(rs, COL_HOME_PLAYER_4_ID.getName());
        playerId[TeamType.AWAY.getId()][PlayerInGame.FIRST.getId()] = SQLConnection.getInteger(rs, COL_AWAY_PLAYER_1_ID.getName());
        playerId[TeamType.AWAY.getId()][PlayerInGame.SECOND.getId()] = SQLConnection.getInteger(rs, COL_AWAY_PLAYER_2_ID.getName());
        playerId[TeamType.AWAY.getId()][PlayerInGame.THIRD.getId()] = SQLConnection.getInteger(rs, COL_AWAY_PLAYER_3_ID.getName());
        playerId[TeamType.AWAY.getId()][PlayerInGame.FOURTH.getId()] = SQLConnection.getInteger(rs, COL_AWAY_PLAYER_4_ID.getName());
    }

    @Override
    protected ColVal[] getColVal() {
        return new ColVal[] {
            new ColVal(COL_NUMBER, getNumber()),
            new ColVal(COL_TYPE, getType().getId()),
            new ColVal(COL_HOME_SCORE, getScore(TeamType.HOME)),
            new ColVal(COL_AWAY_SCORE, getScore(TeamType.AWAY)),
            new ColVal(COL_HOME_PLAYER_1_ID, getPlayerId(TeamType.HOME, PlayerInGame.FIRST)),
            new ColVal(COL_HOME_PLAYER_2_ID, getPlayerId(TeamType.HOME, PlayerInGame.SECOND)),
            new ColVal(COL_HOME_PLAYER_3_ID, getPlayerId(TeamType.HOME, PlayerInGame.THIRD)),
            new ColVal(COL_HOME_PLAYER_4_ID, getPlayerId(TeamType.HOME, PlayerInGame.FOURTH)),
            new ColVal(COL_AWAY_PLAYER_1_ID, getPlayerId(TeamType.AWAY, PlayerInGame.FIRST)),
            new ColVal(COL_AWAY_PLAYER_2_ID, getPlayerId(TeamType.AWAY, PlayerInGame.SECOND)),
            new ColVal(COL_AWAY_PLAYER_3_ID, getPlayerId(TeamType.AWAY, PlayerInGame.THIRD)),
            new ColVal(COL_AWAY_PLAYER_4_ID, getPlayerId(TeamType.AWAY, PlayerInGame.FOURTH))
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Game#").append(getId()).append(" ");
        sb.append(getScore(TeamType.HOME) == null ? "-" : getScore(TeamType.HOME));
        sb.append(":");
        sb.append(getScore(TeamType.AWAY) == null ? "-" : getScore(TeamType.AWAY));
        return sb.toString();
    }
    
    public boolean isWinner(int playerId) {
        if ((getScore(TeamType.HOME) != null)&&(getScore(TeamType.AWAY) != null)) {
            if (getScore(TeamType.HOME) > getScore(TeamType.AWAY)) {
                if ((isPlayer(TeamType.HOME, PlayerInGame.FIRST, playerId)) ||
                        (isPlayer(TeamType.HOME, PlayerInGame.SECOND, playerId)) ||
                        (isPlayer(TeamType.HOME, PlayerInGame.THIRD, playerId)) ||
                        (isPlayer(TeamType.HOME, PlayerInGame.FOURTH, playerId))) {
                    return true;
                }
            }
            if (getScore(TeamType.HOME) < getScore(TeamType.AWAY)) {
                if ((isPlayer(TeamType.AWAY, PlayerInGame.FIRST, playerId)) ||
                        (isPlayer(TeamType.AWAY, PlayerInGame.SECOND, playerId)) ||
                        (isPlayer(TeamType.AWAY, PlayerInGame.THIRD, playerId)) ||
                        (isPlayer(TeamType.AWAY, PlayerInGame.FOURTH, playerId))) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean isPlayer(TeamType team, PlayerInGame player, int playerId) {
        return (getPlayerId(team, player) != null &&
                getPlayerId(team, player) == playerId);
    }
}
