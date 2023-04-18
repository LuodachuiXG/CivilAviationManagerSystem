package cc.loac

import cc.loac.common.Tool
import cc.loac.frame.Home
import cc.loac.myenum.OS
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneDarkIJTheme
import com.formdev.flatlaf.themes.FlatMacLightLaf
import java.io.File


class MyMain {
    companion object {
        // 记录当前系统
        val os: OS = Tool.getOSName()

        // 记录程序在当前系统存储数据目录
        val dataDirPath =
            if (os === OS.OS_WINDOW) System.getProperty("user.home") + "\\AppData\\Local\\CivilAviationManagerSystem"
            else System.getProperty("user.home") + "/.local/share/CivilAviationManagerSystem"

        // 记录程序 data.ini 文件位置
        var dataIniPath: String = dataDirPath + if (os === OS.OS_WINDOW) "\\data.ini" else "/data.ini"
    }
}

/**
 * 初始化组件
 */
fun init() {
    try {
        // 初始化数据目录和 data.ini
        val dataDir = File(MyMain.dataDirPath)
        if (!dataDir.exists()) {
            dataDir.mkdirs()
        }
        val dataIni = File(MyMain.dataIniPath)
        if (!dataIni.exists()) {
            dataIni.createNewFile()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun main() {
    // 设置主题
    FlatMacLightLaf.setup()
    init()
    Home()
}