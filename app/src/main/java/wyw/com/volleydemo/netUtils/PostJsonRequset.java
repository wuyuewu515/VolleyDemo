package wyw.com.volleydemo.netUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者: 伍跃武
 * 时间： 2018/4/11
 * 描述：自定义参数为json的post请求
 */

public class PostJsonRequset extends JsonObjectRequest {

    /**
     * 请求超时时间
     */
    public static final int SOCKET_TIMEOUT = 60 * 1000;
    /**
     * 最大重新请求次数
     */
    public static final int MAX_RETRIES = 0;
    /**
     * 重新请求权重
     */
    public static final float BACK_OFF = 1.0f;
    /**
     * 字符编码
     */
    public static final String ENCODEING = "UTF-8";


    //以上代码建议放在全局的常亮类中，作为演示，暂时在这里

    /**
     * 构造函数
     *
     * @param url           请求链接
     * @param jsonRequest   参数转换成为json对象
     * @param listener      请求成功的监听
     * @param errorListener 请求失败的监听
     */
    public PostJsonRequset(String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, jsonRequest, listener, errorListener);
        //设置重试策略
        setRetryPolicy(new DefaultRetryPolicy(SOCKET_TIMEOUT, MAX_RETRIES, BACK_OFF));
    }

    /**
     * 重写请求编码
     *
     * @return
     */
    @Override
    protected String getParamsEncoding() {
        return ENCODEING;
    }

    /**
     * 重写请求头
     *
     * @return
     * @throws AuthFailureError
     */
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Charsert", getParamsEncoding());
//        headers.put("Content-Type", "application/json;charset=utf-8");
//        headers.put("Accept-Encoding", "gzip,deflate");
//        headers.put("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        return headers;
    }


}
