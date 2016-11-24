import fanhoo.com.projectbase.Man;
import fanhoo.com.projectbase.utils.annotations.BindNoticeProcess;

/**
 * 创建人 胡焕
 * 创建时间 2016年11月23日  20:36
 * 用途
 */

public class Test {
    public static void main(String[] args) {
        BindNoticeProcess process = BindNoticeProcess.getInstance();
        try {
            String not = process.getNotice(new Man());
            System.out.print(not);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }


}
