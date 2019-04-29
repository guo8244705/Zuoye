package guoyifeng.com.example.netplwiz.zuoye.MyFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import guoyifeng.com.example.netplwiz.zuoye.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment3 extends Fragment implements View.OnClickListener {


    private View view;
    private WebView mWv;
    /**
     * WebView
     */
    private Button mBt;

    public MyFragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_fragment3, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mWv = (WebView) view.findViewById(R.id.wv);
        mBt = (Button) view.findViewById(R.id.bt);
        mBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.bt:
                mWv.loadUrl("http://www.baidu.com");
                mWv.setWebViewClient(new WebViewClient());
                mBt.setVisibility(View.GONE);
                break;
        }
    }
}
