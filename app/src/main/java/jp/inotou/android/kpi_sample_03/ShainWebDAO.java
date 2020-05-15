package jp.inotou.android.kpi_sample_03;

import android.content.Context;

import java.util.ArrayList;

public class ShainWebDAO {
    private Context context;
    private String serverUrl;
    public ShainWebDAO(Context context){
        this.context=context;
    }
    /* *****************************************************************
     * ここではすべて空メソッドとして定義
     *******************************************************************/
    public ArrayList<ShainDTO> selectSet(String memNo, String date) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }
    public int deleteOne(String memNo, String date, String planNo) {
        // TODO 自動生成されたメソッド・スタブ
        return 0;
    }
    public int deleteSet(String memNo, String date) {
        // TODO 自動生成されたメソッド・スタブ
        return 0;
    }
    public int insertOne(ShainDTO dto) {
        // TODO 自動生成されたメソッド・スタブ
        return 0;
    }
    public int insertSet(ArrayList<ShainDTO> dto) {
        // TODO 自動生成されたメソッド・スタブ
        return 0;
    }
    public int updateOne(ShainDTO dto) {
        // TODO 自動生成されたメソッド・スタブ
        return 0;
    }
    public int updateSet(ArrayList<ShainDTO> dto) {
        // TODO 自動生成されたメソッド・スタブ
        return 0;
    }
    public int deleteAll() {
        // TODO 自動生成されたメソッド・スタブ
        return 0;
    }
    public ArrayList<ShainDTO> selectAny(String where) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }
}
