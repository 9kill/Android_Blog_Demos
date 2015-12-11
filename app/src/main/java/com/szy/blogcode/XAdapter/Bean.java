package com.szy.blogcode.XAdapter;

/**
 * 类描述：
 * 创建人：sunzhenyu
 * 创建时间：2015/12/11 10:33
 */
public class Bean {
    public String name;
    public String content;
    public boolean isChecked;//避免listview复用造成的数据错乱
    public Bean(String name,String content){
        this.name=name;
        this.content=content;
    }
}
