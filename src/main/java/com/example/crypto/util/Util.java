package com.example.crypto.util;

import java.util.HashMap;

/**
 * Created by krishna on 27/5/17.
 */
public final class Util {
    public static final int UNSIGNED_FLAG = 0xFF;

    private Util() {
    }

    public static byte rightShift(byte data, byte shiftBy) {
        return (byte) ((data & UNSIGNED_FLAG) >> shiftBy);
    }

    public static byte leftShift(byte data, byte shiftBy) {
        return (byte) (data << shiftBy);
    }

    public static char getHexCharFromByte(byte b) {
        switch (b) {
            case 0:
                return '0';
            case 1:
                return '1';
            case 2:
                return '2';
            case 3:
                return '3';
            case 4:
                return '4';
            case 5:
                return '5';
            case 6:
                return '6';
            case 7:
                return '7';
            case 8:
                return '8';
            case 9:
                return '9';
            case 10:
                return 'A';
            case 11:
                return 'B';
            case 12:
                return 'C';
            case 13:
                return 'D';
            case 14:
                return 'E';
            case 15:
                return 'F';
            default:
                throw new IllegalArgumentException("Invalid data: " + b);
        }
    }

    public static byte getByteFromHexChar(char c) {
        switch (c) {
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            case 'A':
                return 10;
            case 'B':
                return 11;
            case 'C':
                return 12;
            case 'D':
                return 13;
            case 'E':
                return 14;
            case 'F':
                return 15;
            default:
                throw new IllegalArgumentException("Invalid data: " + c);
        }
    }

    public static String byteToHex(byte... bytes) {
        byte shiftBy = 4;
        StringBuilder hexBuilder = new StringBuilder();
        for (byte b : bytes) {
            byte leftData = Util.rightShift(b, shiftBy);
            byte rightData = Util.leftShift(b, shiftBy);
            rightData = Util.rightShift(rightData, shiftBy);
            hexBuilder.append(Util.getHexCharFromByte(leftData));
            hexBuilder.append(Util.getHexCharFromByte(rightData));
        }
        return hexBuilder.toString();
    }

    public static byte[] xor(byte[] message, byte key) {
        byte[] data = new byte[message.length];
        int count = 0;
        for (byte b : message) {
            byte xorData = (byte) (b ^ key);
            data[count++] = xorData;
        }
        return data;
    }

    public static byte[] xor(byte[] message, byte[] key) {
        byte[] data = new byte[message.length];
        int keySize = key.length;
        int count = 0;
        for (byte b : message) {
            byte k = key[count % keySize];
            data[count++] = (byte) (b ^ k);
        }
        return data;
    }

    public static byte[] hexToByte(String hexString) {
        byte[] data = new byte[hexString.length() / 2];
        char[] hexCharArr = hexString.toCharArray();
        byte shiftBy = 4;
        for (int idx = 0, pos = 0; idx < hexString.length(); idx += 2, pos++) {
            byte left = leftShift(getByteFromHexChar(hexCharArr[idx]), shiftBy);
            byte right = getByteFromHexChar(hexCharArr[idx + 1]);
            data[pos] = (byte) (left | right);
        }

        return data;
    }

    public static int countFrequency(String message) {
        String feq = "ETAOIN SHRDLU";
        char[] msg = message.toUpperCase().toCharArray();
        int feqCount = 0;
        for (int i = 0; i < message.length(); i++) {
            if (feq.contains("" + msg[i])) {
                feqCount++;
            }
        }
        return feqCount;
    }


    private static final HashMap<Byte, Character> base64 = new HashMap<>();
    private static final HashMap<Character, Byte> revBase64 = new HashMap<>();

    private static void initBase64() {
        byte idx = 0;
        for (char c = 'A'; c <= 'Z'; c++) {
            base64.put(idx, c);
            revBase64.put(c, idx);
            idx++;
        }
        for (char c = 'a'; c <= 'z'; c++) {
            base64.put(idx, c);
            revBase64.put(c, idx);
        }
        for (char c = '0'; c <= '9'; c++) {
            base64.put(idx, c);
            revBase64.put(c, idx);
        }
        base64.put((byte) 62, '+');
        base64.put((byte) 63, '/');
        revBase64.put('+', (byte) 62);
        revBase64.put('/', (byte) 63);
    }

    public static String encodeBase64(byte[] bytes) {
        initBase64();
        StringBuilder builder = new StringBuilder();
        for (int idx = 0, count = 0; idx < bytes.length; idx += 3, count++) {
            byte d1 = bytes[idx];
            byte d2 = bytes[idx + 1];
            byte d3 = bytes[idx + 2];

            byte s1 = rightShift(d1, (byte) 2);
            byte s2 = (byte) ((leftShift(d1, (byte) 4) & 0x3F ) | rightShift(d2, (byte) 4));
            byte s3 = (byte) (leftShift(d2, (byte) 4) | rightShift(d3, (byte) 4));
            byte s4 = (byte) (leftShift(rightShift(d3, (byte) 6), (byte) 6) ^ d3);
//            System.out.println(base64.get(s1) + base64.get(s2)/*+ base64.get(s3) + base64.get(s4)*/);

            builder.append(base64.get(s1)).append(s2);
        }
        return builder.toString();
    }

}
