package cn.martin.lottery.ui.fragment.presenter;

import cn.martin.lottery.data.LotteryServiceManager;
import cn.martin.lottery.model.LotteryHistory;
import cn.martin.lottery.ui.base.presenter.BaseFailPresenterImpl;
import cn.martin.lottery.ui.fragment.view.ConcreteLotteryView;
import rx.Subscriber;


/**
 * Created by junbo on 7/11/2016.
 */

public class ConcreteLotteryHistoryPresenterImpl extends BaseFailPresenterImpl<ConcreteLotteryView> implements ConcreteLotteryHistoryPresenter<ConcreteLotteryView> {
    int currentPage=1;
    int pageCount=10;
    String lotId;

    public ConcreteLotteryHistoryPresenterImpl(String lotId) {
        this.lotId = lotId;
    }

    @Override
    public void start() {
        fetchData();
    }

    @Override
    public void refresh() {
        currentPage=1;
        fetchData();
    }

    @Override
    public void loadMore() {
        currentPage++;
        fetchData();
    }

    public void fetchData() {
        LotteryServiceManager.getInstance().getHistory360(new Subscriber<LotteryHistory>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                view.fail();

            }

            @Override
            public void onNext(LotteryHistory o) {
                pageCount = Integer.parseInt(o.getPageCount());
                if (view != null) {
                    if (currentPage == 1) {
                        view.update(o.getList(), false);
                    } else {
                        view.update(o.getList(), true);
                    }
                }

            }
        }, lotId, currentPage + "");
    }


}
