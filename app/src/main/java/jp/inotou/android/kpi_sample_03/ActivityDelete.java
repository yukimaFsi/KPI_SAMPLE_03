package jp.inotou.android.kpi_sample_03;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import jp.inotou.android.kpi_sample_03.service.ShainService;

//削除画面
public class ActivityDelete extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
    }
    @Override
    public void onResume() {
        super.onResume();
        this.receiveStart();
    }
    @Override
    public void onPause() {
        super.onPause();
        //未作成
        this.receiveStop();
    }
    public void receiveStart(){
        IntentFilter filter=new IntentFilter();
        filter.addAction("DO_ACTION");
        registerReceiver(localReceiver,filter);
    }
    private void receiveStop(){
        unregisterReceiver(localReceiver);
    }

    private BroadcastReceiver localReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d("LocalReceiver","Start Now");
            int retCount = 0;
            String message = "";

            ShainDTO dto =(ShainDTO)intent.getSerializableExtra("shainDTO");
            retCount = (int)intent.getSerializableExtra("retCount");
            int status =(int)intent.getSerializableExtra("status");
            if(dto==null)return;

            finish();//自画面を閉じる
            if(status==0){
                message = "その番号は未登録です";
            }else{
                message = (retCount==0) ? "削除できませんでした" : "削除しました";
            }
            intent.putExtra("message",message);
            intent.setAction("jp.inotou.android.kpi_sample_03.ActivityConfirm");
            startActivityForResult(intent,0);
        }
    };


    // ボタンタッチイベント
    public void onClick(View v) {

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        ShainDTO shainDTO;
        Intent intent = new Intent();
        switch(v.getId()){

            //削除
            case R.id.btnDelete:

                //対象の番号を取得する
                EditText inputNum = findViewById(R.id.editTextNum);
                String num = inputNum.getText().toString();

                if(num.equals("")) {
                    TextView textMessage = findViewById(R.id.textMessage);
                    textMessage.setText("番号を入力してください");

                }else{

                    shainDTO =  new ShainDTO();
                    //画面データの取り込み
                    shainDTO.setNum(num);

                    //サービスの呼び出し
                    intent.setAction("jp.inotou.android.kpi_sample_03.DELETE");
                    intent.setClass(getApplicationContext(), ShainService.class);
                    //intent.setPackage("");
                    intent.putExtra("shainDTO", shainDTO);
                    intent.putExtra("procType","DELETE");
                    startService(intent);
                }
                break;

            //メニューへ戻る
            case R.id.btnBackToMenu:
                finish();
                break;

        }
    }

}
