/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.tpsoft.tablesoccer.view.component.match;

import cz.tpsoft.tablesoccer.data.wrapper.GameWrapper;
import cz.tpsoft.tablesoccer.data.wrapper.MatchWrapper;
import cz.tpsoft.tablesoccer.enums.GameType;
import cz.tpsoft.tablesoccer.view.component.game.EditGameDialog;
import cz.tpsoft.utils.layoutmanager.ComponentManager;
import java.awt.GridLayout;
import java.util.LinkedList;
import javax.swing.JPanel;

/**
 *
 * @author tomas.praslicak
 */
public class GamesPanel extends JPanel {
    private LinkedList<GamePanel> gamePanels = new LinkedList<>();

    public GamesPanel() {
        setLayout(new GridLayout(1, 1));
        
        reload();
    }

    public LinkedList<GamePanel> getGamePanels() {
        return gamePanels;
    }
    
    public final void reload() {
        removeAll();
        
        GamePanel[] components = new GamePanel[gamePanels.size()];
        int a = 0;
        for (GamePanel gamePanel : gamePanels) {
            components[a] = gamePanel;
            components[a].update();
            a++;
        }
        add(ComponentManager.getMergedComponents(components, ComponentManager.Alignment.TOP, 0));
        
        validate();
    }
    
    public void addNewGame(MatchWrapper match) {
        GameWrapper game = new GameWrapper();
        game.setType(GameType.DOUBLE);
        game.setNumber(gamePanels.size());
        game.setMatch(match);
        
        gamePanels.addLast(new GamePanel(game));
        reload();
        
        new EditGameDialog(game).setVisible(true);
    }
}
