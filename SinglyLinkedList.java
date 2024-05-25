public class SinglyLinkedList {
    private Node head;
    private int size;

    private class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    public SinglyLinkedList() {
        this.head = null;
        this.size = 0;
    }

    // Inserts an element at the specified index
    public void insert(int index, int data) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        Node newNode = new Node(data);
        if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else {
            Node current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
        }
        size++;
    }

    // Deletes the element at the specified index
    public void delete(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        if (index == 0) {
            head = head.next;
        } else {
            Node current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            current.next = current.next.next;
        }
        size--;
    }

    // Returns the size of the linked list
    public int size() {
        return size;
    }

    // Returns true if the linked list is empty, false otherwise
    public boolean isEmpty() {
        return size == 0;
    }

    // Rotates the linked list by k positions to the right
    public void rotateRight(int k) {
        if (size == 0 || k <= 0) return;
        k = k % size;
        if (k == 0) return;
        
        Node oldTail = head;
        int len = 1;
        while (oldTail.next != null) {
            oldTail = oldTail.next;
            len++;
        }
        oldTail.next = head;
        
        Node newTail = head;
        for (int i = 0; i < len - k - 1; i++) {
            newTail = newTail.next;
        }
        head = newTail.next;
        newTail.next = null;
    }

    // Reverses the linked list
    public void reverse() {
        Node prev = null;
        Node current = head;
        Node next;
        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        head = prev;
    }

    // Appends an element to the end of the linked list
    public void append(int data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    // Prepends an element to the beginning of the linked list
    public void prepend(int data) {
        Node newNode = new Node(data);
        newNode.next = head;
        head = newNode;
        size++;
    }

    // Merges two linked lists into a single linked list
    public void merge(SinglyLinkedList otherList) {
        if (otherList.head == null) return;
        if (this.head == null) {
            this.head = otherList.head;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = otherList.head;
        }
        this.size += otherList.size;
    }

    // Interleaves two linked lists into a single linked list
    public void interleave(SinglyLinkedList otherList) {
        Node current1 = this.head;
        Node current2 = otherList.head;
        Node next1, next2;

        while (current1 != null && current2 != null) {
            next1 = current1.next;
            next2 = current2.next;

            current1.next = current2;
            if (next1 == null) break;
            current2.next = next1;

            current1 = next1;
            current2 = next2;
        }
        this.size += otherList.size;
    }

    // Returns the middle element of the linked list
    public int getMiddle() {
        if (head == null) throw new IllegalStateException("List is empty");

        Node slow = head;
        Node fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow.data;
    }

    // Returns the index of the first occurrence of the specified element in the linked list, or -1 if the element is not found
    public int indexOf(int data) {
        Node current = head;
        int index = 0;
        while (current != null) {
            if (current.data == data) {
                return index;
            }
            current = current.next;
            index++;
        }
        return -1;
    }

    // Splits the linked list into two linked lists at the specified index
    public SinglyLinkedList[] split(int index) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();

        SinglyLinkedList list1 = new SinglyLinkedList();
        SinglyLinkedList list2 = new SinglyLinkedList();

        Node current = head;
        for (int i = 0; i < index; i++) {
            list1.append(current.data);
            current = current.next;
        }
        while (current != null) {
            list2.append(current.data);
            current = current.next;
        }
        return new SinglyLinkedList[] { list1, list2 };
    }

    // Utility method to print the linked list
    public void printList() {
        Node current = head;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        SinglyLinkedList list = new SinglyLinkedList();
        
        // Test the implemented methods
        list.append(1);
        list.append(2);
        list.append(3);
        list.printList();

        list.insert(1, 4);
        list.printList();

        list.delete(2);
        list.printList();

        System.out.println("Size: " + list.size()); 
        System.out.println("Is empty: " + list.isEmpty());

        list.rotateRight(1);
        list.printList();

        list.reverse();
        list.printList(); 

        list.prepend(0);
        list.printList(); 

        SinglyLinkedList list2 = new SinglyLinkedList();
        list2.append(5);
        list2.append(6);

        list.merge(list2);
        list.printList(); 

        list2.append(7);
        list2.append(8);
        list2.append(9);
        list.interleave(list2);
        list.printList(); 

        System.out.println("Middle element: " + list.getMiddle());

        System.out.println("Index of 5: " + list.indexOf(5)); 

        SinglyLinkedList[] splitLists = list.split(4);
        splitLists[0].printList(); 
        splitLists[1].printList(); 
    }
}