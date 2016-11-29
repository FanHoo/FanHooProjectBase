package fanhoo.com.projectbase.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fanhoo.com.projectbase.base.BaseViewHolder;
import fanhoo.com.projectbase.base.interfaces.OnItemClickListener;

/**
 * 创建人 胡焕
 * 创建时间 2016年11月24日  17:00
 * 用途 RecyclerView Adapter基础封装.
 * 考虑到ViewType的设定, 如果布局用有多种布局的话,可在实例化的时候泛型参数Holder传入BaseViewHolder,
 * 在bindViewHolder的时候再转换为具体类型.
 */

public abstract class BaseRecyclerAdapter<Holder extends BaseViewHolder, Data> extends RecyclerView.Adapter<Holder> {
    protected List<Data> mData;
    protected OnItemClickListener onItemClickListener;

    public BaseRecyclerAdapter() {
        mData = new ArrayList<>();
    }

    public BaseRecyclerAdapter(List<Data> data) {
        if (data == null)
            data = new ArrayList<>();
        this.mData = data;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        parent.setClickable(true);
        View itemView = LayoutInflater.from(parent.getContext()).inflate(getItemViewId(viewType), parent, false);
        return getViewHolder(itemView, viewType);
    }


    protected abstract Holder getViewHolder(View view, int viewType);

    /**
     * 由子类返回Item的布局.
     *
     * @param viewType Item的布局类型(RecycleView可以在同个列表里面显示不同的布局,可根据此参数的值来返回不同的布局)
     * @author 胡焕
     * @date 2016/11/24 17:33
     */
    protected abstract int getItemViewId(int viewType);

    /**
     * 子类实现此方法为ViewHolder中的控件绑定数据
     *
     * @author 胡焕
     * @date 2016/11/25 11:56
     */
    protected abstract void bindViewHolder(Holder holder, Data data);


    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, position);
                }
            });
        }

        bindViewHolder(holder, mData.get(position));
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
