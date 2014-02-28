/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blueferdi.concurrent.railway.third;

import com.blueferdi.concurrent.railway.first.Man;
import com.blueferdi.concurrent.railway.first.Station;
import com.blueferdi.concurrent.railway.first.Train;

/**
 *
 * @author tongyin.ty
 */
public class MultiTrainRailwayTest {

    public static final int ITERATION = 50000000;
    public static final int CAPACITY = 2 * 1024;

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            MultiTrainRailwayTest.test();
        }
    }

    public static void test() {
        int trainNos = 16;
        final MultiTrainRailway r1 = new MultiTrainRailway(trainNos);
        final Station in = new Station(0);
        final Station out = new Station(1);

        for(int i=0;i<trainNos;i++){
            Train t = new Train();
            r1.register(t);
        }


        new Thread() {

            @Override
            public void run() {

                int i = 0;

                while (i < ITERATION) {
                    Train t = r1.waitForTrain(in.getStationNo());

                    int count = 0;
                    while (count < CAPACITY) {
                        Man m = new Man(i);
                        in.enter(m);
                        count++;
                    }

                    Man m_t;

                    while ((m_t = in.prepareGetOn()) != null) {
                        t.getOn(m_t);
                        i++;
                    }

                    r1.send(t.getNo());
                }

            }

        }.start();

        long start = System.nanoTime();

        int i = 0;

        while (i < ITERATION) {

            Train t = r1.waitForTrain(out.getStationNo());

            Man m;

            while ((m = t.takeOff()) != null) {
                out.takeOff(m);
                i++;
            }

            while ((m = out.exit()) != null) {

            }

            r1.send(t.getNo());

        }

        final long duration = System.nanoTime() - start;

        final long ops = (ITERATION * 1000L * 1000L * 1000L) / duration;

        System.out.format("ops/sec       = %,d\n", ops);
        System.out.format("trains/sec    = %,d\n", ops / CAPACITY);
        System.out.format("latency nanos = %.3f%n\n", duration / (float) (i) * (float) CAPACITY);
    }
}
