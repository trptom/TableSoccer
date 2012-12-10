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
public class MySQL extends SQLConnection {
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
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    server + (port != null ? (":" + port) : "") + "/" + database,
                    user,
                    pass);
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
