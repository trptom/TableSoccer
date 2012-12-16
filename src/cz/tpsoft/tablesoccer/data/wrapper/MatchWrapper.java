/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.tpsoft.tablesoccer.data.wrapper;

import cz.tpsoft.tablesoccer.data.model.Match;
import cz.tpsoft.tablesoccer.helper.Formatter;
import java.util.Calendar;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author tomas.praslicak
 */
public class MatchWrapper {
    public static MatchWrapper createInstance(int id) {
        Match ret = null;
        try {
            SessionFactory sf = new Configuration().configure() .buildSessionFactory(); 
            Session session = sf.openSession(); 
            session.load(ret, id);
            session.close(); 
        } catch (HibernateException ex) {
            ex.printStackTrace(System.out);
            return null;
        }
        return new MatchWrapper(ret);
    }
    
    public static void save(MatchWrapper wrapper) {
        SessionFactory sf = new Configuration().configure().buildSessionFactory(); 
        Session session = sf.openSession(); 
        session.saveOrUpdate(wrapper.getMatch());
        session.flush();
        session.refresh(wrapper.getMatch());
        session.close();
    }
    
    private Match match;

    public MatchWrapper() {
        this(new Match());
    }

    public MatchWrapper(Match match) {
        this.match = match;
    }

    protected Match getMatch() {
        return match;
    }
    
    public void setDate(Calendar date) {
        getMatch().setDate(Formatter.dateToStr(date));
    }
    
    public Calendar getDate(Calendar date) {
        return Formatter.strToDate(getMatch().getDate());
    }
    
    public int getId() {
        return getMatch().getId();
    }
}
