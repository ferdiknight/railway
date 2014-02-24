package com.blueferdi.concurrent.railway;

import com.blueferdi.concurrent.railway.train.Train;

/**
 * Unit test for simple App.
 */
public class RailWayTest {

    public static void main(String[] args) {
        RailWayTest.testRailWay();
    }

    public static void testRailWay() {
        final Railway railway = new Railway();
        Train first = new Train(2048);
        railway.setTrain(first);
        final long n = 2000000000l;
        //启动一个消费者进程
        new Thread() {
            long lastValue = 0;

            @Override
            public void run() {
                long[] items = new long[2048];
                while (lastValue < n) {
                    Train train = railway.waitForIn(1); //在#1站等列车
                    int count = train.takeOffNo(1);

                    train.takeOff(1, items);
                    lastValue += count;
                    railway.send(); //将当前列车送到第一站
                }
            }
        }.start();

        final long start = System.nanoTime();
        long[] items = new long[2048];
        long i = 0;
        while (i < n) {
            Train train = railway.waitForIn(0); // 在#0站等列车

            //long startOn = System.nanoTime();

            int capacity = train.capacity();

            for (int j = 0; j < capacity; j++) {
                i += 1;
                items[j] = i; // 将货物装到列车上
            }
            train.put(items, capacity);

            //System.out.println(System.nanoTime() - startOn);

            railway.send();

            if (i % 1000000 == 0) { //每隔100M个条目测量一次性能
                final long duration = System.nanoTime() - start;
                final long ops = (i * 1000L * 1000L * 1000L) / duration;
                System.out.format("ops/sec = %,d\n", ops);
                System.out.format("trains/sec = %,d\n", ops / capacity);
                System.out.format("latency nanos = %.3f%n\n",
                    duration / (float) (i) * (float) capacity);
            }
        }
    }

}
