/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.blueferdi.concurrent.railway.train;

/**
 *
 * @author tongyin.ty
 */
public class Train {

    private final int CAPACITY;

    private final long[] items;

    private int index = 0;

    public Train(int capacity){
        this.CAPACITY = capacity;
        items = new long[CAPACITY];
    }

    public int put(long[] items,int length){
//        int remain = CAPACITY - index;
//        int no = remain >= length?length:remain;
        System.arraycopy(items,0, this.items,index, length);
        index += length;
        return length;
    }

    public int capacity(){
        return this.CAPACITY;
    }

    public int takeOffNo(int station){
        return index;
    }

    public int takeOff(int station,long[] items){
        System.arraycopy(this.items, 0, items, 0, index);
        index -= items.length;
        return index;
    }

}
