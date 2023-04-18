package cc.loac.component

import cc.loac.model.FlightInfoModel
import cc.loac.pojo.FlightInfo
import java.awt.BorderLayout
import java.awt.FlowLayout
import java.awt.event.*
import java.util.*
import javax.swing.JButton
import javax.swing.JComboBox
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTable
import javax.swing.JTextField

/**
 * 航班信息面板
 */
class FlightInfoPanel : JPanel(BorderLayout()), ComponentListener, ActionListener {
    /* 显示航班信息的表格 */
    private lateinit var table: JTable
    private lateinit var tableModel: FlightInfoModel
    private lateinit var scrollPane: JScrollPane

    /* North 工具栏面板 */
    private lateinit var toolPanel: JPanel
    private lateinit var toolPanelWestPanel: JPanel
    private lateinit var toolPanelLabel: JLabel
    private val toolPanelComboBoxItems = listOf("航班号", "出发地", "目的地")
    private lateinit var toolPanelComboBox: JComboBox<String>
    private lateinit var toolPanelSearchTextField: JTextField
    private lateinit var toolPanelSearchBtn: JButton

    init {
        // 初始化组件
        initComponent()
        // 初始化组件数据
        initComponentData()

        addComponentListener(this)
    }

    /**
     * 初始化组件
     */
    private fun initComponent() {
        /* 初始化 toolPanel */
        toolPanelComboBox = JComboBox<String>()
        toolPanelLabel = JLabel("查询航班：")
        toolPanelComboBoxItems.forEach { item ->
            // 将选项加入 JComboBox
            toolPanelComboBox.addItem(item)
        }
        toolPanelSearchTextField = JTextField()
        toolPanelSearchBtn = JButton("查找")
        toolPanelSearchBtn.addActionListener(this)

        toolPanel = JPanel(BorderLayout())
        toolPanelWestPanel = JPanel(FlowLayout())
        toolPanelWestPanel.add(toolPanelLabel)
        toolPanelWestPanel.add(toolPanelComboBox)
        toolPanel.add(toolPanelWestPanel, BorderLayout.WEST)
        toolPanel.add(toolPanelSearchTextField, BorderLayout.CENTER)
        toolPanel.add(toolPanelSearchBtn, BorderLayout.EAST)


        /* 初始化 table */
        tableModel = FlightInfoModel()
        table = JTable()
        table.model = tableModel
        scrollPane = JScrollPane(table)

        /* 将组件添加到面板 */
        add(toolPanel, BorderLayout.NORTH)
        add(scrollPane, BorderLayout.CENTER)
    }

    /**
     * 初始化组件数据
     */
    private fun initComponentData() {

    }

    /**
     * 根据面板大小更新组件大小
     */
    private fun updateComponentSize() {

    }

    /**
     * 组件大小改变事件
     */
    override fun componentResized(e: ComponentEvent?) {
        updateComponentSize()
    }

    /**
     * 组件移动事件
     */
    override fun componentMoved(e: ComponentEvent?) {

    }

    /**
     * 组件显示事件
     */
    override fun componentShown(e: ComponentEvent?) {

    }

    /**
     * 组件隐藏事件
     */
    override fun componentHidden(e: ComponentEvent?) {

    }

    /**
     * 点击事件
     */
    override fun actionPerformed(e: ActionEvent?) {

    }
}