package cn.itlzq.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/3/7 15:36
 * @email 邮箱:905866484@qq.com
 * @description 描述：
 */
public class MD5 {

    public static String getPassword(String password){
        return DigestUtils.md5Hex(password);
    }

    public static void main(String[] args) {
        System.out.println(getPassword("2017100147"));
    }
}
