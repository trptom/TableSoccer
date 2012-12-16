/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.tpsoft.tablesoccer.view.frame;

import cz.tpsoft.tablesoccer.view.component.menu.Menu;
import cz.tpsoft.utils.components.JMaximizedFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;

/**
 *
 * @author tomas.praslicak
 */
public class Main extends JMaximizedFrame {
    public static final int SPACING = 5;
    
    private Menu menu;
    
    public Main() throws HeadlessException {
        super(new Dimension(800, 600));
        
        menu = new Menu();
        
        setTitle("Fotbálek - statistiky (v1.0)");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        setLayout(new BorderLayout(SPACING, SPACING));
        
        add(menu, BorderLayout.NORTH);
    }

    public Menu getMenu() {
        return menu;
    }
    
}
