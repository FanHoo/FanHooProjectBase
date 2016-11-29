package fanhoo.com.projectbase;


import com.fanhoo.lib.utils.annotations.BindNotice;

/**
 * 创建人 胡焕
 * 创建时间 2016年11月23日  20:42
 * 用途
 */

public class Man {
    public int age = 0;
    @BindNotice(title = "性别")
    public String Sex = "man";

    @BindNotice(title = "姓名")
    public String Name;
}
