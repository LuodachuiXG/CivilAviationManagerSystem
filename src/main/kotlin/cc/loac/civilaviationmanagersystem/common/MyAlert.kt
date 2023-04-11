package cc.loac.civilaviationmanagersystem.common

import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.ButtonBar.ButtonData
import javafx.scene.control.ButtonType


object MyAlert {
    // 信息框默认标题
    private const val TITLE = "温馨提示"
    private val _alert: Alert = Alert(AlertType.NONE)

    fun String.alert (
        title: String = TITLE,
        type: AlertType = AlertType.INFORMATION,
        confirm: Boolean = false
    ): Boolean {
        _alert.alertType = if (confirm) AlertType.CONFIRMATION else type
        _alert.title = title
        _alert.contentText = this
        return if (confirm) {
            val option = _alert.showAndWait()
            option.get().buttonData == ButtonData.OK_DONE
        } else {
            _alert.show()
            false
        }
    }
}