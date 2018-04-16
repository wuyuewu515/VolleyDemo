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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wyw.com.volleydemo.netUtils.AbsRequestListener;
import wyw.com.volleydemo.netUtils.HttpUtil;
import wyw.com.volleydemo.netUtils.RequestListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    //接口来自
    //https://free-api.heweather.com/s6/weather/now?parameters
    public static final String BASE_URL = "https://free-api.heweather.com/s6/weather/now";
    public static final String BASE_URL_MEITU = "https://www.apiopen.top/meituApi";

    private TextView textViewByJson;
    private TextView textViewByString;
    private TextView textViewResult;

    //建议在BaseActiviy类中初始化,并设置为protected
    private HttpUtil httpUtil;
    private Context mActivity;
    private String key = "0ae2908783b34579b5af9e8b369aae22";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;
        httpUtil = HttpUtil.getInstance(this);


        textViewByJson = findViewById(R.id.tv_getNetDataByJson);
        textViewByString = findViewById(R.id.tv_getNetDataByString);
        textViewResult = findViewById(R.id.tv_result);


        textViewByJson.setOnClickListener(this);
        textViewByString.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.tv_getNetDataByJson: { //通过json请求

                //暂时不可用，没找到参数是需要json 数据类型的
//                Map<String, Object> params = new HashMap<>();
//                params.put("city", "CN101010100");
//                params.put("key", "0ae2908783b34579b5af9e8b369aae22");
//                httpUtil.doPostByJson(BASE_URL, params, requestListener);

            }
            break;
            case R.id.tv_getNetDataByString: { //通过string请求

                String location = "北京";

                Map<String, String> params = new HashMap<>();
                params.put("key", key);
                params.put("location", location);
                httpUtil.doPostByStr(BASE_URL, params, strRequestListener);

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

            textViewResult.setText("jsonObject返回的结果==" + result);
        }
    };

    /**
     * 网络请求成功回调的监听---string
     */
    private RequestListener strRequestListener = new AbsRequestListener(this) {
        @Override
        public void onSuccess(String result) {
            textViewResult.setText("String 返回的结果==" + result);
        }
    };

    //建议放在baseactivity中
    @Override
    protected void onStop() {
        super.onStop();
        httpUtil.cancelAllRequestQueue();
    }

}


