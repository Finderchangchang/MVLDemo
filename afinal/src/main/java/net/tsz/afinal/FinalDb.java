package net.tsz.afinal;

/**
 * Created by LiuWeiJie on 2015/7/25 0025.
 * Email:1031066280@qq.com
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.tsz.afinal.db.sqlite.CursorUtils;
import net.tsz.afinal.db.sqlite.DbModel;
import net.tsz.afinal.db.sqlite.ManyToOneLazyLoader;
import net.tsz.afinal.db.sqlite.OneToManyLazyLoader;
import net.tsz.afinal.db.sqlite.SqlBuilder;
import net.tsz.afinal.db.sqlite.SqlInfo;
import net.tsz.afinal.db.table.KeyValue;
import net.tsz.afinal.db.table.ManyToOne;
import net.tsz.afinal.db.table.OneToMany;
import net.tsz.afinal.db.table.TableInfo;
import net.tsz.afinal.exception.DbException;

public class FinalDb {
    private static final String TAG = "FinalDb";
    private static HashMap<String, FinalDb> daoMap = new HashMap();
    private SQLiteDatabase db;
    private FinalDb.DaoConfig config;

    private FinalDb(FinalDb.DaoConfig config) {
        if (config == null) {
            throw new DbException("daoConfig is null");
        } else if (config.getContext() == null) {
            throw new DbException("android context is null");
        } else {
            if (config.getTargetDirectory() != null && config.getTargetDirectory().trim().length() > 0) {
                this.db = this.createDbFileOnSDCard(config.getTargetDirectory(), config.getDbName());
            } else {
                this.db = (new FinalDb.SqliteDbHelper(config.getContext().getApplicationContext(), config.getDbName(), config.getDbVersion(), config.getDbUpdateListener())).getWritableDatabase();
            }
            this.config = config;
        }
    }

    private static synchronized FinalDb getInstance(FinalDb.DaoConfig daoConfig) {
        FinalDb dao = (FinalDb) daoMap.get(daoConfig.getDbName());
        if (dao == null) {
            dao = new FinalDb(daoConfig);
            daoMap.put(daoConfig.getDbName(), dao);
        }
        return dao;
    }

    public static FinalDb create(Context context) {
        FinalDb.DaoConfig config = new FinalDb.DaoConfig();
        config.setContext(context);
        return create(config);
    }

    public static FinalDb create(Context context, boolean isDebug) {
        FinalDb.DaoConfig config = new FinalDb.DaoConfig();
        config.setContext(context);
        config.setDebug(isDebug);
        return create(config);
    }

    public static FinalDb create(Context context, String dbName) {
        FinalDb.DaoConfig config = new FinalDb.DaoConfig();
        config.setContext(context);
        config.setDbName(dbName);
        return create(config);
    }

    public static FinalDb create(Context context, String dbName, boolean isDebug) {
        FinalDb.DaoConfig config = new FinalDb.DaoConfig();
        config.setContext(context);
        config.setDbName(dbName);
        config.setDebug(isDebug);
        return create(config);
    }

    public static FinalDb create(Context context, String targetDirectory, String dbName) {
        FinalDb.DaoConfig config = new FinalDb.DaoConfig();
        config.setContext(context);
        config.setDbName(dbName);
        config.setTargetDirectory(targetDirectory);
        return create(config);
    }

    public static FinalDb create(Context context, String targetDirectory, String dbName, boolean isDebug) {
        FinalDb.DaoConfig config = new FinalDb.DaoConfig();
        config.setContext(context);
        config.setTargetDirectory(targetDirectory);
        config.setDbName(dbName);
        config.setDebug(isDebug);
        return create(config);
    }

    public static FinalDb create(Context context, String dbName, boolean isDebug, int dbVersion, FinalDb.DbUpdateListener dbUpdateListener) {
        FinalDb.DaoConfig config = new FinalDb.DaoConfig();
        config.setContext(context);
        config.setDbName(dbName);
        config.setDebug(isDebug);
        config.setDbVersion(dbVersion);
        config.setDbUpdateListener(dbUpdateListener);
        return create(config);
    }

    public static FinalDb create(Context context, String targetDirectory, String dbName, boolean isDebug, int dbVersion, FinalDb.DbUpdateListener dbUpdateListener) {
        FinalDb.DaoConfig config = new FinalDb.DaoConfig();
        config.setContext(context);
        config.setTargetDirectory(targetDirectory);
        config.setDbName(dbName);
        config.setDebug(isDebug);
        config.setDbVersion(dbVersion);
        config.setDbUpdateListener(dbUpdateListener);
        return create(config);
    }

    public static FinalDb create(FinalDb.DaoConfig daoConfig) {
        return getInstance(daoConfig);
    }

    public void save(Object entity) {
        this.checkTableExist(entity.getClass());
        this.exeSqlInfo(SqlBuilder.buildInsertSql(entity));
    }

    public boolean saveBindId(Object entity) {
        this.checkTableExist(entity.getClass());
        List entityKvList = SqlBuilder.getSaveKeyValueListByEntity(entity);
        if (entityKvList != null && entityKvList.size() > 0) {
            TableInfo tf = TableInfo.get(entity.getClass());
            ContentValues cv = new ContentValues();
            this.insertContentValues(entityKvList, cv);
            Long id = Long.valueOf(this.db.insert(tf.getTableName(), (String) null, cv));
            if (id.longValue() == -1L) {
                return false;
            } else {
                tf.getId().setValue(entity, id);
                return true;
            }
        } else {
            return false;
        }
    }

    private void insertContentValues(List<KeyValue> list, ContentValues cv) {
        if (list != null && cv != null) {
            Iterator var4 = list.iterator();

            while (var4.hasNext()) {
                KeyValue kv = (KeyValue) var4.next();
                cv.put(kv.getKey(), kv.getValue().toString());
            }
        } else {
            Log.w("FinalDb", "insertContentValues: List<KeyValue> is empty or ContentValues is empty!");
        }

    }

    public void update(Object entity) {
        this.checkTableExist(entity.getClass());
        this.exeSqlInfo(SqlBuilder.getUpdateSqlAsSqlInfo(entity));
    }

    public void update(Object entity, String strWhere) {
        this.checkTableExist(entity.getClass());
        this.exeSqlInfo(SqlBuilder.getUpdateSqlAsSqlInfo(entity, strWhere));
    }

    public void delete(Object entity) {
        this.checkTableExist(entity.getClass());
        this.exeSqlInfo(SqlBuilder.buildDeleteSql(entity));
    }

    public void deleteById(Class<?> clazz, Object id) {
        this.checkTableExist(clazz);
        this.exeSqlInfo(SqlBuilder.buildDeleteSql(clazz, id));
    }

    public void deleteByWhere(Class<?> clazz, String strWhere) {
        this.checkTableExist(clazz);
        String sql = SqlBuilder.buildDeleteSql(clazz, strWhere);
        this.debugSql(sql);
        this.db.execSQL(sql);
    }

    public void deleteAll(Class<?> clazz) {
        this.checkTableExist(clazz);
        String sql = SqlBuilder.buildDeleteSql(clazz, (String) null);
        this.debugSql(sql);
        this.db.execSQL(sql);
    }

    public void dropTable(Class<?> clazz) {
        this.checkTableExist(clazz);
        TableInfo table = TableInfo.get(clazz);
        String sql = "DROP TABLE " + table.getTableName();
        this.debugSql(sql);
        this.db.execSQL(sql);
    }

    public void dropDb() {
        Cursor cursor = this.db.rawQuery("SELECT name FROM sqlite_master WHERE type =\'table\' AND name != \'sqlite_sequence\'", (String[]) null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                this.db.execSQL("DROP TABLE " + cursor.getString(0));
            }
        }

        if (cursor != null) {
            cursor.close();
            cursor = null;
        }

    }

    private void exeSqlInfo(SqlInfo sqlInfo) {
        if (sqlInfo != null) {
            this.debugSql(sqlInfo.getSql());
            this.db.execSQL(sqlInfo.getSql(), sqlInfo.getBindArgsAsArray());
        } else {
            Log.e("FinalDb", "sava error:sqlInfo is null");
        }

    }

    public <T> T findById(Object id, Class<T> clazz) {
        this.checkTableExist(clazz);
        SqlInfo sqlInfo = SqlBuilder.getSelectSqlAsSqlInfo(clazz, id);
        if (sqlInfo != null) {
            this.debugSql(sqlInfo.getSql());
            Cursor cursor = this.db.rawQuery(sqlInfo.getSql(), sqlInfo.getBindArgsAsStringArray());

            Object var7;
            try {
                if (!cursor.moveToNext()) {
                    return null;
                }

                var7 = CursorUtils.getEntity(cursor, clazz, this);
            } catch (Exception var10) {
                var10.printStackTrace();
                return null;
            } finally {
                cursor.close();
            }

            return (T) var7;
        } else {
            return null;
        }
    }

    public <T> T findWithManyToOneById(Object id, Class<T> clazz) {
        this.checkTableExist(clazz);
        String sql = SqlBuilder.getSelectSQL(clazz, id);
        this.debugSql(sql);
        DbModel dbModel = this.findDbModelBySQL(sql);
        if (dbModel != null) {
            Object entity = CursorUtils.dbModel2Entity(dbModel, clazz);
            return this.loadManyToOne(dbModel, (T) entity, clazz, new Class[0]);
        } else {
            return null;
        }
    }

    public <T> T findWithManyToOneById(Object id, Class<T> clazz, Class... findClass) {
        this.checkTableExist(clazz);
        String sql = SqlBuilder.getSelectSQL(clazz, id);
        this.debugSql(sql);
        DbModel dbModel = this.findDbModelBySQL(sql);
        if (dbModel != null) {
            Object entity = CursorUtils.dbModel2Entity(dbModel, clazz);
            return this.loadManyToOne(dbModel, (T) entity, clazz, findClass);
        } else {
            return null;
        }
    }

    public <T> T loadManyToOne(DbModel dbModel, T entity, Class<T> clazz, Class... findClass) {
        if (entity != null) {
            try {
                Collection e = TableInfo.get(clazz).manyToOneMap.values();
                Iterator var7 = e.iterator();

                while (true) {
                    ManyToOne many;
                    Object id;
                    do {
                        if (!var7.hasNext()) {
                            return entity;
                        }

                        many = (ManyToOne) var7.next();
                        id = null;
                        if (dbModel != null) {
                            id = dbModel.get(many.getColumn());
                        } else if (many.getValue(entity).getClass() == ManyToOneLazyLoader.class && many.getValue(entity) != null) {
                            id = ((ManyToOneLazyLoader) many.getValue(entity)).getFieldValue();
                        }
                    } while (id == null);

                    boolean isFind = false;
                    if (findClass == null || findClass.length == 0) {
                        isFind = true;
                    }

                    Class[] var13 = findClass;
                    int var12 = findClass.length;

                    for (int var11 = 0; var11 < var12; ++var11) {
                        Class manyEntity = var13[var11];
                        if (many.getManyClass() == manyEntity) {
                            isFind = true;
                            break;
                        }
                    }

                    if (isFind) {
                        Object var15 = this.findById(Integer.valueOf(id.toString()), many.getManyClass());
                        if (var15 != null) {
                            if (many.getValue(entity).getClass() == ManyToOneLazyLoader.class) {
                                if (many.getValue(entity) == null) {
                                    many.setValue(entity, new ManyToOneLazyLoader(entity, clazz, many.getManyClass(), this));
                                }

                                ((ManyToOneLazyLoader) many.getValue(entity)).set(var15);
                            } else {
                                many.setValue(entity, var15);
                            }
                        }
                    }
                }
            } catch (Exception var14) {
                var14.printStackTrace();
            }
        }

        return entity;
    }

    public <T> T findWithOneToManyById(Object id, Class<T> clazz) {
        this.checkTableExist(clazz);
        String sql = SqlBuilder.getSelectSQL(clazz, id);
        this.debugSql(sql);
        DbModel dbModel = this.findDbModelBySQL(sql);
        if (dbModel != null) {
            Object entity = CursorUtils.dbModel2Entity(dbModel, clazz);
            return this.loadOneToMany((T) entity, clazz, new Class[0]);
        } else {
            return null;
        }
    }

    public <T> T findWithOneToManyById(Object id, Class<T> clazz, Class... findClass) {
        this.checkTableExist(clazz);
        String sql = SqlBuilder.getSelectSQL(clazz, id);
        this.debugSql(sql);
        DbModel dbModel = this.findDbModelBySQL(sql);
        if (dbModel != null) {
            Object entity = CursorUtils.dbModel2Entity(dbModel, clazz);
            return this.loadOneToMany((T) entity, clazz, findClass);
        } else {
            return null;
        }
    }

    public <T> T loadOneToMany(T entity, Class<T> clazz, Class... findClass) {
        if (entity != null) {
            try {
                Collection e = TableInfo.get(clazz).oneToManyMap.values();
                Object id = TableInfo.get(clazz).getId().getValue(entity);
                Iterator var7 = e.iterator();

                while (var7.hasNext()) {
                    OneToMany one = (OneToMany) var7.next();
                    boolean isFind = false;
                    if (findClass == null || findClass.length == 0) {
                        isFind = true;
                    }

                    Class[] var12 = findClass;
                    int var11 = findClass.length;

                    for (int oneToManyLazyLoader = 0; oneToManyLazyLoader < var11; ++oneToManyLazyLoader) {
                        Class list = var12[oneToManyLazyLoader];
                        if (one.getOneClass() == list) {
                            isFind = true;
                            break;
                        }
                    }

                    if (isFind) {
                        List var14 = this.findAllByWhere(one.getOneClass(), one.getColumn() + "=" + id);
                        if (var14 != null) {
                            if (one.getDataType() == OneToManyLazyLoader.class) {
                                OneToManyLazyLoader var15 = (OneToManyLazyLoader) one.getValue(entity);
                                var15.setList(var14);
                            } else {
                                one.setValue(entity, var14);
                            }
                        }
                    }
                }
            } catch (Exception var13) {
                var13.printStackTrace();
            }
        }

        return entity;
    }

    public <T> List<T> findAll(Class<T> clazz) {
        this.checkTableExist(clazz);
        return this.findAllBySql(clazz, SqlBuilder.getSelectSQL(clazz));
    }

    public <T> List<T> findAll(Class<T> clazz, String orderBy) {
        this.checkTableExist(clazz);
        return this.findAllBySql(clazz, SqlBuilder.getSelectSQL(clazz) + " ORDER BY " + orderBy);
    }

    public <T> List<T> findAllByWhere(Class<T> clazz, String strWhere) {
        this.checkTableExist(clazz);
        return this.findAllBySql(clazz, SqlBuilder.getSelectSQLByWhere(clazz, strWhere));
    }

    public <T> List<T> findAllByWhere(Class<T> clazz, String strWhere, String orderBy) {
        this.checkTableExist(clazz);
        return this.findAllBySql(clazz, SqlBuilder.getSelectSQLByWhere(clazz, strWhere) + " ORDER BY " + orderBy);
    }

    private <T> List<T> findAllBySql(Class<T> clazz, String strSQL) {
        this.checkTableExist(clazz);
        this.debugSql(strSQL);
        Cursor cursor = this.db.rawQuery(strSQL, (String[]) null);
        try {
            ArrayList e = new ArrayList();
            while (cursor.moveToNext()) {
                Object t = CursorUtils.getEntity(cursor, clazz, this);
                e.add(t);
            }
            ArrayList var7 = e;
            return var7;
        } catch (Exception var10) {
            var10.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            cursor = null;
        }
        return null;
    }

    public DbModel findDbModelBySQL(String strSQL) {
        this.debugSql(strSQL);
        Cursor cursor = this.db.rawQuery(strSQL, (String[]) null);

        DbModel var5;
        try {
            if (!cursor.moveToNext()) {
                return null;
            }

            var5 = CursorUtils.getDbModel(cursor);
        } catch (Exception var8) {
            var8.printStackTrace();
            return null;
        } finally {
            cursor.close();
        }

        return var5;
    }

    public List<DbModel> findDbModelListBySQL(String strSQL) {
        this.debugSql(strSQL);
        Cursor cursor = this.db.rawQuery(strSQL, (String[]) null);
        ArrayList dbModelList = new ArrayList();

        try {
            while (cursor.moveToNext()) {
                dbModelList.add(CursorUtils.getDbModel(cursor));
            }
        } catch (Exception var8) {
            var8.printStackTrace();
        } finally {
            cursor.close();
        }

        return dbModelList;
    }

    private void checkTableExist(Class<?> clazz) {
        if (!this.tableIsExist(TableInfo.get(clazz))) {
            String sql = SqlBuilder.getCreatTableSQL(clazz);
            this.debugSql(sql);
            this.db.execSQL(sql);
        }

    }

    private boolean tableIsExist(TableInfo table) {
        if (table.isCheckDatabese()) {
            return true;
        } else {
            Cursor cursor = null;

            try {
                String e = "SELECT COUNT(*) AS c FROM sqlite_master WHERE type =\'table\' AND name =\'" + table.getTableName() + "\' ";
                this.debugSql(e);
                cursor = this.db.rawQuery(e, (String[]) null);
                if (cursor == null || !cursor.moveToNext()) {
                    return false;
                }

                int count = cursor.getInt(0);
                if (count <= 0) {
                    return false;
                }

                table.setCheckDatabese(true);
            } catch (Exception var8) {
                var8.printStackTrace();
                return false;
            } finally {
                if (cursor != null) {
                    cursor.close();
                }

                cursor = null;
            }

            return true;
        }
    }

    private void debugSql(String sql) {
        if (this.config != null && this.config.isDebug()) {
            Log.d("Debug SQL", ">>>>>>  " + sql);
        }

    }

    private SQLiteDatabase createDbFileOnSDCard(String sdcardPath, String dbfilename) {
        File dbf = new File(sdcardPath, dbfilename);
        if (isDbFile(dbf)) {
            try {
                return dbf.createNewFile() ? SQLiteDatabase.openOrCreateDatabase(dbf, (CursorFactory) null) : null;
            } catch (IOException var5) {
                throw new DbException("数据库文件创建失败", var5);
            }
        } else {
            return SQLiteDatabase.openOrCreateDatabase(dbf, (CursorFactory) null);
        }
    }

    /**
     * 判断.db文件是否存在
     */
    public static boolean isDbFile(File file) {
        if (file.exists()) {//文件存在
            return true;
        } else {//文件不存在
            return false;
        }
    }

    public static class DaoConfig {
        private Context mContext = null;
        private String mDbName = "afinal.db";
        private int dbVersion = 1;
        private boolean debug = true;
        private FinalDb.DbUpdateListener dbUpdateListener;
        private String targetDirectory;

        public DaoConfig() {
        }

        public Context getContext() {
            return this.mContext;
        }

        public void setContext(Context context) {
            this.mContext = context;
        }

        public String getDbName() {
            return this.mDbName;
        }

        public void setDbName(String dbName) {
            this.mDbName = dbName;
        }

        public int getDbVersion() {
            return this.dbVersion;
        }

        public void setDbVersion(int dbVersion) {
            this.dbVersion = dbVersion;
        }

        public boolean isDebug() {
            return this.debug;
        }

        public void setDebug(boolean debug) {
            this.debug = debug;
        }

        public FinalDb.DbUpdateListener getDbUpdateListener() {
            return this.dbUpdateListener;
        }

        public void setDbUpdateListener(FinalDb.DbUpdateListener dbUpdateListener) {
            this.dbUpdateListener = dbUpdateListener;
        }

        public String getTargetDirectory() {
            return this.targetDirectory;
        }

        public void setTargetDirectory(String targetDirectory) {
            this.targetDirectory = targetDirectory;
        }
    }

    public interface DbUpdateListener {
        void onUpgrade(SQLiteDatabase var1, int var2, int var3);
    }

    class SqliteDbHelper extends SQLiteOpenHelper {
        private FinalDb.DbUpdateListener mDbUpdateListener;

        public SqliteDbHelper(Context context, String name, int version, FinalDb.DbUpdateListener dbUpdateListener) {
            super(context, name, (CursorFactory) null, version);
            this.mDbUpdateListener = dbUpdateListener;
        }

        public void onCreate(SQLiteDatabase db) {
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (this.mDbUpdateListener != null) {
                this.mDbUpdateListener.onUpgrade(db, oldVersion, newVersion);
            } else {
                FinalDb.this.dropDb();
            }

        }
    }
}
