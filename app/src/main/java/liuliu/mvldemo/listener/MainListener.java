package liuliu.mvldemo.listener;

import java.util.ArrayList;
import java.util.List;

import liuliu.mvldemo.model.MeiNvModel;
import liuliu.mvldemo.model.NewsTagModel;
import liuliu.mvldemo.util.HttpUtil;
import liuliu.mvldemo.view.IMainView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Finder丶畅畅 on 2016/11/21 23:48
 * QQ群481606175
 */

public class MainListener implements MainMView {
    private IMainView mView;
    List<NewsTagModel.ShowapiResBodyBean.ChannelListBean> mList;

    public MainListener(IMainView mView) {
        this.mView = mView;
        mList = new ArrayList<>();
    }

    @Override
    public void loadMv() {
        HttpUtil.load().getNews_PD()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(userModel -> {//返回List
                    return Observable.from(userModel.getShowapi_res_body().getChannelList());
                })
                .toMap(model -> {//循环获得list中的model
                    mList.add(model);
                    return "";
                })
                .subscribe(val -> {
                    mView.result_Tag(mList);
                });
    }
}

interface MainMView {
    void loadMv();
}
