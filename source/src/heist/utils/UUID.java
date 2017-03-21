package heist.utils;

import java.util.Random;

public class UUID
{
    private final static Random random = new Random();
    
    public static String generate()
    {
        String[] chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".split("");
        String id = "";

        int value = 0, temp;

        for(int i = 0; i < 36; i++)
        {
                if(i == 8 || i == 13 || i == 18 || i == 23)
                {
                    id += '-';
                }
                else
                {
                    if(value <= 0x02)
                    {
                        value = (int)(random.nextDouble() * 0x1000000 + 0x2000000);
                    }

                    temp = value & 0xf;
                    value = value >> 4;
                    id += chars[(i == 19) ? (temp & 0x3) | 0x8 : temp];
                }
        }

        return id;
    }
}
