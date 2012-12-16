/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.tpsoft.tablesoccer.data.wrapper;

import cz.tpsoft.tablesoccer.data.model.Player;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author tomas.praslicak
 */
public class PlayerWrapper {
    public static PlayerWrapper createInstance(int id) {
        Player ret = null;
        try {
            SessionFactory sf = new Configuration().configure() .buildSessionFactory(); 
            Session session = sf.openSession(); 
            session.load(ret, id);
            session.close(); 
        } catch (HibernateException ex) {
            ex.printStackTrace(System.out);
            return null;
        }
        return new PlayerWrapper(ret);
    }
    
    Player player;

    public PlayerWrapper() {
        this(new Player());
    }

    public PlayerWrapper(Player player) {
        this.player = player;
    }

    protected Player getPlayer() {
        return player;
    }

    @Override
    public String toString() {
        return getName();
    }
    
    public String getName() {
        if (getPlayer().getNickName() != null) {
            return getPlayer().getNickName();
        }
        if (getPlayer().getFirstName() != null) {
            return getPlayer().getFirstName() + " " + getPlayer().getSecondName();
        }
        if (getPlayer().getSecondName() != null) {
            return getPlayer().getSecondName();
        }
        return "";
    }
}
