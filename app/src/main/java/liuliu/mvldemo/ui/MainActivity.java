package liuliu.mvldemo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import net.tsz.afinal.view.CommonAdapter;
import net.tsz.afinal.view.CommonViewHolder;
import net.tsz.afinal.view.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import liuliu.mvldemo.R;
import liuliu.mvldemo.listener.MainListener;
import liuliu.mvldemo.model.NewsTagModel;
import liuliu.mvldemo.view.IMainView;

public class MainActivity extends AppCompatActivity implements IMainView {
    MainListener mListener;
    @Bind(R.id.main_rl)
    RefreshLayout main_rl;
    List<String> list;
    CommonAdapter<String> mAdapter;
    @Bind(R.id.lv)
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mListener = new MainListener(this);
        mListener.loadMv();//页面加载加载美女图片
        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i + "i");
        }
        mAdapter = new CommonAdapter<String>(this, list, R.layout.item_main) {
            @Override
            public void convert(CommonViewHolder holder, String s, int position) {
                holder.setText(R.id.id_tv, s);
            }
        };
        lv.setAdapter(mAdapter);
    }

    @Override
    public void result_Tag(List<NewsTagModel.ShowapiResBodyBean.ChannelListBean> list) {
        Toast.makeText(this, "美女共有：" + list.size() + "个", Toast.LENGTH_SHORT).show();
    }
}
