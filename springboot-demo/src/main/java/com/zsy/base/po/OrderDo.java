package com.zsy.base.po;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
public class OrderDo {

    private Integer id;
    private Integer pid;
    private String name;
    private Date createTime;

    public OrderDo(Integer pid, String name, Date createTime) {
        this.pid = pid;
        this.name = name;
        this.createTime = createTime;
    }

    public OrderDo(Integer id, Integer pid, String name, Date createTime) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.createTime = createTime;
    }
}
