package cc.loac.dao

import cc.loac.MyMain
import org.ini4j.Wini
import java.awt.Dimension
import java.awt.Point
import java.io.File

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

        /**
         * 获取 MyIni 实例
         */
        fun getInstance(): MyIni {
            if (_myIni == null) {
                _wini = Wini(File(MyMain.dataIniPath))
                _myIni = MyIni()
            }
            return _myIni!!
        }
    }

    /**
     * 获取 Home 窗口位置
     * @return Int
     */
    fun getHomeLocation(): Point? {
        val point = Point()
        try {
            val x = _wini.get(SECTION_HOME, OPTION_LOCATION_X).toInt()
            val y = _wini.get(SECTION_HOME, OPTION_LOCATION_Y).toInt()
            point.setLocation(x, y)
        } catch (e: Exception) {
            return null
        }
        return point
    }

    /**
     * 设置 Home 窗口位置
     * @param x 水平位置
     * @param y 垂直位置
     */
    fun setHomeLocation(x: Int, y: Int) {
        try {
            _wini.put(SECTION_HOME, OPTION_LOCATION_X, x)
            _wini.put(SECTION_HOME, OPTION_LOCATION_Y, y)
            _wini.store()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 获取 Home 窗口大小
     * @return Dimension
     */
    fun getHomeSize(): Dimension? {
        val dimension = Dimension()
        try {
            val width = _wini.get(SECTION_HOME, OPTION_SIZE_WIDTH).toInt()
            val height = _wini.get(SECTION_HOME, OPTION_SIZE_HEIGHT).toInt()
            dimension.setSize(width, height)
        } catch (e: Exception) {
            return null
        }
        return dimension
    }

    /**
     * 设置 Home 窗口大小
     * @param width 宽度
     * @param height 高度
     */
    fun setHomeSize(width: Int, height: Int) {
        try {
            _wini.put(SECTION_HOME, OPTION_SIZE_WIDTH, width)
            _wini.put(SECTION_HOME, OPTION_SIZE_HEIGHT, height)
            _wini.store()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}