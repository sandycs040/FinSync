//package com.example.FinSync.exception;
//
//    import java.util.*;
//import java.util.concurrent.ConcurrentSkipListSet;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//    public class demo {
//
//        public static void main(String[] args) {
//            List<String> largeList = IntStream.range(0, 1000000)
//                    .mapToObj(Integer::toString)
//                    .collect(Collectors.toList());
//
//            String target = "500000";
//
//            long startTime, endTime;
//
//            // Linear Search
//            startTime = System.nanoTime();
//            boolean found = linearSearch(largeList, target);
//            endTime = System.nanoTime();
//            System.out.println("Linear Search: " + found + ", Time: " + (endTime - startTime) + " ns");
//
//            // Binary Search
//            Collections.sort(largeList);
//            startTime = System.nanoTime();
//            found = binarySearch(largeList, target);
//            endTime = System.nanoTime();
//            System.out.println("Binary Search: " + found + ", Time: " + (endTime - startTime) + " ns");
//
//            // HashSet Search
//            startTime = System.nanoTime();
//            found = hashSetSearch(largeList, target);
//            endTime = System.nanoTime();
//            System.out.println("HashSet Search: " + found + ", Time: " + (endTime - startTime) + " ns");
//
//            // list Search
//            startTime = System.nanoTime();
//            found = listSearch(largeList, target);
//            endTime = System.nanoTime();
//            System.out.println("List Search: " + found + ", Time: " + (endTime - startTime) + " ns");
//
//            // ConcurrentHashSet Search
//            startTime = System.nanoTime();
//            found = concurrentHashSetSearch(largeList, target);
//            endTime = System.nanoTime();
//            System.out.println("ConcurrentHashSet Search: " + found + ", Time: " + (endTime - startTime) + " ns");
//
//            // Stream API Search
//            startTime = System.nanoTime();
//            found = streamSearch(largeList, target);
//            endTime = System.nanoTime();
//            System.out.println("Stream API Search: " + found + ", Time: " + (endTime - startTime) + " ns");
//        }
//
//        public static boolean linearSearch(List<String> list, String target) {
//            for (String s : list) {
//                if (s.equals(target)) {
//                    return true;
//                }
//            }
//            return false;
//        }
//
//        public static boolean binarySearch(List<String> list, String target) {
//            int index = Collections.binarySearch(list, target);
//            return index >= 0;
//        }
//
//        public static boolean hashSetSearch(List<String> list, String target) {
//            Set<String> set = new HashSet<>(list);
//            return set.contains(target);
//        }
//
//        public static boolean listSearch(List<String> list, String target) {
//            //Set<String> set = new HashSet<>(list);
//            return list.contains(target);
//        }
//
//        public static boolean concurrentHashSetSearch(List<String> list, String target) {
//            Set<String> set = new ConcurrentSkipListSet<>(list);
//            return set.contains(target);
//        }
//
//        public static boolean streamSearch(List<String> list, String target) {
//            return list.stream().anyMatch(s -> s.equals(target));
//        }
//    }
//
