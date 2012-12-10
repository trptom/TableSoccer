/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.tpsoft.tablesoccer.model;

import cz.tpsoft.tablesoccer.data.SQLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author tomas.praslicak
 */
public abstract class Model {
    public static class Column<AcceptableValueType> {
        public static enum Type {
            NUMBER {
                @Override
                public String getValueSQLString(Object value) {
                    return value == null ? "NULL" : value.toString();
                }
            },
            STRING {
                @Override
                public String getValueSQLString(Object value) {
                    return value == null ? "NULL" :
                            "\"" + value.toString().replace("\"", "\\\"") + "\"";
                }
            };
            
            public abstract String getValueSQLString(Object value);
        }
        
        private String name;
        private Type type;
        private boolean nullable;

        public Column(String name, Type type, boolean nullable) {
            this.name = name;
            this.type = type;
            this.nullable = nullable;
        }

        public String getName() {
            return name;
        }

        public Type getType() {
            return type;
        }

        public String getUpdateSQL(AcceptableValueType value) {
            if (!nullable && value == null) {
                throw new IllegalArgumentException(name + " cant be null");
            }
            return getName() + " = " + getType().getValueSQLString(value);
        }
    }
    
    public static class ColVal<E> {
        private Column<E> column;
        private E value;

        public ColVal(Column<E> column, E value) {
            this.column = column;
            this.value = value;
        }

        public Column<E> getColumn() {
            return column;
        }

        public E getValue() {
            return value;
        }
    }
    
    public static class SortCriteria<E> {
        private Column<E> column;
        private boolean asc;

        public SortCriteria(Column<E> column, boolean asc) {
            this.column = column;
            this.asc = asc;
        }

        public Column<E> getColumn() {
            return column;
        }

        public boolean isAsc() {
            return asc;
        }
    }
    
    public static String mergeColumns(SortCriteria[] sortCriteria) {
        StringBuilder ret = new StringBuilder();
        
        for (SortCriteria item : sortCriteria) {
            if (ret.length() > 0) {
                ret.append(", ");
            }
            ret.append(item.getColumn().getName());
            ret.append(item.isAsc() ? " ASC" : " DESC");
        }
        
        return ret.toString();
    }
    
    public static ResultSet processSelect(SQLConnection connection,
            String tableName, String whereCause, SortCriteria[] sortCriteria,
            Integer limit) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM ").append(tableName);
        if (whereCause != null && !whereCause.equals("")) {
            query.append(" WHERE ").append(whereCause);
        }
        if (sortCriteria != null && sortCriteria.length > 0) {
            query.append(" ORDER BY ").append(mergeColumns(sortCriteria));
        }
        if (limit != null && limit > 0) {
            query.append(" LIMIT ").append(limit);
        }
        return connection.executeQuery(query.toString());
    }
    
    public static final Model.Column<Integer> COL_ID = new Column<>("id", Model.Column.Type.NUMBER, false);
    public static final LinkedList<Column> COLUMNS = new LinkedList<>();
    
    protected abstract void parseResult(ResultSet rs) throws SQLException;
    /**
     * Vypocita a vrati pole dvojic sloupec x hodnota, aby bylo mozne provadet
     * automaticka ukladani a updaty.
     * @return pole dvojic sloupec x hodnota.
     */
    protected abstract ColVal[] getColVal();
    
    private Integer id;

    public Model(ResultSet from) {
        try {
            this.id = from.getInt("id");
            parseResult(from);
        } catch (SQLException ex) {
            throw new RuntimeException("chyba pri zpracovavani SQL vysledku", ex);
        }
    }
    
    public Model(int id, SQLConnection connection) {
        if (id < 0) {
            throw new IllegalArgumentException("id musi byt >= 0 (" + id + ")");
        }
        this.id = id;
        ResultSet rs = connection.executeQuery("SELECT * FROM " + getTableName() + " WHERE id=" + id);//nacteni
        try {
            if (rs.next()) {
                parseResult(rs);
            } else {
                throw new SQLException("zaznam s id " + id + " neexistuje");
            }
        } catch (SQLException ex) {
            throw new RuntimeException("chyba pri zpracovavani SQL vyslecku", ex);
        }
    }

    public Model() {
    }
    
    public int getId() {
        return id;
    }
    
    public String getTableName() {
        return this.getClass().getSimpleName();
    }
    
    public String getInsertSQL() {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbCols = new StringBuilder();
        StringBuilder sbVals = new StringBuilder();
        
        ColVal[] cv = getColVal();
        for (ColVal item : cv) {
            if (item != cv[0]) {
                sbCols.append(", ");
                sbVals.append(", ");
            }
            sbCols.append(item.getColumn().getName());
            sbVals.append(item.getColumn().getType().getValueSQLString(item.getValue()));
        }
        
        sb.append("INSERT INTO ").append(getTableName()).append(" (");
        sb.append(sbCols);
        sb.append(") VALUES (");
        sb.append(sbVals);
        sb.append(")");
        
        return sb.toString();
    }
    public String getUpdateSQL() {
        if (id == null) {
            throw new RuntimeException("unable to generate update for non-saved entity (without id)");
        }
        
        ColVal[] cv = getColVal();
        
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ").append(getTableName()).append(" SET ");
        for (ColVal item : cv) {
            if (item != cv[0]) {
                sb.append(", ");
            }
            sb.append(item.getColumn().getUpdateSQL(item.getValue()));
        }
        sb.append(" WHERE ").append(COL_ID.getUpdateSQL(this.id));
        return sb.toString();
    }
    
    /**
     * Insert or update.
     */
    public String getSaveSQL() {
        if (id == null) {
            return getInsertSQL();
        } else {
            return getUpdateSQL();
        }
    }
    
    public boolean save(SQLConnection connection) {
        if (id == null) {
            Integer ret = connection.executeUpdate(getInsertSQL());
            try {
                ResultSet rs = connection.getStatement().getGeneratedKeys();
                this.id = rs.getInt(1);
            } catch (SQLException ex) {
                throw new RuntimeException("nepodarilo se zjistit pridane id", ex);
            }
            return ret != null;
//            return insert(connection);
        } else {
            return connection.executeUpdate(getUpdateSQL()) != null;
//            return update(connection);
        }
    }
}
