package cn.martin.lottery.ui.fragment.presenter;

import java.util.List;

import cn.martin.lottery.ui.base.presenter.BaseFailPresenterImpl;
import cn.martin.lottery.ui.fragment.view.MainView;
import cn.martin.lottery.model.Lottery;
import cn.martin.lottery.data.LotteryServiceManager;
import rx.Subscriber;

/**
 * Created by junbo on 1/11/2016.
 */

public class MainPresenterImpl extends BaseFailPresenterImpl<MainView> implements MainPresenter<MainView> {

    @Override
    public void refresh() {
        fetchData();
    }

    @Override
    public void start() {
        fetchData();
    }
    private void fetchData(){
        view.showProgress();
        LotteryServiceManager.getInstance().getLastData360(new Subscriber<List<Lottery.IEntity>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                view.fail();
                view.dismissProgress();
            }

            @Override
            public void onNext(List<Lottery.IEntity> list) {
                view.dismissProgress();
                view.update(list);
            }
        });
    }
}
