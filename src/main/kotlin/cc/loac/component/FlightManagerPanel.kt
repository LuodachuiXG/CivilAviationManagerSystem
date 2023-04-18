package cc.loac.component

import java.awt.BorderLayout
import java.awt.FlowLayout
import java.awt.GridLayout
import java.util.*
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.SwingConstants

/**
 * 航班管理器面板
 */
class FlightManagerPanel : JPanel(GridLayout(1, 3)) {
    /* 左面板——手动添加航班信息 */
    private lateinit var panelLeft: JPanel

    /* 右面板——从文件添加航班信息 */
    private lateinit var panelRight: JPanel


    /* 左右面板标题标签 */
    private lateinit var panelLeftTitle: JLabel
    private lateinit var panelRightTitle: JLabel


    /* 左面板——文本框、按钮等操作组件 */
    private lateinit var panelLeftGridPanel: JPanel

    // 存放航班信息八个属性的输入组件的面板
    private lateinit var panelLeftIdPanel: JPanel
    private lateinit var panelLeftFromPanel: JPanel
    private lateinit var panelLeftToPanel: JPanel
    private lateinit var panelLeftDepartureTimePanel: JPanel
    private lateinit var panelLeftArrivalTimePanel: JPanel
    private lateinit var panelLeftFlightTimePanel: JPanel
    private lateinit var panelLeftPersonsPanel: JPanel
    private lateinit var panelLeftPricePanel: JPanel

    // 航班信息八个属性标签
    private lateinit var panelLeftIdLabel: JLabel
    private lateinit var panelLeftFromLabel: JLabel
    private lateinit var panelLeftToLabel: JLabel
    private lateinit var panelLeftDepartureTimeLabel: JLabel
    private lateinit var panelLeftArrivalTimeLabel: JLabel
    private lateinit var panelLeftFlightTimeLabel: JLabel
    private lateinit var panelLeftPersonsLabel: JLabel
    private lateinit var panelLeftPriceLabel: JLabel

    // 航班信息八个属性的输入组件
    private lateinit var panelLeftIdTextField: JTextField
    private lateinit var panelLeftFromTextField: JTextField
    private lateinit var panelLeftToTextField: JTextField
    private lateinit var panelLeftDepartureTimeTextField: JTextField
    private lateinit var panelLeftArrivalTimeTextField: JTextField
    private lateinit var panelLeftFlightTimeTextField: JTextField
    private lateinit var panelLeftPersonsTextField: JTextField
    private lateinit var panelLeftPriceTextField: JTextField

    // 存放两个按钮的面板
    private lateinit var panelLeftBtnPanel: JPanel
    // 确认按钮
    private lateinit var panelLeftBtnConfirm: JButton
    // 重置按钮
    private lateinit var panelLeftBtnReset: JButton

    init {
        initComponent()
        initComponentData()
    }

    /**
     * 初始化组件
     */
    private fun initComponent() {
        /* 初始化左右面板 */
        panelLeft = JPanel(BorderLayout())
        panelRight = JPanel(BorderLayout())

        /* 初始化左右面板标题 */
        panelLeftTitle = JLabel("手动添加航班信息")
        panelRightTitle = JLabel("从文件添加航班信息")
        panelLeftTitle.horizontalAlignment = SwingConstants.CENTER
        panelRightTitle.horizontalAlignment = SwingConstants.CENTER


        /* 初始化左面板组件 */
        panelLeftGridPanel = JPanel(GridLayout(8, 1))
        panelLeftIdPanel = JPanel(FlowLayout())
        panelLeftFromPanel = JPanel(FlowLayout())
        panelLeftToPanel = JPanel(FlowLayout())
        panelLeftDepartureTimePanel = JPanel(FlowLayout())
        panelLeftArrivalTimePanel = JPanel(FlowLayout())
        panelLeftFlightTimePanel = JPanel(FlowLayout())
        panelLeftPersonsPanel = JPanel(FlowLayout())
        panelLeftPricePanel = JPanel(FlowLayout())

        // 左面板标签
        panelLeftIdLabel = JLabel("航班编号")
        panelLeftFromLabel = JLabel("出发地址")
        panelLeftToLabel = JLabel("目的地址")
        panelLeftDepartureTimeLabel = JLabel("起飞时间")
        panelLeftArrivalTimeLabel = JLabel("到达时间")
        panelLeftFlightTimeLabel = JLabel("飞行时间")
        panelLeftPersonsLabel = JLabel("人  数")
        panelLeftPriceLabel = JLabel("票  价")

        // 左面板输入组件
        panelLeftIdTextField = JTextField()
        panelLeftFromTextField = JTextField()
        panelLeftToTextField = JTextField()
        panelLeftDepartureTimeTextField = JTextField()
        panelLeftArrivalTimeTextField = JTextField()
        panelLeftFlightTimeTextField = JTextField()
        panelLeftPersonsTextField = JTextField()
        panelLeftPriceTextField = JTextField()

        // 将左面板组件加入面板
        panelLeftIdPanel.apply {
            add(panelLeftIdLabel)
            add(panelLeftIdTextField)
        }
        panelLeftFromPanel.apply {
            add(panelLeftFromLabel)
            add(panelLeftFromTextField)
        }
        panelLeftToPanel.apply {
            add(panelLeftToLabel)
            add(panelLeftToTextField)
        }
        panelLeftDepartureTimePanel.apply {
            add(panelLeftDepartureTimeLabel)
            add(panelLeftDepartureTimeTextField)
        }
        panelLeftArrivalTimePanel.apply {
            add(panelLeftArrivalTimeLabel)
            add(panelLeftArrivalTimeTextField)
        }
        panelLeftFlightTimePanel.apply {
            add(panelLeftFlightTimeLabel)
            add(panelLeftFlightTimeTextField)
        }
        panelLeftPersonsPanel.apply {
            add(panelLeftPersonsLabel)
            add(panelLeftPersonsTextField)
        }
        panelLeftPricePanel.apply {
            add(panelLeftPriceLabel)
            add(panelLeftPriceTextField)
        }
        panelLeftGridPanel.apply {
            add(panelLeftIdPanel)
            add(panelLeftFromPanel)
            add(panelLeftToPanel)
            add(panelLeftDepartureTimePanel)
            add(panelLeftArrivalTimePanel)
            add(panelLeftFlightTimePanel)
            add(panelLeftPersonsPanel)
            add(panelLeftPricePanel)
        }

        /* 将组件添加到面板 */
        panelLeft.add(panelLeftTitle, BorderLayout.NORTH)
        panelLeft.add(panelLeftGridPanel, BorderLayout.CENTER)
        panelRight.add(panelRightTitle, BorderLayout.NORTH)

        /* 将组件添加到主面板 */
        add(panelLeft)
        add(panelRight)
    }

    /**
     * 初始化组件数据
     */
    private fun initComponentData() {

    }
}