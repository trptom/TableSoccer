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
public class Team extends Model {
    public static final Model.Column<String> COL_NAME = new Column<>("name", Model.Column.Type.STRING, false);
    public static final Model.Column<String> COL_SHORTCUT = new Column<>("shortcut", Model.Column.Type.STRING, false);
    public static final Model.Column<Integer> COL_LEAGUE_LEVEL = new Column<>("league_level", Model.Column.Type.NUMBER, true);
    public static final Model.Column<String> COL_LEAGUE_DIVISION = new Column<>("league_division", Model.Column.Type.STRING, true);
    
    private Integer leagueLevel, leagueDivision;
    private String name, shortcut;

    public Team(int id, SQLConnection connection) {
        super(id, connection);
    }
    
    public Team(String name, String shortcut, Integer leagueLevel, Integer leagueDivision) {
        this.leagueLevel = leagueLevel;
        this.leagueDivision = leagueDivision;
        this.name = name;
        this.shortcut = shortcut;
    }

    public String getName() {
        return name;
    }

    public String getShortcut() {
        return shortcut;
    }

    public Integer getLeagueLevel() {
        return leagueLevel;
    }

    public Integer getLeagueDivision() {
        return leagueDivision;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public void setLeagueLevel(Integer leagueLevel) {
        this.leagueLevel = leagueLevel;
    }

    public void setLeagueDivision(Integer leagueDivision) {
        this.leagueDivision = leagueDivision;
    }
    
    @Override
    protected void parseResult(ResultSet rs) throws SQLException {
        setName(rs.getString(COL_NAME.getName()));
        setShortcut(rs.getString(COL_SHORTCUT.getName()));
        setLeagueLevel(SQLConnection.getInteger(rs, COL_LEAGUE_LEVEL.getName()));
        setLeagueLevel(SQLConnection.getInteger(rs, COL_LEAGUE_DIVISION.getName()));
    }

    @Override
    protected ColVal[] getColVal() {
        return new ColVal[] {
            new ColVal(COL_NAME, getName()),
            new ColVal(COL_SHORTCUT, getShortcut()),
            new ColVal(COL_LEAGUE_LEVEL, getLeagueLevel()),
            new ColVal(COL_LEAGUE_DIVISION, getLeagueDivision()),
        };
    }
    
}
