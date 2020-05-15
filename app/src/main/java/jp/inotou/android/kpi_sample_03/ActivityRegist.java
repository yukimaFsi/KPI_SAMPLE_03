package jp.inotou.android.kpi_sample_03;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import jp.inotou.android.kpi_sample_03.service.ShainService;
import android.view.inputmethod.InputMethodManager;

//新規登録画面
public class ActivityRegist extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
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

            String message = "";
            ShainDTO dto =(ShainDTO)intent.getSerializableExtra("shainDTO");
            int status =(int)intent.getSerializableExtra("status");
            if(dto==null)return;

            finish();//自画面を閉じる
            message = (status==0) ? "その番号は登録済みです" : "登録しました";
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

            //新規登録
            case R.id.btnRegist:
                //入力された番号・氏名を取得する
                EditText inputNum = findViewById(R.id.editTextNum);
                EditText inputName = findViewById(R.id.editTextName);
                String num = inputNum.getText().toString();
                String name = inputName.getText().toString();


                if((num.equals("") || name.equals(""))) {

                    //入力されていない
                    this.setContentView(R.layout.activity_regist);
                    //オブジェクト取得
                    EditText textNum = findViewById(R.id.editTextNum);
                    EditText textName = findViewById(R.id.editTextName);
                    TextView textMessage = findViewById(R.id.textMessage);
                    //値のセット
                    textNum.setText(num);
                    textName.setText(name);
                    textMessage.setText("番号と氏名を入力してください");

                } else {

                    boolean flg = false;
//                    for (String key : ShainMap.keySet()) {
////                        if(key.equals(num)){
////                            flg = true;
////                        }
////                    }

                    if(flg){
                        //登録済みの番号である
                        this.setContentView(R.layout.activity_regist);
                        //オブジェクト取得
                        EditText textNum = findViewById(R.id.editTextNum);
                        EditText textName = findViewById(R.id.editTextName);
                        TextView textMessage = findViewById(R.id.textMessage);
                        //値のセット
                        textNum.setText(num);
                        textName.setText(name);
                        textMessage.setText("その番号は登録済です");

                    } else {

                        //新規登録する

                        shainDTO =  new ShainDTO();
                        shainDTO.setNum(num);
                        shainDTO.setName(name);

                        //サービスの呼び出し
                        intent.setAction("jp.inotou.android.kpi_sample_03.INSERT");
                        intent.setClass(getApplicationContext(), ShainService.class);
                        intent.putExtra("shainDTO", shainDTO);
                        intent.putExtra("procType","INSERT");
                        startService(intent);
                    }
                }
                break;

            //メニューへ戻る
            case R.id.btnBackToMenu:
                finish();
                break;

        }
    }

}
