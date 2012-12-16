/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.tpsoft.tablesoccer.view.component.match;

import cz.tpsoft.tablesoccer.data.model.Game;
import cz.tpsoft.tablesoccer.data.wrapper.GameWrapper;
import cz.tpsoft.tablesoccer.data.wrapper.MatchWrapper;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author tomas.praslicak
 */
public class EditMatchDialog extends JDialog {
    private MatchWrapper match;
    private JScrollPane sp;
    private GamesPanel gamesPanel = new GamesPanel();
    private JPanel buttons = new JPanel(new GridLayout(1, 0, 5, 5));
    private JButton newGame = new JButton("Nový zápas");
    private JButton create = new JButton("Vytvořit");
    
    public EditMatchDialog(MatchWrapper match) {
        this.match = match;
        
        setModal(true);
        setSize(400, 300);
        
        setLayout(new BorderLayout(5, 5));
        add(new JScrollPane(gamesPanel), BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
        
        buttons.add(newGame);
        buttons.add(create);
        
        buttons.setBorder(new EmptyBorder(0, 5, 5, 5));
        
        newGame.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                gamesPanel.addNewGame(EditMatchDialog.this.match);
                validate();
            }
        });
    }

    public MatchWrapper getMatch() {
        return match;
    }
    
    
}
