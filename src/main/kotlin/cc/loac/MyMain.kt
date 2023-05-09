package cc.loac

import cc.loac.common.MyLinkList
import cc.loac.common.Tool
import cc.loac.pojo.FlightInfo
import cc.loac.pojo.Order
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter
import kotlin.NumberFormatException
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.abs
import kotlin.system.exitProcess

val scan = Scanner(System.`in`)
val aviations = MyLinkList<FlightInfo>()
val orders = MyLinkList<Order>()

fun main() {
    showOptionMenu()
}

/**
 * 显示操作菜单
 */
private fun showOptionMenu() {
    while (true) {
        printFormat("loac 民航管理系统") {
            println("0. 显示航班")
            println("1. 查找航班")
            println("2. 添加航班")
            println("3. 导出航班")
            println("4. 删除航班")
            println("5. 订票")
            println("6. 退票")
            println("7. 改签")
            println("8. 查询订单")
            println("*. 退出系统")
        }
        print("请输入你想要进行的操作：")

        when (getNumberFromConsole()) {
            // 显示航班
            0 -> menuShowAviation(aviations.asList())
            // 查找航班
            1 -> menuSearchAviation()
            // 添加航班
            2 -> menuAddAviation()
            // 导出航班
            3 -> menuExportAviations()
            // 删除航班
            4 -> menuDelAviation()
            // 订票
            5 -> menuBook()
            // 退票
            6 -> menuRefund()
            // 改签
            7 -> menuRebook()
            // 查询订单
            8 -> menuSearchOrder()
            // 退出系统
            else -> {
                println("退出系统......")
                exitProcess(0)
            }
        }
    }
}

/**
 * 菜单项 —— 打印航班信息
 */
private fun menuShowAviation(mList: List<FlightInfo>? = null, showIndex: Boolean = false, title: String = "显示航班信息") = printFormat(title) {
    val list = (mList ?: listOf())
    if (list.isEmpty()) {
        println("航班信息为空......")
        return@printFormat
    }

    println("${if (showIndex) "航班索引\t\t" else ""}航班号\t出发地\t目的地\t起飞时间\t\t\t\t到达时间" +
            "\t\t\t\t飞行时间（分钟）\t人数\t\t已订人数\t\t票价（元）\n")
    var index = 0
    list.forEach {
        println("${if (showIndex) "$index\t\t\t" else ""}${it.id}\t${it.from}\t\t${it.to}\t\t${Tool.formatDate(it.departureTime)}\t\t" +
                "${Tool.formatDate(it.arrivalTime)}\t\t${it.flightTime}分钟\t\t\t${it.persons}人" +
                "\t${it.orderCount}人\t\t\t${it.price}元\n")
        index++
    }
}

/**
 * 菜单项 —— 查找航班信息
 */
private fun menuSearchAviation() {
    while (true) {
        printFormat("查找航班") {
            println("0. 按航班号查找")
            println("1. 按航班日期查询")
            println("2. 按出发地查找")
            println("3. 按目的地查找")
            println("4. 按日期顺序查找")
            println("*. 返回")
        }
        print("请输入你想要进行的操作：")
        when (getNumberFromConsole()) {
            // 按航班号查找
            0 -> {
                // 如果查询结果为空就返回空数组
                val flightInfo = getAviationById()
                if (flightInfo == null) {
                    menuShowAviation()
                } else {
                    menuShowAviation(listOf(flightInfo))
                }
            }
            // 按航班日期查找
            1 -> menuShowAviation(getAviationByDate())
            // 按出发地查找
            2 -> menuShowAviation(getAviationByFrom())
            // 按目的地查找
            3 -> menuShowAviation(getAviationByTo())
            4 -> menuShowAviation(getAviationBySort())
            else -> return
        }
    }


}

/**
 * 菜单项 —— 添加航班信息
 */
private fun menuAddAviation() {
    while (true) {
        printFormat("添加航班") {
            println("0. 手动输入")
            println("1. 从文件导入")
            println("*. 返回")
        }
        print("请输入你想要进行的操作：")
        when (getNumberFromConsole()) {
            0 -> {
                // 添加航班到链表
                packageFlightInfoFromConsole()?.let {
                    aviations.add(it)
                    println("航班添加成功！目前航班数量：${aviations.size()}")
                }
            }
            1 -> {
                // 从文件导入航班信息
                importAviationsFromFile()
            }
            else -> break
        }
    }

}


/**
 * 菜单项 —— 导出航班信息
 */
private fun menuExportAviations() {
    if (aviations.isEmpty()) {
        println("航班信息为空，无法导出......")
        return
    }

    printFormat("导出航班信息") {
        println("选择导出目录......")
    }

    val jFileChooser = JFileChooser().apply {
        dialogTitle = "选择导出目录"
        fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
    }

    // 没有选择导出目录
    if (jFileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
        println("你没有选择导出目录......")
        return
    }

    print("输入导出的文件名（无需后缀）：")
    val fileName = scan.next()
    // 封装按导出的文件 File
    val file = File(jFileChooser.selectedFile.absolutePath + File.separator + fileName + ".txt")

    val list = aviations.asList()
    val writeStr = StringBuffer()
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm")
    list.forEach {
        writeStr.append("${it.id},")
        writeStr.append("${it.from},")
        writeStr.append("${it.to},")
        writeStr.append("${sdf.format(it.departureTime)},")
        writeStr.append("${sdf.format(it.arrivalTime)},")
        writeStr.append("${it.persons},")
        writeStr.append("${it.price}")
        writeStr.append("\n")
    }
    try {
        file.writeText(writeStr.toString())
        println("导出成功，${file.absolutePath}")
    } catch (e: Exception) {
        print("导出失败，${e.message}")
    }
}


/**
 * 菜单项 —— 删除航班
 */
private fun menuDelAviation() {
    if (aviations.isEmpty()) {
        println("航班信息为空，无法删除......")
        return
    }

    while (true) {
        printFormat("删除航班") {
            println("0. 根据航班号删除")
            println("1. 根据出发地删除")
            println("2. 根据目的地删除")
            println("*. 返回")
        }
        print("请输入你想要进行的操作：")
        val optionIndex = getNumberFromConsole()
        when (optionIndex) {
            0 -> print("请输入航班号：")
            1 -> print("请输入出发地：")
            2 -> print("请输入目的地：")
            else -> break
        }
        val key = scan.next()
        var delCount = 0
        when (optionIndex) {
            // 根据航班号删除
            0 -> delCount = aviations.deleteWhere { it.id == key }
            // 根据出发地删除
            1 -> delCount = aviations.deleteWhere { it.from == key }
            // 根据目的地删除
            2 -> delCount = aviations.deleteWhere { it.to == key }
        }
        println("删除航班数量：$delCount")
    }
}


/**
 * 菜单项 —— 订票
 */
private fun menuBook() {
    // 从控制台获取用户想要订购的机票
    val aviation = getAviationByConsole() ?: return
    // 从控制台输入封装 Order 实体类
    val order = packageOrderFromConsole(aviation.id, aviation.price)
    if (isOrderContain(order)) {
        // 订单已经存在，订票失败
        println("订单已存在，订票失败......")
        return
    }
    aviation.orderCount++
    aviations.replace(aviations.isContainWhere { it.id == aviation.id }, aviation)
    orders.add(order)
    println("订票成功，票价：${aviation.price}")
}

/**
 * 菜单项 —— 退票
 */
private fun menuRefund() {
    if (orders.isEmpty()) {
        println("订单信息为空，无法使用退票功能......")
        return
    }

    print("请输入订票人手机号：")
    val tel = getFromConsoleWhere("手机号有误") { isNumber(it) }
    val orderList = getOrderByTel(tel)
    if (orderList.isEmpty()) {
        println("订单为空......")
        return
    }

    showOrder(orderList, true)
    print("请输入要退的票的订单索引号（输入 -1 退出）：")
    val orderIndex = getFromConsoleWhere("订单索引有误") { isNumber(it) && it.toInt() >= -1 && it.toInt() < orderList.size }
    if (orderIndex.toInt()== -1) {
        return
    }

    val order = orderList[orderIndex.toInt()]
    val aviationIndex = aviations.isContainWhere { it.id == order.flightId }
    val aviation = aviations.get(aviationIndex)
    aviation!!.orderCount--
    aviations.replace(aviationIndex, aviation)

    val delCount = orders.deleteWhere { it.id == order.id }
    println("退票成功，退票数量：$delCount......")
}

/**
 * 菜单项 —— 改签
 */
private fun menuRebook() {
    if (orders.isEmpty()) {
        println("订单信息为空，无法使用改签功能......")
        return
    }

    print("请输入订票人手机号：")
    val tel = getFromConsoleWhere("手机号有误") { isNumber(it) }
    val orderList = getOrderByTel(tel)
    if (orderList.isEmpty()) {
        println("订单为空......")
        return
    }

    showOrder(orderList, true)
    print("请输入要改签票的订单索引号（输入 -1 退出）：")
    val orderIndex = getFromConsoleWhere("订单索引有误") { isNumber(it) && it.toInt() >= -1 && it.toInt() < orderList.size }
    if (orderIndex.toInt()== -1) {
        return
    }

    // 想要改签的订单
    val order = orderList[orderIndex.toInt()]

    // 从控制台获取用户想要订购的机票
    val aviation = getAviationByConsole() ?: return

    // 新订购航班人数加一
    aviation.orderCount++
    aviations.replace(aviations.isContainWhere { it.id == aviation.id}, aviation)

    // 原航班人数减 1
    val oldAviationIndex = aviations.isContainWhere { it.id == order.flightId }
    val oldAviation = aviations.get(oldAviationIndex)
    oldAviation!!.orderCount--
    aviations.replace(oldAviationIndex, oldAviation)


    order.orderTime = Date()
    order.flightId = aviation.id
    // 计算差价
    val price = aviation.price - order.price
    order.price = aviation.price

    // 获取原来 Order 在链表中的索引，用于在下面进行替换
    val oldIndex = orders.isContainWhere { it.id == order.id }
    orders.replace(oldIndex, order)

    println("改签成功，${if (price < 0) "退还 ${abs(price)} 元差价" else "请补 $price 元差价"}......")
}

/**
 * 菜单项 —— 查看订单
 */
private fun menuSearchOrder() {
    if (orders.isEmpty()) {
        println("订单信息为空，无法使用查看订单功能......")
        return
    }
    print("请输入想要查看订单的手机号：")
    val tel = getFromConsoleWhere("手机号有误") { isNumber(it) }
    showOrder(getOrderByTel(tel))
}

/**
 * 从控制台读取信息来获取符合的航班
 */
private fun getAviationByConsole(): FlightInfo? {
    print("请输入出发地：")
    val from = scan.next();
    print("请输入目的地：")
    val to = scan.next()
    val date = packageDateFromConsole("订票", false)
    val aviationList = getAviationByFromAToADate(from, to, date)
    if (aviationList.isEmpty()) {
        println("没有查到符合的航班......")
        return null
    }

    // 打印查询到的航班
    menuShowAviation(aviationList, true,"查询到的航班信息")
    print("请输入你想要订购的航班索引号(输入 -1 返回）：")
    val aviationIndex = getFromConsoleWhere("航班号有误") {
        // 输入的是数字并且不能小于 0 和大于 aviationList 长度
        isNumber(it) && it.toInt() >= -1 && it.toInt() < aviationList.size
    }

    // 输入 -1 返回
    if (aviationIndex.toInt() == -1) {
        return null
    }

    return aviationList[aviationIndex.toInt()]
}


/**
 * 显示订单信息
 */
private fun showOrder(orderList: List<Order>, showIndex: Boolean = false) = printFormat("订单信息") {
    val list = orderList
    if (list.isEmpty()) {
        println("订单为空......")
        return@printFormat
    }

    println("${if (showIndex) "订单索引\t" else ""}航班号\t姓名\t\t年龄\t\t性别\t\t电话\t\t\t\t下单时间\t\t\t\t票价（元）\n")
    var index = 0
    list.forEach {
        println("${if (showIndex) "$index\t\t" else ""}${it.flightId}\t${it.name}\t${it.age}\t\t${it.gender}\t\t" +
                "${it.tel}\t\t${Tool.formatDate(it.orderTime)}\t\t${it.price}\n")
        index++
    }
}

/**
 * 根据手机号获取所有订单
 */
private fun getOrderByTel(mTel: String): List<Order> {
    val list = ArrayList<Order>()
    if (orders.isEmpty()) {
        return list
    }
    orders.asList().forEach {
        if (it.tel == mTel) {
            list.add(it)
        }
    }
    return list
}

/**
 * 根据出发地、目的地和日期查找航班信息
 */
private fun getAviationByFromAToADate(from: String, to: String, date: Date): List<FlightInfo> {
    val list = ArrayList<FlightInfo>()
    if (aviations.isEmpty()) {
        return list
    }
    // 根据查询 Date 封装成 Calendar 对象
    val searchCal = Calendar.getInstance().apply {
        time = date
    }
    aviations.asList().forEach {
        val aviationCal = Calendar.getInstance().apply {
            time = it.departureTime
        }

        // 出发地、目的地和航班日期匹配
        if (it.from == from && it.to == to &&
            aviationCal.get(Calendar.YEAR) == searchCal.get(Calendar.YEAR) &&
            aviationCal.get(Calendar.MONTH) == searchCal.get(Calendar.MONTH) &&
            aviationCal.get(Calendar.DAY_OF_MONTH) == searchCal.get(Calendar.DAY_OF_MONTH))
            list.add(it)
    }
    return list
}

/**
 * 根据航班号查找航班信息
 */
private fun getAviationById(): FlightInfo? {
    print("输入航班号：")
    val id = scan.next()
    val list = aviations.asList()
    list.forEach {
        if (it.id == id) {
            return it
        }
    }
    return null
}


/**
 * 按航班日期查询
 */
private fun getAviationByDate(): List<FlightInfo> {
    val listResult = ArrayList<FlightInfo>()
    printFormat("按航班日期查询") {
        val date = packageDateFromConsole("查询", false)
        // 将要查找的时间封装成 Calendar 对象
        val searchCal = Calendar.getInstance().apply { this.time = date }

        val list = aviations.asList()
        list.forEach {
            // 将当前航班的起飞时间封装成 Calendar 对象
            val departureCal = Calendar.getInstance().apply { this.time = it.departureTime }
            // 日期相同
            if (departureCal.get(Calendar.YEAR) == searchCal.get(Calendar.YEAR) &&
                departureCal.get(Calendar.MONTH) == searchCal.get(Calendar.MONTH) &&
                departureCal.get(Calendar.DAY_OF_MONTH) == searchCal.get(Calendar.DAY_OF_MONTH)) {
                listResult.add(it)
            }
        }
    }
    return listResult
}

/**
 * 按出发地查询
 */
private fun getAviationByFrom(): List<FlightInfo> {
    print("输入出发地：")
    val from = scan.next()
    val listResult = ArrayList<FlightInfo>()
    val list = aviations.asList()
    list.forEach {
        if (it.from == from) {
            listResult.add(it)
        }
    }
    return listResult
}

/**
 * 按目的地查询
 */
private fun getAviationByTo(): List<FlightInfo> {
    print("输入目的地：")
    val to = scan.next()
    val listResult = ArrayList<FlightInfo>()
    val list = aviations.asList()
    list.forEach {
        if (it.to == to) {
            listResult.add(it)
        }
    }
    return listResult
}

/**
 * 按航班日期顺序查询
 */
private fun getAviationBySort(): List<FlightInfo> {
    val list = aviations.asList() as ArrayList<FlightInfo>
    for (i in list.indices) {
        for (j in 0 until list.size - 1 - i) {
            if (list[j].departureTime > list[j + 1].departureTime) {
                val temp = list[j + 1]
                list[j + 1] = list[j]
                list[j] = temp
            }
        }
    }
    return list
}

/**
 * 从文件导入航班信息
 */
private fun importAviationsFromFile() {
    printFormat("从文件导入航班信息") {
        println("从文件导入航班信息需要使用固定格式，否则导入失败，\n" +
                "属性之间使用英文逗号分隔，不同航班数据换行分隔。\n" +
                "下面是一个例子：\n\n" +
                "MF123,南京,北京,2023-05-26 13:00,2023-05-26 15:00,240,1200\n\n" +
                "从左往右依次为：\n" +
                "航班号,出发地,目的地,起飞时间,到达时间,人数,票价。\n" +
                "起飞和达到时间请严格按照格式填写，日期和时间之间一个空格，\n" +
                "不要添加多余的字符，此格式与导出数据格式一致。\n\n")
        println("1. 选择文件")
        println("*. 返回")
    }
    print("请输入你想要进行的操作：")
    val optionIndex = scan.next()
    if (optionIndex != "1") {
        return
    }

    // 选择文件
    val file = selectFile()
    if (file == null) {
        println("选择的文件为空......")
        return
    }

    // 读取文件并分析
    val fileContent = file.readText()
    // 把航班信息按换行符进行分割
    val aviationSplit = fileContent.split("\n")

    // 记录成功、失败、和非空格总数
    var successCount = 0
    var failureCount = 0
    var count = 0

    // 记录错误条数和错误原因
    val failureMap = HashMap<String, String>()

    // 遍历每行航班信息
    for (i in aviationSplit.indices) {
        val flightInfo = FlightInfo()
        val aviationStr = aviationSplit[i]
        if (aviationStr.isEmpty()) {
            // 空行就跳过
            continue
        }

        count++

        // 分割航班信息的每个属性
        val attr = aviationStr.split(",")
        if (attr.size != 7) {
            // 分割后长度小于 7，导入失败，跳过
            failureCount++
            failureMap[aviationStr] = "属性数量应为 7 条"
            continue
        }


        flightInfo.id = attr[0]
        flightInfo.from = attr[1]
        flightInfo.to = attr[2]

        if (isAviationContains(flightInfo.id)) {
            // 航班号已经存在，导入失败
            failureCount++
            failureMap[aviationStr] = "航班号已存在"
            continue
        }

        // 出发日期和时间用空格分割
        val departureSplit = attr[3].split(" ")
        // 到达日期和时间用空格分割
        val arrivalSplit = attr[4].split(" ")

        // 出发或到达时间格式错误
        if (departureSplit.size != 2 || arrivalSplit.size != 2) {
            failureCount++
            failureMap[aviationStr] = "出发或到达时间格式错误"
            continue
        }

        // 分割出发日期
        val departureDateSplit = departureSplit[0].split("-")
        // 分割出发时间
        val departureTimeSplit = departureSplit[1].split(":")

        // 分割到达日期
        val arrivalDateSplit = arrivalSplit[0].split("-")
        // 分割到达时间
        val arrivalTimeSplit = arrivalSplit[1].split(":")

        // 日期格式错误
        if (departureDateSplit.size != 3 || arrivalDateSplit.size != 3 ||
            departureTimeSplit.size != 2 || arrivalTimeSplit.size != 2) {
            failureCount++
            failureMap[aviationStr] = "出发或到达时间格式错误"
            continue
        }

        // 日期存在非数字属性
        if (!isNumber(departureDateSplit[0]) || !isNumber(departureDateSplit[1]) || !isNumber(departureDateSplit[2]) ||
            !isNumber(departureTimeSplit[0]) || !isNumber(departureTimeSplit[1]) ||
            !isNumber(arrivalDateSplit[0]) || !isNumber(arrivalDateSplit[1]) || !isNumber(arrivalDateSplit[2]) ||
            !isNumber(arrivalTimeSplit[0]) || !isNumber(arrivalTimeSplit[1])) {
            failureCount++
            failureMap[aviationStr] = "出发或到达时间存在非数字属性"
            continue
        }

        val dYear = departureDateSplit[0].toInt()
        val dMonth = departureDateSplit[1].toInt()
        val dDay = departureDateSplit[2].toInt()
        val dHour = departureTimeSplit[0].toInt()
        val dMinute = departureTimeSplit[1].toInt()
        // 出发时间异常
        if (!checkMonthDay(dYear, dMonth, dDay) || dHour !in 0..23 || dMinute !in 0..59) {
            failureCount++
            failureMap[aviationStr] = "出发日期时间不正确"
            continue
        }

        val aYear = arrivalDateSplit[0].toInt()
        val aMonth = arrivalDateSplit[1].toInt()
        val aDay = arrivalDateSplit[2].toInt()
        val aHour = arrivalTimeSplit[0].toInt()
        val aMinute = arrivalTimeSplit[1].toInt()
        // 到达时间异常
        if (!checkMonthDay(aYear, aMonth, aDay) || aHour !in 0..23 || aMinute !in 0..59) {
            failureCount++
            failureMap[aviationStr] = "到达日期时间不正确"
            continue
        }

        // 到达和出发时间日期类
        val calendarDeparture = Calendar.getInstance()
        val calendarArrival = Calendar.getInstance()
        calendarDeparture.set(dYear, dMonth - 1, dDay, dHour, dMinute)
        calendarArrival.set(aYear, aMonth -1, aDay, aHour, aMinute)

        flightInfo.departureTime = calendarDeparture.time
        flightInfo.arrivalTime = calendarArrival.time

        // 航班人数和票价不是数字
        if (!isNumber(attr[5]) || !isNumber(attr[6])) {
            failureCount++
            failureMap[aviationStr] = "航班人数或票价不是数字"
            continue
        }


        flightInfo.flightTime = ((flightInfo.arrivalTime.time - flightInfo.departureTime.time) / 60000).toInt()
        if (flightInfo.flightTime <= 0) {
            failureCount++
            failureMap[aviationStr] = "到达时间不能在出发时间之前或等于出发时间"
            continue
        }
        flightInfo.persons = attr[5].toInt()
        flightInfo.price = attr[6].toInt()
        aviations.add(flightInfo)
        successCount++
    }

    // 如果有错误条数，打印错误原因
    if (failureMap.size > 0) {
        printFormat("从文件导入错误条数") {
            failureMap.forEach { (k, v) ->
                println("($k)：$v")
            }
        }
    }
    println("总行数：$count，成功：$successCount，失败：$failureCount")
}

/**
 * 检查航班号是否已经存在
 */
private fun isAviationContains(id: String): Boolean {
    val list = aviations.asList()
    list.forEach {
        if (id == it.id) {
            return true
        }
    }
    return false
}


/**
 * 选择文件
 */
private fun selectFile(): File? {
    val jFileChooser = JFileChooser()
    jFileChooser.dialogTitle = "选择要导入的文件"
    jFileChooser.fileSelectionMode = JFileChooser.FILES_ONLY
    jFileChooser.fileFilter = FileNameExtensionFilter("文本文件 (*.txt)", "txt")

    return if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
        jFileChooser.selectedFile
    } else {
        null
    }
}

/**
 * 从控制台输入封装 FlightInfo 实体对象
 */
private fun packageFlightInfoFromConsole(): FlightInfo? {
    val flightInfo = FlightInfo()
    println("下面将开始录入航班信息，请根据要求逐一输入")
    print("航班号（String）：")
    flightInfo.id = getFromConsoleWhere("航班号已存在") { i -> !isAviationContains(i) }
    print("出发地（String)：")
    flightInfo.from = scan.next()
    print("目的地（String)：")
    flightInfo.to = scan.next()
    flightInfo.departureTime = packageDateFromConsole("起飞")
    flightInfo.arrivalTime = packageDateFromConsole("到达")

    // 飞行时间
    flightInfo.flightTime = ((flightInfo.arrivalTime.time - flightInfo.departureTime.time) / 60000).toInt()

    if (flightInfo.flightTime <= 0) {
        println("出发时间不能在起飞时间之前或等于起飞时间，导入错误......")
        return null
    }

    // 人数位于 1 到 240 之间
    print("航班人数（Int，最大 240 人)：")
    flightInfo.persons = getIntFromConsoleWhere("人数有误") { c -> c in 1..240 }
    // 票价必须大于 0
    print("航班票价（Int，元)：")
    flightInfo.price = getIntFromConsoleWhere("票价有误") { p -> p > 0 }
    return flightInfo
}


/**
 * 从控制台输入封装 Date 实体对象
 * @param hourAMinute 是否包括时和分
 */
private fun packageDateFromConsole(str: String, hourAMinute: Boolean = true): Date {
    val cal = Calendar.getInstance()

    // 年份必须位于 1970 到 2099 之间
    print("${str}时间 —— 年（Int)：")
    val year = getIntFromConsoleWhere("年份有误") { y -> y in 1970..2099 }

    // 月份必须位于 1 到 12 之间
    print("${str}时间 —— 月（Int)：")
    val month = getIntFromConsoleWhere("月份有误") { m -> m in 1..12 }

    // 日必须准确
    print("${str}时间 —— 日（Int)：")
    val day = getIntFromConsoleWhere("日期有误") { d -> checkMonthDay(year, month, d) }


    var hour = 0
    var minute = 0
    if (hourAMinute) {
        // 时必须位于 0 到 23 之间
        print("${str}时间 —— 时（Int，24 时)：")
        hour = getIntFromConsoleWhere("时间有误") { h -> h in 0..23  }

        // 分必须位于 0 到 59 之间
        print("${str}时间 —— 分（Int)：")
        minute = getIntFromConsoleWhere("时间有误") { m -> m in 0..59  }
    }

    // 将读取的日期设置到 cal
    if (hourAMinute) {
        cal.set(year, month - 1, day, hour, minute)
    } else {
        cal.set(year, month - 1, day)
    }

    return Date(cal.timeInMillis)
}

/**
 * 从控制台输入封装 Order 实体对象
 */
private fun packageOrderFromConsole(aviationId: String, mPrice: Int): Order {
    print("请输入订票人姓名：")
    val mName = scan.next()
    print("请输入订票人年龄：")
    val mAge = getFromConsoleWhere("年龄有误") { isNumber(it) && it.toInt() > 0 }
    print("请输入订票人性别（男 / 女）：")
    val mGender = getFromConsoleWhere("性别有误") { it == "男" || it == "女" }
    print("请输入订票人电话：")
    val mTel = getFromConsoleWhere("电话有误") { isNumber(it) }

    return Order().apply {
        name = mName
        age = mAge.toInt()
        gender = mGender
        tel = mTel
        flightId = aviationId
        price = mPrice
        // 当前时间戳作为订单 ID
        id = Date().time
    }
}

/**
 * 判断机票订单是否已经存在
 */
private fun isOrderContain(mOrder: Order): Boolean {
    return orders.isContainWhere { it.id == mOrder.id } != -1
}


/**
 * 根据年日月判断日是否正确
 */
private fun checkMonthDay(year: Int, month: Int, day: Int): Boolean {
    if (year < 1 || year > 9999 || month < 1 || month > 12 || day < 1 || day > 31) {
        return false
    }
    // 是否是闰年，闰年 2 月 29 天，非闰年 2 月 28 天
    val isLeapYear = (year % 4 == 0) && (year % 100 != 0 || year % 400 == 0)
    when (month) {
        1, 3, 5, 7, 8, 10, 12 -> return true
        2 -> {
            return if (isLeapYear && day <= 29) {
                true
            } else !isLeapYear && day <= 28
        }
        4, 6, 9, 11 -> return day <= 30
    }
    return false
}

/**
 * 从控制台获取整形数据，直到满足条件
 * @param errMsg 不满足条件时输出的文本
 * @param block 输入需要满足的条件
 */
private fun getIntFromConsoleWhere(errMsg: String = "", block: (input: Int) -> Boolean): Int {
    while (true) {
        val input = getNumberFromConsole()
        if (block(input)) {
            return input
        } else {
            print("${if (errMsg == "") "输入有误" else errMsg}，请重新输入：")
        }
    }
}

/**
 * 从控制台获取数据，直到满足条件
 * @param errMsg 不满足条件时输出的文本
 * @param block 输入需要满足的条件
 */
private fun getFromConsoleWhere(errMsg: String = "", block: (input: String) -> Boolean): String {
    while (true) {
        val input = scan.next()
        if (block(input)) {
            return input
        } else {
            print("${if (errMsg == "") "输入有误" else errMsg}，请重新输入：")
        }
    }
}

/**
 * 格式化打印，在要打印的文本前后加上特定标识
 */
private fun printFormat(title: String, block: () -> Unit) {
    println()
    println("———————————————— $title ————————————————")
    block()
    println("——————————————— #$title# ———————————————")

}

/**
 * 从控制台读取数字，如果读取失败就一直读取
 */
private fun getNumberFromConsole(): Int {
    while (true) {
        val num = scan.next()
        if (isNumber(num)) {
            return num.toInt()
        } else {
            print("你输入了非数字文本，请重新输入：")
        }
    }
}

/**
 * 判断一段文本是否是纯数字
 */
private fun isNumber(str: String): Boolean {
    return try {
        str.toLong()
        true
    } catch (e: NumberFormatException) {
        false
    }
}