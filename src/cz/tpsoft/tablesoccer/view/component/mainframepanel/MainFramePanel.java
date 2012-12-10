/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.tpsoft.tablesoccer.view.component.mainframepanel;

import java.awt.GridLayout;
import java.awt.LayoutManager;
import javax.swing.JPanel;

/**
 *
 * @author tomas.praslicak
 */
public class MainFramePanel extends JPanel {
    public static final int CELLS_SPACING = 10;

    private PlayersSummaryTable playersSummaryTable = new PlayersSummaryTable();
    
    public MainFramePanel() {
        setLayout(new GridLayout(2, 2, CELLS_SPACING, CELLS_SPACING));
    }
}
