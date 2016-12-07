package net.tsz.afinal.db.sqlite;

/**
 * Created by LiuWeiJie on 2015/7/25 0025.
 * Email:1031066280@qq.com
 */
import java.util.HashMap;

public class DbModel {
    private HashMap<String, Object> dataMap = new HashMap();

    public DbModel() {
    }

    public Object get(String column) {
        return this.dataMap.get(column);
    }

    public String getString(String column) {
        return String.valueOf(this.get(column));
    }

    public int getInt(String column) {
        return Integer.valueOf(this.getString(column)).intValue();
    }

    public boolean getBoolean(String column) {
        return Boolean.valueOf(this.getString(column)).booleanValue();
    }

    public double getDouble(String column) {
        return Double.valueOf(this.getString(column)).doubleValue();
    }

    public float getFloat(String column) {
        return Float.valueOf(this.getString(column)).floatValue();
    }

    public long getLong(String column) {
        return Long.valueOf(this.getString(column)).longValue();
    }

    public void set(String key, Object value) {
        this.dataMap.put(key, value);
    }

    public HashMap<String, Object> getDataMap() {
        return this.dataMap;
    }
}

