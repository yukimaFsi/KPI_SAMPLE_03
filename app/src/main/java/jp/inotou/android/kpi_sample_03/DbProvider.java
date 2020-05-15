package jp.inotou.android.kpi_sample_03;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class DbProvider extends ContentProvider {
    private final static String DB_NAME = "kpi_03_db.db";
    private final static String MEMBER_TABLE = "member";
    private final static int DB_VERSION = 1;
    private SQLiteDatabase db;
    private final static String AUTHORITY = "jp.inotou.android.kpi_sample_03.dbprovider";
    private final static String MEMBER_PATH = "member";
    //private final static String CAREPLANGROUP_PATH="careplangroup";
    private final static int MEMBER_NO = 1;
    //private final static int CAREPLAN_NO=2;
    //private final static int CAREPLANGROUP_NO=3;
    private UriMatcher uriMatcher;
    public final static Uri memberUri = Uri.parse("content://" + AUTHORITY + "/" + MEMBER_PATH);

    //public final static Uri carePlanUri=Uri.parse("content://"+AUTHORITY+"/"+CAREPLAN_PATH);
    //public final static Uri carePlanGroupUri=Uri.parse("content://"+AUTHORITY+"/"+CAREPLANGROUP_PATH);


    @Override
    public boolean onCreate() {
        this.db = new DbHelper(getContext()).getWritableDatabase();
        this.uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        this.uriMatcher.addURI(AUTHORITY, MEMBER_PATH, MEMBER_NO);
        //this.uriMatcher.addURI(AUTHORITY, CAREPLAN_PATH, CAREPLAN_NO);
        //this.uriMatcher.addURI(AUTHORITY, CAREPLANGROUP_PATH, CAREPLANGROUP_NO);
        return (db != null);
    }

    //登録 ===========================================================
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch (uriMatcher.match(uri)) {
            case MEMBER_NO:
                this.db.insert(MEMBER_TABLE, null, values);
                break;
//            case CAREPLAN_NO:
//                this.db.insert(CAREPLAN_TABLE, null, values);
//                break;
        }
        return uri;
    }

    //削除 ===========================================================
    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        int ret = 0;
        switch (uriMatcher.match(uri)) {
            case MEMBER_NO:
                ret = this.db.delete(MEMBER_TABLE, where, whereArgs);
                break;
//            case CAREPLAN_NO:
//                ret=this.db.delete(CAREPLAN_TABLE, where, whereArgs);
//                break;
        }
        return ret;
    }

    //更新 ===========================================================
    @Override
    public int update(Uri uri, ContentValues values, String where,
                      String[] whereArgs) {
        int ret = 0;
        switch (uriMatcher.match(uri)) {
            case MEMBER_NO:
                ret = this.db.update(MEMBER_TABLE, values, where, whereArgs);
                break;
//            case CAREPLAN_NO:
//                ret=this.db.update(CAREPLAN_TABLE, values,where, whereArgs);
//                break;
        }
        return ret;
    }

    //？？ ===========================================================
    @Override
    public Cursor query(Uri uri, String[] select, String where,
                        String[] whereArgs, String orderBy) {
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case MEMBER_NO:
                cursor = db.query(MEMBER_TABLE, select, where, whereArgs, null, null, orderBy);
                break;
//            case CAREPLAN_NO:
//                cursor=db.query(CAREPLAN_TABLE, select, where, whereArgs, null, null, orderBy);
//                break;
//            case CAREPLANGROUP_NO:
//                String groupBy="memNo,date";
//                cursor=db.query(CAREPLAN_TABLE, select, where, whereArgs, groupBy, null, orderBy);
//                break;
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    private static class DbHelper extends SQLiteOpenHelper {
        public DbHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        //テーブル作成===============================================-
        @Override
        public void onCreate(SQLiteDatabase db) {
            String tblSpec1 = "(memNo text primary key,memName text)";
            db.execSQL("create table if not exists " +
                    MEMBER_TABLE + tblSpec1);

//            String tblSpec2="(memNo text,memName text,date text," +
//                    "careCode text,careName text," +
//                    "planNo text,predTime text,implTime text," +
//                    "result text,helperCode text,helperName text)";
//            db.execSQL("create table if not exists "+
//                    CAREPLAN_TABLE+tblSpec2);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db,
                              int oldVersion, int newVersion) {
            //Version Up時の処理
        }
    }
}