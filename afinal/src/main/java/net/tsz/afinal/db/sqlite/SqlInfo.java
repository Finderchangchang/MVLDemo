package net.tsz.afinal.db.sqlite;

/**
 * Created by LiuWeiJie on 2015/7/25 0025.
 * Email:1031066280@qq.com
 */
import java.util.LinkedList;

public class SqlInfo {
    private String sql;
    private LinkedList<Object> bindArgs;

    public SqlInfo() {
    }

    public String getSql() {
        return this.sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public LinkedList<Object> getBindArgs() {
        return this.bindArgs;
    }

    public void setBindArgs(LinkedList<Object> bindArgs) {
        this.bindArgs = bindArgs;
    }

    public Object[] getBindArgsAsArray() {
        return this.bindArgs != null?this.bindArgs.toArray():null;
    }

    public String[] getBindArgsAsStringArray() {
        if(this.bindArgs == null) {
            return null;
        } else {
            String[] strings = new String[this.bindArgs.size()];

            for(int i = 0; i < this.bindArgs.size(); ++i) {
                strings[i] = this.bindArgs.get(i).toString();
            }

            return strings;
        }
    }

    public void addValue(Object obj) {
        if(this.bindArgs == null) {
            this.bindArgs = new LinkedList();
        }

        this.bindArgs.add(obj);
    }
}

