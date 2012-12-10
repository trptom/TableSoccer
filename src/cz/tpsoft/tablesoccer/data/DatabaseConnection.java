/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.tpsoft.tablesoccer.data;

/**
 *
 * @author tomas.praslicak
 */
public interface DatabaseConnection {
    public Object connect(String server, Integer port, String user, String pass,
            String database);
    public Object close();
    public Object executeQuery(String query);
    public Object executeUpdate(String query);
    public boolean isConnected();
}
