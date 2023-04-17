package cc.loac.civilaviationmanagersystem.common

/**
 * 链表 Node
 */
data class Node<T> (var t: T, var next: Node<T>?)

/**
 * 链表
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
     * 获取链表长度
     */
    fun size(): Int {
        return _length
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

}