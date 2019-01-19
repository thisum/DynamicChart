package org;

import java.util.Random;

public class Test
{
    public static void main(String[] args)
    {
        Random r = new Random();
        int low = 2085000;
        int high = 2095000;
        int i = 20;

        while(i > 0)
        {
            i--;

            System.out.print(r.nextInt(high-low) + low);
            System.out.println(",");
        }
    }
}
