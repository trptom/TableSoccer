/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.tpsoft.tablesoccer.data.wrapper;

import cz.tpsoft.tablesoccer.data.model.Game;
import cz.tpsoft.tablesoccer.enums.GameType;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author tomas.praslicak
 */
public class GameWrapper {
    public static GameWrapper createInstance(int id) {
        Game ret = null;
        try {
            SessionFactory sf = new Configuration().configure() .buildSessionFactory(); 
            Session session = sf.openSession(); 
            session.load(ret, id);
            session.close(); 
        } catch (HibernateException ex) {
            ex.printStackTrace(System.out);
            return null;
        }
        return new GameWrapper(ret);
    }
    
    private Game game;
    private MatchWrapper match = null;

    public GameWrapper() {
        this(new Game());
    }
    
    public GameWrapper(Game game) {
        this.game = game;
    }

    protected Game getGame() {
        return game;
    }
    
    public void setType(GameType type) {
        getGame().setType(type.getId());
    }
    
    public GameType getType() {
        return GameType.getById(getGame().getType());
    }
    
    public int getId() {
        return getGame().getId();
    }
    
    public MatchWrapper getMatch() {
        if (match == null || match.getId() != getGame().getMatchId()) {
            match = MatchWrapper.createInstance(getGame().getMatchId());
        }
        return match;
    }
    
    public void setMatch(MatchWrapper match) {
        getGame().setMatchId(match.getId());
    }
    
    public void setNumber(int number) {
        getGame().setNumber(number);
    }
}
