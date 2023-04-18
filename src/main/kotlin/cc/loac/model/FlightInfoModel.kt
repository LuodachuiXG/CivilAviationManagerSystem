package cc.loac.model

import cc.loac.common.Tool
import cc.loac.pojo.FlightInfo
import javax.swing.table.AbstractTableModel

class FlightInfoModel() : AbstractTableModel() {

    // 表格数据项
    private var items: List<FlightInfo> = ArrayList()

    // 表格列名
    private val columns = listOf<String>("航班号", "出发地", "目的地", "起飞时间", "到达时间", "飞行时间", "人数", "票价")

    constructor(items: List<FlightInfo>) : this() {
        this.items = items
    }

    /**
     * 根据索引获取 FlightInfo
     * @param index 索引
     * @return FlightInfo
     */
    fun getItem(index: Int): FlightInfo {
        return items[index]
    }

    /**
     * 根据航班号获取 FlightInfo
     * @param id 航班号
     * @return FlightInfo
     */
    fun getItem(id: String): FlightInfo? {
        for (flightInfo in items) {
            if (flightInfo.id == id) {
                return flightInfo
            }
        }
        return null
    }

    /**
     * 获取所有 FlightInfo
     * @return List<FlightInfo>
     */
    fun getItems(): List<FlightInfo> {
        return items
    }

    /**
     * 设置 items
     */
    fun setItems(items: List<FlightInfo>) {
        this.items = items
        // 更新表格
        fireTableDataChanged()
    }


    /**
     * 获取数据行数
     * @return int
     */
    override fun getRowCount(): Int {
        return items.size
    }

    /**
     * 获取列行
     * @return int
     */
    override fun getColumnCount(): Int {
        return columns.size
    }

    /**
     * 根据行列获取数据
     * @param rowIndex 行索引
     * @param columnIndex 列索引
     * @return Object
     */
    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any {
        val flightInfo = items[rowIndex]
        when (columnIndex) {
            0 -> return flightInfo.id
            1 -> return flightInfo.from
            2 -> return flightInfo.to
            3 -> return Tool.formatDate(flightInfo.departureTime)
            4 -> return Tool.formatDate(flightInfo.arrivalTime)
            5 -> return "${flightInfo.flightTime} 分钟"
            6 -> return "${flightInfo.persons} 人"
            7 -> return "${flightInfo.price} 元"
            else -> System.err.println("getValueAt Error")
        }
        return ""
    }

    /**
     * 获取列名
     * @param column 列索引
     * @return String
     */
    override fun getColumnName(column: Int): String {
        return columns[column]
    }

    /**
     * 获取列数据类型
     * @param columnIndex 列索引
     * @return Class
     */
    override fun getColumnClass(columnIndex: Int): Class<*> {
        return String::class.java
    }

    /**
     * 禁止表格编辑
     */
    override fun isCellEditable(rowIndex: Int, columnIndex: Int): Boolean {
        return false
    }
}