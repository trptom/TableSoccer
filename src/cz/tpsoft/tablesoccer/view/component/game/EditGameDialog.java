/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.tpsoft.tablesoccer.view.component.game;

import cz.tpsoft.tablesoccer.data.wrapper.GameWrapper;
import cz.tpsoft.tablesoccer.data.wrapper.MatchWrapper;
import cz.tpsoft.tablesoccer.data.wrapper.PlayerWrapper;
import cz.tpsoft.tablesoccer.view.component.select.PlayerSelect;
import java.util.LinkedList;
import javax.swing.JDialog;
import org.hibernate.Hibernate;

/**
 *
 * @author tomas.praslicak
 */
public class EditGameDialog extends JDialog {
    private LinkedList<PlayerWrapper> homePlayers;
    private LinkedList<PlayerWrapper> awayPlayers;
    private GameWrapper game;
    private MatchWrapper match;
    private PlayerSelect[] homePlayerSelect = new PlayerSelect[4];
    private PlayerSelect[] awayPlayerSelect = new PlayerSelect[4];
    
    public EditGameDialog(GameWrapper game) {
        this.game = game;
        this.match = game.getMatch();
        this.homePlayers = /* TODO*/ null;
        this.awayPlayers = /* TODO*/ null;
        
//        this.match.getMatch().get
        
        setSize(400, 300);
        setModal(true);
    }
}
