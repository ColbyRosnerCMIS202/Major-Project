package com.example.demodemo.schedulebuilder;

import java.util.Iterator;
import java.util.NoSuchElementException;
// BST class for implementing a binary search tree
class BST<T extends Comparable<T>> {
    private Node<T> root; // Root node of the binary search tree

    // Node class for representing each node in the binary search tree
    private static class Node<T> {
        T data; // Data stored in the node
        Node<T> left, right; // References to the left and right child nodes

        // Constructor to create a new node with the given data
        Node(T data) {
            this.data = data;
            left = right = null;
        }
    }

    // Method to insert a new element into the binary search tree
    public void insert(T data) {
        root = insertRec(root, data); // Call the recursive insert method
    }

    // Recursive method to insert a new element into the binary search tree
    private Node<T> insertRec(Node<T> root, T data) {
        // If the root is null, create a new node and return it
        if (root == null) {
            return new Node<>(data);
        }

        // Compare the new data with the data at the current node
        // If the new data is less, insert it into the left subtree
        if (data.compareTo(root.data) < 0) {
            root.left = insertRec(root.left, data);
        }
        // If the new data is greater, insert it into the right subtree
        else if (data.compareTo(root.data) > 0) {
            root.right = insertRec(root.right, data);
        }

        // Return the root node
        return root;
    }

    // Method to perform a search for a specific element in the binary search tree
    public boolean search(T data) {
        return searchRec(root, data); // Call the recursive search method
    }

    // Recursive method to search for a specific element in the binary search tree
    private boolean searchRec(Node<T> root, T data) {
        // If the root is null, the element is not found
        if (root == null) {
            return false;
        }

        // If the data at the current node is equal to the target data, return true
        if (data.compareTo(root.data) == 0) {
            return true;
        }

        // If the target data is less than the data at the current node, search the left subtree
        if (data.compareTo(root.data) < 0) {
            return searchRec(root.left, data);
        }

        // Otherwise, search the right subtree
        return searchRec(root.right, data);
    }

    // Method to perform an inorder traversal of the binary search tree
    public void inorder() {
        inorderRec(root); // Call the recursive inorder traversal method
    }

    // Recursive method to perform an inorder traversal of the binary search tree
    private void inorderRec(Node<T> root) {
        // If the root is not null, recursively traverse the left subtree, visit the root, and then recursively traverse the right subtree
        if (root != null) {
            inorderRec(root.left);
            System.out.print(root.data + " ");
            inorderRec(root.right);
        }
    }
}

// LinkedList class for implementing a singly linked list
public class LinkedList<T> implements Iterable<T> {
    private Node<T> head; // Head node of the linked list
    private int size; // Size of the linked list

    // Node class for representing each node in the linked list
    private static class Node<T> {
        T data; // Data stored in the node
        Node<T> next; // Reference to the next node in the linked list

        // Constructor to create a new node with the given data
        Node(T data) {
            this.data = data;
        }
    }

    // Method to add a new element to the end of the linked list
    public void add(T data) {
        Node<T> newNode = new Node<>(data); // Create a new node with the given data
        // If the linked list is empty, set the new node as the head
        if (head == null) {
            head = newNode;
        }
        // Otherwise, traverse the linked list to the last node and append the new node
        else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++; // Increment the size of the linked list
    }

    // Method to remove the first occurrence of the specified element from the linked list
    public void remove(T data) {
        // If the linked list is empty, return
        if (head == null) {
            return;
        }
        // If the element to be removed is at the head, update the head to the next node
        if (head.data.equals(data)) {
            head = head.next;
            size--; // Decrement the size of the linked list
            return;
        }
        // Otherwise, traverse the linked list to find the element and remove it
        Node<T> current = head;
        while (current.next != null) {
            if (current.next.data.equals(data)) {
                current.next = current.next.next;
                size--; // Decrement the size of the linked list
                return;
            }
            current = current.next;
        }
    }

    // Method to get the size of the linked list
    public int size() {
        return size;
    }

    // Method to create an iterator for the linked list
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    // Inner class for implementing the iterator
    private class LinkedListIterator implements Iterator<T> {
        private Node<T> current = head; // Current node in the iteration

        // Method to check if there are more elements in the iteration
        public boolean hasNext() {
            return current != null;
        }

        // Method to get the next element in the iteration
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException(); // Throw an exception if there are no more elements
            }
            T data = current.data; // Get the data from the current node
            current = current.next; // Move to the next node
            return data;
        }
    }

    // Method to print the elements of the linked list
    public void print() {
        Node<T> current = head; // Start from the head of the linked list
        while (current != null) { // Iterate until the end of the linked list
            System.out.print(current.data + " "); // Print the data of the current node
            current = current.next; // Move to the next node
        }
        System.out.println(); // Print a newline after printing all elements
    }
}