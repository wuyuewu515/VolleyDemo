package wyw.com.volleydemo;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * 作者: 伍跃武
 * 时间： 2018/4/11
 * 描述：最强王者
 */

public class ResponseInfo implements Serializable {

//             "code": 200,
//             "msg": "成功!",
//             "data": []

    @Expose
    protected int code;
    @Expose
    protected String msg;


    public int getCode() {
        return code;
    }

    public ResponseInfo setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResponseInfo setMsg(String msg) {
        this.msg = msg;
        return this;
    }

}
