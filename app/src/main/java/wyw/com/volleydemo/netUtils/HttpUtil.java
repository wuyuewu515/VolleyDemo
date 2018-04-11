package wyw.com.volleydemo.netUtils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Map;

/**
 * 作者: 伍跃武
 * 时间： 2018/4/11
 * 描述：网络工具类
 */

public class HttpUtil {
    /**
     * 请求队列
     */
    private RequestQueue queue;
    /**
     * 上下文对象
     */
    private Context mContext;

    /**
     * 会话识别号
     */
    public static String sessionId = "0000";

    private static HttpUtil httpUtil;

    /**
     * 构造函数
     *
     * @param context 上下文对象
     */
    private HttpUtil(Context context) {
        queue = Volley.newRequestQueue(context);
        this.mContext = context;
    }

    public static HttpUtil getInstance(Context context) {
        if (null == httpUtil) {
            httpUtil = new HttpUtil(context);
        }
        if (context != httpUtil.mContext) {
            httpUtil.cancelAllRequestQueue();
            httpUtil = new HttpUtil(context);
        }

        return httpUtil;
    }


    /**
     * postJson请求
     *
     * @param url             链接地址
     * @param params          参数集合
     * @param requestListener 监听回调
     * @return
     */
    public Request<JSONObject> doPostByJson(String url, Map<String, Object> params,
                                            RequestListener requestListener) {
//    在每次请求发起之前先进行网络检查
//        if (!checkNetState(mContext)) {
//            Toast.makeText(mContext, R.string.net_error_check, Toast.LENGTH_SHORT)
//                    .show();
//            return null;
//        }


//依照实际需求添加固定参数 如token之类的
//        params.put("appVerison", BuildConfig.VERSION_NAME);
//        String accessToken = Constants.getAccessToken(mContext);
//        if (!TextUtils.isEmpty(accessToken)) {
//            params.put("access_token", accessToken);
//        }

        JSONObject jsonObject = new JSONObject(params);

        return doPostByJson(url, jsonObject, requestListener);
    }

    /**
     * postJson请求
     *
     * @param url             服务器地址
     * @param jsonObject      json 对象
     * @param requestListener 请求监听
     * @return
     */
    private Request<JSONObject> doPostByJson(String url, JSONObject jsonObject,
                                             RequestListener requestListener) {

        PostJsonRequset postJsonRequset = new PostJsonRequset(url, jsonObject,
                new IMJsonListener(requestListener, mContext),
                new IMErrorListener(requestListener, mContext));

        Request<JSONObject> request = queue.add(postJsonRequset);

        // 为请求添加context标记
        request.setTag(mContext);
        return request;
    }

    /**
     * 清除当前activity所有的请求
     */
    public void cancelAllRequestQueue() {
        if (null != queue && null != mContext) {
            queue.cancelAll(mContext);
            queue.start();
            queue = null;
            mContext = null;
            httpUtil = null;
        }
    }
}
