package cc.loac.civilaviationmanagersystem.common

import cc.loac.civilaviationmanagersystem.myenum.OS
import java.text.SimpleDateFormat
import java.util.*

object Tool {
    // 获取 OS.NAME
    private var OS_NAME: String = System.getProperty("os.name").uppercase()

    /**
     * 获取 OS.NAME
     *
     * @return OS
     */
    fun getOSName(): OS {
        return if (OS_NAME.contains("WINDOWS")) {
            OS.OS_WINDOW;
        } else if (OS_NAME.contains("LINUX")) {
            OS.OS_LINUX;
        } else {
            OS.OS_MACOS;
        }
    }

    /**
     * 格式化日期
     *
     * @param date
     * @return
     */
    fun formatDate(date: Date): String {
        val sdf: SimpleDateFormat
        val cal = Calendar.getInstance()
        cal.time = date;
        val currentCal = Calendar.getInstance()
        var result = ""
        sdf = if (cal.get(Calendar.YEAR) == currentCal.get(Calendar.YEAR)) {
            // 文件修改年份和当前年份一致
            SimpleDateFormat("MM 月 dd 日")
        } else {
            // 文件修改年份和当前年份不一致
            SimpleDateFormat("yyyy 年 MM 月 dd 日")
        }
        result = sdf.format(date);
        return result;
    }
}