package com.mysite.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainStreams {
    public static void main(String[] args) {
        System.out.println(minValue(new int[]{2, 1, 3, 3, 2, 3}));
        System.out.println(minValue(new int[]{9, 8}));
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        System.out.println(oddOrEven(integers));

    }

    private static int minValue(int[] values) {
        int[] sortedArray = Arrays.stream(values).distinct().sorted().toArray();
        AtomicInteger res = new AtomicInteger();
        IntStream.range(0, sortedArray.length).forEach(index -> res.set(res.get() + (int) (Math.pow(10, sortedArray.length - 1 - index)) * sortedArray[index]));
        return res.get();
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        return integers.stream().filter(n -> {
            if (integers.stream().mapToInt(i -> i).sum() % 2 == 0) {
                return (n % 2 == 0);
            } else {
                return (n % 2 != 0);
            }
        }).collect(Collectors.toList());
    }
}
