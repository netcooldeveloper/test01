package test01.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{
    private int timeMM = 4;
    private int timeSS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button)findViewById(R.id.btn1)).setOnClickListener(this);
        ((Button)findViewById(R.id.btn2)).setOnClickListener(this);

        // 文字列型の変数定義と初期化
        String version = null;

        try {
            // ファイル="/proc/version"の中身をまとめてバッファに読み込む
            BufferedReader br = new BufferedReader(new FileReader("/proc/version"));

            // 読み込んだバッファの内容を String 型の変数に代入
            version = br.readLine();

        } catch (FileNotFoundException e) {
            // ファイルが存在しなかった場合のエラー処理
            Log.e("tag", e.toString());

        } catch (IOException e) {
            // 入出力に問題がある場合のエラー処理
            Log.e("tag", e.toString());

        }

        // TextView 型の変数を定義、textView01 のポインタを設定
        TextView tbxView01 = (TextView)findViewById(R.id.textView01);

        // version の文字列を textView01 にセット
        tbxView01.setText(version);

        // Long型の変数定義と初期化
        // * システムの起動経過時間をセット (ミリ秒)
        long ut = SystemClock.uptimeMillis();

        tbxView01.setText(
                "起動経過時間\n"
                + "(ミリ秒):" + ut + "\n"
                + "(時間単位)" + ( ut / 1000 / 60 / 60 ) + ":" + ( ut / 1000 / 60 % 60 )
        );

        // ロケーション情報を取得
        Locale lc = Locale.getDefault();

        // String型の変数定義と初期化
        // * 国名をセット
        String dc = lc.getDisplayCountry();

        // dc の文字列を textView01 にセット
        tbxView01.setText(dc);

        // Calendar型の変数定義と初期化
        Calendar cd = Calendar.getInstance();

        // int型の変数定義と初期化
        // * 現在の年(西暦)をセット
        int yr = cd.get(Calendar.YEAR);

        // 現在年を textView01 にセット
        tbxView01.setText(yr + "年");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if( intent.getAction().equals((intent.ACTION_BATTERY_CHANGED))){
                int level = intent.getIntExtra("level",0);
                int scale = intent.getIntExtra("scale",0);
                TextView tbxView01 = (TextView)findViewById(R.id.textView01);
                tbxView01.setText("Batter:"+((float)level / (float)scale * 100 ) +"%");
            }
        }
    };

    private void btn1Click(){
        // テキストボックスにテキストをセット
        ((TextView)findViewById(R.id.textView01)).setText("btn1 Click!");

        // 簡易メッセージ表示
        Toast.makeText(getApplicationContext(),"Btn1 Click Toast Short",Toast.LENGTH_SHORT).show();

        dispTime();
    }

    private void btn2Click(){
        // テキストボックスにテキストをセット
        ((TextView)findViewById(R.id.textView01)).setText("btn2 Click!");

        // 簡易メッセージ表示
        Toast.makeText(getApplicationContext(),"Btn1 Click Toast Long",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v){
        // onClick 発生した Id から分岐
        switch (v.getId()){
            case R.id.btn1:
                // btn1 をクリックした場合の処理
                btn1Click();
                break;

            case R.id.btn2:
                // btn2 をクリックした場合の処理
                btn2Click();
                break;

        }
    }

    @Override
    protected void onResume(){
        super.onResume();

        IntentFilter newIntentFilter = new IntentFilter();

        newIntentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);

        registerReceiver(br,newIntentFilter);
    }

    @Override
    protected void onPause(){
        super.onPause();

        unregisterReceiver(br);
    }

    private void dispTime(){
        DecimalFormat df = new DecimalFormat("00");

        String strTime = df.format(timeMM) + ":" + df.format(timeSS);

        // テキストボックスにテキストをセット
        ((TextView)findViewById(R.id.textView01)).setText(strTime);
    }

/*
    @Override
    public boolean onCreateOptionsMenu
    (Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
   */
}
