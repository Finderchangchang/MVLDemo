package net.tsz.afinal.db.sqlite;

/**
 * Created by LiuWeiJie on 2015/7/25 0025.
 * Email:1031066280@qq.com
 */
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import net.tsz.afinal.db.sqlite.ManyToOneLazyLoader;
import net.tsz.afinal.db.sqlite.SqlInfo;
import net.tsz.afinal.db.table.Id;
import net.tsz.afinal.db.table.KeyValue;
import net.tsz.afinal.db.table.ManyToOne;
import net.tsz.afinal.db.table.Property;
import net.tsz.afinal.db.table.TableInfo;
import net.tsz.afinal.exception.DbException;

public class SqlBuilder {
    public SqlBuilder() {
    }

    public static SqlInfo buildInsertSql(Object entity) {
        List keyValueList = getSaveKeyValueListByEntity(entity);
        StringBuffer strSQL = new StringBuffer();
        SqlInfo sqlInfo = null;
        if(keyValueList != null && keyValueList.size() > 0) {
            sqlInfo = new SqlInfo();
            strSQL.append("INSERT INTO ");
            strSQL.append(TableInfo.get(entity.getClass()).getTableName());
            strSQL.append(" (");
            Iterator i = keyValueList.iterator();

            while(i.hasNext()) {
                KeyValue length = (KeyValue)i.next();
                strSQL.append(length.getKey()).append(",");
                sqlInfo.addValue(length.getValue());
            }

            strSQL.deleteCharAt(strSQL.length() - 1);
            strSQL.append(") VALUES ( ");
            int var6 = keyValueList.size();

            for(int var7 = 0; var7 < var6; ++var7) {
                strSQL.append("?,");
            }

            strSQL.deleteCharAt(strSQL.length() - 1);
            strSQL.append(")");
            sqlInfo.setSql(strSQL.toString());
        }

        return sqlInfo;
    }

    public static List<KeyValue> getSaveKeyValueListByEntity(Object entity) {
        ArrayList keyValueList = new ArrayList();
        TableInfo table = TableInfo.get(entity.getClass());
        Object idvalue = table.getId().getValue(entity);
        if(!(idvalue instanceof Integer) && idvalue instanceof String && idvalue != null) {
            KeyValue propertys = new KeyValue(table.getId().getColumn(), idvalue);
            keyValueList.add(propertys);
        }

        Collection propertys1 = table.propertyMap.values();
        Iterator many = propertys1.iterator();

        while(many.hasNext()) {
            Property manyToOnes = (Property)many.next();
            KeyValue kv = property2KeyValue(manyToOnes, entity);
            if(kv != null) {
                keyValueList.add(kv);
            }
        }

        Collection manyToOnes1 = table.manyToOneMap.values();
        Iterator kv2 = manyToOnes1.iterator();

        while(kv2.hasNext()) {
            ManyToOne many1 = (ManyToOne)kv2.next();
            KeyValue kv1 = manyToOne2KeyValue(many1, entity);
            if(kv1 != null) {
                keyValueList.add(kv1);
            }
        }

        return keyValueList;
    }

    private static String getDeleteSqlBytableName(String tableName) {
        return "DELETE FROM " + tableName;
    }

    public static SqlInfo buildDeleteSql(Object entity) {
        TableInfo table = TableInfo.get(entity.getClass());
        Id id = table.getId();
        Object idvalue = id.getValue(entity);
        if(idvalue == null) {
            throw new DbException("getDeleteSQL:" + entity.getClass() + " id value is null");
        } else {
            StringBuffer strSQL = new StringBuffer(getDeleteSqlBytableName(table.getTableName()));
            strSQL.append(" WHERE ").append(id.getColumn()).append("=?");
            SqlInfo sqlInfo = new SqlInfo();
            sqlInfo.setSql(strSQL.toString());
            sqlInfo.addValue(idvalue);
            return sqlInfo;
        }
    }

    public static SqlInfo buildDeleteSql(Class<?> clazz, Object idValue) {
        TableInfo table = TableInfo.get(clazz);
        Id id = table.getId();
        if(idValue == null) {
            throw new DbException("getDeleteSQL:idValue is null");
        } else {
            StringBuffer strSQL = new StringBuffer(getDeleteSqlBytableName(table.getTableName()));
            strSQL.append(" WHERE ").append(id.getColumn()).append("=?");
            SqlInfo sqlInfo = new SqlInfo();
            sqlInfo.setSql(strSQL.toString());
            sqlInfo.addValue(idValue);
            return sqlInfo;
        }
    }

    public static String buildDeleteSql(Class<?> clazz, String strWhere) {
        TableInfo table = TableInfo.get(clazz);
        StringBuffer strSQL = new StringBuffer(getDeleteSqlBytableName(table.getTableName()));
        if(!TextUtils.isEmpty(strWhere)) {
            strSQL.append(" WHERE ");
            strSQL.append(strWhere);
        }

        return strSQL.toString();
    }

    private static String getSelectSqlByTableName(String tableName) {
        return "SELECT * FROM " + tableName;
    }

    public static String getSelectSQL(Class<?> clazz, Object idValue) {
        TableInfo table = TableInfo.get(clazz);
        StringBuffer strSQL = new StringBuffer(getSelectSqlByTableName(table.getTableName()));
        strSQL.append(" WHERE ");
        strSQL.append(getPropertyStrSql(table.getId().getColumn(), idValue));
        return strSQL.toString();
    }

    public static SqlInfo getSelectSqlAsSqlInfo(Class<?> clazz, Object idValue) {
        TableInfo table = TableInfo.get(clazz);
        StringBuffer strSQL = new StringBuffer(getSelectSqlByTableName(table.getTableName()));
        strSQL.append(" WHERE ").append(table.getId().getColumn()).append("=?");
        SqlInfo sqlInfo = new SqlInfo();
        sqlInfo.setSql(strSQL.toString());
        sqlInfo.addValue(idValue);
        return sqlInfo;
    }

    public static String getSelectSQL(Class<?> clazz) {
        return getSelectSqlByTableName(TableInfo.get(clazz).getTableName());
    }

    public static String getSelectSQLByWhere(Class<?> clazz, String strWhere) {
        TableInfo table = TableInfo.get(clazz);
        StringBuffer strSQL = new StringBuffer(getSelectSqlByTableName(table.getTableName()));
        if(!TextUtils.isEmpty(strWhere)) {
            strSQL.append(" WHERE ").append(strWhere);
        }

        return strSQL.toString();
    }

    public static SqlInfo getUpdateSqlAsSqlInfo(Object entity) {
        TableInfo table = TableInfo.get(entity.getClass());
        Object idvalue = table.getId().getValue(entity);
        if(idvalue == null) {
            throw new DbException("this entity[" + entity.getClass() + "]\'s id value is null");
        } else {
            ArrayList keyValueList = new ArrayList();
            Collection propertys = table.propertyMap.values();
            Iterator sqlInfo = propertys.iterator();

            while(sqlInfo.hasNext()) {
                Property manyToOnes = (Property)sqlInfo.next();
                KeyValue strSQL = property2KeyValue(manyToOnes, entity);
                if(strSQL != null) {
                    keyValueList.add(strSQL);
                }
            }

            Collection manyToOnes1 = table.manyToOneMap.values();
            Iterator strSQL1 = manyToOnes1.iterator();

            KeyValue kv;
            while(strSQL1.hasNext()) {
                ManyToOne sqlInfo1 = (ManyToOne)strSQL1.next();
                kv = manyToOne2KeyValue(sqlInfo1, entity);
                if(kv != null) {
                    keyValueList.add(kv);
                }
            }

            if(keyValueList != null && keyValueList.size() != 0) {
                SqlInfo sqlInfo2 = new SqlInfo();
                StringBuffer strSQL2 = new StringBuffer("UPDATE ");
                strSQL2.append(table.getTableName());
                strSQL2.append(" SET ");
                Iterator var9 = keyValueList.iterator();

                while(var9.hasNext()) {
                    kv = (KeyValue)var9.next();
                    strSQL2.append(kv.getKey()).append("=?,");
                    sqlInfo2.addValue(kv.getValue());
                }

                strSQL2.deleteCharAt(strSQL2.length() - 1);
                strSQL2.append(" WHERE ").append(table.getId().getColumn()).append("=?");
                sqlInfo2.addValue(idvalue);
                sqlInfo2.setSql(strSQL2.toString());
                return sqlInfo2;
            } else {
                return null;
            }
        }
    }

    public static SqlInfo getUpdateSqlAsSqlInfo(Object entity, String strWhere) {
        TableInfo table = TableInfo.get(entity.getClass());
        ArrayList keyValueList = new ArrayList();
        Collection propertys = table.propertyMap.values();
        Iterator sqlInfo = propertys.iterator();

        while(sqlInfo.hasNext()) {
            Property manyToOnes = (Property)sqlInfo.next();
            KeyValue strSQL = property2KeyValue(manyToOnes, entity);
            if(strSQL != null) {
                keyValueList.add(strSQL);
            }
        }

        Collection manyToOnes1 = table.manyToOneMap.values();
        Iterator strSQL1 = manyToOnes1.iterator();

        KeyValue kv;
        while(strSQL1.hasNext()) {
            ManyToOne sqlInfo1 = (ManyToOne)strSQL1.next();
            kv = manyToOne2KeyValue(sqlInfo1, entity);
            if(kv != null) {
                keyValueList.add(kv);
            }
        }

        if(keyValueList != null && keyValueList.size() != 0) {
            SqlInfo sqlInfo2 = new SqlInfo();
            StringBuffer strSQL2 = new StringBuffer("UPDATE ");
            strSQL2.append(table.getTableName());
            strSQL2.append(" SET ");
            Iterator var9 = keyValueList.iterator();

            while(var9.hasNext()) {
                kv = (KeyValue)var9.next();
                strSQL2.append(kv.getKey()).append("=?,");
                sqlInfo2.addValue(kv.getValue());
            }

            strSQL2.deleteCharAt(strSQL2.length() - 1);
            if(!TextUtils.isEmpty(strWhere)) {
                strSQL2.append(" WHERE ").append(strWhere);
            }

            sqlInfo2.setSql(strSQL2.toString());
            return sqlInfo2;
        } else {
            throw new DbException("this entity[" + entity.getClass() + "] has no property");
        }
    }

    public static String getCreatTableSQL(Class<?> clazz) {
        TableInfo table = TableInfo.get(clazz);
        Id id = table.getId();
        StringBuffer strSQL = new StringBuffer();
        strSQL.append("CREATE TABLE IF NOT EXISTS ");
        strSQL.append(table.getTableName());
        strSQL.append(" ( ");
        Class primaryClazz = id.getDataType();
        if(primaryClazz != Integer.TYPE && primaryClazz != Integer.class && primaryClazz != Long.TYPE && primaryClazz != Long.class) {
            strSQL.append(id.getColumn()).append(" TEXT PRIMARY KEY,");
        } else {
            strSQL.append(id.getColumn()).append(" INTEGER PRIMARY KEY AUTOINCREMENT,");
        }

        Collection propertys = table.propertyMap.values();

        for(Iterator manyToOne = propertys.iterator(); manyToOne.hasNext(); strSQL.append(",")) {
            Property manyToOnes = (Property)manyToOne.next();
            strSQL.append(manyToOnes.getColumn());
            Class dataType = manyToOnes.getDataType();
            if(dataType != Integer.TYPE && dataType != Integer.class && dataType != Long.TYPE && dataType != Long.class) {
                if(dataType != Float.TYPE && dataType != Float.class && dataType != Double.TYPE && dataType != Double.class) {
                    if(dataType == Boolean.TYPE || dataType == Boolean.class) {
                        strSQL.append(" NUMERIC");
                    }
                } else {
                    strSQL.append(" REAL");
                }
            } else {
                strSQL.append(" INTEGER");
            }
        }

        Collection manyToOnes1 = table.manyToOneMap.values();
        Iterator dataType1 = manyToOnes1.iterator();

        while(dataType1.hasNext()) {
            ManyToOne manyToOne1 = (ManyToOne)dataType1.next();
            strSQL.append(manyToOne1.getColumn()).append(" INTEGER").append(",");
        }

        strSQL.deleteCharAt(strSQL.length() - 1);
        strSQL.append(" )");
        return strSQL.toString();
    }

    private static String getPropertyStrSql(String key, Object value) {
        StringBuffer sbSQL = (new StringBuffer(key)).append("=");
        if(!(value instanceof String) && !(value instanceof Date) && !(value instanceof java.sql.Date)) {
            sbSQL.append(value);
        } else {
            sbSQL.append("\'").append(value).append("\'");
        }

        return sbSQL.toString();
    }

    private static KeyValue property2KeyValue(Property property, Object entity) {
        KeyValue kv = null;
        String pcolumn = property.getColumn();
        Object value = property.getValue(entity);
        if(value != null) {
            kv = new KeyValue(pcolumn, value);
        } else if(property.getDefaultValue() != null && property.getDefaultValue().trim().length() != 0) {
            kv = new KeyValue(pcolumn, property.getDefaultValue());
        }

        return kv;
    }

    private static KeyValue manyToOne2KeyValue(ManyToOne many, Object entity) {
        KeyValue kv = null;
        String manycolumn = many.getColumn();
        Object manyobject = many.getValue(entity);
        if(manyobject != null) {
            Object manyvalue;
            if(manyobject.getClass() == ManyToOneLazyLoader.class) {
                manyvalue = TableInfo.get(many.getManyClass()).getId().getValue(((ManyToOneLazyLoader)manyobject).get());
            } else {
                manyvalue = TableInfo.get(manyobject.getClass()).getId().getValue(manyobject);
            }

            if(manycolumn != null && manyvalue != null) {
                kv = new KeyValue(manycolumn, manyvalue);
            }
        }

        return kv;
    }
}
