package wyw.com.volleydemo;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * 作者: 伍跃武
 * 时间： 2018/4/11
 * 描述：
 */

public class BeanInfo implements Serializable {

    @Expose
    private String text;

    @Expose
    private String url;

    public String getUrl() {
        return url;
    }

    public BeanInfo setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getText() {
        return text;
    }

    public BeanInfo setText(String text) {
        this.text = text;
        return this;
    }
}
