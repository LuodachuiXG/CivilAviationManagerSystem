package cc.loac.civilaviationmanagersystem.dao

import org.ini4j.Wini


/**
 * Wini 操作
 */
class MyIni {
    companion object {
        // Ini 配资文件操作类
        private lateinit var _wini: Wini

        // 对象实例
        private var _myIni: MyIni? = null
        private const val SECTION_HOME = "HOME"
        private const val OPTION_LOCATION_X = "location_x"
        private const val OPTION_LOCATION_Y = "location_y"
        private const val OPTION_SIZE_WIDTH = "size_width"
        private const val OPTION_SIZE_HEIGHT = "size_height"
        private const val OPTION_TABLE_FILE_SHOW_HIDDEN = "table_file_show_hidden"
    }
}