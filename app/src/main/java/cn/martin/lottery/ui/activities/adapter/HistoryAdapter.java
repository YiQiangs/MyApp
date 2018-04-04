package cn.martin.lottery.ui.activities.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import java.util.List;

import cn.martin.lottery.ui.base.adapter.BasePagerAdapter;
import cn.martin.lottery.ui.fragment.base.BaseFragment;

/**
 * Created by junbo on 7/11/2016.
 */

public class HistoryAdapter extends BasePagerAdapter {
    public HistoryAdapter(FragmentManager fm, List<BaseFragment> fragmentList, Context context) {
        super(fm, fragmentList, context);
    }

    public HistoryAdapter(FragmentManager fm, List<BaseFragment> fragmentList) {
        super(fm, fragmentList);
    }
}
