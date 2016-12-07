package net.tsz.afinal.utils;

/**
 * Created by LiuWeiJie on 2015/7/25 0025.
 * Email:1031066280@qq.com
 */
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;
import net.tsz.afinal.db.sqlite.ManyToOneLazyLoader;
import net.tsz.afinal.db.table.ManyToOne;
import net.tsz.afinal.db.table.OneToMany;
import net.tsz.afinal.db.table.Property;
import net.tsz.afinal.exception.DbException;
import net.tsz.afinal.utils.FieldUtils;

public class ClassUtils {
    public ClassUtils() {
    }

    public static String getTableName(Class<?> clazz) {
        Table table = (Table)clazz.getAnnotation(Table.class);
        return table != null && table.name().trim().length() != 0?table.name():clazz.getName().replace('.', '_');
    }

    public static Object getPrimaryKeyValue(Object entity) {
        return FieldUtils.getFieldValue(entity, getPrimaryKeyField(entity.getClass()));
    }

    public static String getPrimaryKeyColumn(Class<?> clazz) {
        String primaryKey = null;
        Field[] fields = clazz.getDeclaredFields();
        if(fields == null) {
            throw new RuntimeException("this model[" + clazz + "] has no field");
        } else {
            Id idAnnotation = null;
            Field idField = null;
            Field[] var8 = fields;
            int var7 = fields.length;

            Field field;
            int var6;
            for(var6 = 0; var6 < var7; ++var6) {
                field = var8[var6];
                idAnnotation = (Id)field.getAnnotation(Id.class);
                if(idAnnotation != null) {
                    idField = field;
                    break;
                }
            }

            if(idAnnotation != null) {
                primaryKey = idAnnotation.column();
                if(primaryKey == null || primaryKey.trim().length() == 0) {
                    primaryKey = idField.getName();
                }
            } else {
                var8 = fields;
                var7 = fields.length;

                for(var6 = 0; var6 < var7; ++var6) {
                    field = var8[var6];
                    if("_id".equals(field.getName())) {
                        return "_id";
                    }
                }

                var8 = fields;
                var7 = fields.length;

                for(var6 = 0; var6 < var7; ++var6) {
                    field = var8[var6];
                    if("id".equals(field.getName())) {
                        return "id";
                    }
                }
            }

            return primaryKey;
        }
    }

    public static Field getPrimaryKeyField(Class<?> clazz) {
        Field primaryKeyField = null;
        Field[] fields = clazz.getDeclaredFields();
        if(fields == null) {
            throw new RuntimeException("this model[" + clazz + "] has no field");
        } else {
            Field[] var6 = fields;
            int var5 = fields.length;

            Field field;
            int var4;
            for(var4 = 0; var4 < var5; ++var4) {
                field = var6[var4];
                if(field.getAnnotation(Id.class) != null) {
                    primaryKeyField = field;
                    break;
                }
            }

            if(primaryKeyField == null) {
                var6 = fields;
                var5 = fields.length;

                for(var4 = 0; var4 < var5; ++var4) {
                    field = var6[var4];
                    if("_id".equals(field.getName())) {
                        primaryKeyField = field;
                        break;
                    }
                }
            }

            if(primaryKeyField == null) {
                var6 = fields;
                var5 = fields.length;

                for(var4 = 0; var4 < var5; ++var4) {
                    field = var6[var4];
                    if("id".equals(field.getName())) {
                        primaryKeyField = field;
                        break;
                    }
                }
            }

            return primaryKeyField;
        }
    }

    public static String getPrimaryKeyFieldName(Class<?> clazz) {
        Field f = getPrimaryKeyField(clazz);
        return f == null?null:f.getName();
    }

    public static List<Property> getPropertyList(Class<?> clazz) {
        ArrayList plist = new ArrayList();

        try {
            Field[] e = clazz.getDeclaredFields();
            String primaryKeyFieldName = getPrimaryKeyFieldName(clazz);
            Field[] var7 = e;
            int var6 = e.length;

            for(int var5 = 0; var5 < var6; ++var5) {
                Field f = var7[var5];
                if(!FieldUtils.isTransient(f) && FieldUtils.isBaseDateType(f) && !f.getName().equals(primaryKeyFieldName)) {
                    Property property = new Property();
                    property.setColumn(FieldUtils.getColumnByField(f));
                    property.setFieldName(f.getName());
                    property.setDataType(f.getType());
                    property.setDefaultValue(FieldUtils.getPropertyDefaultValue(f));
                    property.setSet(FieldUtils.getFieldSetMethod(clazz, f));
                    property.setGet(FieldUtils.getFieldGetMethod(clazz, f));
                    property.setField(f);
                    plist.add(property);
                }
            }

            return plist;
        } catch (Exception var9) {
            throw new RuntimeException(var9.getMessage(), var9);
        }
    }

    public static List<ManyToOne> getManyToOneList(Class<?> clazz) {
        ArrayList mList = new ArrayList();

        try {
            Field[] e = clazz.getDeclaredFields();
            Field[] var6 = e;
            int var5 = e.length;

            for(int var4 = 0; var4 < var5; ++var4) {
                Field f = var6[var4];
                if(!FieldUtils.isTransient(f) && FieldUtils.isManyToOne(f)) {
                    ManyToOne mto = new ManyToOne();
                    if(f.getType() == ManyToOneLazyLoader.class) {
                        Class pClazz = (Class)((ParameterizedType)f.getGenericType()).getActualTypeArguments()[1];
                        if(pClazz != null) {
                            mto.setManyClass(pClazz);
                        }
                    } else {
                        mto.setManyClass(f.getType());
                    }

                    mto.setColumn(FieldUtils.getColumnByField(f));
                    mto.setFieldName(f.getName());
                    mto.setDataType(f.getType());
                    mto.setSet(FieldUtils.getFieldSetMethod(clazz, f));
                    mto.setGet(FieldUtils.getFieldGetMethod(clazz, f));
                    mList.add(mto);
                }
            }

            return mList;
        } catch (Exception var9) {
            throw new RuntimeException(var9.getMessage(), var9);
        }
    }

    public static List<OneToMany> getOneToManyList(Class<?> clazz) {
        ArrayList oList = new ArrayList();

        try {
            Field[] e = clazz.getDeclaredFields();
            Field[] var6 = e;
            int var5 = e.length;

            for(int var4 = 0; var4 < var5; ++var4) {
                Field f = var6[var4];
                if(!FieldUtils.isTransient(f) && FieldUtils.isOneToMany(f)) {
                    OneToMany otm = new OneToMany();
                    otm.setColumn(FieldUtils.getColumnByField(f));
                    otm.setFieldName(f.getName());
                    Type type = f.getGenericType();
                    if(!(type instanceof ParameterizedType)) {
                        throw new DbException("getOneToManyList Exception:" + f.getName() + "\'s type is null");
                    }

                    ParameterizedType pType = (ParameterizedType)f.getGenericType();
                    Class pClazz;
                    if(pType.getActualTypeArguments().length == 1) {
                        pClazz = (Class)pType.getActualTypeArguments()[0];
                        if(pClazz != null) {
                            otm.setOneClass(pClazz);
                        }
                    } else {
                        pClazz = (Class)pType.getActualTypeArguments()[1];
                        if(pClazz != null) {
                            otm.setOneClass(pClazz);
                        }
                    }

                    otm.setDataType(f.getType());
                    otm.setSet(FieldUtils.getFieldSetMethod(clazz, f));
                    otm.setGet(FieldUtils.getFieldGetMethod(clazz, f));
                    oList.add(otm);
                }
            }

            return oList;
        } catch (Exception var11) {
            throw new RuntimeException(var11.getMessage(), var11);
        }
    }
}

