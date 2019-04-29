package guoyifeng.com.example.netplwiz.zuoye.presenter;

import guoyifeng.com.example.netplwiz.zuoye.bean.MyBean;
import guoyifeng.com.example.netplwiz.zuoye.callback.MyCallBack;
import guoyifeng.com.example.netplwiz.zuoye.model.MyModelpler;
import guoyifeng.com.example.netplwiz.zuoye.view.MyView;

public class MyPresenter implements MyPresent, MyCallBack {
    private MyModelpler myModelpler;
    private MyView myView;

    public MyPresenter(MyModelpler myModelpler, MyView myView) {
        this.myModelpler = myModelpler;
        this.myView = myView;
    }

    @Override
    public void getData() {
        myModelpler.getData(this);
    }

    @Override
    public void onSuccess(MyBean myBean) {
        myView.onSuccess(myBean);
    }

    @Override
    public void onFial(String msg) {
        myView.onFial(msg);
    }
}
