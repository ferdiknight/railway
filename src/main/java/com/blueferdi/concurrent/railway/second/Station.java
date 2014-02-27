/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.blueferdi.concurrent.railway.second;

import java.util.concurrent.BlockingQueue;

/**
 *
 * @author tongyin.ty
 */
public class Station<E> {

    private final BlockingQueue<E> railway;

    public Station(BlockingQueue<E> railway){
        this.railway = railway;
    }

    public E exit(){
        return railway.poll();
    }

    public void enter(E e){
        railway.offer(e);
    }

}
