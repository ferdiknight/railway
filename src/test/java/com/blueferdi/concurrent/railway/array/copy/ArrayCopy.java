/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.blueferdi.concurrent.railway.array.copy;

/**
 *
 * @author tongyin.ty
 */
public class ArrayCopy {

    public static void testSystemArrayCopy(long[] src,long[] dest,int length)
    {
        System.arraycopy(src, 0, dest, 0, length);
    }

    public static void testLoopCopy(long[] src,long[] dest,int length)
    {
        for(int i=0;i<length;i++)
        {
            dest[i] = src[i];
        }
    }

    public static void main(String[] args)
    {
        long[] src = new long[2048];
        long[] dest = new long[2048];

        long k=0;

        for(int i=0;i<2048;i++)
        {
            k += 1;
            src[i] = k;
        }

        for(int i=0;i<10000000;i++)
        {
            System.arraycopy(src, 0, dest, 0, 2048);
        }



        long start = System.nanoTime();

        for(int i=0;i<10000000;i++)
        {
            testSystemArrayCopy(src,dest,2048);

        }

        System.out.println(System.nanoTime() - start);

        start = System.nanoTime();

         for(int i=0;i<10000000;i++)
        {
            testLoopCopy(src,dest,2048);

        }

        System.out.println(System.nanoTime() - start);

    }


}
