package cc.loac.common

/**
 * 链表 Node
 */
data class Node<T> (var t: T, var next: Node<T>?)

/**
 * 自定义链表类
 */
class MyLinkList<T> {
    // 链表长度
    private var _length = 0
    // 链表头结点
    private var _head: Node<T>? = null

    /**
     * 获取链表最后一个结点
     */
    private fun getLastNode(): Node<T>? {
        // 链表还没有任何元素
        if (_head == null) {
            return null
        }

        var temp = _head
        while (temp?.next != null) {
            temp = temp.next
        }
        return temp
    }

    /**
     * 找到链表指定位置的前后元素
     */
    private fun getPreAndNext(i: Int, callBack: (pre: Node<T>?, next: Node<T>?) -> Unit) {
        // 找到指定位置和前位置的索引
        var j = 0
        var pre: Node<T>? = null
        var temp: Node<T>? = _head
        while (j != i && temp != null) {
            pre = temp
            temp = temp.next
            j++
        }
        // 回调
        callBack(pre, temp)
    }


    /**
     * 添加元素到链表
     */
    fun add(t: T) {
        val node = Node(t, null)
        if (_head == null) {
            // 头结点为空，新加的结点为头结点
            _head = node
        } else {
            // 插入到最后一个结点后
            val lastNode = getLastNode()
            lastNode?.next = node
        }
        _length++
    }

    /**
     * 删除链表中指定元素
     */
    fun delete(i: Int): Boolean {
        // 链表为空
        if (isEmpty()) {
            return false
        }

        // 删除位置非法
        if (i > _length - 1 || i < 0) {
            return false
        }

        if (i == 0) {
            // 删除的是第一个元素
            _head = _head?.next
        } else {
            // 找到指定位置和前位置的索引
            getPreAndNext(i) { pre, next ->
                // 删除指定位置元素
                pre?.next = next?.next
            }
        }
        _length--
        return true
    }


    /**
     * 条件删除，根据自定义条件删除元素
     */
    fun deleteWhere(block: (T) -> Boolean): Int {
        // 链表为空
        if (isEmpty()) {
            return 0;
        }
        var pre: Node<T>? = null
        var temp: Node<T>? = _head
        var delCount = 0
        while (temp != null) {
            if (block(temp.t)) {
                // 符合条件
                if (pre == null) {
                    // 链表首元素符合条件
                    _head = _head?.next
                } else {
                    // 非链表首元素，正常删除
                    pre.next = temp.next
                }
                delCount++
                _length--
            } else {
                // 不符合条件（未删除元素）才把 temp 赋给 pre
                // 如果删除了元素就不改变 pre
                pre = temp
            }
            temp = temp.next
        }
        return delCount
    }

    /**
     * 在链表指定位置插入元素
     */
    fun insert(i: Int, t: T): Boolean {
        // 插入位置非法
        if (i > _length - 1 || i < 0) {
            return false
        }

        // 包装新 Node
        val node = Node(t, null)
        if (i == 0) {
            // 插入的是第一个位置
            node.next = _head
            _head = node
        } else {
            // 找到指定位置和前位置的索引
            getPreAndNext(i) { pre, next ->
                // 插入 node
                node.next = next
                pre?.next = node
            }
        }
        _length++
        return true
    }

    /**
     * 获取指定元素
     */
    fun get(i: Int): T? {
        // 索引位置非法
        if (i > _length - 1 || i < 0) {
            return null
        }
        var j = 0
        var temp = _head
        while (j != i && temp != null) {
            temp = temp.next
            j++
        }
        return temp?.t
    }

    /**
     * 替换指定位置元素
     */
    fun replace(i: Int, t: T): Boolean {
        // 先插入再删除
        insert(i, t)
        delete(i + 1)
        return true
    }

    /**
     * 获取链表长度
     */
    fun size(): Int {
        return _length
    }

    /**
     * 链表是否为空
     */
    fun isEmpty(): Boolean {
        return _length == 0
    }

    /**
     * 打印链表所有数据
     */
    fun show() {
        var temp = _head
        while (temp != null) {
            println(temp.t)
            temp = temp.next
        }
    }

    /**
     * 将列表数据包装成 List
     */
    fun asList(): List<T> {
        var temp = _head
        val list = ArrayList<T>()
        while (temp != null) {
            list.add(temp.t)
            temp = temp.next
        }
        return list
    }
}