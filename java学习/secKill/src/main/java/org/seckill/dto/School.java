package org.seckill.dto;

/**
 * Created by daojia on 2018-5-31.
 */
public class School {
    private String xiaoxue;
    private String zhongxue;
    private String daxue;

    public String getXiaoxue() {
        return xiaoxue;
    }

    public void setXiaoxue(String xiaoxue) {
        this.xiaoxue = xiaoxue;
    }

    public String getZhongxue() {
        return zhongxue;
    }

    public void setZhongxue(String zhongxue) {
        this.zhongxue = zhongxue;
    }

    public String getDaxue() {
        return daxue;
    }

    public void setDaxue(String daxue) {
        this.daxue = daxue;
    }

    @Override
    public String toString() {
        return "School{" +
                "xiaoxue='" + xiaoxue + '\'' +
                ", zhongxue='" + zhongxue + '\'' +
                ", daxue='" + daxue + '\'' +
                '}';
    }
}
