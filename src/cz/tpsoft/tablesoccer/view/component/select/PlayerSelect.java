/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.tpsoft.tablesoccer.view.component.select;

import cz.tpsoft.tablesoccer.data.wrapper.PlayerWrapper;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 *
 * @author tomas.praslicak
 */
public class PlayerSelect extends JComboBox<PlayerWrapper> {
    private PlayerWrapper[] values;
    private DefaultComboBoxModel<PlayerWrapper> model = new DefaultComboBoxModel();

    public PlayerSelect(Iterable<PlayerWrapper> values) {
        int a = 0;
        for (PlayerWrapper player : values) {
            a++;
        }
        
        PlayerWrapper[] tmp = new PlayerWrapper[a];
        
        a = 0;
        for (PlayerWrapper player : values) {
            tmp[a] = player;
        }
        
        this.values = tmp;
        model = new DefaultComboBoxModel<>(tmp);
    }
    
    public PlayerSelect(PlayerWrapper[] values) {
        this.values = values;
        model = new DefaultComboBoxModel<>(values);
    }

    @Override
    public DefaultComboBoxModel<PlayerWrapper> getModel() {
        return model;
    }
}
