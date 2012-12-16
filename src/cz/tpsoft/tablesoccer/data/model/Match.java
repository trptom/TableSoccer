package cz.tpsoft.tablesoccer.data.model;
// Generated 14.12.2012 13:40:40 by Hibernate Tools 3.2.1.GA



/**
 * Match generated by hbm2java
 */
public class Match  implements java.io.Serializable {


     private int id;
     private Integer leagueId;
     private int season;
     private int round;
     private int type;
     private String date;
     private Integer resultHome;
     private Integer resultAway;
     private Integer homeTeamId;
     private Integer awayTeamId;

    public Match() {
    }

	
    public Match(int id, int season, int round, int type, String date) {
        this.id = id;
        this.season = season;
        this.round = round;
        this.type = type;
        this.date = date;
    }
    public Match(int id, Integer leagueId, int season, int round, int type, String date, Integer resultHome, Integer resultAway, Integer homeTeamId, Integer awayTeamId) {
       this.id = id;
       this.leagueId = leagueId;
       this.season = season;
       this.round = round;
       this.type = type;
       this.date = date;
       this.resultHome = resultHome;
       this.resultAway = resultAway;
       this.homeTeamId = homeTeamId;
       this.awayTeamId = awayTeamId;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public Integer getLeagueId() {
        return this.leagueId;
    }
    
    public void setLeagueId(Integer leagueId) {
        this.leagueId = leagueId;
    }
    public int getSeason() {
        return this.season;
    }
    
    public void setSeason(int season) {
        this.season = season;
    }
    public int getRound() {
        return this.round;
    }
    
    public void setRound(int round) {
        this.round = round;
    }
    public int getType() {
        return this.type;
    }
    
    public void setType(int type) {
        this.type = type;
    }
    public String getDate() {
        return this.date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    public Integer getResultHome() {
        return this.resultHome;
    }
    
    public void setResultHome(Integer resultHome) {
        this.resultHome = resultHome;
    }
    public Integer getResultAway() {
        return this.resultAway;
    }
    
    public void setResultAway(Integer resultAway) {
        this.resultAway = resultAway;
    }
    public Integer getHomeTeamId() {
        return this.homeTeamId;
    }
    
    public void setHomeTeamId(Integer homeTeamId) {
        this.homeTeamId = homeTeamId;
    }
    public Integer getAwayTeamId() {
        return this.awayTeamId;
    }
    
    public void setAwayTeamId(Integer awayTeamId) {
        this.awayTeamId = awayTeamId;
    }




}


