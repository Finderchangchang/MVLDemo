package liuliu.mvldemo.util;

import liuliu.mvldemo.model.MeiNvModel;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/11/21.
 */

public interface BaiDuAPI {
    @GET("txapi/mvtp/meinv")
    Observable<MeiNvModel> getMvs(@Query("num") String num);
}
