package com.asyncj.core.api.article.onetrain;

/**
 * @author Aliaksei Papou
 * @since 23.11.13
 */
public class Train {

    public static int CAPACITY = 2048;

    private final long[] goodsArray = new long[CAPACITY];

    private int index;

    public int goodsCount() {
        return index;
    }

    public void addGoods(long i) {
        goodsArray[index++] = i;
    }

    public void addGoods(long[] items){
        System.arraycopy(items, 0, goodsArray, 0,items.length);
        index += items.length;
    }

    public long getGoods(int i) {
        index--;
        return goodsArray[i];
    }

    public void getGoods(long[] items){
        System.arraycopy(goodsArray, 0, items, 0, index);
        index = 0;
    }

    public int getCapacity() {
        return CAPACITY;
    }


}
