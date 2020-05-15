package jp.inotou.android.kpi_sample_03;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityConfirm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        String message = "";
        Intent intent = getIntent();
        if(intent != null){
            message = intent.getStringExtra("message");
          }

        //オブジェクト取得
        TextView textMessage = findViewById(R.id.textMessage);
        //前画面からのメッセージをセット
        textMessage.setText(message);
    }


    // ボタンタッチイベント
    public void onClick(View v) {

        Intent intent ;
        switch(v.getId()){

            //メニューへ戻る
            case R.id.btnBackToMenu:
                //finish();

                 intent = new Intent(this , MainActivity.class);
                 intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                 startActivity(intent);

                break;

        }
    }
}
