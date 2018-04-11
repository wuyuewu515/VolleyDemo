package wyw.com.volleydemo.netUtils;

/**
 * 作者: 伍跃武
 * 时间： 2018/4/11
 * 描述：响应监听接口，负责 response返回后针对业务对象的逻辑处理
 */

public interface RequestListener {

    void onSuccess(String result);

    void onError(String printMe);
}
