/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blueferdi.concurrent.railway.second;

import com.blueferdi.concurrent.railway.first.Man;
import static com.blueferdi.concurrent.railway.first.RailWayTest.CAPACITY;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author tongyin.ty
 */
public class StationTest {

    public static final int ITERATION = 50000000;

    public static void main(String[] args){

        for(int i=0;i<20;i++){
            StationTest.test();
        }
    }


    public static void test() {

        BlockingQueue<Man> queue = new LinkedBlockingQueue<Man>();

        final Station<Man> in = new Station<Man>(queue);
        final Station<Man> out = new Station<Man>(queue);

        new Thread() {
            @Override
            public void run() {
                int i = 0;
                while (i < ITERATION) {
                    in.enter(new Man(i));
                    i++;
                }
            }

        }.start();

        long start = System.nanoTime();

        int i = 0;

        while (i < ITERATION) {
            Man m;
            while ((m = out.exit()) != null) {
                i++;
            }
        }

        final long duration = System.nanoTime() - start;

        final long ops = (ITERATION * 1000L * 1000L * 1000L) / duration;

        System.out.format("ops/sec       = %,d\n", ops);
        System.out.format("latency nanos = %.3f%n\n", duration / (float) (i) * (float) CAPACITY);

    }

}
