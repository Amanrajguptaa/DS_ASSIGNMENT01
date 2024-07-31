package assignment03;
class HashMapOpenAddressing {
    private static class Entry {
        int key, value;
        boolean isDeleted;

        Entry(int key, int value) {
            this.key = key;
            this.value = value;
            this.isDeleted = false;
        }
    }

    private Entry[] table;
    private int capacity;
    private int size;
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    public HashMapOpenAddressing() {
        this(DEFAULT_CAPACITY);
    }

    public HashMapOpenAddressing(int capacity) {
        this.capacity = capacity;
        this.table = new Entry[capacity];
        this.size = 0;
    }

    private int hash(int key) {
        return key % capacity;
    }

    public boolean find(int key) {
        int index = hash(key);
        for (int i = 0; i < capacity; i++) {
            int currentIndex = (index + i) % capacity;
            if (table[currentIndex] == null) return false;
            if (table[currentIndex].key == key && !table[currentIndex].isDeleted) return true;
        }
        return false;
    }

    public void insert(int key, int value) {
        if (size >= capacity * LOAD_FACTOR) resize();
        int index = hash(key);
        for (int i = 0; i < capacity; i++) {
            int currentIndex = (index + i) % capacity;
            if (table[currentIndex] == null || table[currentIndex].isDeleted) {
                table[currentIndex] = new Entry(key, value);
                size++;
                return;
            }
            if (table[currentIndex].key == key) {
                table[currentIndex].value = value;
                table[currentIndex].isDeleted = false;
                return;
            }
        }
    }

    public void remove(int key) {
        int index = hash(key);
        for (int i = 0; i < capacity; i++) {
            int currentIndex = (index + i) % capacity;
            if (table[currentIndex] == null) return;
            if (table[currentIndex].key == key && !table[currentIndex].isDeleted) {
                table[currentIndex].isDeleted = true;
                size--;
                return;
            }
        }
    }

    private void resize() {
        Entry[] oldTable = table;
        capacity *= 2;
        table = new Entry[capacity];
        size = 0;
        for (Entry entry : oldTable) {
            if (entry != null && !entry.isDeleted) {
                insert(entry.key, entry.value);
            }
        }
    }
}