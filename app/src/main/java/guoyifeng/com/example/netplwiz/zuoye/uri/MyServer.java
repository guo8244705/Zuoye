package guoyifeng.com.example.netplwiz.zuoye.uri;

import guoyifeng.com.example.netplwiz.zuoye.bean.MyBean;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface MyServer {
//    https://api.apiopen.top/getJoke?page=1&count=2&type=video
    public String url = "https://api.apiopen.top/";
    @GET("getJoke?page=1&count=2&type=video")
    Observable<MyBean> get();
}
