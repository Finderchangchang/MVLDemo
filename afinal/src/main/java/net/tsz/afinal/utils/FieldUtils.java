package net.tsz.afinal.utils;

/**
 * Created by LiuWeiJie on 2015/7/25 0025.
 * Email:1031066280@qq.com
 */
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.ManyToOne;
import net.tsz.afinal.annotation.sqlite.OneToMany;
import net.tsz.afinal.annotation.sqlite.Property;
import net.tsz.afinal.annotation.sqlite.Transient;
import net.tsz.afinal.utils.ClassUtils;

public class FieldUtils {
    public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public FieldUtils() {
    }

    public static Method getFieldGetMethod(Class<?> clazz, Field f) {
        String fn = f.getName();
        Method m = null;
        if(f.getType() == Boolean.TYPE) {
            m = getBooleanFieldGetMethod(clazz, fn);
        }

        if(m == null) {
            m = getFieldGetMethod(clazz, fn);
        }

        return m;
    }

    public static Method getBooleanFieldGetMethod(Class<?> clazz, String fieldName) {
        String mn = "is" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        if(isISStart(fieldName)) {
            mn = fieldName;
        }

        try {
            return clazz.getDeclaredMethod(mn, new Class[0]);
        } catch (NoSuchMethodException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public static Method getBooleanFieldSetMethod(Class<?> clazz, Field f) {
        String fn = f.getName();
        String mn = "set" + fn.substring(0, 1).toUpperCase() + fn.substring(1);
        if(isISStart(f.getName())) {
            mn = "set" + fn.substring(2, 3).toUpperCase() + fn.substring(3);
        }

        try {
            return clazz.getDeclaredMethod(mn, new Class[]{f.getType()});
        } catch (NoSuchMethodException var5) {
            var5.printStackTrace();
            return null;
        }
    }

    private static boolean isISStart(String fieldName) {
        return fieldName != null && fieldName.trim().length() != 0?fieldName.startsWith("is") && !Character.isLowerCase(fieldName.charAt(2)):false;
    }

    public static Method getFieldGetMethod(Class<?> clazz, String fieldName) {
        String mn = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

        try {
            return clazz.getDeclaredMethod(mn, new Class[0]);
        } catch (NoSuchMethodException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public static Method getFieldSetMethod(Class<?> clazz, Field f) {
        String fn = f.getName();
        String mn = "set" + fn.substring(0, 1).toUpperCase() + fn.substring(1);

        try {
            return clazz.getDeclaredMethod(mn, new Class[]{f.getType()});
        } catch (NoSuchMethodException var5) {
            return f.getType() == Boolean.TYPE?getBooleanFieldSetMethod(clazz, f):null;
        }
    }

    public static Method getFieldSetMethod(Class<?> clazz, String fieldName) {
        try {
            return getFieldSetMethod(clazz, clazz.getDeclaredField(fieldName));
        } catch (SecurityException var3) {
            var3.printStackTrace();
        } catch (NoSuchFieldException var4) {
            var4.printStackTrace();
        }

        return null;
    }

    public static Object getFieldValue(Object entity, Field field) {
        Method method = getFieldGetMethod(entity.getClass(), field);
        return invoke(entity, method);
    }

    public static Object getFieldValue(Object entity, String fieldName) {
        Method method = getFieldGetMethod(entity.getClass(), fieldName);
        return invoke(entity, method);
    }

    public static void setFieldValue(Object entity, Field field, Object value) {
        try {
            Method e = getFieldSetMethod(entity.getClass(), field);
            if(e != null) {
                e.setAccessible(true);
                Class type = field.getType();
                if(type == String.class) {
                    e.invoke(entity, new Object[]{value.toString()});
                } else if(type != Integer.TYPE && type != Integer.class) {
                    if(type != Float.TYPE && type != Float.class) {
                        if(type != Long.TYPE && type != Long.class) {
                            if(type == Date.class) {
                                e.invoke(entity, new Object[]{value == null?null:stringToDateTime(value.toString())});
                            } else {
                                e.invoke(entity, new Object[]{value});
                            }
                        } else {
//                            e.invoke(entity, new Object[]{Long.valueOf(value == null?null.longValue():Long.parseLong(value.toString()))});
                        }
                    } else {
//                        e.invoke(entity, new Object[]{Float.valueOf(value == null?null.floatValue():Float.parseFloat(value.toString()))});
                    }
                } else {
//                    e.invoke(entity, new Object[]{Integer.valueOf(value == null?null.intValue():Integer.parseInt(value.toString()))});
                }
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

    public static Field getFieldByColumnName(Class<?> clazz, String columnName) {
        Field field = null;
        if(columnName != null) {
            Field[] fields = clazz.getDeclaredFields();
            if(fields != null && fields.length > 0) {
                if(columnName.equals(ClassUtils.getPrimaryKeyColumn(clazz))) {
                    field = ClassUtils.getPrimaryKeyField(clazz);
                }

                if(field == null) {
                    Field[] var7 = fields;
                    int var6 = fields.length;

                    for(int var5 = 0; var5 < var6; ++var5) {
                        Field f = var7[var5];
                        Property property = (Property)f.getAnnotation(Property.class);
                        if(property != null && columnName.equals(property.column())) {
                            field = f;
                            break;
                        }

                        ManyToOne manyToOne = (ManyToOne)f.getAnnotation(ManyToOne.class);
                        if(manyToOne != null && manyToOne.column().trim().length() != 0) {
                            field = f;
                            break;
                        }
                    }
                }

                if(field == null) {
                    field = getFieldByName(clazz, columnName);
                }
            }
        }

        return field;
    }

    public static Field getFieldByName(Class<?> clazz, String fieldName) {
        Field field = null;
        if(fieldName != null) {
            try {
                field = clazz.getDeclaredField(fieldName);
            } catch (SecurityException var4) {
                var4.printStackTrace();
            } catch (NoSuchFieldException var5) {
                var5.printStackTrace();
            }
        }

        return field;
    }

    public static String getColumnByField(Field field) {
        Property property = (Property)field.getAnnotation(Property.class);
        if(property != null && property.column().trim().length() != 0) {
            return property.column();
        } else {
            ManyToOne manyToOne = (ManyToOne)field.getAnnotation(ManyToOne.class);
            if(manyToOne != null && manyToOne.column().trim().length() != 0) {
                return manyToOne.column();
            } else {
                OneToMany oneToMany = (OneToMany)field.getAnnotation(OneToMany.class);
                if(oneToMany != null && oneToMany.manyColumn() != null && oneToMany.manyColumn().trim().length() != 0) {
                    return oneToMany.manyColumn();
                } else {
                    Id id = (Id)field.getAnnotation(Id.class);
                    return id != null && id.column().trim().length() != 0?id.column():field.getName();
                }
            }
        }
    }

    public static String getPropertyDefaultValue(Field field) {
        Property property = (Property)field.getAnnotation(Property.class);
        return property != null && property.defaultValue().trim().length() != 0?property.defaultValue():null;
    }

    public static boolean isTransient(Field f) {
        return f.getAnnotation(Transient.class) != null;
    }

    private static Object invoke(Object obj, Method method) {
        if(obj != null && method != null) {
            try {
                return method.invoke(obj, new Object[0]);
            } catch (IllegalArgumentException var3) {
                var3.printStackTrace();
            } catch (IllegalAccessException var4) {
                var4.printStackTrace();
            } catch (InvocationTargetException var5) {
                var5.printStackTrace();
            }

            return null;
        } else {
            return null;
        }
    }

    public static boolean isManyToOne(Field field) {
        return field.getAnnotation(ManyToOne.class) != null;
    }

    public static boolean isOneToMany(Field field) {
        return field.getAnnotation(OneToMany.class) != null;
    }

    public static boolean isManyToOneOrOneToMany(Field field) {
        return isManyToOne(field) || isOneToMany(field);
    }

    public static boolean isBaseDateType(Field field) {
        Class clazz = field.getType();
        return clazz.equals(String.class) || clazz.equals(Integer.class) || clazz.equals(Byte.class) || clazz.equals(Long.class) || clazz.equals(Double.class) || clazz.equals(Float.class) || clazz.equals(Character.class) || clazz.equals(Short.class) || clazz.equals(Boolean.class) || clazz.equals(Date.class) || clazz.equals(Date.class) || clazz.equals(java.sql.Date.class) || clazz.isPrimitive();
    }

    public static Date stringToDateTime(String strDate) {
        if(strDate != null) {
            try {
                return SDF.parse(strDate);
            } catch (ParseException var2) {
                var2.printStackTrace();
            }
        }

        return null;
    }
}
