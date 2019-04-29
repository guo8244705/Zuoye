package guoyifeng.com.example.netplwiz.zuoye.view;

import guoyifeng.com.example.netplwiz.zuoye.bean.MyBean;

public interface MyView {
    void onSuccess(MyBean myBean);
    void onFial(String msg);
}
