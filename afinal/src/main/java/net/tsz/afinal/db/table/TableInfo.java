package net.tsz.afinal.db.table;

/**
 * Created by LiuWeiJie on 2015/7/25 0025.
 * Email:1031066280@qq.com
 */
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.tsz.afinal.db.table.Id;
import net.tsz.afinal.db.table.ManyToOne;
import net.tsz.afinal.db.table.OneToMany;
import net.tsz.afinal.db.table.Property;
import net.tsz.afinal.exception.DbException;
import net.tsz.afinal.utils.ClassUtils;
import net.tsz.afinal.utils.FieldUtils;

public class TableInfo {
    private String className;
    private String tableName;
    private Id id;
    public final HashMap<String, Property> propertyMap = new HashMap();
    public final HashMap<String, OneToMany> oneToManyMap = new HashMap();
    public final HashMap<String, ManyToOne> manyToOneMap = new HashMap();
    private boolean checkDatabese;
    private static final HashMap<String, TableInfo> tableInfoMap = new HashMap();

    private TableInfo() {
    }

    public static TableInfo get(Class<?> clazz) {
        if(clazz == null) {
            throw new DbException("table info get error,because the clazz is null");
        } else {
            TableInfo tableInfo = (TableInfo)tableInfoMap.get(clazz.getName());
            if(tableInfo == null) {
                tableInfo = new TableInfo();
                tableInfo.setTableName(ClassUtils.getTableName(clazz));
                tableInfo.setClassName(clazz.getName());
                Field idField = ClassUtils.getPrimaryKeyField(clazz);
                if(idField == null) {
                    throw new DbException("the class[" + clazz + "]\'s idField is null , \n you can define _id,id property or use annotation @id to solution this exception");
                }

                Id pList = new Id();
                pList.setColumn(FieldUtils.getColumnByField(idField));
                pList.setFieldName(idField.getName());
                pList.setSet(FieldUtils.getFieldSetMethod(clazz, idField));
                pList.setGet(FieldUtils.getFieldGetMethod(clazz, idField));
                pList.setDataType(idField.getType());
                tableInfo.setId(pList);
                List pList1 = ClassUtils.getPropertyList(clazz);
                if(pList1 != null) {
                    Iterator oList = pList1.iterator();

                    while(oList.hasNext()) {
                        Property mList = (Property)oList.next();
                        if(mList != null) {
                            tableInfo.propertyMap.put(mList.getColumn(), mList);
                        }
                    }
                }

                List mList1 = ClassUtils.getManyToOneList(clazz);
                if(mList1 != null) {
                    Iterator o = mList1.iterator();

                    while(o.hasNext()) {
                        ManyToOne oList1 = (ManyToOne)o.next();
                        if(oList1 != null) {
                            tableInfo.manyToOneMap.put(oList1.getColumn(), oList1);
                        }
                    }
                }

                List oList2 = ClassUtils.getOneToManyList(clazz);
                if(oList2 != null) {
                    Iterator var7 = oList2.iterator();

                    while(var7.hasNext()) {
                        OneToMany o1 = (OneToMany)var7.next();
                        if(o1 != null) {
                            tableInfo.oneToManyMap.put(o1.getColumn(), o1);
                        }
                    }
                }

                tableInfoMap.put(clazz.getName(), tableInfo);
            }

            if(tableInfo == null) {
                throw new DbException("the class[" + clazz + "]\'s table is null");
            } else {
                return tableInfo;
            }
        }
    }

    public static TableInfo get(String className) {
        try {
            return get(Class.forName(className));
        } catch (ClassNotFoundException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Id getId() {
        return this.id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public boolean isCheckDatabese() {
        return this.checkDatabese;
    }

    public void setCheckDatabese(boolean checkDatabese) {
        this.checkDatabese = checkDatabese;
    }
}

