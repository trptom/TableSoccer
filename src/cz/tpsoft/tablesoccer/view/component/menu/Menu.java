/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.tpsoft.tablesoccer.view.component.menu;

import cz.tpsoft.tablesoccer.data.wrapper.MatchWrapper;
import cz.tpsoft.tablesoccer.view.component.match.EditMatchDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author tomas.praslicak
 */
public class Menu extends JMenuBar {
    private JMenu gProgram = new JMenu("Program");
    private JMenu gStatistics = new JMenu("Statistiky");
    private JMenu gAdministration = new JMenu("Administrace");

    private JMenuItem iClose = new JMenuItem("Konec");
    private JMenuItem iTeam =  new JMenuItem("Tým");
    private JMenuItem iPlayers =  new JMenuItem("Hráči");
    private JMenuItem iNewMatch =  new JMenuItem("Nový zápas");
    
    public Menu() {
        gProgram.add(iClose);
        gStatistics.add(iTeam);
        gStatistics.add(iPlayers);
        gAdministration.add(iNewMatch);
        
        add(gProgram);
        add(gStatistics);
        add(gAdministration);
        
        iClose.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        iNewMatch.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                MatchWrapper match = new MatchWrapper();
                match.setDate(Calendar.getInstance());
                MatchWrapper.save(match);
                new EditMatchDialog(match).setVisible(true);
            }
        });
    }
}
