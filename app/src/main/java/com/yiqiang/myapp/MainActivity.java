package com.yiqiang.myapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.peng.one.push.OnePush;
import com.peng.one.push.utils.JsonUtils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private static final String PUSH_DATA = "PUSH_DATA";
    private static final String ACTION_LOG = "com.peng.one.push.ACTION_LOG";


    private TextView tvLog;


    private int[] resId = new int[]{
            R.id.btn_register_push, R.id.btn_unregister_push, R.id.btn_set_tag,
            R.id.btn_unset_tag, R.id.btn_bind_alias, R.id.btn_unbind_alias};

    private BroadcastReceiver mLogReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            initCurrentPushData(intent);
        }
    };

    private IntentFilter mLogFilter = new IntentFilter(ACTION_LOG);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTitle();
        initView();
        initEvent();
        initCurrentPushData(getIntent());
        MainActivityPermissionsDispatcher.requestAllPermissionWithCheck(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestAllPermission();
    }

    private void initTitle() {
        getSupportActionBar().setTitle("Push:" + OnePush.getPushPlatFormName() + "--Rom:" + Build.MANUFACTURER );
    }

    private void initView() {
        tvLog = (TextView) findViewById(R.id.log);
    }

    private void initEvent() {
        for (int i : resId) {
            findViewById(i).setOnClickListener(this);
        }
        this.mLogFilter.addCategory(getPackageName());
        registerReceiver(mLogReceiver, mLogFilter);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        initCurrentPushData(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register_push:
                OnePush.register();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        httpUrlConnGet();
                    }
                }).start();
                break;
            case R.id.btn_unregister_push:
                OnePush.unRegister();
                break;
            case R.id.btn_set_tag:
                OnePush.addTag("test");
                OnePush.addTag("kkkk");
                OnePush.addTag("1233");
                OnePush.addTag("1111");
                OnePush.addTag("2222");
                break;
            case R.id.btn_unset_tag:
                OnePush.deleteTag("test");
                break;
            case R.id.btn_bind_alias:
                OnePush.bindAlias("test");
                break;
            case R.id.btn_unbind_alias:
                OnePush.unBindAlias("test");
                break;
        }
    }

    private void initCurrentPushData(Intent intent) {
        String stringExtra = intent.getStringExtra(PUSH_DATA);
        if (!TextUtils.isEmpty(stringExtra)) {
            tvLog.append(stringExtra);
            tvLog.append(System.getProperty("line.separator"));
            tvLog.append(System.getProperty("line.separator"));
        }
    }

    /**
     * 启动这个Activity
     * @param context
     * @param pushData
     */
    public static void start(Context context, String pushData) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(PUSH_DATA, pushData);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        }
        context.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public static void sendLogBroadcast(Context context, String log) {
        Intent intent = new Intent(ACTION_LOG);
        intent.putExtra(PUSH_DATA, log);
        intent.addCategory(context.getPackageName());
        context.sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mLogReceiver);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        tvLog.setText("");
        return false;
    }

    @NeedsPermission(value = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.ACCESS_FINE_LOCATION})
    public void requestAllPermission() {

    }

    @OnShowRationale(value = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.ACCESS_FINE_LOCATION})
    void showRationale(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("请同意app权限，否则app，将不能使用")
                .setPositiveButton("继续", (dialog, button) -> request.proceed())
                .setNegativeButton("取消", (dialog, button) -> request.cancel())
                .show();
    }

    @OnPermissionDenied(value = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.ACCESS_FINE_LOCATION})
    void showDenied() {
        Toast.makeText(this, "拒绝，需要到系统设置，自己设定，否者有可能导致消息推送不成功！", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(value = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.ACCESS_FINE_LOCATION})
    void showNeverAskAgain() {

    }

    @SuppressLint("NeedOnRequestPermissionsResult")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }


    public void httpUrlConnGet(){
        HttpURLConnection urlConnection = null;
        URL url = null;
        try {
            String urlStr = "http://www.27305.com/frontApi/getAboutUs?appid=12171601";
            url = new URL(urlStr);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            if(urlConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                InputStream in = urlConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line = null;
                StringBuffer buffer = new StringBuffer();
                while((line=br.readLine())!=null){
                    buffer.append(line);
                }
                in.close();
                br.close();
                String res = buffer.toString();
                JSONObject jsonObject = new JSONObject(res);
                Result result = new Result();
                result.setAppid(jsonObject.getString("appid"));
                result.setAppname(jsonObject.getString("appname"));
                result.setIsshowwap(jsonObject.getString("isshowwap"));
                result.setWapurl(jsonObject.getString("wapurl"));
                result.setStatus(jsonObject.getString("status"));
                result.setDesc(jsonObject.getString("desc"));
                if(result.getStatus().equals("1")){
                    mHandler.sendEmptyMessage(0);
                    Message msg = new Message();
                    msg.obj = result;
                    mHandler.handleMessage(msg);
                }else{
                    mHandler.sendEmptyMessage(1);
                }
            }else{
                mHandler.sendEmptyMessage(2);
            }
        } catch (Exception e) {
            mHandler.sendEmptyMessage(3);
        }finally{
            urlConnection.disconnect();
        }
    }

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    //完成主界面更新,拿到数据
                    Result data = (Result) msg.obj;
                    if (data != null && data.getIsshowwap().equals("1")){
                        Intent intent = new Intent(MainActivity.this,WebViewActivity.class);
                        intent.putExtra("url",data.getWapurl());
                        startActivity(intent);
                    }
                    System.out.println(data);
                    break;
                default:
                    break;
            }
        }
    };

}

