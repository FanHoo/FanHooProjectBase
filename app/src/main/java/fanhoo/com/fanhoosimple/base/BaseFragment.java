package fanhoo.com.fanhoosimple.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import fanhoo.com.fanhoosimple.utils.ToastUtils;


/**
 * 创建人 胡焕
 * 创建时间 2016年11月23日  00:08
 * 用途 Fragment基础封装
 * <p>
 * 内部已经集成了ButterKnife.
 */

public abstract class BaseFragment extends Fragment {
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        //绑定butterknife
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }


    /**
     * 这样做是处理内存过低的时候getActivity返回为null的情况.
     *
     * @return 宿主Activity.(这里是BaseActivity, 如果想得到具体的Activity实例的话, 可使用强制转换)
     * @author 胡焕
     * @date 2016/11/23 09:38
     */
    protected BaseActivity getHoldingActivity() {
        return (BaseActivity) mContext;
    }

    /**
     * 返回Fragment的布局文件ID.
     *
     * @author 胡焕
     * @date 2016/11/23 09:30
     */
    protected abstract int getLayoutId();

    /**
     * 子类重写此方法进行初始化操作.
     *
     * @author 胡焕
     * @date 2016/11/23 09:32
     */
    protected abstract void init(View view, Bundle savedInstanceState);

    protected void showToast(String msg) {
        ToastUtils.showToast(msg, mContext);
    }
}
