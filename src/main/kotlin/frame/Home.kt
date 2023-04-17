package frame

import java.awt.FlowLayout
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel

class Home() : JFrame("Loac 民航管理器") {
    private val panel = JPanel(FlowLayout())
    private val btn = JButton("Hello")

    init {
        initComponent()
        setLocation(100, 100)
        setSize(400, 300)
        defaultCloseOperation = EXIT_ON_CLOSE
        isVisible = true
    }

    /**
     * 初始化组件
     */
    private fun initComponent() {
        panel.add(btn)

        this.add(panel)
    }
}