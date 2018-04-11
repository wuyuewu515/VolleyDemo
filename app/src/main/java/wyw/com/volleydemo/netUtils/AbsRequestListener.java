package wyw.com.volleydemo.netUtils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

/**
 * 作者: 伍跃武
 * 时间： 2018/4/11
 * 描述：二次封装的请求监听回调
 */

public abstract class AbsRequestListener implements RequestListener {
    @NonNull
    protected Context mContext;

    public AbsRequestListener(Context context) {
        this.mContext = context;
    }

    @Override
    public void onError(String printMe) {
        if (null != mContext)
            Toast.makeText(mContext,printMe,Toast.LENGTH_SHORT).show();
    }
}
