package cc.loac.frame

import cc.loac.component.FlightInfoPanel
import cc.loac.component.FlightManagerPanel
import cc.loac.component.UserServicePanel
import cc.loac.dao.MyIni
import java.awt.Dimension
import java.awt.Point
import java.awt.Toolkit
import java.awt.event.WindowEvent
import java.awt.event.WindowListener
import javax.swing.JFrame
import javax.swing.JTabbedPane
import kotlin.system.exitProcess

class Home() : JFrame("Loac 民航管理器"), WindowListener {
    private val myIni: MyIni = MyIni.getInstance()

    // 记录窗口默认位置
    private var point: Point? = myIni.getHomeLocation()
    // 记录窗口默认大小
    private var dimension: Dimension? = myIni.getHomeSize()


    /* 选项卡面板 */
    private var tabbedPane = JTabbedPane()

    init {
        // 初始化组件
        initComponent()

        // 设置窗口
        val toolkit = Toolkit.getDefaultToolkit()
        val ds = toolkit.screenSize
        if (point == null) {
            // 当前可能是第一次打开程序，没有设置过窗口位置
            // 设置默认位置为屏幕中间
            point = Point()
            point?.setLocation(ds.getWidth() / 2 - 450, ds.getHeight() / 2 - 350)
        }

        if (dimension == null) {
            // 当前可能是第一次打开程序，没有设置过窗口大小
            // 设置默认大小
            dimension = Dimension()
            dimension?.setSize(900, 700)
        }



        // 添加组件
        add(tabbedPane)

        // 添加事件
        addWindowListener(this)
        this.location = point!!
        this.size = dimension
        defaultCloseOperation = DO_NOTHING_ON_CLOSE
        isVisible = true
    }

    /**
     * 初始化组件
     */
    private fun initComponent() {
        // 添加选项卡
        tabbedPane.addTab("航班信息", FlightInfoPanel())
        tabbedPane.addTab("航班管理", FlightManagerPanel())
        tabbedPane.addTab("客户服务", UserServicePanel())
    }


    /**
     * 保存数据并退出程序
     */
    private fun exitApp() {
        // 在程序关闭时记录窗口大小和位置
        val ds = this.size
        val pt = this.location
        myIni.setHomeLocation(pt.getX().toInt(), pt.getY().toInt())
        myIni.setHomeSize(ds.getWidth().toInt(), ds.getHeight().toInt())
        exitProcess(0)
    }

    /**
     * 窗口打开事件
     */
    override fun windowOpened(e: WindowEvent?) {

    }

    /**
     * 窗口关闭按钮点击事件
     */
    override fun windowClosing(e: WindowEvent?) {
        exitApp()
    }

    /**
     * 窗口完全关闭事件
     */
    override fun windowClosed(e: WindowEvent?) {

    }

    /**
     * 窗口最小化事件
     */
    override fun windowIconified(e: WindowEvent?) {

    }

    /**
     * 窗口最小化还原事件
     */
    override fun windowDeiconified(e: WindowEvent?) {

    }

    /**
     * 窗口完全激活事件
     */
    override fun windowActivated(e: WindowEvent?) {

    }

    /**
     * 窗口失去活性事件
     */
    override fun windowDeactivated(e: WindowEvent?) {

    }
}