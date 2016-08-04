package com.lezhi.sts.enums;

/**
 * Created by Colin Yan on 2016/7/29.
 */
public enum TaskStatus {
    created(10, ""), running(20, ""), timeout(30, ""), success(40, ""), failed(50, ""), canceled(60, ""), giveup(70, "");

    public final Integer dbval;
    public final String name;

    private TaskStatus(int dbval, String name) {
        this.dbval = dbval;
        this.name = name;
    }

    public Integer getDbval() {
        return dbval;
    }

    public String getName() {
        return name;
    }

}
