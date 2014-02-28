package com.asyncj.core.api.article.onetrain;

/**
 *
 * @author Aliaksei Papou
 * @since 23.11.13
 */
public class RailWayTest {

    public static final int ITERATION = 50000000;
    public static final int CAPACITY = 2048;

    public static void main(String[] args) {

        for(int i=0;i<50;i++){
            RailWayTest.testRailWay();
        }
    }

    public static void testRailWay() {
        final Railway railway = new Railway();

        new Thread() {
            long lastValue = 0;

            @Override
            public void run() {
                long time = 0;
                while (lastValue < ITERATION) {
                    Train train = railway.waitTrainOnStation(1);
                    int count = train.goodsCount();

                    for (int i = 0; i < count; i++) {
                        lastValue = train.getGoods(i);
                    }

                    railway.sendTrain();
                }
            }
        }.start();

        final long start = System.nanoTime();
        long[] items = new long[2048];
        long i = 0;
        while (i < ITERATION) {
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

        }
        final long duration = System.nanoTime() - start;

        final long ops = (i * 1000L * 1000L * 1000L) / duration;

        System.out.format("ops/sec       = %,d\n", ops);
        System.out.format("trains/sec    = %,d\n", ops / Train.CAPACITY);
        System.out.format("latency nanos = %.3f%n\n", duration / (float) (i) * (float) Train.CAPACITY);
    }
}
