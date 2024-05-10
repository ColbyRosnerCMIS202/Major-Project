package com.example.demodemo.schedulebuilder;

import java.util.Iterator;

public class MyHashTable {
    // Default capacity of the hash table
    private static final int DEFAULT_CAPACITY = 16;
    // Default load factor threshold for resizing the hash table
    private static final double DEFAULT_LOAD_FACTOR = 0.75;

    // Array of linked lists to store key-value pairs
    private LinkedList<Entry>[] table;
    // Current number of elements in the hash table
    private int size;
    // Maximum number of elements before resizing
    private int capacity;
    // Load factor threshold for resizing
    private double loadFactor;

    // Entry class to store key-value pairs
    private static class Entry {
        String key;
        Worker value;

        Entry(String key, Worker value) {
            this.key = key;
            this.value = value;
        }
    }

    // Constructor to create a hash table with default capacity and load factor
    public MyHashTable() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    // Constructor to create a hash table with specified capacity and load factor
    public MyHashTable(int capacity, double loadFactor) {
        this.capacity = capacity;
        this.loadFactor = loadFactor;
        this.size = 0;
        this.table = new LinkedList[capacity];
    }

    // Method to put a key-value pair into the hash table
    public void put(String key, Worker value) {
        // Calculate the index in the table based on the hash code of the key
        int index = hash(key);
        // If the linked list at the index is null, create a new linked list
        if (table[index] == null) {
            table[index] = new LinkedList<>();
        }

        // Check if the key already exists in the linked list
        for (Entry entry : table[index]) {
            if (entry.key.equals(key)) {
                // If the key exists, update the value and return
                entry.value = value;
                return;
            }
        }

        // If the key does not exist, add a new entry to the linked list
        table[index].add(new Entry(key, value));
        // Increment the size of the hash table
        size++;

        // Check if the load factor threshold is reached
        if ((double) size / capacity > loadFactor) {
            // If the threshold is reached, resize the hash table
            resize();
        }
    }

    // Method to get the value associated with a key from the hash table
    public Worker get(String key) {
        // Calculate the index in the table based on the hash code of the key
        int index = hash(key);
        // If the linked list at the index is not null, iterate over it
        if (table[index] != null) {
            for (Entry entry : table[index]) {
                // If the key is found, return the associated value
                if (entry.key.equals(key)) {
                    return entry.value;
                }
            }
        }
        // If the key is not found, return null
        return null;
    }

    // Method to resize the hash table when the load factor threshold is reached
    private void resize() {
        // Double the capacity of the hash table
        capacity *= 2;
        // Create a new array of linked lists with the new capacity
        LinkedList<Entry>[] newTable = new LinkedList[capacity];

        // Iterate over the existing table and rehash all the entries
        for (LinkedList<Entry> list : table) {
            if (list != null) {
                for (Entry entry : list) {
                    // Calculate the new index for each entry
                    int index = hash(entry.key);
                    // If the linked list at the new index is null, create a new linked list
                    if (newTable[index] == null) {
                        newTable[index] = new LinkedList<>();
                    }
                    // Add the entry to the linked list at the new index
                    newTable[index].add(entry);
                }
            }
        }

        // Replace the old table with the new table
        table = newTable;
    }

    // Method to calculate the index in the table based on the hash code of the key
    private int hash(String key) {
        // Take the absolute value of the hash code and mod it by the capacity to get the index
        return Math.abs(key.hashCode()) % capacity;
    }
}