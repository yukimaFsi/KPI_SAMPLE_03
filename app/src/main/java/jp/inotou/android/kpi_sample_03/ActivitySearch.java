package jp.inotou.android.kpi_sample_03;

import android.app.Activity;
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


//検索画面
public class ActivitySearch extends AppCompatActivity {
    private Activity act;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
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

            ShainDTO dto =(ShainDTO)intent.getSerializableExtra("shainDTO");
            if(dto==null)return;

            //自画面に再表示

            //オブジェクト取得
            EditText textNum = findViewById(R.id.editTextNum);       //検索条件
            String num = textNum.getText().toString();
            TextView textRetNum = findViewById(R.id.textRetNum);     //検索結果
            TextView textRetName = findViewById(R.id.textRetName);   //検索結果
            //値のセット
            textNum.setText(num);
            textRetNum.setText(dto.getNum());
            if(dto.getName().equals("")){
                textRetName.setText("この番号は未登録です");
            }else{
                textRetName.setText(dto.getName());
            }

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

            //検索
            case R.id.btnSearch:

                //入力された番号を取得する
                EditText inputNum = findViewById(R.id.editTextNum);
                String num = inputNum.getText().toString();

                shainDTO =  new ShainDTO();
                //画面データの取り込み
                shainDTO.setNum(num);

                //サービスの呼び出し
                intent.setAction("jp.inotou.android.kpi_sample_03.SELECT");
                intent.setClass(getApplicationContext(), ShainService.class);
                //intent.setPackage("");
                intent.putExtra("shainDTO", shainDTO);
                intent.putExtra("procType","SELECT");
                startService(intent);
                break;

            //メニューへ戻る
            case R.id.btnBackToMenu:
                finish();
                break;
        }
    }
}
