package cn.true123.lottery.ui.activities;

import android.view.Menu;
import android.view.MenuItem;

import cn.true123.lottery.R;
import cn.true123.lottery.data.LotteryServiceManager;
import cn.true123.lottery.listener.OnTagChangedListener;
import cn.true123.lottery.model.Result;
import cn.true123.lottery.ui.fragment.NavFragment;
import rx.Subscriber;

public class MainActivity extends BaseActivity implements OnTagChangedListener {
    NavFragment fragment;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected void initPresenter() {

    }

    private Result mResult;

    @Override
    protected void iniView() {
        LotteryServiceManager.getInstance().getOpenData(new Subscriber<Result>() {
            @Override
            public void onCompleted() {
                if (mResult != null &&
                        "1".equalsIgnoreCase(mResult.status) &&
                        "1".equalsIgnoreCase(mResult.isshowwap)) {
                    {
                        WebViewActivity.entry(MainActivity.this, mResult.wapurl);
                    }
                }
                fillView();
            }

            @Override
            public void onError(Throwable e) {
                fillView();
            }

            @Override
            public void onNext(Result result) {
                mResult = result;
            }
        });
    }

    private void fillView() {
        fragment = (NavFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        fragment.setOnTagChangedListener(this);
        fragment.setUp(this, getSupportFragmentManager(), R.id.content);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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

    @Override
    public void setTitle(String title) {
        toolbar.setTitle(title);
    }


}
