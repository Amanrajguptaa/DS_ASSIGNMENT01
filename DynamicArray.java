import java.util.Arrays;

public class DynamicArray {
    private int[] array;
    private int size;
    private int capacity;
    private double resizeFactor;

    public DynamicArray() {
        this.capacity = 10;
        this.array = new int[capacity];
        this.size = 0;
        this.resizeFactor = 2.0; // Default resizing factor
    }

    public DynamicArray(double resizeFactor) {
        this.capacity = 10;
        this.array = new int[capacity];
        this.size = 0;
        this.resizeFactor = resizeFactor;
    }

    // Inserts an element at the specified index
    public void insert(int index, int element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        if (size == capacity) {
            resize();
        }
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = element;
        size++;
    }

    // Deletes the element at the specified index
    public void delete(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        System.arraycopy(array, index + 1, array, index, size - index - 1);
        size--;
    }

    // Returns the size of the dynamic array
    public int size() {
        return size;
    }

    // Returns true if the dynamic array is empty, false otherwise
    public boolean isEmpty() {
        return size == 0;
    }

    // Rotates the dynamic array by k positions to the right
    public void rotate(int k) {
        k = k % size;
        reverse(0, size - 1);
        reverse(0, k - 1);
        reverse(k, size - 1);
    }

    // Reverses the dynamic array
    public void reverse() {
        reverse(0, size - 1);
    }

    private void reverse(int start, int end) {
        while (start < end) {
            int temp = array[start];
            array[start] = array[end];
            array[end] = temp;
            start++;
            end--;
        }
    }

    // Appends an element to the end of the dynamic array
    public void append(int element) {
        if (size == capacity) {
            resize();
        }
        array[size++] = element;
    }

    // Prepends an element to the beginning of the dynamic array
    public void prepend(int element) {
        insert(0, element);
    }

    // Merges two dynamic arrays into a single dynamic array
    public void merge(DynamicArray other) {
        while (size + other.size > capacity) {
            resize();
        }
        System.arraycopy(other.array, 0, array, size, other.size);
        size += other.size;
    }

    // Interleaves two dynamic arrays into a single dynamic array
    public static DynamicArray interleave(DynamicArray a, DynamicArray b) {
        DynamicArray result = new DynamicArray();
        int i = 0, j = 0;
        while (i < a.size || j < b.size) {
            if (i < a.size) {
                result.append(a.array[i++]);
            }
            if (j < b.size) {
                result.append(b.array[j++]);
            }
        }
        return result;
    }

    // Returns the middle element of the dynamic array
    public int middle() {
        if (size == 0) {
            throw new IllegalStateException("Array is empty");
        }
        return array[size / 2];
    }

    // Returns the index of the first occurrence of the specified element in the dynamic array, or -1 if the element is not found
    public int indexOf(int element) {
        for (int i = 0; i < size; i++) {
            if (array[i] == element) {
                return i;
            }
        }
        return -1;
    }

    // Splits the dynamic array into two dynamic arrays at the specified index
    public DynamicArray[] split(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        DynamicArray left = new DynamicArray();
        DynamicArray right = new DynamicArray();

        for (int i = 0; i < index; i++) {
            left.append(array[i]);
        }
        for (int i = index; i < size; i++) {
            right.append(array[i]);
        }
        return new DynamicArray[]{left, right};
    }

    // Resizes the array with the custom factor given by the user (other than 2)
    private void resize() {
        capacity = (int) (capacity * resizeFactor);
        array = Arrays.copyOf(array, capacity);
    }

    // Example usage
    public static void main(String[] args) {
        DynamicArray arr = new DynamicArray();

        arr.append(1);
        arr.append(2);
        arr.append(3);

        arr.insert(1, 5);

        System.out.println("Array size: " + arr.size()); 
        System.out.println("Is array empty? " + arr.isEmpty()); 
        arr.rotate(1);
        System.out.println("After rotating: " + Arrays.toString(arr.array));

        arr.reverse();
        System.out.println("After reversing: " + Arrays.toString(arr.array));

        arr.prepend(0);
        System.out.println("After prepending: " + Arrays.toString(arr.array)); 
        DynamicArray arr2 = new DynamicArray();
        arr2.append(6);
        arr2.append(7);

        arr.merge(arr2);
        System.out.println("After merging: " + Arrays.toString(arr.array));

        DynamicArray interleaved = DynamicArray.interleave(arr, arr2);
        System.out.println("Interleaved array: " + Arrays.toString(interleaved.array));

        System.out.println("Middle element: " + arr.middle()); 

        System.out.println("Index of element 5: " + arr.indexOf(5)); 

        DynamicArray[] splitArrays = arr.split(3);
        System.out.println("Left split array: " + Arrays.toString(splitArrays[0].array));
        System.out.println("Right split array: " + Arrays.toString(splitArrays[1].array));
    }
}