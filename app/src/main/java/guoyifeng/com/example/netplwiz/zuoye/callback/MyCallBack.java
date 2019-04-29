package guoyifeng.com.example.netplwiz.zuoye.callback;

import guoyifeng.com.example.netplwiz.zuoye.bean.MyBean;

public interface MyCallBack {
    void onSuccess(MyBean myBean);
    void onFial(String msg);
}
