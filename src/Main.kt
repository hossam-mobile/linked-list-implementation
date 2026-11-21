class Node<T>(
    var value: T,
    var next: Node<T>? = null
) {
    override fun toString(): String {
//        super.toString()
        return "Node(value=$value, next=$next)"
    }
}

class LinkedList<T>(var head: Node<T>?, var tail: Node<T>?, var length: Int = 1) {
    fun printList() {
        var tempNode: Node<T>? = this.head
        while (tempNode != null) {
            println(tempNode)
            tempNode = tempNode.next
        }
    }

    //TIP push = append
    fun push(newNode: Node<T>) {

        if (this.head == null) {
            head = newNode
            tail = newNode
        } else {
            tail?.next = newNode
            tail = newNode
        }

        length++
    }

    fun pop() {
        // no items
        if (length == 0 || head == null) {
            println("Cannot pop empty node")
            return
        }
        // one item
        if (length == 1 && head?.next == null) {
            head = null
            tail = null
            length--
            return
        }

        // more items
        var tempNode = head
        while (tempNode?.next != null) {
            if (tempNode.next == tail) {
                tempNode.next = null
                tail = tempNode
                break
            }
            tempNode = tempNode.next
        }

        length--
    }

    fun unshift(newNode: Node<T>) {

        if (length == 0 || head == null) {
            head = newNode
            tail = newNode
            length++
            return
        }

        newNode.next = head
        head = newNode

        length++
    }

    fun shift() {
        if (length == 0 || head == null) {
            println("Cannot shift empty node")
            return
        }

        if (length == 1 || head?.next == null) {
            head = null
            tail = null
            length--
            return
        }

        head = head?.next
        length--
    }

    fun get(index: Int): Node<T>? {
        if (index !in 0..<length) {
            throw IndexOutOfBoundsException()
        }
        var tempNode = head
        repeat(index) {
            tempNode = tempNode?.next
        }
        return tempNode
    }

    fun set(index: Int, value: T): Boolean {
        val node = get(index) ?: return false
        node.value = value
        return true
    }

    fun insert(index: Int, newNode: Node<T>): Boolean {

        if (index < 0 || index < length) {
            return false
        }

        if (index == 0) {
            unshift(newNode)
            return true
        }

        if (index == length) {
            push(newNode)
            return true
        }
        val node = get(index - 1) ?: return false
        newNode.next = node.next
        node.next = newNode
        length++
        return true
    }

    fun remove(index: Int) {
        if (index > length) return
        if (index == 0) {
            shift()
            return
        }

        if (index == length - 1) {
            pop()
            return
        }

        val node = get(index - 1) ?: return
        node.next = node.next?.next
        length--
    }

    fun reverse() {
        if (length == 0 || head == null) return
        var tempNode = head
        head = tail
        tail = tempNode

        var next: Node<T>?
        var prev: Node<T>? = null
        repeat(length) {
            next = tempNode?.next
            tempNode?.next = prev
            prev = tempNode
            tempNode = next
        }
    }

    fun emptyList(): LinkedList<T> {
        val list = LinkedList<T>(
            null, null, 0
        )
        return list as LinkedList<T>
    }

    fun removeNextNode(node: Node<T>?) {
        if (length == 1 || node?.next == null) {
            return
        } else if (node.next == tail) {
            tail = node
            node.next = null
            length--
            return
        }

        node.next = node.next?.next

        length--
    }
}


fun main() {
    val node1 = Node(value = 10, next = null)
    val linkedList = LinkedList(head = node1, tail = node1, length = 1)
    println("Head ==> ${linkedList.head}")
    println("Tail ==> ${linkedList.tail}")
    println("Length ==> ${linkedList.length}")

    linkedList.push(Node(value = 15))
    linkedList.push(Node(value = 20))
    linkedList.push(Node(value = 30))
//    linkedList.push(Node(value = 40))
    println("===== LinkedList =====")
    linkedList.printList()
    println("===========")

    linkedList.pop()
    println("===== Popped LinkedList =====")
    linkedList.printList()
    println("===========")

    linkedList.unshift(Node(value = 50))
    println("===== Unshifted LinkedList =====")
    linkedList.printList()
    println("===========")

    linkedList.shift()
    println("===== Shifted LinkedList =====")
    linkedList.printList()
    println("===========")

    println(linkedList.get(1))
    println("===========")

    linkedList.set(index = 1, value = 5)
    linkedList.insert(index = 2, newNode = Node(value = 40))
    linkedList.printList()
    println("===========")

    linkedList.remove(1)
    linkedList.printList()
    println("===========")

    linkedList.reverse()
    linkedList.printList()

}
