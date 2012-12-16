/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.tpsoft.tablesoccer.data.wrapper;

import cz.tpsoft.tablesoccer.data.model.Team;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author tomas.praslicak
 */
public class TeamWrapper {
    public static TeamWrapper createInstance(int id) {
        Team ret = null;
        try {
            SessionFactory sf = new Configuration().configure() .buildSessionFactory(); 
            Session session = sf.openSession(); 
            session.load(ret, id);
            session.close(); 
        } catch (HibernateException ex) {
            ex.printStackTrace(System.out);
            return null;
        }
        return new TeamWrapper(ret);
    }
    
    public static void save(TeamWrapper wrapper) {
        SessionFactory sf = new Configuration().configure().buildSessionFactory(); 
        Session session = sf.openSession(); 
        session.saveOrUpdate(wrapper.getTeam());
        session.flush();
        session.refresh(wrapper.getTeam());
        session.close();
    }
    
    private Team team;

    public TeamWrapper(Team team) {
        this.team = team;
    }

    protected Team getTeam() {
        return team;
    }

    @Override
    public String toString() {
        return getTeam().getName();
    }
}
