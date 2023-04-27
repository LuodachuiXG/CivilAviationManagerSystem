package cc.loac.common

import java.text.SimpleDateFormat
import java.util.*

object Tool {
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
            SimpleDateFormat("MM月dd日 HH:mm")
        } else {
            // 文件修改年份和当前年份不一致
            SimpleDateFormat("yyyy年MM月dd日 HH:mm")
        }
        result = sdf.format(date);
        return result;
    }
}