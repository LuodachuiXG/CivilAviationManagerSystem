package cc.loac.pojo

import java.util.Date

/**
 * 航班信息实体类
 */
data class FlightInfo(
    // 航班号
    var id: String,
    // 出发地
    var from: String,
    // 目的地
    var to: String,
    // 起飞时间
    var departureTime: Date,
    // 到达时间
    var arrivalTime: Date,
    // 飞行时间
    var flightTime: Int,
    // 人数
    var persons: Int,
    // 票价
    var price: Double
)
