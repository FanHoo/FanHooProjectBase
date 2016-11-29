import android.text.TextUtils;

import com.fanhoo.lib.utils.annotations.BindNoticeProcess;
import fanhoo.com.projectbase.Man;

/**
 * 创建人 胡焕
 * 创建时间 2016年11月23日  20:36
 * 用途
 */

public class Test {
    public static void main(String[] args) {
        BindNoticeProcess process = BindNoticeProcess.getInstance();
        try {
            String notice = process.getNotice(new Man());
            if (!TextUtils.isEmpty(notice))
                System.out.print(notice);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }


}
