/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.tpsoft.tablesoccer.model;

import cz.tpsoft.tablesoccer.data.SQLConnection;
import cz.tpsoft.tablesoccer.enums.GameType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author tomas.praslicak
 */
public class Player extends Model {
    public static LinkedList<Player> getAll(SQLConnection connection,
            String where, SortCriteria[] sortCriteria, Integer limit)
            throws SQLException {
        ResultSet set = processSelect(connection, "Player", where, sortCriteria, limit);
        LinkedList<Player> ret = new LinkedList<>();
        while (set.next()) {
            ret.add(new Player(set));
        }
        return ret;
    }
    
    public static LinkedList<Player> getByTeamId(SQLConnection connection,
            int teamId, SortCriteria[] sortCriteria, Integer limit)
            throws SQLException{
        return getAll(connection, "(teamId = " + teamId + ")", sortCriteria, limit);
    }
    
    public static final Model.Column<String> COL_NAME = new Column<>("name", Model.Column.Type.STRING, false);
    public static final Model.Column<Integer> COL_TEAM_ID = new Column<>("teamId", Model.Column.Type.NUMBER, false);
    
    static {
        Player.COLUMNS.addLast(COL_NAME);
        Player.COLUMNS.addLast(COL_TEAM_ID);
    }
    
    private String name;
    private int teamId;
    private Double percentage = null;

    public Player(ResultSet from) {
        super(from);
    }
    
    public Player(int id, SQLConnection connection) {
        super(id, connection);
    }
    
    public Player(String name, int teamId) {
        this.name = name;
        this.teamId = teamId;
    }
    
    public int getTeamId() {
        return teamId;
    }

    public String getName() {
        return name;
    }
    
    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    protected void parseResult(ResultSet rs) throws SQLException {
        this.name = rs.getString(COL_NAME.getName());
        this.teamId = SQLConnection.getInteger(rs, COL_TEAM_ID.getName());
    }

    @Override
    protected ColVal[] getColVal() {
        return new ColVal[] {
            new ColVal(COL_NAME, getName()),
            new ColVal(COL_TEAM_ID, getTeamId())
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Player#").append(getId()).append(": ");
        sb.append(getName()).append(" (teamId: ").append(getTeamId()).append(")");
        return sb.toString();
    }
    
    public LinkedList<Game> getGames(SQLConnection connection) {
        try {
            return Game.getByPlayer(connection, getId(), null, null);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            return null;
        }
    }
    
    public LinkedList<Game> getGames(SQLConnection connection, GameType type) {
        try {
            return Game.getByPlayerAndType(connection, getId(), type, null, null);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            return null;
        }
    }

    public Double getPercentage(SQLConnection connection) {
        return getPercentage(connection, false);
    }
    
    public Double getPercentage(SQLConnection connection, boolean alwaysReload) {
        if (alwaysReload || percentage == null) {
            percentage = countPercentage(getGames(connection));
        }
        return percentage;
    }
    
    public Double countPercentage(LinkedList<Game> games) {
        int pts = 0;
        for (Game game : games) {
            if (game.isWinner(getId())) {
                pts++;
            }
        }
        return games.size() > 0 ? (((double)pts / (double)games.size()) * 100) : 0;
    }
}
