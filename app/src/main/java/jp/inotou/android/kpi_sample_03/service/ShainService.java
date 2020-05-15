package jp.inotou.android.kpi_sample_03.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;

import jp.inotou.android.kpi_sample_03.ShainDTO;
import jp.inotou.android.kpi_sample_03.ShainProvDAO;

public class ShainService extends Service implements Runnable{

    private Thread thread;
    private ShainProvDAO provDAO;
    final static String INSERT="jp.inotou.android.kpi_sample_03.INSERT";
    final static String DELETE="jp.inotou.android.kpi_sample_03.DELETE";
    final static String SELECT="jp.inotou.android.kpi_sample_03.SELECT";

    @Override
    public void onCreate(){
        android.os.Debug.waitForDebugger();
        Log.d("ShainService", "Create now");
        this.thread=null;
        //this.webDAO=new PlanWebDAO(this);
        this.provDAO=new ShainProvDAO(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("ShainService", "Start now");
        while(!Queue.getInstance().enq(intent)){
            Log.d("ShainService", "Wait now");
            try{Thread.sleep(2000);}catch(Exception e){}
        }
        if(this.thread ==null){
            this.thread=new Thread(this);
            this.thread.start();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("ShainService", "Destroy now");
        this.thread=null;
    }
    /* *****************************************************************
     * 処理の本体(キューからIntentを取り出しサーバへ送信)
     *******************************************************************/
    public void run() {
        Intent intent=null;
        ShainDTO shainDTO=null;
        ShainDTO shainDTOcheck=null;
        String srtAction="";
        int retry=0;
        int retCount = 0;
        int status = 0;
        checkQueue();
        while(retry++<5){
            intent=Queue.getInstance().deq();
            while(intent!=null){
                Log.d("ShainService", "Running now "+intent.getStringExtra("procType"));
                shainDTO=(ShainDTO) intent.getSerializableExtra("shainDTO");
                if(shainDTO==null)continue;
                srtAction=intent.getAction();

                if(intent.getAction().equals(INSERT)){
                    shainDTOcheck = provDAO.selectOne(shainDTO.getNum());
                    if(shainDTOcheck.getName().equals("")){
                        status = 1;
                        provDAO.insertOne(shainDTO);
                    }else{
                        //statusは0のまま。
                        //その番号は登録済みです
                    }
                }
                if(intent.getAction().equals(DELETE)){
                    shainDTOcheck = provDAO.selectOne(shainDTO.getNum());
                    if(shainDTOcheck.getName().equals("")){
                        //statusは0のまま。
                        //その番号は未登録です
                    }else{
                        status = 1;
                        retCount = provDAO.deleteOne(shainDTO.getNum());
                    }
                }
                if(intent.getAction().equals(SELECT)){
                    shainDTO = provDAO.selectOne(shainDTO.getNum());
                }
                intent=Queue.getInstance().deq();
                retry=0;
                checkQueue();
            }
            Log.d("ShainService", "Sleep now "+retry);
            //try{Thread.sleep(3000);}catch(Exception e){}
            try{Thread.sleep(30);}catch(Exception e){}
        }
        Log.d("ShainService", "Stop now");

        Intent broadcast = new Intent();
        broadcast.setAction("DO_ACTION");
        broadcast.putExtra("status", status);
        broadcast.putExtra("retCount", retCount);
        broadcast.putExtra("shainDTO", shainDTO);
        getBaseContext().sendBroadcast(broadcast);

        stopSelf();

    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;

    }
    /* *****************************************************************
     * キューの状態をチェックし，５０%以上の滞留があればノティフィケーションに表示
     *******************************************************************/
    private void checkQueue(){
        NotificationManager nm=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        if(Queue.getInstance().getArray().size()<1)return;
        if(Queue.QUEUE_SIZE/Queue.getInstance().getArray().size()<2){
            Notification notif=new Notification();
            //notif.icon=R.drawable.caution;
            notif.number=Queue.getInstance().getArray().size();
            notif.defaults |=Notification.DEFAULT_SOUND;
            notif.flags |=Notification.FLAG_AUTO_CANCEL;
            notif.when=System.currentTimeMillis();
            PendingIntent pi =
                    PendingIntent.getActivity(getApplicationContext(), 0, new Intent(), 0);
            //notif.setLatestEventInfo(getApplicationContext(), "サーバ未処理件数の増加","更新処理を抑えて下さい。", pi);
            nm.notify(0, notif);
        }else nm.cancel(0);
    }
}
/* *****************************************************************
 * 待ち行列(ArrayListを使用)
 *******************************************************************/
class Queue{
    private static ArrayList<Intent> array;
    private static Queue instance=null;
    final static int QUEUE_SIZE=24;
    private Queue(){
        array=new ArrayList<Intent>();
    }
    public static synchronized Queue getInstance(){
        if(instance==null){
            instance=new Queue();
        }
        return instance;
    }
    public synchronized Intent deq(){
        Intent intent=null;
        if(!array.isEmpty()){
            intent=array.get(0);
            array.remove(0);
            array.trimToSize();
        }
        return intent;
    }
    public synchronized boolean enq(Intent intent){
        if(array.size()>=QUEUE_SIZE)return false;
        array.add(intent);
        return true;
    }
    public ArrayList<Intent> getArray(){return array; }


}
