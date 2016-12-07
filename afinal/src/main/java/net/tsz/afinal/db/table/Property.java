package net.tsz.afinal.db.table;

/**
 * Created by LiuWeiJie on 2015/7/25 0025.
 * Email:1031066280@qq.com
 */

import net.tsz.afinal.utils.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

public class Property {
    private String fieldName;
    private String column;
    private String defaultValue;
    private Class<?> dataType;
    private Field field;
    private Method get;
    private Method set;

    public Property() {
    }

    public void setValue(Object receiver, Object value) {
        if (this.set != null && value != null) {
            try {
                if (this.dataType == String.class) {
                    this.set.invoke(receiver, new Object[]{value.toString()});
                } else if (this.dataType != Integer.TYPE && this.dataType != Integer.class) {
                    if (this.dataType != Float.TYPE && this.dataType != Float.class) {
                        if (this.dataType != Double.TYPE && this.dataType != Double.class) {
                            if (this.dataType != Long.TYPE && this.dataType != Long.class) {
                                if (this.dataType != Date.class && this.dataType != java.sql.Date.class) {
                                    if (this.dataType != Boolean.TYPE && this.dataType != Boolean.class) {
                                        this.set.invoke(receiver, new Object[]{value});
                                    } else {
                                        this.set.invoke(receiver, new Object[]{Boolean.valueOf(value == null ? null : "1".equals(value.toString()))});
                                    }
                                } else {
                                    this.set.invoke(receiver, new Object[]{value == null ? null : FieldUtils.stringToDateTime(value.toString())});
                                }
                            } else {
                                this.set.invoke(receiver, new Object[]{Long.valueOf(value == null ? null : Long.parseLong(value.toString()))});
                            }
                        } else {
                            this.set.invoke(receiver, new Object[]{Double.valueOf(value == null ? null : Double.parseDouble(value.toString()))});
                        }
                    } else {
                        this.set.invoke(receiver, new Object[]{Float.valueOf(value == null ? null : Float.parseFloat(value.toString()))});
                    }
                } else {
                    this.set.invoke(receiver, new Object[]{Integer.valueOf(value == null ? null : Integer.parseInt(value.toString()))});
                }
            } catch (Exception var5) {
                var5.printStackTrace();
            }
        } else {
            try {
                this.field.setAccessible(true);
                this.field.set(receiver, value);
            } catch (Exception var4) {
                var4.printStackTrace();
            }
        }

    }

    public <T> T getValue(Object obj) {
        if (obj != null && this.get != null) {
            try {
                return (T) this.get.invoke(obj, new Object[0]);
            } catch (Exception var3) {
                var3.printStackTrace();
            }
        }

        return null;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getColumn() {
        return this.column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Class<?> getDataType() {
        return this.dataType;
    }

    public void setDataType(Class<?> dataType) {
        this.dataType = dataType;
    }

    public Method getGet() {
        return this.get;
    }

    public void setGet(Method get) {
        this.get = get;
    }

    public Method getSet() {
        return this.set;
    }

    public void setSet(Method set) {
        this.set = set;
    }

    public Field getField() {
        return this.field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}

