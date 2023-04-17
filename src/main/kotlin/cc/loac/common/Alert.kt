package cc.loac.common

import javax.swing.JOptionPane


object Alert {
    // 信息框默认标题
    private const val TITLE = "温馨提示"

    fun String.info() {
        JOptionPane.showMessageDialog(null, this, TITLE, JOptionPane.INFORMATION_MESSAGE)
    }

    fun String.error() {
        JOptionPane.showMessageDialog(null, this, TITLE, JOptionPane.ERROR_MESSAGE)
    }

    fun String.confirm(): Boolean {
        val result = JOptionPane.showConfirmDialog(null, this, TITLE, JOptionPane.YES_NO_OPTION)
        return result == JOptionPane.YES_OPTION
    }

    fun String.input(value: String = ""): String {
        return JOptionPane.showInputDialog(
            null,
            this,
            TITLE,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            null,
            value
        ) as String
    }
}