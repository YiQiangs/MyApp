package cn.martin.lottery.ui.fragment.presenter;

import java.util.List;
import java.util.Map;

import cn.martin.lottery.data.NewsServiceManager;
import cn.martin.lottery.model.News;
import cn.martin.lottery.ui.base.presenter.BaseFailPresenterImpl;
import cn.martin.lottery.ui.fragment.view.NewsView;
import cn.martin.lottery.utils.Constants;
import rx.Subscriber;

/**
 * Created by junbo on 14/11/2016.
 */

public class NewsPresenterImpl extends BaseFailPresenterImpl<NewsView> implements NewsPresenter<NewsView> {
    int page = 0;
    int pageSize = 20;

    @Override
    public void loadMore() {
        load();
    }

    private void load() {
        NewsServiceManager.getInstance()
                .getNews("", Constants.HEADLINE_TYPE, Constants.LOTTERY_ID, getPage())
                .subscribe(new Subscriber<Map<String, List<News>>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.fail();
                    }

                    @Override
                    public void onNext(Map<String, List<News>> stringListMap) {
                        if (view != null) {
                            view.update(stringListMap.get(Constants.LOTTERY_ID), page > 0);
                        }
                        page++;
                    }
                });
    }

    private int getPage() {
        return page * pageSize;
    }

    @Override
    public void refresh() {
        page = 0;
        load();
    }


    @Override
    public void start() {
        load();
    }
}
