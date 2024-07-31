package assignment03;
import java.util.LinkedList;

public class HashMapSeparateChaining {
    private static class Entry {
        int key, value;
        Entry next;

        Entry(int key, int value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    private LinkedList<Entry>[] table;
    private int capacity;
    private int size;
    private static final int DEFAULT_CAPACITY = 16;

    public HashMapSeparateChaining() {
        this(DEFAULT_CAPACITY);
    }

    public HashMapSeparateChaining(int capacity) {
        this.capacity = capacity;
        this.table = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            table[i] = new LinkedList<>();
        }
        this.size = 0;
    }

    private int hash(int key) {
        return key % capacity;
    }

    public boolean find(int key) {
        int index = hash(key);
        for (Entry entry : table[index]) {
            if (entry.key == key) return true;
        }
        return false;
    }

    public void insert(int key, int value) {
        int index = hash(key);
        for (Entry entry : table[index]) {
            if (entry.key == key) {
                entry.value = value;
                return;
            }
        }
        table[index].add(new Entry(key, value));
        size++;
    }

    public void remove(int key) {
        int index = hash(key);
        LinkedList<Entry> entries = table[index];
        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i).key == key) {
                entries.remove(i);
                size--;
                return;
            }
        }
    }

    public static void main(String[] args) {
        HashMapSeparateChaining hashMap = new HashMapSeparateChaining();
        hashMap.insert(1, 100);
        hashMap.insert(2, 200);
        System.out.println("Find 1: " + hashMap.find(1)); // true
        hashMap.remove(1);
        System.out.println("Find 1 after removal: " + hashMap.find(1)); // false
    }
}