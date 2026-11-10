
data class Node<T>(
    val value: T,
    var next: Node<T>? = null
)

class LinkedList<T>(var head: Node<T>?, var tail: Node<T>?, var length: Int = 1) {
    fun printList(): Unit {
        var tempNode: Node<T>? = this.head
        while(tempNode != null) {
            println(tempNode)
            tempNode = tempNode.next
        }
    }

    //TIP push = append
    fun push(value: T): Unit {
        val newNode = Node(value = value)
        val tempNode = head
        if(this.head == null) {
            head = newNode
            tail = newNode
        } else {
            tail?.next = newNode
            tail = newNode
        }

        length++
    }

    fun pop(): Unit {
        // no items
        if(head == null) return
        // one item
        if(length < 1 && head?.next == null) {
            head = null
            tail = null
        }

        // more items
        if(length > 1 && head?.next != null) {
            val tempNode = head
            while (head?.next != null) {
                if(head?.next == tail) {
                    tail = head
                    head?.next = null
                    head = tempNode
                } else{

                head = head?.next
                }
            }
        }

        length--
    }
}

fun main() {
    val node1 = Node<Int>(value = 10, next = null)
    val linkedList = LinkedList<Int>(head = node1, tail = node1, length = 1)
    println("Head ==> ${linkedList.head}")
    println("Tail ==> ${linkedList.tail}")
    println("Length ==> ${linkedList.length}")

    linkedList.push(3)
    println("===== LinkedList =====")
    linkedList.printList()
    println("===========")

    linkedList.pop()
    println("===== Popped LinkedList =====")
    linkedList.printList()
    println("===========")


}
