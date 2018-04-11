package wyw.com.volleydemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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
    public static final String BASE_URL_MEITU = "https://www.apiopen.top/meituApi";

    private TextView textViewByJson;
    private TextView textViewByString;

    //文字recycleview
    private RecyclerView recyclerView;

    //图片recycleview
    private RecyclerView recyclerViewStr;
    //建议在application类中初始化
    private HttpUtil httpUtil;
    private Context mActivity;

    private List<BeanInfo> datas = new ArrayList<>();
    private ContentAdapter contentAdapter;
    private ContentAdapterStr contentAdapterStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;
        httpUtil = HttpUtil.getInstance(this);


        textViewByJson = findViewById(R.id.tv_getNetDataByJson);
        textViewByString = findViewById(R.id.tv_getNetDataByString);
        recyclerView = findViewById(R.id.recy_content);
        recyclerViewStr = findViewById(R.id.recy_contentString);

        textViewByJson.setOnClickListener(this);
        textViewByString.setOnClickListener(this);

        //json请求
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        //string请求
        final StaggeredGridLayoutManager strManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        //防止回滚item交换
        strManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerViewStr.setLayoutManager(strManager);

//        recyclerViewStr.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                strManager.invalidateSpanAssignments(); //防止第一行到顶部有空白区域
//            }
//        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.tv_getNetDataByJson: { //通过json请求
                recyclerView.setVisibility(View.VISIBLE);
                recyclerViewStr.setVisibility(View.GONE);
                Map<String, Object> params = new HashMap<>();
                params.put("type", "2");
                params.put("page", "0");
                httpUtil.doPostByJson(BASE_URL, params, requestListener);

            }
            break;
            case R.id.tv_getNetDataByString: { //通过string请求
                recyclerViewStr.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                Map<String, String> params = new HashMap<>();
                params.put("page", "0");
                httpUtil.doPostByStr(BASE_URL_MEITU, params, strRequestListener);

            }
            break;
        }
    }

    /**
     * 网络请求成功回调的监听---json
     */
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

    /**
     * 网络请求成功回调的监听---string
     */
    private RequestListener strRequestListener = new AbsRequestListener(this) {
        @Override
        public void onSuccess(String result) {
            datas.clear();
            datas.addAll(JsonUtils.json2List(result, BeanInfo.class));
            if (null == contentAdapterStr) {
                contentAdapterStr = new ContentAdapterStr(mActivity, datas);

                recyclerViewStr.setAdapter(contentAdapterStr);
            } else {
                contentAdapterStr.notifyDataSetChanged();
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

    /**
     * String返回数据的适配器
     */
    class ContentAdapterStr extends RecyclerView.Adapter<ContentHolderStr> {
        private Context mContext;
        private List<BeanInfo> mDatas;

        public ContentAdapterStr(Context context, List<BeanInfo> datas) {
            this.mContext = context;
            this.mDatas = datas;
        }


        @Override
        public ContentHolderStr onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(mContext).inflate(R.layout.item_recyle_str, parent, false);

            return new ContentHolderStr(view);
        }

        @Override
        public void onBindViewHolder(ContentHolderStr holder, int position) {
            Glide.with(mContext).load(mDatas.get(position).getUrl()).placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher).into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }
    }


    static class ContentHolderStr extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public ContentHolderStr(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_item_content);
        }
    }
}


