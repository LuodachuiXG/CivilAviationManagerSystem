package cc.loac

import cc.loac.common.MyLinkList
import cc.loac.common.Tool
import cc.loac.pojo.FlightInfo
import cc.loac.pojo.Order
import java.io.File
import java.util.*
import javax.swing.JFileChooser
import javax.swing.filechooser.FileFilter
import javax.swing.filechooser.FileNameExtensionFilter
import kotlin.NumberFormatException
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
fun showOptionMenu() {
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
            println("*. 退出系统")
        }
        print("请输入你想要进行的操作：")

        when (getNumberFromConsole()) {
            0 -> menuShowAviation()
            1 -> {}
            2 -> menuAddAviation()
            3 -> {}
            4 -> {}
            5 -> {}
            6 -> {}
            7 -> {}
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
private fun menuShowAviation() = printFormat("显示航班信息") {
    if (aviations.size() == 0) {
        println("航班信息为空，请先添加航班")
        return@printFormat
    }

    val list = aviations.asList()
    println("航班号\t出发地\t目的地\t\t起飞时间\t\t到达时间\t\t飞行时间（分钟）\t\t人数\t\t票价（元）\n")
    list.forEach {
        println("${it.id}\t\t${it.from}\t${it.to}\t${Tool.formatDate(it.departureTime)}\t\t" +
                "${Tool.formatDate(it.arrivalTime)}\t\t${it.flightTime}分钟\t\t${it.persons}人\t\t${it.price}元\n")
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
        println("请输入你想要进行的操作：")
        when(getNumberFromConsole()) {
            0 -> {
                // 添加航班到链表
                aviations.add(packageFlightInfoFromConsole())
                println("航班添加成功！目前航班数量：${aviations.size()}")
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
 * 从文件导入航班信息
 */
private fun importAviationsFromFile() {

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
private fun packageFlightInfoFromConsole(): FlightInfo {
    val flightInfo = FlightInfo()
    println("下面将开始录入航班信息，请根据要求逐一输入")
    print("航班号（String）：")
    flightInfo.id = scan.next()
    print("出发地（String)：")
    flightInfo.from = scan.next()
    print("目的地（String)：")
    flightInfo.to = scan.next()
    flightInfo.departureTime = packageDateFromConsole("起飞")
    flightInfo.arrivalTime = packageDateFromConsole("到达")

    // 飞行时间
    flightInfo.flightTime = ((flightInfo.arrivalTime.time - flightInfo.departureTime.time) / 60000).toInt()

    // 人数位于 1 到 240 之间
    print("航班人数（Int，最大 240 人)：")
    flightInfo.persons = getIntFromConsoleWhere("人数") { c -> c in 1..240 }
    // 票价必须大于 0
    print("航班票价（Int，元)：")
    flightInfo.price = getIntFromConsoleWhere("票价") { p -> p > 0 }
    return flightInfo
}


/**
 * 从控制台输入封装 Date 实体对象
 */
private fun packageDateFromConsole(str: String): Date {
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

    // 时必须位于 0 到 23 之间
    print("${str}时间 —— 时（Int，24 时)：")
    val hour = getIntFromConsoleWhere("时间有误") { h -> h in 0..23  }

    // 分必须位于 0 到 59 之间
    print("${str}时间 —— 分（Int)：")
    val minute = getIntFromConsoleWhere("时间有误") { m -> m in 0..59  }

    // 将读取的日期设置到 cal
    cal.set(year, month - 1, day, hour, minute)
    return Date(cal.timeInMillis)
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
    println("—————————— $title ——————————")
    block()
    println("————————— #$title# —————————")

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
        str.toInt()
        true
    } catch (e: NumberFormatException) {
        false
    }
}