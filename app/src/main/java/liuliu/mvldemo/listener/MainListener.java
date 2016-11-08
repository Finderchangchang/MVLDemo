package liuliu.mvldemo.listener;

import liuliu.mvldemo.view.IMainView;

/**
 * Created by 畅畅 on 2016/11/8.
 * QQ群481606175
 */

public class MainListener implements MainMView{
    private IMainView mView;

    public MainListener(IMainView mView) {
        this.mView = mView;
    }

    /**
     * @param name 用户名
     * @param pwd 密码
     */
    @Override
    public void normalLogin(String name,String pwd){
        if(("1").equals(name)&&("1").equals(pwd)){
            mView.LoginRequest(true);
        }else{
            mView.LoginRequest(false);
        }
    }
}
interface MainMView{
    void normalLogin(String name,String pwd);
}
