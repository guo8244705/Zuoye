package guoyifeng.com.example.netplwiz.zuoye.MyFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import guoyifeng.com.example.netplwiz.zuoye.R;
import guoyifeng.com.example.netplwiz.zuoye.adapter.MyRecyclerAdapter;
import guoyifeng.com.example.netplwiz.zuoye.bean.MyBean;
import guoyifeng.com.example.netplwiz.zuoye.model.MyModelpler;
import guoyifeng.com.example.netplwiz.zuoye.presenter.MyPresenter;
import guoyifeng.com.example.netplwiz.zuoye.view.MyView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment1 extends Fragment implements MyView {


    private RecyclerView mRv;
    private ArrayList<MyBean.ResultBean> list;
    private MyRecyclerAdapter recyclerAdapter;

    public MyFragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_fragment1, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        MyPresenter myPresenter = new MyPresenter(new MyModelpler(), this);
        myPresenter.getData();
    }

    private void initView(View view) {
        mRv = (RecyclerView) view.findViewById(R.id.rv);
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<>();
        recyclerAdapter = new MyRecyclerAdapter(list, getContext());
        mRv.setAdapter(recyclerAdapter);
    }

    @Override
    public void onSuccess(MyBean myBean) {
        list.addAll(myBean.getResult());
        recyclerAdapter.setList(list);
        recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFial(String msg) {
        Log.e("wqqwgq",msg);
    }
}
