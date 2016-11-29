package fanhoo.com.projectbase.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.fanhoo.lib.utils.ToastUtil;

import java.io.Serializable;
import java.util.Stack;

import butterknife.ButterKnife;
import fanhoo.com.projectbase.R;

/**
 * 创建人 胡焕
 * 创建时间 2016年11月22日  11:00
 * 用途 Activity基类,做一些基础封装
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected static Stack<Activity> mActivityStack = new Stack<Activity>();
    protected String TAG = "";
    protected String MODE_KEY = "modl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (setNoSysTitle())
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        mActivityStack.add(this);
        TAG = this.getClass().getSimpleName();

    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        init();
    }


    /**
     * 为保持基本一致性,子类重写此方法进行初始化操作.
     *
     * @author 胡焕
     * @date 2016/11/23 09:33
     */
    protected abstract void init();

    /**
     * 设置是否显示系统自带的title栏.
     * 默认是隐藏的,可通过子类重写此方法或者直接修改此处的返回值.
     *
     * @author 胡焕
     * @date 2016/11/22 16:38
     */
    protected boolean setNoSysTitle() {
        return true;
    }

    /**
     * 启动Activity
     *
     * @param cls 目标Activity
     * @author 胡焕
     * @date 2016/11/22 15:41
     */
    protected void startActivity(Class cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    protected void startActivity(Class cls, Serializable serializable) {
        Intent intent = new Intent(this, cls);
        intent.putExtra(MODE_KEY, serializable);
        startActivity(intent);
    }


    /**
     * 重写finish方法, finish时移除栈中的Activity, 并设置动画.
     *
     * @author 胡焕
     * @date 2016/11/22 11:33
     */
    @Override
    public void finish() {
        mActivityStack.remove(this);
        super.finish();
        int[] changeAnim = getActivitChangeAnim();
        overridePendingTransition(changeAnim[0], changeAnim[1]);

    }

    /**
     * 退出应用
     *
     * @author 胡焕
     * @date 2016/11/22 11:42
     */
    protected void quitApplication() {
        Stack<Activity> tempStack = mActivityStack;
        mActivityStack = new Stack<>();
        clearActivityStack(tempStack);

    }

    /**
     * finish除了自身以为的其他所有Activity.
     *
     * @author 胡焕
     * @date 2016/11/22 11:51
     */
    protected void finishOthers() {
        Stack<Activity> tempStack = mActivityStack;
        tempStack.remove(this);
        clearActivityStack(tempStack);
    }

    /**
     * finish stack中所有的Activity.
     *
     * @param stack 需要finish的stack
     * @author 胡焕
     * @date 2016/11/22 11:45
     */
    private void clearActivityStack(Stack<Activity> stack) {
        for (int i = 0; i < stack.size(); i++) {
            stack.get(i).finish();
        }
    }

    /**
     * 获取Activity切换的时候的动画效果, 默认平滑左进又出.如果想实现自己的效果可重写此方法.
     *
     * @author 胡焕
     * @date 2016/11/22 11:39
     */
    protected int[] getActivitChangeAnim() {
        return new int[]{R.anim.push_right_in, R.anim.push_right_out};
    }

    /**
     * 单例的Toast用于提示
     *
     * @param msg 提示内容
     * @author 胡焕
     * @date 2016/11/22 16:04
     */
    protected final void showToast(String msg) {
        ToastUtil.showToast(msg, this);
    }


}
