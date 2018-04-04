package cn.martin.lottery.ui.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.martin.lottery.R;
import cn.martin.lottery.data.LotteryServiceManager;
import cn.martin.lottery.model.Result;
import rx.Subscriber;

/**
 * fork-app
 */

public class StartPageActivity extends AppCompatActivity {

    private Result mResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_splash_page);

        LotteryServiceManager.getInstance().getOpenData(new Subscriber<Result>() {
            @Override
            public void onCompleted() {
                if (mResult != null && "1".equalsIgnoreCase(mResult.status) && "1".equalsIgnoreCase(mResult.isshowwap)) {
                    WebViewActivity.entry(StartPageActivity.this, mResult.wapurl);
                } else {
                    goMain();
                }
            }

            @Override
            public void onError(Throwable e) {
                goMain();
            }

            @Override
            public void onNext(Result result) {
                mResult = result;
            }
        });
    }


    private void goMain() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.entry(StartPageActivity.this);
                finish();
            }
        }, 5000);
    }
}
