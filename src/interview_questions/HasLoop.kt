
package interview_questions

import LinkedList
import Node

/**
 * Problem: Linked List Cycle Detection (Has Loop)
 *
 * Goal:
 * Determine whether a Linked List contains a cycle.
 *
 * A cycle exists when a node's `next` reference points to a node
 * that has already been visited, instead of eventually reaching null.
 *
 * Important:
 * A cycle does NOT necessarily mean that the last node points to the head.
 * It can point to any previous node in the list.
 *
 *
 * APPROACH 1: Checking tail.next == head
 *
 * Initial Idea:
 * Since a linked list has a tail, we could check whether:
 *
 *     tail.next == head
 *
 * If true, the list contains a cycle.
 *
 * Why this approach is incomplete:
 * - Not every LinkedList implementation maintains a tail pointer.
 * - Even if a tail pointer exists, a cycle can point to any node,
 *   not necessarily the head.
 *
 * Example:
 *
 *     1 -> 2 -> 3 -> 4
 *          ^         |
 *          |_________|
 *
 * Here, node 4 points to node 2, so a cycle exists,
 * but tail.next != head.
 *
 * Conclusion:
 * This approach only detects one specific type of cycle,
 * not all possible cycles.
 *
 *
 * APPROACH 2: HashSet (Visited Nodes)
 *
 * Idea:
 * Traverse the list while keeping track of every node we have visited.
 *
 * Before processing a node:
 * - Check whether this exact node has already been visited.
 * - If yes, we found a cycle.
 * - Otherwise, store it and continue.
 *
 * Why HashSet?
 * - A List requires O(n) time to search for an element.
 * - A HashSet provides O(1) average-time lookup and insertion.
 *
 * Important Implementation Detail:
 * We must compare node identity (reference identity), not node values.
 *
 * Two different nodes may contain the same value but are still
 * different nodes.
 *
 * In Kotlin:
 * - == compares using equals().
 * - === compares reference identity.
 *
 * Therefore, Node should be a regular class rather than a data class
 * if we want the default equals() and hashCode() implementations
 * to use object identity.
 *
 * Time Complexity: O(n) average
 * Space Complexity: O(n)
 *
 * Implementation:
 *
 *     var temp = list.head
 *     val visitedNodes = mutableSetOf<Node<T>>()
 *
 *     while (temp != null) {
 *         if (!visitedNodes.add(temp)) return true
 *         temp = temp.next
 *     }
 *
 *     return false
 *
 * Why is add() enough?
 * MutableSet.add() returns:
 * - true if the element was successfully added.
 * - false if the element already exists.
 *
 * This eliminates the need for a separate contains() call.
 *
 *
 * APPROACH 3: Floyd's Cycle Detection Algorithm
 * (Tortoise and Hare Algorithm)
 *
 * Optimal Solution
 *
 * Idea:
 * Instead of storing visited nodes, use two pointers:
 *
 * - Slow pointer moves one node at a time.
 * - Fast pointer moves two nodes at a time.
 *
 * Why does this work?
 *
 * If there is no cycle:
 * The fast pointer will eventually reach null.
 *
 * If there is a cycle:
 * Once both pointers enter the cycle, the fast pointer
 * gains one node on the slow pointer in every iteration.
 *
 * The distance between them decreases by one modulo
 * the cycle length.
 *
 * Since the cycle has a finite number of nodes,
 * the two pointers must eventually meet.
 *
 * Important:
 * They do not need to start one node apart.
 * The important property is their relative speed difference
 * of one node per iteration.
 *
 * Why is this optimal?
 * - We do not need to store previously visited nodes.
 * - We only maintain two references.
 * - We detect the cycle in linear time.
 *
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 *
 *
 * FINAL CONCLUSION:
 *
 * We moved from a simple assumption about the tail,
 * to explicitly tracking visited nodes,
 * and finally to exploiting the mathematical behavior
 * of two pointers moving at different speeds.
 *
 * The key insight:
 * We do not need to remember every visited node
 * if we can guarantee that a cycle will cause
 * two moving pointers to meet.
 */
fun <T> hasLoop(list: LinkedList<T>): Boolean {

    // Floyd's Cycle Detection Algorithm
    var slow = list.head
    var fast = list.head

    while (fast != null) {
        slow = slow?.next
        fast = fast.next?.next

        if (slow === fast) return true
    }

    return false
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
    linkedList.push(Node(value = 40))

    println("===== LinkedList =====")
    linkedList.printList()
    println("===========")

    println(hasLoop(linkedList))
}
