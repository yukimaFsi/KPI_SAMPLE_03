package jp.inotou.android.kpi_sample_03;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

public class ShainProvDAO {

    private ContentResolver resolver;
    private final static String AUTHORITY="jp.inotou.android.kpi_sample_03.dbprovider";
    private final static String MEMBER_PATH="member";
    private Uri uri=Uri.parse("content://"+AUTHORITY+"/"+MEMBER_PATH);

    public ShainProvDAO(Context context){
        this.resolver=context.getContentResolver();
    }

    public ShainDTO selectOne(String memNo){
        ShainDTO dto=new ShainDTO();
        String[] select={"memNo","memName"};
        String where="memNo=?";
        String[] whereArgs=new String[1];
        whereArgs[0]=memNo;
        String orderBy="";
        Cursor cursol=resolver.query(uri,select,where,whereArgs,orderBy);
        if(cursol!=null){
            if(cursol.moveToFirst()){
                dto.setNum(cursol.getString(0));
                dto.setName(cursol.getString(1));
            }
            cursol.close();
        }
        return dto;
    }

    public int deleteOne(String memNo){
        String where="memNo=?";
        String[] whereArgs=new String[1];
        whereArgs[0]=memNo;
        return resolver.delete(uri,where,whereArgs);
    }

    public int insertOne(ShainDTO dto){
        ContentValues values=new ContentValues();
        values.put("memNo",dto.getNum());
        values.put("memName",dto.getName());
        Uri ret = resolver.insert(uri,values);
        return 1;
    }

}
