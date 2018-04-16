package wyw.com.volleydemo.netUtils;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;

import com.android.volley.Response;

import org.json.JSONObject;

import wyw.com.volleydemo.JsonUtils;
import wyw.com.volleydemo.ResponseInfo;

/**
 * 作者: 伍跃武
 * 时间： 2018/4/11
 * 描述：响应监听类，对正常返回进行后续处理（Listener<String>子类） 对返回信息进行预处理
 */

public class IMJsonListener implements Response.Listener<JSONObject> {
    private RequestListener requestListener;
    private Context mContext;


    public IMJsonListener(RequestListener requestListener, Context context) {
        this.requestListener = requestListener;
        mContext = context;
    }


    /**
     * 针对volley返回回来的数据进行处理,比如token过期，重新登录等，其他状态码等
     *
     * @param response
     */

    @Override
    public void onResponse(JSONObject response) {

        try {
            //     LoadingDialog.dismiss();

            // 将JSONObject转换成String
            String responseText = response.toString();

            // 获得请求结果
            Resources res = mContext.getResources();

            // 返回对象为NULL、空、以及状态码为-1时
            if (TextUtils.isEmpty(responseText)) {
                this.requestListener.onError("服务器貌似GG了...");
                return;
            }

            // 获取状态码
            ResponseInfo responseInfo = JsonUtils.json2Object(responseText, ResponseInfo.class);

            // response不符合规范
            if (null == responseInfo) {
                this.requestListener.onError("服务器还是GG了");
                return;
            }
            if (200 == responseInfo.getCode()) { //服务器正常，回调到onsucess方法
                this.requestListener.onSuccess(JsonUtils.getJSONObjectKeyVal(responseText,"data"));
            }
//

        } catch (Exception e) {
//            if (BuildConfig.DEBUG) {//调试模式下直接抛出异常
//                throw e;
//            } else {//release环境下提示错误，同时上报异常
//                this.requestListener.onError(mContext.getResources().getString(R.string.net_error_ununited));
//                MobclickAgent.reportError(mContext, e);
//            }
        }
    }
}
