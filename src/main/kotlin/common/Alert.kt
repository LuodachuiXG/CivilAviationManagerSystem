package common

import javax.swing.JOptionPane


object Alert {
    // 信息框默认标题
    private const val TITLE = "温馨提示"

    fun error(errMsg: String?, title: String? = TITLE) {
        JOptionPane.showMessageDialog(null, errMsg, title, JOptionPane.ERROR_MESSAGE)
    }


    fun info(msg: String?, title: String? = TITLE) {
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE)
    }


    fun confirm(msg: String?, title: String? = TITLE): Boolean {
        val result = JOptionPane.showConfirmDialog(null, msg, title, JOptionPane.YES_NO_OPTION)
        return result == JOptionPane.YES_OPTION
    }

    fun inputWithValue(msg: String?, value: String?): String {
        return input(msg, TITLE, value)
    }


    @JvmStatic
    fun input(msg: String?, title: String? = TITLE, value: String? = ""): String {
        return JOptionPane.showInputDialog(
            null,
            msg,
            title,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            null,
            value
        ) as String
    }
}