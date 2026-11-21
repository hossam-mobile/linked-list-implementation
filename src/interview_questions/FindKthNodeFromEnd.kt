package interview_questions

import LinkedList
import Node

/**
 * Problem: Kth Node From The End
 *
 * Goal:
 * Find and return the Kth node from the end of a singly linked list.
 *
 * Example:
 *
 *     1 -> 2 -> 3 -> 4 -> 5 -> null
 *
 *     k = 2
 *
 *     Expected Result: Node(4)
 *
 * Important:
 * - K is 1-based.
 * - k = 1 means the last node.
 * - k = length means the head node.
 * - If k <= 0 or k > length, return null.
 *
 *
 * ============================================================
 * INITIAL THINKING: Understanding the Problem
 * ============================================================
 *
 * The main challenge:
 *
 * In a singly linked list, we can only move forward.
 * We cannot directly access the previous node or traverse
 * the list backward.
 *
 * Therefore, finding a node based on its position from the end
 * is not straightforward.
 *
 *
 * ============================================================
 * APPROACH 1: Reversing the Linked List
 * ============================================================
 *
 * Initial Idea:
 *
 * Reverse the linked list so that the last node becomes the head.
 *
 * Example:
 *
 *     Original:
 *     1 -> 2 -> 3 -> 4 -> 5 -> null
 *
 *     After Reverse:
 *     5 -> 4 -> 3 -> 2 -> 1 -> null
 *
 * Why this approach makes sense:
 *
 * Reversing the list transforms the problem from finding
 * the Kth node from the end into finding the Kth node
 * from the beginning.
 *
 * Why we did not choose it:
 *
 * - It modifies the original linked list.
 * - We need to reverse the list back if its original order
 *   must be preserved.
 * - It requires additional traversal to restore the list.
 *
 * Conclusion:
 *
 * The approach is valid, but it introduces unnecessary
 * modifications and additional work.
 *
 *
 * ============================================================
 * KEY OBSERVATION: The Distance Between Two Positions
 * ============================================================
 *
 * Instead of reversing the list, we can think about
 * the relationship between the target node and the end.
 *
 * If we know the distance between two pointers,
 * we may be able to reach the target node without
 * reversing the list or calculating its length.
 *
 * This leads us to consider using two pointers:
 *
 * - Slow
 * - Fast
 *
 *
 * ============================================================
 * APPROACH 2: Two Pointers With a Fixed Distance
 * ============================================================
 *
 * Main Idea:
 *
 * Start both pointers at the head.
 *
 * Move the fast pointer K steps ahead while keeping
 * the slow pointer at the head.
 *
 * This creates a fixed distance of K nodes between them.
 *
 * Then move both pointers one step at a time.
 *
 * Since both pointers move at the same speed,
 * the distance between them remains constant.
 *
 * When fast reaches null, slow will be pointing
 * to the Kth node from the end.
 *
 *
 * WHY DOES THIS WORK?
 *
 * The important property is the fixed distance
 * between the two pointers.
 *
 * Once the distance is established:
 *
 * - Fast is always K nodes ahead of slow.
 * - Both pointers move at the same speed.
 * - Their relative distance never changes.
 *
 * Therefore, when fast reaches the end,
 * slow must be exactly K nodes away from the end.
 *
 *
 * ============================================================
 * STEP-BY-STEP EXAMPLE
 * ============================================================
 *
 * Given:
 *
 *     1 -> 2 -> 3 -> 4 -> 5 -> null
 *
 *     k = 2
 *
 * Step 1: Initialize both pointers.
 *
 *     Slow = 1
 *     Fast = 1
 *
 * Step 2: Move fast K steps ahead.
 *
 *     Slow = 1
 *     Fast = 3
 *
 *     1 -> 2 -> 3 -> 4 -> 5 -> null
 *     S       F
 *
 * Step 3: Move both pointers at the same speed.
 *
 *     Iteration 1:
 *     Slow = 2
 *     Fast = 4
 *
 *     Iteration 2:
 *     Slow = 3
 *     Fast = 5
 *
 *     Iteration 3:
 *     Slow = 4
 *     Fast = null
 *
 * Step 4: Return slow.
 *
 *     Result = Node(4)
 *
 *
 * ============================================================
 * EDGE CASES AND VALIDATION
 * ============================================================
 *
 * 1. Invalid K:
 *
 *     If k <= 0, return null.
 *
 *     K must be a positive 1-based position.
 *
 * 2. K is greater than the list length:
 *
 *     If fast reaches null before completing K steps,
 *     the requested position does not exist.
 *
 *     Return null.
 *
 * 3. K equals the list length:
 *
 *     If fast becomes null immediately after completing
 *     the K steps, the answer is the head node.
 *
 *     Return slow.
 *
 * 4. Empty Linked List:
 *
 *     Both pointers start at null.
 *
 *     Return null.
 *
 *
 * ============================================================
 * IMPLEMENTATION
 * ============================================================
 *
 *     fun <T> findKthNodeFromEnd(
 *         list: LinkedList<T>,
 *         k: Int
 *     ): Node<T>? {
 *
 *         if (k <= 0) return null
 *
 *         var slow = list.head
 *         var fast = list.head
 *
 *         // Establish a fixed distance of K nodes.
 *         for (i in k downTo 1) {
 *             if (fast == null) return null
 *             fast = fast.next
 *         }
 *
 *         // K equals the list length.
 *         if (fast == null) return slow
 *
 *         // Maintain the fixed distance.
 *         while (fast != null) {
 *             slow = slow?.next
 *             fast = fast.next
 *         }
 *
 *         return slow
 *     }
 *
 *
 * ============================================================
 * COMPLEXITY ANALYSIS
 * ============================================================
 *
 * Time Complexity: O(n)
 *
 * First loop:
 * Moves fast K steps.
 * Time = O(K)
 *
 * Second loop:
 * Moves both pointers until fast reaches null.
 * Time = O(n - K)
 *
 * Total:
 *
 *     O(K) + O(n - K) = O(n)
 *
 * Although there are two loops, they are sequential,
 * not nested.
 *
 * Both loops together traverse at most the entire list.
 *
 * Space Complexity: O(1)
 *
 * Only two pointers are used.
 * No additional data structure or memory proportional
 * to the list size is required.
 *
 *
 * ============================================================
 * WHY IS THIS THE OPTIMAL SOLUTION?
 * ============================================================
 *
 * - We traverse the list only once.
 * - We do not need to calculate the list length separately.
 * - We do not modify or reverse the original list.
 * - We use constant extra space.
 *
 * The solution achieves:
 *
 *     Time:  O(n)
 *     Space: O(1)
 *
 *
 * ============================================================
 * FINAL CONCLUSION: THE KEY INSIGHT
 * ============================================================
 *
 * The key insight was realizing that we do not need
 * to access the list backward or know its length.
 *
 * Instead, we can establish a fixed distance between
 * two pointers and maintain that distance while moving.
 *
 * This allows us to determine the Kth node from the end
 * using only forward traversal.
 *
 * The important problem-solving lesson:
 *
 * When direct access to a position is difficult,
 * think about whether you can establish a relationship
 * between two positions that makes the target predictable.
 *
 */

fun <T> findKthNodeFromEnd(list: LinkedList<T>, k: Int): Node<T>? {

    if (k <= 0) return null

    var slow = list.head
    var fast = list.head

    repeat(k) {
        if(fast == null) return null
        fast = fast.next
    }

    if(fast == null) return slow

    while (fast != null) {
        slow = slow?.next
        fast = fast.next
    }
    return slow
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

    println(findKthNodeFromEnd(linkedList, 1))
    println(findKthNodeFromEnd(linkedList, 5))
    println(findKthNodeFromEnd(linkedList, 8))
}