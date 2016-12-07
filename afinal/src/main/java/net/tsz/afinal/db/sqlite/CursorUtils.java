package net.tsz.afinal.db.sqlite;

/**
 * Created by LiuWeiJie on 2015/7/25 0025.
 * Email:1031066280@qq.com
 */
import android.database.Cursor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.db.sqlite.DbModel;
import net.tsz.afinal.db.sqlite.ManyToOneLazyLoader;
import net.tsz.afinal.db.sqlite.OneToManyLazyLoader;
import net.tsz.afinal.db.table.ManyToOne;
import net.tsz.afinal.db.table.OneToMany;
import net.tsz.afinal.db.table.Property;
import net.tsz.afinal.db.table.TableInfo;

public class CursorUtils {
    public CursorUtils() {
    }

    public static <T> T getEntity(Cursor cursor, Class<T> clazz, FinalDb db) {
        try {
            if(cursor != null) {
                TableInfo e = TableInfo.get(clazz);
                int columnCount = cursor.getColumnCount();
                if(columnCount > 0) {
                    Object entity = clazz.newInstance();

                    for(int manyToOneProp = 0; manyToOneProp < columnCount; ++manyToOneProp) {
                        String column = cursor.getColumnName(manyToOneProp);
                        Property manyToOneLazyLoader = (Property)e.propertyMap.get(column);
                        if(manyToOneLazyLoader != null) {
                            manyToOneLazyLoader.setValue(entity, cursor.getString(manyToOneProp));
                        } else if(e.getId().getColumn().equals(column)) {
                            e.getId().setValue(entity, cursor.getString(manyToOneProp));
                        }
                    }

                    Iterator var12 = e.oneToManyMap.values().iterator();

                    while(var12.hasNext()) {
                        OneToMany var10 = (OneToMany)var12.next();
                        if(var10.getDataType() == OneToManyLazyLoader.class) {
                            OneToManyLazyLoader var13 = new OneToManyLazyLoader(entity, clazz, var10.getOneClass(), db);
                            var10.setValue(entity, var13);
                        }
                    }

                    var12 = e.manyToOneMap.values().iterator();

                    while(var12.hasNext()) {
                        ManyToOne var11 = (ManyToOne)var12.next();
                        if(var11.getDataType() == ManyToOneLazyLoader.class) {
                            ManyToOneLazyLoader var14 = new ManyToOneLazyLoader(entity, clazz, var11.getManyClass(), db);
                            var14.setFieldValue(Integer.valueOf(cursor.getInt(cursor.getColumnIndex(var11.getColumn()))));
                            var11.setValue(entity, var14);
                        }
                    }

                    return (T) entity;
                }
            }
        } catch (Exception var9) {
            var9.printStackTrace();
        }

        return null;
    }

    public static DbModel getDbModel(Cursor cursor) {
        if(cursor != null && cursor.getColumnCount() > 0) {
            DbModel model = new DbModel();
            int columnCount = cursor.getColumnCount();

            for(int i = 0; i < columnCount; ++i) {
                model.set(cursor.getColumnName(i), cursor.getString(i));
            }

            return model;
        } else {
            return null;
        }
    }

    public static <T> T dbModel2Entity(DbModel dbModel, Class<?> clazz) {
        if(dbModel != null) {
            HashMap dataMap = dbModel.getDataMap();

            try {
                Object e = clazz.newInstance();
                Iterator var5 = dataMap.entrySet().iterator();

                while(var5.hasNext()) {
                    Entry entry = (Entry)var5.next();
                    String column = (String)entry.getKey();
                    TableInfo table = TableInfo.get(clazz);
                    Property property = (Property)table.propertyMap.get(column);
                    if(property != null) {
                        property.setValue(e, entry.getValue() == null?null:entry.getValue().toString());
                    } else if(table.getId().getColumn().equals(column)) {
                        table.getId().setValue(e, entry.getValue() == null?null:entry.getValue().toString());
                    }
                }

                return (T) e;
            } catch (Exception var9) {
                var9.printStackTrace();
            }
        }

        return null;
    }
}
