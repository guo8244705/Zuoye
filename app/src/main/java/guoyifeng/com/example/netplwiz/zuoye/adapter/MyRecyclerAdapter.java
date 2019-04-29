package guoyifeng.com.example.netplwiz.zuoye.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import guoyifeng.com.example.netplwiz.zuoye.R;
import guoyifeng.com.example.netplwiz.zuoye.bean.MyBean;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {
    private ArrayList<MyBean.ResultBean> list;
    private Context context;

    public MyRecyclerAdapter(ArrayList<MyBean.ResultBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setList(ArrayList<MyBean.ResultBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.recycler_item, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.tv.setText(list.get(i).getText());

        RoundedCorners roundedCorners = new RoundedCorners(50);

        RequestOptions override = RequestOptions.bitmapTransform(roundedCorners).override(200, 200);

        Glide.with(context).load(list.get(i).getThumbnail()).apply(override).into(holder.iv);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv;
        private TextView tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            tv = itemView.findViewById(R.id.tv);
        }
    }
}
