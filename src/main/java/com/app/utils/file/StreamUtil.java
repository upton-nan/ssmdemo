package com.app.utils.file;

import java.io.*;

/**
 * 
 * @ClassName: StreamUtil
 * @Description: 流相关的操作方法封装
 * @author lyn
 * @date 2019年8月6日
 *
 */
public final class StreamUtil {
   
	/**
	 * 
	 * @Title: streamToString
	 * @Description: 将InputStream转变成字符串
	 * @param in
	 * @return
	 * @throws IOException
	 */
    public final static String streamToString(InputStream in) throws IOException {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1; ) {
            out.append(new String(b, 0, n));
        }
        return out.toString();
    }

    /**
     * 
     * @Title: stream2Byte
     * @Description: 将InputStream转变为byte[]
     * @param is InputStream
     * @return
     * @throws IOException
     */
    public final static byte[] stream2Byte(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = 0;
        byte[] b = new byte[1024];
        while ((len = is.read(b, 0, b.length)) != -1) {
            baos.write(b, 0, len);
        }
        byte[] buffer = baos.toByteArray();
        return buffer;
    }

    /**
     * 
     * @Title: inputStream2Byte
     * @Description: InputStream 转为 byte
     * @param inStream
     * @return
     * @throws Exception
     */
    public final static byte[] inputStream2Byte(InputStream inStream) throws Exception {
        int count = 0;
        while (count == 0) {
            count = inStream.available();
        }
        byte[] b = new byte[count];
        inStream.read(b);
        return b;
    }

    /**
     * 
     * @Title: byte2InputStream
     * @Description: byte 转为 InputStream
     * @param b
     * @return
     * @throws Exception
     */
    public final static InputStream byte2InputStream(byte[] b) throws Exception {
        InputStream is = new ByteArrayInputStream(b);
        return is;
    }

    /**
     * 将流另存为文件
     */
    /**
     * 
     * @Title: streamSaveAsFile
     * @Description: 将InputStream流另存为文件File
     * @param is 需要转变的InputStream
     * @param outfile 输出的File
     */
    public final static void streamSaveAsFile(InputStream is, File outfile) {
        try (FileOutputStream fos = new FileOutputStream(outfile)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}
