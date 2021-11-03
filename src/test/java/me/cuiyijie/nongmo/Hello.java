package me.cuiyijie.nongmo;

import java.io.*;

public class Hello {
    public static void main(String[] args) {

        File file = new File("D:\\BaiduYunDownload\\婚纱照\\精修照\\自找-24");
        File[] childFiles = file.listFiles();
        for (int index = 0; index < childFiles.length; index++) {
            File originFile = childFiles[index];
            copy(originFile, new File("D:\\BaiduYunDownload\\婚纱照\\精修照\\123\\" + (51 + index) + ".JPG"), 2048);
        }
    }

    public static void copy(File source, File dest, int bufferSize) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(source);
            out = new FileOutputStream(dest);
            byte[] buffer = new byte[bufferSize];
            int len;

            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception exception) {
            }
        }
    }
}
