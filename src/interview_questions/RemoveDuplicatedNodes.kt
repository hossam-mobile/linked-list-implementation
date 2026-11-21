package interview_questions

import LinkedList
import Node

/**
 * Problem: Remove Duplicates From an Unsorted Singly Linked List
 *
 * Goal:
 * Remove all duplicate values from an unsorted singly linked list
 * while preserving the original relative order of the remaining nodes.
 *
 * Example:
 *
 *     10 -> 20 -> 10 -> 30 -> 20 -> null
 *
 *     Expected Result:
 *     10 -> 20 -> 30 -> null
 *
 * Important:
 * - The list is unsorted (duplicates are not necessarily adjacent).
 * - The first occurrence of each value must be kept.
 * - Tail pointer and list length must stay consistent after deletions.
 *
 *
 * ============================================================
 * INITIAL THINKING: Understanding the Problem
 * ============================================================
 *
 * The main challenges:
 *
 * 1. Unsorted order:
 *    Unlike a sorted linked list, duplicate values can appear
 *    anywhere in the list, not just next to each other.
 *
 * 2. Singly linked list structure:
 *    We can only traverse forward and have no backward (prev) pointer.
 *    Deleting a node directly requires knowing the node before it.
 *
 *
 * ============================================================
 * APPROACH 1: Brute Force With Head-Based Deletion
 * ============================================================
 *
 * Initial Idea:
 *
 * Use two nested loops: pick a node (current), scan forward to find
 * matching values, and delete each duplicate by calling a general
 * delete method that searches from the head.
 *
 * Why it was inefficient:
 *
 * - Searching from the head on every deletion takes O(n) time.
 * - Combining O(n) deletion inside two nested loops degrades
 *   overall performance to O(n³).
 * - Standing directly on the target node forces us to re-traverse
 *   just to find its previous node.
 *
 * Conclusion:
 *
 * Logically valid, but performs redundant traversals during deletion.
 *
 *
 * ============================================================
 * KEY OBSERVATION 1: Look-Ahead Deletion (O(1) Removal)
 * ============================================================
 *
 * Instead of standing on the node we want to delete, we can stand
 * one step BEFORE it and inspect `node.next`.
 *
 * Because we already hold a reference to the previous node:
 *
 * - Removing `node.next` is just `node.next = node.next?.next` -> O(1).
 * - When a deletion happens, we DO NOT advance the pointer, because
 *   a new node shifts into `node.next` and must also be checked
 *   (critical for consecutive duplicates like 10 -> 10 -> 10).
 *
 *
 * ============================================================
 * APPROACH 2: Two Pointers / Runner Technique (In-Place)
 * ============================================================
 *
 * Main Idea:
 *
 * - Use `current` to anchor each unique element.
 * - Use `runner` starting at `current` to look ahead at `runner.next`.
 * - If `runner.next.value == current.value`, remove `runner.next` in O(1)
 *   and keep `runner` in place.
 * - Otherwise, advance `runner = runner.next`.
 *
 * Complexity:
 *
 * - Time Complexity:  O(n²) (nested traversal for every element)
 * - Space Complexity: O(1)  (in-place, only two pointers used)
 *
 * Trade-off:
 *
 * Optimal when memory is strictly constrained, but slow on large lists
 * because we repeatedly scan the remaining nodes to detect duplicates.
 *
 *
 * ============================================================
 * KEY OBSERVATION 2: Trading Space for Time (HashSet)
 * ============================================================
 *
 * Why did Approach 2 take O(n²)?
 * Because we had no fast way to remember which values we had already seen.
 *
 * If we store seen values in a HashSet:
 *
 * - Checking and inserting a value takes O(1) average time.
 * - We must store `node.value` (type T), NOT `Node<T>` references,
 *   because distinct Node instances have different memory addresses
 *   even when holding equal values.
 * - Combining a HashSet with our look-ahead pointer (`tempNode.next`)
 *   lets us detect AND delete duplicates in a single pass.
 *
 *
 * ============================================================
 * APPROACH 3: HashSet + Look-Ahead Pointer (Optimal Time)
 * ============================================================
 *
 * Main Idea:
 *
 * 1. Handle empty list upfront.
 * 2. Create a `MutableSet<T>` to track seen values.
 * 3. Add `head.value` to the set and start `tempNode` at `head`.
 * 4. While `tempNode?.next != null`:
 *    - Try adding `tempNode.next.value` to the set.
 *    - If `add()` returns `false` (already seen):
 *      Remove `tempNode.next` in O(1) using `removeNextNode(tempNode)`.
 *      Keep `tempNode` stationary to inspect the next shifted node.
 *    - If `add()` returns `true` (first time seen):
 *      Advance `tempNode = tempNode.next`.
 *
 *
 * ============================================================
 * STEP-BY-STEP EXAMPLE
 * ============================================================
 *
 * Given:
 *
 *     10 -> 20 -> 10 -> 30 -> null
 *
 * Step 1: Initialize set with head value.
 *
 *     seen = {10}
 *     tempNode = Node(10)
 *
 * Step 2: Check tempNode.next -> Node(20).
 *
 *     20 is not in seen -> add 20, advance tempNode.
 *     seen = {10, 20}
 *     tempNode = Node(20)
 *
 * Step 3: Check tempNode.next -> Node(10).
 *
 *     10 is already in seen -> remove Node(10), keep tempNode at Node(20).
 *     List becomes: 10 -> 20 -> 30 -> null
 *     tempNode = Node(20)
 *
 * Step 4: Check tempNode.next -> Node(30).
 *
 *     30 is not in seen -> add 30, advance tempNode.
 *     seen = {10, 20, 30}
 *     tempNode = Node(30)
 *
 * Step 5: tempNode.next is null -> loop terminates.
 *
 *     Result: 10 -> 20 -> 30 -> null
 *
 *
 * ============================================================
 * EDGE CASES AND VALIDATION
 * ============================================================
 *
 * 1. Empty Linked List (`head == null`):
 *    - Handled immediately at the start; avoids null pointer issues.
 *
 * 2. Single-Node List (`10 -> null`):
 *    - `tempNode.next` is null; loop does not run; list returned unchanged.
 *
 * 3. All Duplicates (`10 -> 10 -> 10 -> 10 -> null`):
 *    - `tempNode` stays at head while every duplicate `next` is removed
 *      one by one until `tail` is updated back to head.
 *
 * 4. No Duplicates (`10 -> 20 -> 30 -> 40 -> null`):
 *    - Every element is added to the set; `tempNode` advances cleanly
 *      to the end with zero deletions.
 *
 * 5. Duplicate at the Tail:
 *    - `removeNextNode` detects `node.next == tail` and updates `tail = node`.
 *
 *
 * ============================================================
 * IMPLEMENTATION
 * ============================================================
 *
 *     fun <T> removeDuplicatedNodes(list: LinkedList<T>): LinkedList<T> {
 *         if (list.head == null) return list.emptyList()
 *
 *         val seenValues = mutableSetOf<T>()
 *         var tempNode = list.head
 *         seenValues.add(tempNode!!.value)
 *
 *         while (tempNode?.next != null) {
 *             if (!seenValues.add(tempNode.next!!.value)) {
 *                 list.removeNextNode(tempNode)
 *             } else {
 *                 tempNode = tempNode.next
 *             }
 *         }
 *
 *         return list
 *     }
 *
 *     // Helper inside LinkedList<T> for O(1) deletion:
 *     fun removeNextNode(node: Node<T>?) {
 *         if (length == 1 || node?.next == null) {
 *             return
 *         } else if (node.next == tail) {
 *             tail = node
 *             node.next = null
 *             length--
 *             return
 *         }
 *
 *         node.next = node.next?.next
 *         length--
 *     }
 *
 *
 * ============================================================
 * COMPLEXITY ANALYSIS
 * ============================================================
 *
 * Approach 3 (HashSet + Look-Ahead):
 *
 * - Time Complexity: O(n)
 *   We traverse the list once, performing O(1) HashSet lookup/insertion
 *   and O(1) pointer rewiring at each step.
 *
 * - Space Complexity: O(n)
 *   In the worst case (no duplicates), the HashSet stores all n values.
 *
 * Comparison Summary:
 *
 * - Runner Approach:  Time O(n²), Space O(1) -> Best when no extra memory is allowed.
 * - HashSet Approach: Time O(n),  Space O(n) -> Best when speed is the priority.
 *
 *
 * ============================================================
 * FINAL CONCLUSION: THE KEY INSIGHTS & SIGNALS
 * ============================================================
 *
 * Problem Signals:
 *
 * - "Unsorted": Cannot rely on adjacent comparisons; need either nested
 *   scanning or an auxiliary lookup structure.
 * - "Duplicates": Direct signal to consider a HashSet/HashMap for O(1)
 *   membership checks.
 * - "Singly Linked List": No backward pointer; always position your
 *   pointer one node BEFORE the target node to delete in O(1).
 *
 * Key Problem-Solving Lesson:
 *
 * 1. Identify the bottleneck: Re-traversing to delete nodes or check history
 *    inflates complexity. Fix structural bottlenecks with look-ahead pointers,
 *    and lookup bottlenecks with time-space trade-offs.
 * 2. When mutating a list during traversal, only advance the pointer when
 *    no deletion occurs so consecutive targets are never skipped.
 */

fun <T> removeDuplicatedNodes(list: LinkedList<T>): LinkedList<T> {
    if (list.head == null) return list.emptyList()

//    var current = list.head
//    var runner = current
//
//    while (current != null) {
//        while (runner?.next != null) {
//
//            if (current.value == runner.next?.value) {
//                list.removeNextNode(runner)
//
//            } else {
//                runner = runner.next
//            }
//        }
//        current = current.next
//        runner = current
//
//    }

    val newList = mutableSetOf<T>()
    var tempNode = list.head
    newList.add(tempNode!!.value)
    while (tempNode?.next != null) {
        if (!newList.add(tempNode.next!!.value)) {
            list.removeNextNode(tempNode)
        } else {

            tempNode = tempNode.next
        }


    }
    return list
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

    println(removeDuplicatedNodes(linkedList))
}