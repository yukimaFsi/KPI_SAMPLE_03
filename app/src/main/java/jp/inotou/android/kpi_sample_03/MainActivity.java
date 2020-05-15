package jp.inotou.android.kpi_sample_03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.HashMap;


//メニュー画面
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // ボタンタッチイベント
    public void onClick(View v) {
        Intent intent = new Intent();
        switch(v.getId()){

            //検索
            case R.id.btnSearch:
                intent.setAction("jp.inotou.android.kpi_sample_03.ActivitySearch");
                startActivityForResult(intent,0);
                break;
            //新規登録
            case R.id.btnRegist:
                intent.setAction("jp.inotou.android.kpi_sample_03.ActivityRegist");
                startActivityForResult(intent,0);
                break;
            //削除
            case R.id.btnDelete:
                intent.setAction("jp.inotou.android.kpi_sample_03.ActivityDelete");
                startActivityForResult(intent,0);
                break;

        }
    }
}
