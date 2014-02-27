/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blueferdi.concurrent.railway.first;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tongyin.ty
 */
public class RunAuto {

    public static void main(String[] args) {

        final Railway r1 = new Railway();
        final Station in = new Station(0);
        final Station out = new Station(1);

        new Thread() {
            final byte[] buffer = new byte[1024];

            @Override
            public void run() {

                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        Train t = r1.waitForTrain(in.getStationNo());

                        int flag = System.in.read(buffer);

                        if (flag > 0) {
                            Man m = new Man(new String(buffer));
                            in.enter(m);
                            System.out.println("some one enter : " + in.getStationNo() + " : " + m);
                        }

                        Man m;

                        while ((m = in.prepareGetOn()) != null) {
                            t.getOn(m);
                            System.out.println("some one get on the train : " + m);
                        }

                        r1.send();
                        System.out.println("train go 1");
                    } catch (IOException ex) {
                        Logger.getLogger(RunAuto.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }

        }.start();

        new Thread() {

            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    Train t = r1.waitForTrain(out.getStationNo());

                    Man m;

                    while ((m = t.takeOff()) != null) {
                        out.takeOff(m);
                        System.out.println("some one take off the train : " + m);
                    }

                    while ((m = out.exit()) != null) {
                        System.out.println("some one leave station : " + out.getStationNo() + " : " + m);
                    }

                    r1.send();
                    System.out.println("train go 0");
                }
            }

        }.start();

    }

}
