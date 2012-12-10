/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.tpsoft.tablesoccer.model;

import cz.tpsoft.tablesoccer.data.SQLConnection;
import cz.tpsoft.tablesoccer.enums.TeamType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

/**
 *
 * @author tomas.praslicak
 */
public class Match extends Model {
    public static final Model.Column<Integer> COL_SEASON = new Column<>("season", Model.Column.Type.NUMBER, false);
    public static final Model.Column<Integer> COL_ROUND = new Column<>("round", Model.Column.Type.NUMBER, true);
    public static final Model.Column<Integer> COL_TYPE = new Column<>("type", Model.Column.Type.NUMBER, false);
    public static final Model.Column<String> COL_DATE = new Column<>("date", Model.Column.Type.STRING, false);
    public static final Model.Column<Integer> COL_RESULT_HOME = new Column<>("result_home", Model.Column.Type.NUMBER, true);
    public static final Model.Column<Integer> COL_RESULT_AWAY = new Column<>("result_away", Model.Column.Type.NUMBER, true);
    public static final Model.Column<Integer> COL_HOME_TEAM_ID = new Column<>("home_team_id", Model.Column.Type.NUMBER, false);
    public static final Model.Column<Integer> COL_AWAY_TEAM_ID = new Column<>("away_team_id", Model.Column.Type.NUMBER, false);

    private int season;
    private Integer round;
    private int type;
    private Calendar date;
    private Integer[] result = new Integer[2];
    private int[] teamId = new int[2];

    public Match(int id, SQLConnection connection) {
        super(id, connection);
    }
    
    public Match(int season, Integer round, int type, Calendar date,
            Integer homeResult, Integer awayResult, int homeTeamId,
            int awayTeamId) {
        this.season = season;
        this.round = round;
        this.type = type;
        this.date = date;
        this.result[TeamType.HOME.getId()] = homeResult;
        this.result[TeamType.AWAY.getId()] = awayResult;
        this.teamId[TeamType.HOME.getId()] = homeTeamId;
        this.teamId[TeamType.AWAY.getId()] = awayTeamId;
    }

    public int getSeason() {
        return season;
    }

    public Integer getRound() {
        return round;
    }

    public int getType() {
        return type;
    }

    public Calendar getDate() {
        return date;
    }

    public Integer getResult(TeamType team) {
        return result[team.getId()];
    }

    public int getTeamId(TeamType team) {
        return teamId[team.getId()];
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public void setResult(TeamType team, Integer result) {
        this.result[team.getId()] = result;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public void setTeamId(TeamType team, int teamId) {
        this.teamId[team.getId()] = teamId;
    }

    public void setType(int type) {
        this.type = type;
    }
    
    @Override
    protected void parseResult(ResultSet rs) throws SQLException {
        setSeason(rs.getInt(COL_SEASON.getName()));
        setRound(SQLConnection.getInteger(rs, COL_SEASON.getName()));
        setType(rs.getInt(COL_TYPE.getName()));
        setDate(SQLConnection.strToDate(rs.getString(COL_DATE.getName())));
        setResult(TeamType.HOME, SQLConnection.getInteger(rs, COL_RESULT_HOME.getName()));
        setResult(TeamType.AWAY, SQLConnection.getInteger(rs, COL_RESULT_AWAY.getName()));
        setTeamId(TeamType.HOME, rs.getInt(COL_HOME_TEAM_ID.getName()));
        setTeamId(TeamType.AWAY, rs.getInt(COL_AWAY_TEAM_ID.getName()));
    }

    @Override
    protected ColVal[] getColVal() {
        return new ColVal[] {
            new ColVal(COL_SEASON, getSeason()),
            new ColVal(COL_ROUND, getRound()),
            new ColVal(COL_TYPE, getType()),
            new ColVal(COL_DATE, SQLConnection.dateToStr(getDate())),
            new ColVal(COL_RESULT_HOME, getResult(TeamType.HOME)),
            new ColVal(COL_RESULT_AWAY, getResult(TeamType.AWAY)),
            new ColVal(COL_HOME_TEAM_ID, getTeamId(TeamType.HOME)),
            new ColVal(COL_AWAY_TEAM_ID, getTeamId(TeamType.AWAY)),
        };
    }
    
}
