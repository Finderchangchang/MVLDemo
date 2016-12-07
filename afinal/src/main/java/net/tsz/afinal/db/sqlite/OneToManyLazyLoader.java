package net.tsz.afinal.db.sqlite;

/**
 * Created by LiuWeiJie on 2015/7/25 0025.
 * Email:1031066280@qq.com
 */
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.FinalDb;

public class OneToManyLazyLoader<O, M> {
    O ownerEntity;
    Class<O> ownerClazz;
    Class<M> listItemClazz;
    FinalDb db;
    List<M> entities;

    public OneToManyLazyLoader(O ownerEntity, Class<O> ownerClazz, Class<M> listItemclazz, FinalDb db) {
        this.ownerEntity = ownerEntity;
        this.ownerClazz = ownerClazz;
        this.listItemClazz = listItemclazz;
        this.db = db;
    }

    public List<M> getList() {
        if(this.entities == null) {
            this.db.loadOneToMany(this.ownerEntity, this.ownerClazz, new Class[]{this.listItemClazz});
        }

        if(this.entities == null) {
            this.entities = new ArrayList();
        }

        return this.entities;
    }

    public void setList(List<M> value) {
        this.entities = value;
    }
}

