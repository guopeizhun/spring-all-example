package com.example.springredis.test;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author:letg(pz)
 * @Date: 2023/2/6 9:53
 * @Description:
 */


@Data
@AllArgsConstructor
public class Order implements Serializable {
    private String orderNo;
    private Date createTime;
    private String goodsId;
    private String addr;
    private String userName;
    private String tel;
    private String deliverId;
}
