package liuliu.mvldemo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import liuliu.mvldemo.R;
import liuliu.mvldemo.listener.LoginListener;
import liuliu.mvldemo.view.ILoginView;

/**
 * 登录
 * Created by 畅畅 on 2016/11/21.
 * QQ群481606175
 */

public class LoginActivity extends AppCompatActivity implements ILoginView {
    Button login_btn;
    EditText pwd_et;
    EditText usr_name_tv;
    LoginListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_btn = (Button) findViewById(R.id.login_btn);
        pwd_et = (EditText) findViewById(R.id.pwd_et);
        usr_name_tv = (EditText) findViewById(R.id.usr_name_tv);
        mListener = new LoginListener(this);
        login_btn.setOnClickListener(v ->
                mListener.normalLogin(usr_name_tv.getText().toString(), pwd_et.getText().toString())
        );
    }

    @Override
    public void LoginRequest(boolean result) {
        if (result) {
            Toast.makeText(this, "登陆成功~~", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "登陆失败~~", Toast.LENGTH_SHORT).show();
        }
    }
}
