package com.example.crypto;

import com.example.crypto.util.Util;

/**
 * Created by krishna on 27/5/17.
 */
public class Exercise {
    public static void main(String[] args) {
        byte[] bytes = {126, 76, 94, 23};
        System.out.println(Util.byteToHex(bytes));
        System.out.println("-----------------------------------");

        String message = "demo";
        byte key = 'a';
        System.out.println(Util.byteToHex(Util.xor(message.getBytes(), key)));
        System.out.println("-----------------------------------");

        String key1 = "ab";
        System.out.println(Util.byteToHex(Util.xor(message.getBytes(), key1.getBytes())));
        System.out.println("-----------------------------------");

        String hexString = "3915141D081B0E0F161B0E131514095B5A23150F5D081F5A0E121F5A1D1F14130F09";
        byte[] data = Util.hexToByte(hexString);
        int maxFreq = 0;
        byte max = 0;
        for (byte i = 0; i < 127; i++) {
            byte[] xor = Util.xor(data, i);
            String xorData = new String(xor);
            int feq = Util.countFrequency(xorData);
            if (maxFreq < feq) {
                maxFreq = feq;
                max = i;
            }
        }

        System.out.println(max + " : " + maxFreq);
        System.out.println(new String(Util.xor(data, max)));
        System.out.println("-----------------------------------");

        System.out.println(Util.encodeBase64("And".getBytes()));
        System.out.println("-----------------------------------");
    }


}
