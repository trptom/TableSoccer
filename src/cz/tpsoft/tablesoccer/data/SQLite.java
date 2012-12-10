/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.tpsoft.tablesoccer.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author tomas.praslicak
 */
public class SQLite extends SQLConnection {
    public Boolean connect(String folder, String file) {
        return connect(folder, null, null, null, file);
    }
    
    /**
     * @param server slozka s DB (bez koncoveho lomitka).
     * @param port neni pouzito (vzdy se bere jako null, nezavisle na posilane
     * hodnote).
     * @param user neni pouzito (vzdy se bere jako null, nezavisle na posilane
     * hodnote).
     * @param pass neni pouzito (vzdy se bere jako null, nezavisle na posilane
     * hodnote).
     * @param database nazev DB souboru.
     * @return
     * @deprecated obsahuje parametry (port, user, pass), ktere nejsou
     * pouzivany, proto je lepsi pouzit alternativni connector.
     * @see SQLite#connect(java.lang.String, java.lang.String) 
     */
    @Deprecated
    @Override
    public Boolean connect(String server, Integer port, String user, String pass,
            String database) {
        if (connection != null) {
            throw new UnsupportedOperationException("connected yet: connection != null");
        }
        if (statement != null) {
            throw new UnsupportedOperationException("connected yet: statement != null");
        }
        
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:"+server+"\\"+database);
            System.out.println("jdbc:sqlite:"+server+"\\"+database);
            statement = connection.createStatement();
        } catch (ClassNotFoundException|SQLException ex) {
            ex.printStackTrace(System.out);
            return false;
        }
        return true;
    }

    @Override
    public Boolean close() {
        if (!isConnected()) {
            throw new UnsupportedOperationException("not connected");
        }
        
        boolean ret = true;
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            ret = false;
        }
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            ret = false;
        }
        statement = null;
        connection = null;
        return ret;
    }

    @Override
    public ResultSet executeQuery(String query) {
        if (!isConnected()) {
            throw new UnsupportedOperationException("not connected");
        }
        
        try {
            return statement.executeQuery(query);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            return null;
        }
    }
    
    @Override
    public Integer executeUpdate(String query) {
        if (!isConnected()) {
            throw new UnsupportedOperationException("not connected");
        }
        
        try {
            return statement.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            return null;
        }
    }

    @Override
    public boolean isConnected() {
        return connection != null && statement != null;
    }
}
