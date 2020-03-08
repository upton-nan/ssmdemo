package com.app.utils.file;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 
 * @ClassName: FileDownload
 * @Description: 下载文件
 * @author lyn
 * @date 2019年8月4日
 *
 */
public class FileDownload {
    //测试
    public static void main(String[] args) {

    }

    /**
     * 
     * @Title: fileDownload
     * @Description: 下载文件
     * @param response
     * @param filePath 文件完整路径(包括文件名和扩展名)
     * @param fileName 下载后看到的文件名
     * @throws IOException
     */
    public static void fileDownload(final HttpServletResponse response, String filePath, String fileName) throws IOException {

        byte[] data=filePath.getBytes();
        fileName= URLEncoder.encode(fileName,"UTF-8");
        response.reset();
        response.setHeader("Content-Disposition","attachment; filename=\"" + fileName + "\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream outputStream=new BufferedOutputStream(response.getOutputStream());
        outputStream.write(data);
        outputStream.flush();
        outputStream.close();
        response.flushBuffer();

    }
    
    /**
     * 
     * @Title: convertAttachmentFileName
     * @Description: 根据不同浏览器防止下载的附件名称乱码
     * @param request
     * @param response
     * @param fileName
     */
	public static void convertAttachmentFileName(HttpServletRequest request, HttpServletResponse response, String fileName) {
		if (StringUtils.isNotBlank(fileName)) {
			try {
				fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
				String userAgent = request.getHeader("User-Agent");
				if (StringUtils.isNotBlank(userAgent)) {
					userAgent = userAgent.toLowerCase();
					// IE浏览器只能采用URLEncoder编码
					if (userAgent.indexOf("msie") != -1) {
						fileName = "filename=\"" + fileName + "\"";
					}
					// Opera浏览器只能采用filename*
					else if (userAgent.indexOf("opera") != -1) {
						fileName = "filename*=UTF-8''" + fileName;
					}
					// Chrome浏览器只能采用MimeUtility编码或ISO编码的中文输出
					else if (userAgent.indexOf("chrome") != -1) {
						fileName = "filename=\"" + MimeUtility.encodeText(fileName, "UTF-8", "B") + "\"";
					}
					// Safari浏览器只能采用ISO编码的中文输出
					else if (userAgent.indexOf("safari") != -1) {
						fileName = "filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"";
					}
					// FireFox浏览器可以使用MimeUtility或filename*或ISO编码的中文输出
					else if (userAgent.indexOf("mozilla") != -1) {
						fileName = "filename*=UTF-8''" + fileName;
					}
				}
				response.setHeader("Content-Disposition", "attachment; " + fileName);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}
    
}
