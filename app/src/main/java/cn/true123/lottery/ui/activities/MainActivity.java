package cn.true123.lottery.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import cn.true123.lottery.R;
import cn.true123.lottery.listener.OnTagChangedListener;
import cn.true123.lottery.ui.fragment.NavFragment;

public class MainActivity extends BaseActivity implements OnTagChangedListener {

    public static void entry(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

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



    @Override
    protected void iniView() {
        fillView();
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
