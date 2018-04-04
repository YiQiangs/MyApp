package cn.true123.lottery.data;


import cn.true123.lottery.model.Result;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by feimeng0530 on 2016/3/17.
 */
public interface ForkApiService {
    @GET("frontApi/getAboutUs?appid=12171601")
    Observable<Result> getData(@Query("appid") String appid);
}
