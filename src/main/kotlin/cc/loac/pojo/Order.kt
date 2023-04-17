package cc.loac.pojo

import java.util.Date

/**
 * 机票订单实体类
 */
data class Order(
    // 订单 ID
    var orderId: Int,
    // 航班号
    var flightId: String,
    // 下单时间
    var orderTime: Date,
    // 姓名
    var name: String,
    // 年龄
    var age: Int,
    // 性别
    var gender: String,
    // 电话
    var tel: String
)
