package com.app.utils.path;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * 
 * @ClassName: PathUtil
 * @Description: 路径工具类：截取特定路径、获取ClassPath、获取URl地址
 * @author lyn
 * @date 2019年8月5日
 *
 */
public class PathUtil {

    //测试
    public static void main(String[] args) {
        System.out.println("splitString:"+splitString("E:java/IDEA/bin/Helloworld.java","bin/")+"\n");
        //System.out.println("getPicturePath:"+getPicturePath("save","topic"));
        System.out.println("getClasspath:"+getClasspath());
        System.out.println("getClassResources:"+getClassResources());
        System.out.println("pathAddress:"+pathAddress());
    }

    
    /**
     * 
     * @Title: splitString
     * @Description: 截取特定路径<br>
     * eg：splitString("E:java/IDEA/bin/Helloworld.java","bin/") 输出：E:java/IDEA/
     * @param str 全路径
     * @param param 分隔符
     * @return
     */
    private static String splitString(String str,String param){
        String result=str;
        if(str.contains(param)){
            int start=str.indexOf(param);
            result=str.substring(0,start);
        }
        return result;
    }

    /**
     * 
     * @Title: getClasspath
     * @Description: 获取Classpath
     * <br>eg:getClasspath:E:/JAVA/IDEA/JavaProject/FH/out/production/FH/../../
     * @return
     */
    public static String getClasspath(){
        String path=(String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))+"../../").replaceAll("file:/","").replaceAll("%20"," ").trim();
        if(1!=path.indexOf(":")){
            path= File.separator+path;
        }
        return path;
    }

    /**
     * 
     * @Title: getClassResources
     * @Description: 获取Classpath
     * <br>eg:getClassResources:E:/JAVA/IDEA/JavaProject/FH/out/production/FH/
     * @return
     */
    public static String getClassResources(){
        String path =  (String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))).replaceAll("file:/", "").replaceAll("%20", " ").trim();
        if(path.indexOf(":") != 1){
            path = File.separator + path;
        }
        return path;
    }

    /**
     * 
     * @Title: pathAddress
     * @Description: 获取地址
     * @return
     */
    public static String pathAddress(){
        String strResult="";
        HttpServletRequest request=((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        StringBuffer strBuf=new StringBuffer();

        strBuf.append(request.getScheme()+"://");   //协议
        strBuf.append(request.getServletPath()+":");//服务器地址
        strBuf.append(request.getServerPort()+"");  //端口号
        strBuf.append(request.getContextPath()+"/"); //上下文路径

        strResult=strBuf.toString();
        return strResult;
    }
    
    
    /**
     * 
     * @Title: getProjectPath
     * @Description: 获取项目地址
     * @return
     */
    public static String getProjectPath(){
        String nowPath="";
        nowPath=System.getProperty("user.dir")+"/";
        return nowPath;
    }

    /**
     * 
     * @Title: commandPath
     * @Description: 返回一个通用的文件路径
     * @param file 需要处理的文件路径
     * @return windows中路径分隔符是\在linux中是/但windows也支持/方式 故全部使用/
     */ 
    public final static String commandPath(String file) {
        return file.replaceAll("\\\\{1,}", "/").replaceAll("\\/{2,}", "/");
    }
    
}
