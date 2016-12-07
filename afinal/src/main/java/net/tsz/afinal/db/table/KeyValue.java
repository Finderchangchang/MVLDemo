package net.tsz.afinal.db.table;

/**
 * Created by LiuWeiJie on 2015/7/25 0025.
 * Email:1031066280@qq.com
 */
import java.util.Date;
import net.tsz.afinal.utils.FieldUtils;

public class KeyValue {
    private String key;
    private Object value;

    public KeyValue(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public KeyValue() {
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return !(this.value instanceof Date) && !(this.value instanceof java.sql.Date)?this.value:FieldUtils.SDF.format(this.value);
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
