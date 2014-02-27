/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blueferdi.concurrent.railway.first;

/**
 *
 * @author tongyin.ty
 */
public class RailWayTest {

    public static final int ITERATION = 50000000;
    public static final int CAPACITY = 2048;

    public static void main(String[] args) {
        RailWayTest.test();
    }

    public static void test() {

        final Railway r1 = new Railway();
        final Station in = new Station(0);
        final Station out = new Station(1);

        new Thread() {

            @Override
            public void run() {

                int i = 0;

                while (i < ITERATION) {
                    Train t = r1.waitForTrain(in.getStationNo());

                    int count = 0;
                    while (count < CAPACITY) {
                        Man m = new Man(i);
                        t.getOn(m);
                        count++;
                        i++;
                    }

                    Man m_t;

                    while ((m_t = in.prepareGetOn()) != null) {
                        t.getOn(m_t);

                    }

                    r1.send();
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

            r1.send();

        }

        final long duration = System.nanoTime() - start;

        final long ops = (ITERATION * 1000L * 1000L * 1000L) / duration;

        System.out.format("ops/sec       = %,d\n", ops);
        System.out.format("trains/sec    = %,d\n", ops / CAPACITY);
        System.out.format("latency nanos = %.3f%n\n", duration / (float) (i) * (float) CAPACITY);

    }

}
