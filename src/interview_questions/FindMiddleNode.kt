package interview_questions

import LinkedList
import Node

/**
 * To solve this problem
 *
 * Use the Two Pointers technique to find the middle node.
 *
 * The slow pointer moves one node at a time,
 * while the fast pointer moves two nodes at a time.
 *
 * When the fast pointer reaches the end of the list,
 * the slow pointer will be pointing to the middle node.
 *
 * For even-sized lists, returns the second middle node.
 */

fun <T> findMiddleNode(list: LinkedList<T>): Node<T>? {
    var slow = list.head
    var fast = list.head

    while (fast?.next != null) {
        slow = slow?.next
        fast = fast.next?.next
    }

    return slow
}

fun main () {
    val node1 = Node(value = 10, next = null)
    val linkedList = LinkedList(head = node1, tail = node1, length = 1)
    println("Head ==> ${linkedList.head}")
    println("Tail ==> ${linkedList.tail}")
    println("Length ==> ${linkedList.length}")

    linkedList.push(Node(value = 15))
    linkedList.push(Node(value = 20))
    linkedList.push(Node(value = 30))
    linkedList.push(Node(value = 40))
    println("===== LinkedList =====")
    linkedList.printList()
    println("===========")

    println(findMiddleNode(linkedList))
}