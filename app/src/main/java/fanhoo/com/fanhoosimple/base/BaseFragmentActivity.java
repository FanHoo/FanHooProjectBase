package fanhoo.com.fanhoosimple.base;

/**
 * 创建人 胡焕
 * 创建时间 2016年11月23日  10:12
 * 用途
 */

public abstract class BaseFragmentActivity extends BaseActivity {

    /**
     * 跳转Fragment并添加到回退栈中.
     *
     * @author 胡焕
     * @date 2016/11/23 10:15
     */
    protected void addFragment(BaseFragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(getFragmentContainerId(), fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }
    }

    /**
     * 跳转Fragment不放入回退栈.
     *
     * @author 胡焕
     * @date 2016/11/23 10:48
     */
    protected void replaceFragment(BaseFragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(getFragmentContainerId(), fragment)
                    .commitAllowingStateLoss();
        }
    }

    /**
     * 调用此方法可进行回退操作,到最后一个Fragment的时候会Finish掉宿主Activity.
     *
     * @author 胡焕
     * @date 2016/11/23 10:44
     */
    protected void removeFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    /**
     * 这里重写了回退按钮事件,默认是按回退需要做回退栈流程的.如果想直接finish的话可在子类重写此方法.
     *
     * @author 胡焕
     * @date 2016/11/23 10:55
     */
    @Override
    public void onBackPressed() {
        removeFragment();
    }

    /**
     * 子类重写此方法返回Fragment的容器ID这个容器一般是个layout
     *
     * @author 胡焕
     * @date 2016/11/23 10:17
     */
    protected abstract int getFragmentContainerId();
}
