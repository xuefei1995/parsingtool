package com.xuefei.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static List<String> getFiles(String parentPath) {
        List<String> paths = new ArrayList<>();
        File file = new File(parentPath);
        File[] files = file.listFiles();
        for (File childFile : files) {
            paths.add(childFile.getAbsolutePath());
        }
        return paths;
    }


    public static void savePhoto(String outPath, String idcardnum, String scenephoto, String roomnum) {
        InputStream inputStream = Base64Util.baseToInputStream(scenephoto);
        FileOutputStream fileOutputStream = FileUtil.getOutputStream(outPath + "/验证照片/" + roomnum + "/" + idcardnum + ".jpg");
        int len = -1;
        byte[] buffer = new byte[1024];
        try {
            while ((len = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeInputStream(inputStream);
        closeOutputStream(fileOutputStream);
    }

    public static FileInputStream getFileInputStream(String path) {
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fs;
    }

    public static FileOutputStream getOutputStream(String path) {
        File file = new File(path);
        createParentPath(file);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileOutputStream;
    }

    public static void closeInputStream(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeOutputStream(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //创建文件夹
    private static void createParentPath(File file) {
        File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            parentFile.mkdirs();
            createParentPath(parentFile);
        }

    }
}
