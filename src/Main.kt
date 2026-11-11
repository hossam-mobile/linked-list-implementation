data class Node<T>(
    val value: T,
    var next: Node<T>? = null
)

class LinkedList<T>(var head: Node<T>?, var tail: Node<T>?, var length: Int = 1) {
    fun printList() {
        var tempNode: Node<T>? = this.head
        while (tempNode != null) {
            println(tempNode)
            tempNode = tempNode.next
        }
    }

    //TIP push = append
    fun push(value: T) {
        val newNode = Node(value = value)
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
        if (length < 1 && head?.next == null) {
            head = null
            tail = null
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

    fun unshift(value: T) {
        val newNode = Node(value = value)
        if (length == 0 || head == null) {
            head = newNode
            tail = newNode
            return
        }

        newNode.next = head
        head = newNode

        length++
    }

    fun shift() {
        if (length == 0 || head == null) {
            println("Cannot shift an empty node")
            return
        }

        if (length == 1 || head?.next == null) {
            head = null
            tail = null
            return
        }

        head = head?.next
        length--
    }
}


fun main() {
    val node1 = Node(value = 10, next = null)
    val linkedList = LinkedList(head = node1, tail = node1, length = 1)
    println("Head ==> ${linkedList.head}")
    println("Tail ==> ${linkedList.tail}")
    println("Length ==> ${linkedList.length}")

    linkedList.push(3)
    linkedList.push(2)
    linkedList.push(9)
    println("===== LinkedList =====")
    linkedList.printList()
    println("===========")

    linkedList.pop()
    println("===== Popped LinkedList =====")
    linkedList.printList()
    println("===========")

    linkedList.unshift(5)
    println("===== Unshifted LinkedList =====")
    linkedList.printList()
    println("===========")

    linkedList.shift()
    println("===== Shifted LinkedList =====")
    linkedList.printList()
    println("===========")


}
