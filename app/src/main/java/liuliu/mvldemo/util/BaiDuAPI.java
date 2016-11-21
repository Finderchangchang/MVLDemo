package liuliu.mvldemo.util;

import liuliu.mvldemo.model.MeiNvModel;
import liuliu.mvldemo.model.NewsTagModel;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/11/21.
 */

public interface BaiDuAPI {
    @GET("txapi/mvtp/meinv")
    Observable<MeiNvModel> getMvs(@Query("num") String num);

    @GET("showapi_open_bus/channel_news/channel_news")
    Observable<NewsTagModel> getNews_PD();//获得新闻分类
}
