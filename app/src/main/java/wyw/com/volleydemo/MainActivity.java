package wyw.com.volleydemo;

import android.content.Context;
import android.net.sip.SipSession;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wyw.com.volleydemo.netUtils.AbsRequestListener;
import wyw.com.volleydemo.netUtils.HttpUtil;
import wyw.com.volleydemo.netUtils.RequestListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //接口来自
    //https://blog.csdn.net/c__chao/article/details/78573737
    public static final String BASE_URL = "https://www.apiopen.top/satinApi";
    private TextView textView;
    private RecyclerView recyclerView;
    //建议在application类中初始化
    private HttpUtil httpUtil;
    private Context mActivity;

    private List<BeanInfo> datas = new ArrayList<>();
    private ContentAdapter contentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;
        httpUtil = HttpUtil.getInstance(this);

        textView = findViewById(R.id.tv_getNetData);
        recyclerView = findViewById(R.id.recy_content);


        textView.setOnClickListener(this);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);


    }

    @Override
    public void onClick(View view) {
        Map<String, Object> params = new HashMap<>();
        params.put("type", "2");
        params.put("page", "0");
        httpUtil.doPostByJson(BASE_URL, params, requestListener);
    }


    private RequestListener requestListener = new AbsRequestListener(this) {
        @Override
        public void onSuccess(String result) {
            datas.clear();
            datas.addAll(JsonUtils.json2List(result, BeanInfo.class));
            if (null == contentAdapter) {
                contentAdapter = new ContentAdapter(mActivity, datas);
                recyclerView.setAdapter(contentAdapter);
            } else {
                contentAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        httpUtil.cancelAllRequestQueue();
    }

    /**
     * 返回数据的适配器
     */
    class ContentAdapter extends RecyclerView.Adapter<ContentHolder> {
        private Context mContext;
        private List<BeanInfo> mDatas;

        public ContentAdapter(Context context, List<BeanInfo> datas) {
            this.mContext = context;
            this.mDatas = datas;
        }

        @Override
        public ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(mContext).inflate(R.layout.item_recyle, parent, false);

            return new ContentHolder(view);
        }

        @Override
        public void onBindViewHolder(ContentHolder holder, int position) {

            holder.textView.setText(mDatas.get(position).getText());
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }
    }


    static class ContentHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ContentHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_item_content);
        }
    }
}


