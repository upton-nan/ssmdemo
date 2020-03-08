package com.app.utils.file;


import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * @ClassName: FileUpload
 * @Description: 文件上传
 * @author lyn
 * @date 2019年8月4日
 *
 */
public class FileUpload {
    //测试
    public static void main(String[] args) {

    }

    /**
     * 
     * @Title: copyFile
     * @Description: 写文件到当前目录的upload目录中
     * @param in
     * @param dir
     * @param realName
     * @return
     * @throws IOException
     */
    public static String copyFile(InputStream in,String dir,String realName) throws IOException {
    	 final File tempFile = File.createTempFile("stream2file", ".tmp");
         tempFile.deleteOnExit();
         try (FileOutputStream out = new FileOutputStream(tempFile)) {
             IOUtils.copy(in, out);
         }
    	File file=new File(dir,realName);
        if(!file.exists()){
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdir();
            }
            file.createNewFile();
        }
        FileUtils.copy(tempFile, file, true);
        return realName;
    }

    
    /**
     * 
     * @Title: fileUp
     * @Description: 文件上传
     * @param file 文件对象
     * @param filePath 上传路径
     * @param fileName 文件名
     * @return
     */
    public static String fileUp(MultipartFile file,String filePath,String fileName){
        String extName="";  //后缀
        try {
            if(file.getOriginalFilename().lastIndexOf(".")>=0){
                extName=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            }
            copyFile(file.getInputStream(),filePath,fileName+extName).replace("-","");
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        return fileName+extName;
    }

}
