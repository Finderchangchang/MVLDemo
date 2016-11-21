package liuliu.mvldemo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import liuliu.mvldemo.R;
import liuliu.mvldemo.listener.LoginListener;
import liuliu.mvldemo.listener.MainListener;
import liuliu.mvldemo.model.MeiNvModel;
import liuliu.mvldemo.view.ILoginView;
import liuliu.mvldemo.view.IMainView;

public class MainActivity extends AppCompatActivity implements IMainView {
    MainListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListener = new MainListener(this);
        mListener.loadMv();//页面加载加载美女图片
    }

    @Override
    public void FH_meinv(List<MeiNvModel.NewslistBean> list) {
        Toast.makeText(this, "美女共有：" + list.size() + "个", Toast.LENGTH_SHORT).show();
    }
}
