package cn.martin.lottery.ui.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.martin.lottery.R;
import cn.martin.lottery.ui.activities.RuleActivity;
import cn.martin.lottery.ui.fragment.base.BaseFragment;
import cn.martin.lottery.ui.fragment.presenter.MePresenter;
import cn.martin.lottery.ui.fragment.presenter.MePresenterImpl;
import cn.martin.lottery.ui.fragment.view.MeView;
import cn.martin.lottery.utils.Constants;
import cn.martin.lottery.utils.LotteryUtils;

/**
 * 关于界面
 * Created by junbo on 11/11/2016.
 */

public class MeFragment extends BaseFragment<MePresenter> implements MeView {
    private AlertDialog ad;
    @BindView(R.id.switch_wifi)
    Switch wifi;
    //    @BindView(R.id.checkVersion)
//    TextView checkVersion;
//    @BindView(R.id.about)
//    TextView about;
    @BindView(R.id.lotteryRule)
    TextView rule;


    @Override
    protected void initView() {
        boolean savedWifi = LotteryUtils.getBooleanFromSharePreferences(getActivity(), Constants.Setting.WIFI_KEY, false);
        wifi.setChecked(savedWifi);
    }

    @OnClick(R.id.switch_wifi)
    public void onSwitch() {
        LotteryUtils.saveBooleanToSharePreferences(getActivity(), Constants.Setting.WIFI_KEY, wifi.isChecked());
    }

//    @OnClick(R.id.checkVersion)
//    public void checkVersion() {
//        presenter.checkVersion();
//    }

//    @OnClick(R.id.about)
//    public void about() {
//    }

    @OnClick(R.id.lotteryRule)
    public void rule() {
        startActivity(new Intent(getActivity(), RuleActivity.class));
    }

    @Override
    protected MePresenter getPresenter() {
        return new MePresenterImpl();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_me;
    }

    @Override
    public String getTitle() {
        return "设置";
    }


    private void showDialog(final String url) {

    }

    @Override
    public void newVersion(String url) {
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void update(List list) {
        super.update(null);
    }

    @Override
    public void fail() {

    }
}
