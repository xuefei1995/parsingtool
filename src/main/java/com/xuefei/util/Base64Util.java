package com.xuefei.util;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

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
}
