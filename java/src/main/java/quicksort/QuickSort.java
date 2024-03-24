package quicksort;

import java.util.Arrays;

public class QuickSort {
    public static void main(String[] args) {
        int[] input = new int[]{5, 4, 3, 2, 1};
        QuickSort algo = new QuickSort();
        algo.sort(input);
        System.out.println(Arrays.toString(input)); // Show that the input is sorted.
    }

    // Entrypoint of search.
    public void sort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    public void quickSort(int[] arr, int start, int end) {
        if (start >= end) return;

        // At each stage of quick sort, print out the sequence of array.
        System.out.println(Arrays.toString(arr));
        System.out.println("Start: " + start + ", End: " + end);
        int pivot = partition(arr, start, end);
        // Recursively performing quick sort between first and second partition.
        quickSort(arr, start, pivot - 1);
        quickSort(arr, pivot + 1, end);
    }

    public int partition(int[] arr, int start, int end) {
        int pivot = arr[end];

        int left = start - 1; // Begin at start - 1.
        int right = start;

        while (right <= end) {
            if (arr[right] < pivot) {
                left++;
                swap(arr, left, right);
            }
            right++;
        }
        // Swap the pivot to left and become the pivot position.
        left++;
        swap(arr, left, end);
        return left; // return left+1 as the pivot
    }

    public void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
