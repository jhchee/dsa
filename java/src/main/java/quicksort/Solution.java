package quicksort;

import java.util.Arrays;

public class Solution {
    public static void main(String[] args) {
        int[] input = new int[]{5, 4, 3, 2, 1};
        sort(input);
        System.out.println(Arrays.toString(input));
    }

    public static void sort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    public static void quickSort(int[] arr, int start, int end) {
        System.out.println(Arrays.toString(arr));

        if (start > end) return;
        int pivot = partition(arr, start, end);
        quickSort(arr, start, pivot - 1);
        quickSort(arr, pivot + 1, end);
    }

    public static int partition(int[] arr, int start, int end) {
        int pivot = arr[end];

        int left = start - 1;
        int right = start;

        while (right <= end) {
            if (arr[right] < pivot) {
                left++;
                swap(arr, left, right);
            }
            right++;
        }
        swap(arr, left + 1, end);
        return left + 1; // return left+1 as the pivot
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
