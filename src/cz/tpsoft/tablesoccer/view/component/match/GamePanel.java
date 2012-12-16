/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.tpsoft.tablesoccer.view.component.match;

import cz.tpsoft.tablesoccer.data.wrapper.GameWrapper;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 *
 * @author tomas.praslicak
 */
public class GamePanel extends JPanel implements MouseListener {
    public static final Border OUT_BORDER = new EmptyBorder(1, 1, 1, 1);
    public static final Border OVER_BORDER = new LineBorder(Color.BLACK);
    
    private boolean over = false;
    private GameWrapper game;
    private JLabel gameType = new JLabel();
    private JPanel detail = new JPanel(new GridLayout(3, 1));
    private JLabel home = new JLabel();
    private JLabel score = new JLabel("-:-");
    private JLabel away = new JLabel();

    public GamePanel(GameWrapper game) {
        this.game = game;
        
        setLayout(new BorderLayout(5, 5));
        
        add(gameType, BorderLayout.WEST);
        add(detail, BorderLayout.CENTER);
        detail.add(home);
        detail.add(score);
        detail.add(away);
        
        gameType.setPreferredSize(new Dimension(50, 0));
        gameType.setHorizontalAlignment(SwingConstants.CENTER);
        gameType.setVerticalAlignment(SwingConstants.CENTER);
        gameType.setBackground(Color.lightGray);
        
        home.setHorizontalAlignment(SwingConstants.CENTER);
        score.setHorizontalAlignment(SwingConstants.CENTER);
        away.setHorizontalAlignment(SwingConstants.CENTER);
        
        update();
        
        addMouseListener(this);
    }

    public GameWrapper getGame() {
        return game;
    }
    
    public void update() {
        gameType.setText(game.getType().toString());
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
        setBorder(OVER_BORDER);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setBorder(OUT_BORDER);
    }
}
