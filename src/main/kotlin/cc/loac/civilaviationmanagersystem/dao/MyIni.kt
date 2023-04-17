package cc.loac.civilaviationmanagersystem.dao

import cc.loac.civilaviationmanagersystem.HelloApplication
import cc.loac.civilaviationmanagersystem.myenum.OS
import org.ini4j.Wini
import java.io.File
import java.lang.Exception


/**
 * Wini 操作
 */
class MyIni() {
    companion object {
        // Ini 配资文件操作类
        private lateinit var _wini: Wini
        // 对象实例
        private var _myIni: MyIni? = null

        // 记录配置文件位置
        private val dataDirPath =
            if (HelloApplication.os == OS.OS_WINDOW) System.getProperty("user.home") + "\\AppData\\Local\\CivilAviationManagerSystem"
            else System.getProperty("user.home") + "/.local/share/CivilAviationManagerSystem"

//        private const val SECTION_HOME = "HOME"
//        private const val OPTION_LOCATION_X = "location_x"
//        private const val OPTION_LOCATION_Y = "location_y"
//        private const val OPTION_SIZE_WIDTH = "size_width"
//        private const val OPTION_SIZE_HEIGHT = "size_height"
//        private const val OPTION_TABLE_FILE_SHOW_HIDDEN = "table_file_show_hidden"


        /**
         * 获取 MyIni 实例
         */
        fun getInstance(): MyIni {
            if (_myIni == null) {
                _wini = Wini(File(dataDirPath))
                _myIni = MyIni()
            }
            return _myIni!!
        }
    }
}