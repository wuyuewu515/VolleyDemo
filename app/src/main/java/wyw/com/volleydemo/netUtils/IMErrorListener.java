package wyw.com.volleydemo.netUtils;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import wyw.com.volleydemo.R;

/**
 * 作者: 伍跃武
 * 时间： 2018/4/11
 * 描述：错误的回调
 */

public class IMErrorListener implements Response.ErrorListener {
    private RequestListener requestListener;
    private Context mContext;

    public IMErrorListener(RequestListener requestListener) {
        this.requestListener = requestListener;
    }

    public IMErrorListener(RequestListener requestListener, Context context) {
        this.requestListener = requestListener;
        this.mContext = context;
    }

    /**
     * 接口返回错误信息，进行相应的操作
     *
     * @param error
     */
    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(mContext, "返回错误" + error.getMessage(), Toast.LENGTH_LONG).show();
//        LoadingDialog.dismiss();
//
//        Resources resources = App.getInstance().getResources();
//
//        this.requestListener.onError(error instanceof TimeoutError ? resources.getString(R.string.net_error_timeout) :
//                (error instanceof NoConnectionError ? resources.getString(R.string.net_error_noConnection) : resources.getString(R.string.net_error_ununited)));
//
//        if (AppConfig.DEBUG && error != null && error.networkResponse != null && error.networkResponse.data != null) {
//            byte[] htmlBodyBytes = error.networkResponse.data;
//            if (htmlBodyBytes != null) {
//                CommonUtils.LOG_D(getClass(), "VolleyError=" + new String(htmlBodyBytes));
//            }
//        }
    }
}
