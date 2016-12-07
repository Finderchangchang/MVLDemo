package net.tsz.afinal.db.table;

/**
 * Created by LiuWeiJie on 2015/7/25 0025.
 * Email:1031066280@qq.com
 */
import net.tsz.afinal.db.table.Property;

public class OneToMany extends Property {
    private Class<?> oneClass;

    public OneToMany() {
    }

    public Class<?> getOneClass() {
        return this.oneClass;
    }

    public void setOneClass(Class<?> oneClass) {
        this.oneClass = oneClass;
    }
}