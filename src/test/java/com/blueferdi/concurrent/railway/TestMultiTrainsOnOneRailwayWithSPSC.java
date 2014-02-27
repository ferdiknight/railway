package com.blueferdi.concurrent.railway;

import com.blueferdi.concurrent.railway.train.Train;
import java.math.BigInteger;

/**
 *  the consumer station is always empty when it is waiting for train
 *  the producer station is always full when the train comming
 * @author tongyin.ty
 */
public class TestMultiTrainsOnOneRailwayWithSPSC {

    public static void main(String[] args){
        testRailway();
    }

    public static void testRailway()
    {
        final int trainNumbers = 8;
        final int capacity = 2 * 1024;
        final Railway r1 = new Railway(trainNumbers);

        for(int i=0;i<trainNumbers;i++){
            r1.registerTrain(new Train(capacity));
        }

        new Thread(){

            final long[] station = new long[capacity];

            @Override
            public void run(){
                int trainIndex = 0;
                while(true){
                    final int trainNo = trainIndex % trainNumbers;
                    Train train = r1.waitForIn(1,trainNo);
                    train.takeOff(1, station);
                    r1.send(trainNo);
                    trainIndex++;
                }
            }

        }.start();

        long[] station = new long[capacity];

        BigInteger count = BigInteger.ZERO;

        for(int i=0;i<capacity;i++){
            station[i] = i;
        }

        long start = System.nanoTime();
        int trainIndex = 0;
        while(true){
            final int trainNo = trainIndex % trainNumbers;
            Train train = r1.waitForIn(0,trainNo);
            train.put(station);
            r1.send(trainNo);
            trainIndex++;
            count = count.add(BigInteger.valueOf(capacity));

            if(count.remainder(BigInteger.valueOf(100000)).compareTo(BigInteger.ZERO) == 0){
                final long duration = System.nanoTime() - start;
                final long ops = count.multiply(BigInteger.valueOf(1000L * 1000L * 1000L)).divide(BigInteger.valueOf(duration)).longValue();
                System.out.format("ops/sec = %,d\n", ops);
                System.out.format("trains/sec = %,d\n", ops / capacity);
                System.out.format("latency nanos = %.3f%n\n",
                    duration / count.floatValue() * capacity);
            }

        }


    }

}
