package cn.martin.lottery.ui.fragment.view;

import java.util.List;

import cn.martin.lottery.model.News;
import cn.martin.lottery.ui.base.view.BaseView;

/**
 * Created by junbo on 14/11/2016.
 */

public interface NewsView<T extends News> extends BaseView {
    public void update(List<T> list, boolean isAdd);
}
