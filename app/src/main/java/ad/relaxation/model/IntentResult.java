package ad.relaxation.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/15 0015.
 * 所有请求返回结果的基类
 */

public class IntentResult<T> implements Serializable{
    public T data;
    public int cmd;
    public int code;
    public String msg;
}
