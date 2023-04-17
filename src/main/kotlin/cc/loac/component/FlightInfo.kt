package cc.loac.component

import javax.swing.JButton
import javax.swing.JPanel

/**
 * 航班信息面板
 */
class FlightInfo : JPanel() {
    val btn = JButton("Hello")

    init {
        add(btn)
    }
}