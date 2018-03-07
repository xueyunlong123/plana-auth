package com.scaff.utils;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
/**
 * Created by xyl on 12/26/17.
 */
public class NUUID {

    private final static String STR_BASE = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final static char[] digits = STR_BASE.toCharArray();
    private final static Map<Character, Integer> digitMap = new HashMap<Character, Integer>();
    static {
        for (int i = 0; i < digits.length; i++) {
            digitMap.put(digits[i], (int) i);
        }
    }
    private static final int MIN_RADIX = 2;// 支持的最小进制数
    private static final int MAX_RADIX = digits.length;// 支持的最大进制数

    public static void main(String[] args) {
        System.out.println(NUUID.randomUUIDBase64());
    }

    /**
     * 获取36位UUID(原生UUID)
     *
     * @return
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取32位UUID
     *
     * @return
     */
    public static String randomUUID32() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取19位的UUID
     *
     * @return
     */
    public static String randomUUID19() {
        // 产生UUID
        UUID uuid = UUID.randomUUID();
        StringBuffer sb = new StringBuffer();
        // 分区转换
        sb.append(digits(uuid.getMostSignificantBits() >> 32, 8));
        sb.append(digits(uuid.getMostSignificantBits() >> 16, 4));
        sb.append(digits(uuid.getMostSignificantBits(), 4));
        sb.append(digits(uuid.getLeastSignificantBits() >> 48, 4));
        sb.append(digits(uuid.getLeastSignificantBits(), 12));
        return sb.toString();
    }

    /**
     * 获取15位的UUID（精度有所损失）
     *
     * @return
     */
    public static String randomUUID15() {
        return UUIDMaker.generate();
    }

    /**
     * 获取15位的Long型UUID（精度有所损失）
     *
     * @return
     */
    public static long randomUUID15Long() {
        return toNumber(randomUUID15(), 10);
    }

    public static String randomUUIDBase64() {
        UUID uuid = UUID.randomUUID();
        byte[] byUuid = new byte[16];
        long least = uuid.getLeastSignificantBits();
        long most = uuid.getMostSignificantBits();
        long2bytes(most, byUuid, 0);
        long2bytes(least, byUuid, 8);

        return Base64.getEncoder().encodeToString(byUuid);
    }

    private static void long2bytes(long value, byte[] bytes, int offset) {
        for (int i = 7; i > -1; i--) {
            bytes[offset++] = (byte) ((value >> 8 * i) & 0xFF);
        }
    }

    /**
     * 将字符串转换为长整型数字
     *
     * @param s 数字字符串
     * @param radix 进制数
     * @return
     */
    public static long toNumber(String s, int radix) {
        if (s == null) {
            throw new NumberFormatException("null");
        }
        if (radix < MIN_RADIX) {
            throw new NumberFormatException("radix " + radix + " less than Numbers.MIN_RADIX");
        }
        if (radix > MAX_RADIX) {
            throw new NumberFormatException("radix " + radix + " greater than Numbers.MAX_RADIX");
        }

        boolean negative = false;
        Integer digit, i = 0, len = s.length();
        long result = 0, limit = -Long.MAX_VALUE, multmin;
        if (len <= 0) {
            throw forInputString(s);
        }

        char firstChar = s.charAt(0);
        if (firstChar < '0') {
            if (firstChar == '-') {
                negative = true;
                limit = Long.MIN_VALUE;
            } else if (firstChar != '+'){
                throw forInputString(s);
            }
            if (len == 1) {
                throw forInputString(s);
            }
            i++;
        }
        multmin = limit / radix;
        while (i < len) {
            digit = digitMap.get(s.charAt(i++));
            if (digit == null || digit < 0 || result < multmin) {
                throw forInputString(s);
            }
            result *= radix;
            if (result < limit + digit) {
                throw forInputString(s);
            }
            result -= digit;
        }

        return negative ? result : -result;
    }

    /**
     * 将长整型数值转换为指定的进制数（最大支持62进制，字母数字已经用尽）
     *
     * @param i
     * @param radix
     * @return
     */
    private static String toString(long i, int radix) {
        if (radix < MIN_RADIX || radix > MAX_RADIX) {
            radix = 10;
        }
        if (radix == 10) {
            return Long.toString(i);
        }

        final int size = 65;
        int charPos = 64;
        char[] buf = new char[size];
        boolean negative = (i < 0);
        if (!negative) {
            i = -i;
        }
        while (i <= -radix) {
            buf[charPos--] = digits[(int) (-(i % radix))];
            i = i / radix;
        }
        buf[charPos] = digits[(int) (-i)];
        if (negative) {
            buf[--charPos] = '-';
        }

        return new String(buf, charPos, (size - charPos));
    }

    private static String digits(long val, int digits) {
        long hi = 1L << (digits * 4);
        return toString(hi | (val & (hi - 1)), MAX_RADIX).substring(1);
    }

    private static NumberFormatException forInputString(String s) {
        return new NumberFormatException("For input string: \"" + s + "\"");
    }

    private static class UUIDMaker {

        private final static String str = "0123456789abcdefghijklmnopqrstuvwxyz";
        private final static int pixLen = str.length();
        private static volatile int pixOne = 0;
        private static volatile int pixTwo = 0;
        private static volatile int pixThree = 0;
        private static volatile int pixFour = 0;

        /**
         * 生成短时间内不会重复的长度为15位的字符串，主要用于模块数据库主键生成使用。<br/>
         * 生成策略为获取自1970年1月1日零时零分零秒至当前时间的毫秒数的16进制字符串值，该字符串值为11位<br/>
         * 并追加四位"0-z"的自增字符串.<br/>
         * 如果系统时间设置为大于<b>2304-6-27 7:00:26<b/>的时间，将会报错！<br/>
         * 由于系统返回的毫秒数与操作系统关系很大，所以本方法并不准确。本方法可以保证在系统返回的一个毫秒数内生成36的4次方个（1679616）
         * ID不重复。<br/>
         */
        private synchronized final static String generate() {
            String hexString = Long.toHexString(System.currentTimeMillis());
            pixFour++;
            if (pixFour == pixLen) {
                pixFour = 0;
                pixThree++;
                if (pixThree == pixLen) {
                    pixThree = 0;
                    pixTwo++;
                    if (pixTwo == pixLen) {
                        pixTwo = 0;
                        pixOne++;
                        if (pixOne == pixLen) {
                            pixOne = 0;
                        }
                    }
                }
            }

            return hexString + str.charAt(pixOne) + str.charAt(pixTwo) + str.charAt(pixThree) + str.charAt(pixFour);
        }
    }

}
