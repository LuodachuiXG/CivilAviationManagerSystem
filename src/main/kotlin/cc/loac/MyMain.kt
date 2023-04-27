package cc.loac

import cc.loac.common.Tool
import cc.loac.myenum.OS


class MyMain {
    companion object {
        // 记录当前系统
        val os: OS = Tool.getOSName()

        // 记录程序在当前系统存储数据目录
        val dataDirPath =
            if (os === OS.OS_WINDOW) System.getProperty("user.home") + "\\AppData\\Local\\CivilAviationManagerSystem"
            else System.getProperty("user.home") + "/.local/share/CivilAviationManagerSystem"

    }
}

fun main() {

}