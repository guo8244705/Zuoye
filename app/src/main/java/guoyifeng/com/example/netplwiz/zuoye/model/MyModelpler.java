package guoyifeng.com.example.netplwiz.zuoye.model;

import android.util.Log;

import guoyifeng.com.example.netplwiz.zuoye.bean.MyBean;
import guoyifeng.com.example.netplwiz.zuoye.callback.MyCallBack;
import guoyifeng.com.example.netplwiz.zuoye.uri.MyServer;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyModelpler implements MyModel{
    @Override
    public void getData(final MyCallBack callBack) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyServer.url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        MyServer myServer = retrofit.create(MyServer.class);

        Observable<MyBean> observable = myServer.get();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MyBean myBean) {
                        if (myBean!=null){
                            callBack.onSuccess(myBean);
                        }else{
                            callBack.onFial("失败");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("qwgqgq",e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
