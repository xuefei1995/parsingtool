package com.xuefei.util;

import org.apache.commons.codec.binary.Base64;

import java.io.*;

public class Base64Util {
    //base64转换流
    public static InputStream baseToInputStream(String base64string) {
        byte[] b = Base64.decodeBase64(base64string);
        // Base64解码
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {// 调整异常数据
                b[i] += 256;
            }
        }
        return new ByteArrayInputStream(b);
    }

    public static String inputStreamToBase64(InputStream inputStream) {
        try {
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = inputStream.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            byte[] data = swapStream.toByteArray();
            return new String(Base64.encodeBase64(data));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
