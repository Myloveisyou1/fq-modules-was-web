package com.fq.modules.was.web.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @Author: 刘英杰
 * @Description: MD5工具类
 * @Date: Created in 2017/12/27 22:43
 */
public class MD5Util {

    public static String getMD5(String pw) {
        try {

            // 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            // 输入的字符串转换成字节数组
            byte[] inputByteArray = pw.getBytes();
            // inputByteArray是输入字符串转换得到的字节数组
            messageDigest.update(inputByteArray);
            // 转换并返回结果，也是字节数组，包含16个元素
            byte[] resultByteArray = messageDigest.digest();
            // 字符数组转换成字符串返回
            return byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String byteArrayToHex(byte[] byteArray) {

        // 首先初始化一个字符数组，用来存放每个16进制字符
        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        char[] resultCharArray = new char[byteArray.length * 2];
        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        // 字符数组组合成字符串返回
        return new String(resultCharArray);
    }

    public static String getMD5String(String a, String b, String c){

        String t =a+b+c;

        return getMD5(t);
    }

    public static String genRandomNum(int pwd_len){
        //35是因为数组是从0开始的，26个字母+10个数字
        final int  maxNum = 36;
        int i;  //生成的随机数
        int count = 0; //生成的密码的长度
        char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while(count < pwd_len){
            //生成随机数，取绝对值，防止生成负数，
            //前4位是字母，后面的全是数字
            if(count < 4){
                i = Math.abs(r.nextInt(25));  //生成的数最大为36-1
            }else{
                i = Math.abs(r.nextInt(10))+26;  //生成的数最大为36-1
            }

            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count ++;
            }
        }
        return pwd.toString();
    }


    public static void main(String[] args) {

        String str =getMD5("adminE10ADC3949BA59ABBE56E057F20F883E");
        System.out.println(genRandomNum(8));
//		String str2 =getMD5("changeme");
		System.out.println(str);

    }
}
