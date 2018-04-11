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

    public String getText() {
        return text;
    }

    public BeanInfo setText(String text) {
        this.text = text;
        return this;
    }
}
