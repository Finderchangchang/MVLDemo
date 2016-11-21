package liuliu.mvldemo.listener;

import liuliu.mvldemo.view.ILoginView;

/**
 * Created by 畅畅 on 2016/11/8.
 * QQ群481606175
 */

public class LoginListener implements LoginMView {
    private ILoginView mView;

    public LoginListener(ILoginView mView) {
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
interface LoginMView {
    void normalLogin(String name,String pwd);
}
