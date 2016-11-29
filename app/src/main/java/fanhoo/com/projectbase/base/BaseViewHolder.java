package fanhoo.com.projectbase.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * 创建人 胡焕
 * 创建时间 2016年11月24日  16:46
 * 用途 RecyclerViewHolder基础封装
 * 1.使用了ButterKnife,让Holer可以使用注解获取控件.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
