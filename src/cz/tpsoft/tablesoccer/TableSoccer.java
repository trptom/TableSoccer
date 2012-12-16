/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.tpsoft.tablesoccer;

import cz.tpsoft.tablesoccer.data.wrapper.LeagueWrapper;
import cz.tpsoft.tablesoccer.data.wrapper.TeamWrapper;
import cz.tpsoft.tablesoccer.view.frame.Main;
import java.util.List;

/**
 *
 * @author tomas.praslicak
 */
public class TableSoccer {
    public static Main FRAME = new Main();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FRAME.setVisible(true);
        
        List<TeamWrapper> tmp = LeagueWrapper.createInstance(3).getTeams();
        
        for (TeamWrapper team : tmp) {
            System.out.println(team);
        }
    }
}
