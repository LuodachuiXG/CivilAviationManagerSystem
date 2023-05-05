package cc.loac.pojo

import java.util.Date

/**
 * 机票订单实体类
 */
data class Order(
    // 订单 ID，一般用当前时间戳作为 ID
    var id: Long = 0,
    // 航班号
    var flightId: String = "",
    // 下单时间
    var orderTime: Date = Date(),
    // 姓名
    var name: String = "",
    // 年龄
    var age: Int = 0,
    // 性别
    var gender: String = "男",
    // 电话
    var tel: String = "",
    // 票价
    var price: Int = 0
)
