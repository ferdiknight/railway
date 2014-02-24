package com.asyncj.core.api.article.onetrain;

/**
 *
 * @author Aliaksei Papou
 * @since 23.11.13
 */
public class RailWayTest {
    public static void main(String[] args) {
        new RailWayTest().testRailWay();
    }

    public void testRailWay() {
        final Railway railway = new Railway();

        final long n = 2000000000l;

        new Thread() {
            long lastValue = 0;

            @Override
            public void run() {
                long time = 0;
                while (lastValue < n) {
                    Train train = railway.waitTrainOnStation(1);
                    int count = train.goodsCount();

                    long start = System.nanoTime();

                    for (int i = 0; i < count; i++) {
                        lastValue = train.getGoods(i);
                    }

                    time += System.nanoTime() - start;

                    railway.sendTrain();
                }
                System.out.println(time);
            }
        }.start();

        final long start = System.nanoTime();
        long[] items = new long[2048];
        long i = 0;
        while (i < n) {
            Train train = railway.waitTrainOnStation(0);
            int capacity = train.getCapacity();

            for (int j = 0; j < capacity; j++) {
                i += 1;
                items[j] = i; // 将货物装到列车上
            }

            for (int j = 0; j < capacity; j++) {
                train.addGoods(items[j]);
            }
            railway.sendTrain();

            if (i % 1000000 == 0) {
                final long duration = System.nanoTime() - start;

                final long ops = (i * 1000L * 1000L * 1000L) / duration;

                System.out.format("ops/sec       = %,d\n", ops);
                System.out.format("trains/sec    = %,d\n", ops / Train.CAPACITY);
                System.out.format("latency nanos = %.3f%n\n", duration / (float)(i) * (float) Train.CAPACITY);

            }
        }
    }
}
