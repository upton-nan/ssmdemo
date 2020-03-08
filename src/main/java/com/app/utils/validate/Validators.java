package com.app.utils.validate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.app.utils.path.PathUtil;

/**
 * 
 * 
 * @ClassName: Validators
 * @Description: 验证工具类
 * @author lyn
 * @date 2019年8月4日
 *
 */
public class Validators {

	/**
	 * 
	 * @Title: checkEmail
	 * @Description: 验证Email
	 * @param email email地址，格式：zhangsan@sina.com，zhangsan@xxx.com.cn，xxx代表邮件服务商
	 * @return 验证成功返回true，验证失败返回false
	 */
    public static boolean checkEmail(String email) { 
        String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?"; 
        return Pattern.matches(regex, email); 
    } 
     
    /**
     * 
     * @Title: checkIdCard
     * @Description: 验证身份证号码
     * @param idCard 居民身份证号码18位，第一位不能为0，最后一位可能是数字或字母，中间16位为数字 \d同[0-9]
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkIdCard(String idCard) { 
        String regex = "[1-9]\\d{16}[a-zA-Z0-9]{1}"; 
        return Pattern.matches(regex,idCard); 
    } 
     
    /**
     * 
     * @Title: checkMobile
     * @Description: 验证手机号码（支持国际格式，+86135xxxx...（中国内地），+00852137xxxx...（中国香港））
     * @param mobile 移动、联通、电信运营商的号码段
     * <p>移动的号段：134(0-8)、135、136、137、138、139、147（预计用于TD上网卡）
     * 、150、151、152、157（TD专用）、158、159、187（未启用）、188（TD专用）</p>
     * <p>联通的号段：130、131、132、155、156（世界风专用）、185（未启用）、186（3g）</p>
     * <p>电信的号段：133、153、180（未启用）、189</p>
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkMobile(String mobile) { 
        String regex = "(\\+\\d+)?1[3458]\\d{9}$"; 
        return Pattern.matches(regex,mobile); 
    } 
     
    /**
     * 
     * @Title: checkPhone
     * @Description: 验证固定电话号码
     * @param phone 电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：+8602085588447
     * <p><b>国家（地区） 代码 ：</b>标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9 的一位或多位数字，
     *  数字之后是空格分隔的国家（地区）代码。</p>
     * <p><b>区号（城市代码）：</b>这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号——
     * 对不使用地区或城市代码的国家（地区），则省略该组件。</p>
     * <p><b>电话号码：</b>这包含从 0 到 9 的一个或多个数字 </p>
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkPhone(String phone) { 
        String regex = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$"; 
        return Pattern.matches(regex, phone); 
    } 
     
    /**
     * 
     * @Title: checkDigit
     * @Description: 验证整数（正整数和负整数）
     * @param digit 一位或多位0-9之间的整数
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkDigit(String digit) { 
        String regex = "\\-?[1-9]\\d+"; 
        return Pattern.matches(regex,digit); 
    } 
     
    /**
     * 
     * @Title: checkDecimals
     * @Description: 验证整数和浮点数（正负整数和正负浮点数）
     * @param decimals 一位或多位0-9之间的浮点数，如：1.23，233.30
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkDecimals(String decimals) { 
        String regex = "\\-?[1-9]\\d+(\\.\\d+)?"; 
        return Pattern.matches(regex,decimals); 
    }  
     
    /**
     * 
     * @Title: checkBlankSpace
     * @Description: 验证空白字符
     * @param blankSpace 空白字符，包括：空格、\t、\n、\r、\f、\x0B
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkBlankSpace(String blankSpace) { 
        String regex = "\\s+"; 
        return Pattern.matches(regex,blankSpace); 
    } 
     
   
    /**
     * 
     * @Title: checkChinese
     * @Description: 验证中文
     * @param chinese 中文字符
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkChinese(String chinese) { 
        String regex = "^[\u4E00-\u9FA5]+$"; 
        return Pattern.matches(regex,chinese); 
    } 
    
    /**
     * 
     * @Title: checkBirthday
     * @Description: 验证日期（年月日）
     * @param birthday 日期，格式：1992-09-03，或1992.09.03
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkBirthday(String birthday) { 
        String regex = "[1-9]{4}([-./])\\d{1,2}\\1\\d{1,2}"; 
        return Pattern.matches(regex,birthday); 
    } 
     
    /**
     * 
     * @Title: checkURL
     * @Description: 验证URL地址
     * @param url 格式：http://blog.csdn.net:80/xyang81/article/details/7705960? 或 http://www.csdn.net:80
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkURL(String url) { 
        String regex = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?"; 
        return Pattern.matches(regex, url); 
    } 
    
    /**
     * 
     * @Title: getDomain
     * @Description: 
     * <pre>
     * 获取网址 URL 的一级域名
     * http://detail.tmall.com/item.htm?spm=a230r.1.10.44.1xpDSH&id=15453106243&_u=f4ve1uq1092 ->> tmall.com
     * </pre>
     * @param url
     * @return
     */
    public static String getDomain(String url) {
		Pattern p = Pattern.compile("(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE);
		// 获取完整的域名
		// Pattern p=Pattern.compile("[^//]*?\\.(com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(url);
		matcher.find();
		return matcher.group();
    }
   
    /**
     * 
     * @Title: checkPostcode
     * @Description: 匹配中国邮政编码
     * @param postcode 邮政编码
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkPostcode(String postcode) { 
        String regex = "[1-9]\\d{5}"; 
        return Pattern.matches(regex, postcode); 
    } 
     
    /**
     * 
     * @Title: checkIpAddress
     * @Description: 匹配IP地址(简单匹配，格式，如：192.168.1.1，127.0.0.1，没有匹配IP段的大小)
     * @param ipAddress IPv4标准地址
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkIpAddress(String ipAddress) { 
        String regex = "[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))"; 
        return Pattern.matches(regex, ipAddress); 
    }
    
    /**
     * 
     * @Title: isDate
     * @Description: 判断字符串是否是符合指定格式的时间
     * @param date 时间字符串
     * @param format 时间格式
     * @return 是否符合
     */
    public final static boolean isDate(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * 
     * @Title: isStringNotEmpty
     * @Description: 判断字符串有效性
     * @param src
     * @return
     */
    public final static boolean isStringNotEmpty(String src) {
        return !(src == null || "".equals(src.trim()));
    }
    
    /**
     * 
     * @Title: isStringNotEmpty
     * @Description: 判断一组字符串是否有效
     * @param src
     * @return
     */
    public final static boolean isStringNotEmpty(String... src) {
        for (String s : src) {
            if (!isStringNotEmpty(s)) {
                return false;
            }
        }
        return true;
    }
    
   /**
    * 
    * @Title: isObjectNotNull
    * @Description: 判断一个对象是否为空
    * @param obj
    * @return
    */
    public final static boolean isObjectNotNull(Object obj) {
        return !(null == obj);
    }
    
    /**
     * 
     * @Title: isObjectNotNull
     * @Description: 判断一组对象是否有效
     * @param objs
     * @return
     */
    public final static boolean isObjectNotNull(Object... objs) {
        if (objs != null && objs.length != 0) {
            return true;
        }
        return false;
    }
    
    /**
     * 
     * @Title: isCollectionNotEmpty
     * @Description: 判断集合的有效性
     * @param col
     * @return
     */
    public final static boolean isCollectionNotEmpty(Collection col) {
        return !(col == null || col.isEmpty());
    }
    
    /**
     * 
     * @Title: isCollectionNotEmpty
     * @Description: 判断一组集合是否有效
     * @param cols
     * @return
     */
    public final static boolean isCollectionNotEmpty(Collection... cols) {
        for (Collection c : cols) {
            if (!isCollectionNotEmpty(c)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 
     * @Title: isMapNotEmpty
     * @Description: 判断map是否有效
     * @param map
     * @return
     */
    public final static boolean isMapNotEmpty(Map map) {
        return !(map == null || map.isEmpty());
    }
    
    /**
     * 
     * @Title: isMapNotEmpty
     * @Description: 判断一组map是否有效
     * @param maps 需要判断map
     * @return 是否全部有效
     */
    public final static boolean isMapNotEmpty(Map... maps) {
        for (Map m : maps) {
            if (!isMapNotEmpty(m)) {
                return false;
            }
        }
        return true;
    }
    
    
    /**
     * 
     * @Title: legalFile
     * @Description: 判断是否符是合法的文件路径
     * @param path 需要处理的文件路径
     * @return
     */
    public final static boolean legalFile(String path) {
        //下面的正则表达式有问题
        String regex = "[a-zA-Z]:(?:[/][^/:*?\"<>|.][^/:*?\"<>|]{0,254})+";
        //String regex ="^([a-zA-z]:)|(^\\.{0,2}/)|(^\\w*)\\w([^:?*\"><|]){0,250}";
        return Pattern.matches(regex, PathUtil.commandPath(path)); 
    }
    
    
    /**
     * 
     * @Title: isABC
     * @Description: 判断是否纯字母组合
     * @param src 源字符串
     * @return 是否纯字母组合的标志
     */
    public final static boolean isABC(String src) {
        boolean return_value = false;
        if (src != null && src.length() > 0) {
            Matcher m = Pattern.compile("^[a-z|A-Z]+$").matcher(src);
            if (m.find()) {
                return_value = true;
            }
        }
        return return_value;
    }
    
    /**
     * 
     * @Title: isFloatNumber
     * @Description: 判断是否浮点数字表示
     * @param src 源字符串
     * @return 是否数字的标志
     */
    public final static boolean isFloatNumber(String src) {
        boolean return_value = false;
        if (src != null && src.length() > 0) {
            Matcher m = Pattern.compile("^[0-9\\-\\.]+$").matcher(src);
            if (m.find()) {
                return_value = true;
            }
        }
        return return_value;
    }
    
    
  
	 /**
	  * 
	  * @Title: isNumber
	  * @Description: 校验是否正负数字
	  * @param str
	  * @return
	  */
	public static boolean isNumber(String str) {
		return Validators.isStringNotEmpty(str) && Pattern.compile("^[-\\+]?[\\d]+$").matcher(str).matches();
	}
    
    
    
    
    
    
    
    
    public static void main(String[] args) {
		System.out.println(Validators.isDate("20191122-01", "yyyy-MM-dd"));
	}
    
}
