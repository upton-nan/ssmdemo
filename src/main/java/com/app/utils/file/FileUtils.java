package com.app.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;




/**
 * 
 * @ClassName: FileUtils
 * @Description: 文件工具类
 * @author lyn
 * @date 2019年8月4日
 *
 */
public class FileUtils {
	private static final String FOLDER_SEPARATOR = "/";
	private static final char EXTENSION_SEPARATOR = '.';
	
	/**
	 * 
	 * @Title: isExist
	 * @Description: 判断指定路径是否存在，如果不存在，根据参数决定是否新建
	 * @param filePath 指定的文件路径
	 * @param isNew true：新建、false：不新建
	 * @return 存在返回TRUE，不存在返回FALSE
	 */
	public static boolean isExist(String filePath,boolean isNew){
		File file = new File(filePath);
		if(!file.exists() && isNew){    
			return file.mkdirs();    //新建文件路径
		}
		return false;
	}
	
	/**
	 * 
	 * @Title: getFileName
	 * @Description: 获取文件名，构建结构为 prefix + yyyyMMddHH24mmss + 10位随机数 + suffix + .type
	 * @param type 文件类型
	 * @param prefix 前缀
	 * @param suffix 后缀
	 * @return
	 */
	public static String getFileName(String type,String prefix,String suffix){
		//当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH24mmss");
		Date date = new Date();
		String date1 = sdf.format(date);
		//10位随机数
		StringBuffer sb = new StringBuffer();
        Random random = new Random(); 
        for (int i = 0; i < 10; i++) { 
                sb.append(random.nextInt(10)); 
        } 
		String randomStr = sb.toString();    
		
		//返回文件名  
		return prefix + date1 + randomStr + suffix + "." + type;
	}
	
	/**
	 * 
	 * @Title: getFileName
	 * @Description: 获取文件名，文件名构成:当前时间 + 10位随机数 + .type
	 * @param type 文件类型
	 * @return
	 */
	public static String getFileName(String type){
		return getFileName(type, "", "");
	}
	
	/**
	 * 
	 * @Title: getFileName
	 * @Description: 获取文件名，文件构成：当前时间 + 10位随机数
	 * @return
	 */
	public static String getFileName(){
		//当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH24mmss");
		Date date = new Date();
		String date1 = sdf.format(date);
		//10位随机数
		StringBuffer sb = new StringBuffer();
        Random random = new Random(); 
        for (int i = 0; i < 10; i++) { 
                sb.append(random.nextInt(10)); 
        } 
		String randomStr = sb.toString();   
		//返回文件名  
		return date1 + randomStr;
	}
	
	/**
	 * 
	 * @Title: getFileSize
	 * @Description: 获取指定文件的大小
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static long getFileSize(File file) throws Exception {
		long size = 0;
		if (file.exists()) {
			FileInputStream fis = null;
			fis = new FileInputStream(file);
			size = fis.available();
		} else {
			file.createNewFile();
		}
		return size;
	}
	
	/**
	 * 
	 * @Title: deleteAll
	 * @Description: 删除所有文件，包括文件夹
	 * @param dirpath
	 */
    public void deleteAll(String dirpath) {  
    	 File path = new File(dirpath);  
         try {  
             if (!path.exists())  
                 return;// 目录不存在退出   
             if (path.isFile()) // 如果是文件删除   
             {  
                 path.delete();  
                 return;  
             }  
             File[] files = path.listFiles();// 如果目录中有文件递归删除文件   
             for (int i = 0; i < files.length; i++) {  
                 deleteAll(files[i].getAbsolutePath());  
             }  
             path.delete();  

         } catch (Exception e) {  
             e.printStackTrace();  
         }   
    }
    
    
    /**
     * 
     * @Title: copy
     * @Description: 复制文件或者文件夹
     * @param inputFile 源文件
     * @param outputFile 目的文件
     * @param isOverWrite 是否覆盖文件
     * @throws IOException
     */
    public static void copy(File inputFile, File outputFile, boolean isOverWrite)
			throws IOException {
		if (!inputFile.exists()) {
			throw new RuntimeException(inputFile.getPath() + "源目录不存在!");
		}
		copyPri(inputFile, outputFile, isOverWrite);
	}
    
   
    /**
     * 
     * @Title: copyPri
     * @Description: 复制文件或者文件夹
     * @param inputFile 源文件
     * @param outputFile 目的文件
     * @param isOverWrite 是否覆盖文件
     * @throws IOException
     */
    private static void copyPri(File inputFile, File outputFile, boolean isOverWrite) throws IOException {
		if (inputFile.isFile()) {		//文件
			copySimpleFile(inputFile, outputFile, isOverWrite);
		} else {
			if (!outputFile.exists()) {		//文件夹	
				outputFile.mkdirs();
			}
			// 循环子文件夹
			for (File child : inputFile.listFiles()) {
				copy(child, new File(outputFile.getPath() + "/" + child.getName()), isOverWrite);
			}
		}
	}
    
    
    /**
     * 
     * @Title: copySimpleFile
     * @Description: 复制单个文件
     * @param inputFile 源文件
     * @param outputFile 目的文件
     * @param isOverWrite 是否覆盖
     * @throws IOException
     */
    private static void copySimpleFile(File inputFile, File outputFile,
			boolean isOverWrite) throws IOException {
		if (outputFile.exists()) {
			if (isOverWrite) {		//可以覆盖
				if (!outputFile.delete()) {
					throw new RuntimeException(outputFile.getPath() + "无法覆盖！");
				}
			} else {
				// 不允许覆盖
				return;
			}
		}
		InputStream in = new FileInputStream(inputFile);
		OutputStream out = new FileOutputStream(outputFile);
		byte[] buffer = new byte[1024];
		int read = 0;
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
		in.close();
		out.close();
	}
    
    /**
     * 
     * @Title: getFileMD5
     * @Description: 获取文件的MD5
     * @param file 文件
     * @return
     */
	public static String getFileMD5(File file){
		if (!file.exists() || !file.isFile()) {  
            return null;  
        }  
        MessageDigest digest = null;  
        FileInputStream in = null;  
        byte buffer[] = new byte[1024];  
        int len;  
        try {  
            digest = MessageDigest.getInstance("MD5");  
            in = new FileInputStream(file);  
            while ((len = in.read(buffer, 0, 1024)) != -1) {  
                digest.update(buffer, 0, len);  
            }  
            in.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
        BigInteger bigInt = new BigInteger(1, digest.digest());  
        return bigInt.toString(16);  
	}
	
	
	/**
	 * 
	 * @Title: getFileSuffix
	 * @Description: 获取文件的后缀
	 * @param file 文件
	 * @return
	 */
	public static String getFileSuffix(String file) {
		if (file == null) {
			return null;
		}
		int extIndex = file.lastIndexOf(EXTENSION_SEPARATOR);
		if (extIndex == -1) {
			return null;
		}
		int folderIndex = file.lastIndexOf(FOLDER_SEPARATOR);
		if (folderIndex > extIndex) {
			return null;
		}
		return file.substring(extIndex + 1);
	}
	
	/**
	 * 
	 * @Title: renameDir
	 * @Description: 文件重命名
	 * @param oldPath 老文件
	 * @param newPath 新文件
	 * @return
	 */
    public boolean renameDir(String oldPath, String newPath) {  
        File oldFile = new File(oldPath);// 文件或目录   
        File newFile = new File(newPath);// 文件或目录   
        
        return oldFile.renameTo(newFile);// 重命名   
    }
    
    /**
     * 
     * @Title: listFile
     * @Description: 罗列指定路径下的全部文件
     * @param path 指定的路径
     * @param child 是否罗列子目录
     * @return
     */
    public final static List<File> listFile(File path, boolean child) {
        List<File> list = new ArrayList<>();
        File[] files = path.listFiles();
        if (files!=null&&files.length>0) {
            for (File file : files) {
                if (child && file.isDirectory()) {
                    list.addAll(listFile(file));
                } else {
                    list.add(file);
                }
            }
        }
        return list;
    }
    
   
    /**
     * 
     * @Title: listFile
     * @Description: 罗列指定路径下的全部文件
     * @param path 需要处理的文件
     * @return 包含所有文件的的list
     */
    public final static List<File> listFile(String path) {
        File file = new File(path);
        return listFile(file);
    }

    /**
     * 
     * @Title: listFile
     * @Description: 罗列指定路径下的全部文件
     * @param path 需要处理的文件
     * @param child 是否罗列子文件
     * @return 包含所有文件的的list
     */
    public final static List<File> listFile(String path, boolean child) {
        return listFile(new File(path), child);
    }


    /**
     * 
     * @Title: listFile
     * @Description: 罗列指定路径下的全部文件
     * @param path 需要处理的文件
     * @return 返回文件列表
     */
    public final static List<File> listFile(File path) {
        List<File> list = new ArrayList<>();
        File[] files = path.listFiles();
        if (files!=null&&files.length>0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    list.addAll(listFile(file));
                } else {
                    list.add(file);
                }
            }
        }
        return list;
    }

    
    
}
