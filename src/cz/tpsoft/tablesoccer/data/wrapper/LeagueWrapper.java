/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.tpsoft.tablesoccer.data.wrapper;

import cz.tpsoft.tablesoccer.data.model.League;
import cz.tpsoft.tablesoccer.data.model.Team;
import java.util.LinkedList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author tomas.praslicak
 */
public class LeagueWrapper {
    public static LeagueWrapper createInstance(int id) {
        League ret = null;
        try {
            SessionFactory sf = new Configuration().configure() .buildSessionFactory(); 
            Session session = sf.openSession(); 
            Criteria criteria = session.createCriteria(League.class);
            criteria.add(Restrictions.eq("id", id));
            ret = (League)criteria.list().get(0);
            Hibernate.initialize(ret.getTeams());
            session.close(); 
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            return null;
        }
        return new LeagueWrapper(ret);
    }
    
    public static void save(LeagueWrapper wrapper) {
        SessionFactory sf = new Configuration().configure().buildSessionFactory(); 
        Session session = sf.openSession(); 
        session.saveOrUpdate(wrapper.getLeague());
        session.flush();
        session.refresh(wrapper.getLeague());
        session.close();
    }
    
    private League league;

    public LeagueWrapper(League league) {
        this.league = league;
    }

    protected League getLeague() {
        return league;
    }

    @Override
    public String toString() {
        return getLeague().getName();
    }
    
    public List<TeamWrapper> getTeams() {
        LinkedList<TeamWrapper> ret = new LinkedList<>();
        for (Object team : getLeague().getTeams()) {
            if (team instanceof Team) {
                ret.add(new TeamWrapper((Team)team));
            }
        }
        return ret;
    }
}
