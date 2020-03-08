package com.app.utils.string;

/**
 * 
 * @ClassName: StringUtils
 * @Description: 字符串的工具类
 * @author lyn
 * @date 2019年8月6日
 *
 */
public class StringUtils {
	/**
	 * 
	 * @Title: replace
	 * @Description: 字符串中所含的子串变成被另外一个子串替代
	 * @param inString 主串
	 * @param oldPattern 被替换子串
	 * @param newPattern 替换子串
	 * @return 主串被替换之后的字符串
	 */
	public static String replace(String inString, String oldPattern, String newPattern) {
        if (inString != null && !inString.isEmpty() && oldPattern != null && !oldPattern.isEmpty() && newPattern != null && !newPattern.isEmpty()) {
            int index = inString.indexOf(oldPattern);
            if (index == -1) {
                return inString;
            } else {
                int capacity = inString.length();
                if (newPattern.length() > oldPattern.length()) {
                    capacity += 16;
                }

                StringBuilder sb = new StringBuilder(capacity);
                int pos = 0;

                for(int patLen = oldPattern.length(); index >= 0; index = inString.indexOf(oldPattern, pos)) {
                    sb.append(inString.substring(pos, index));
                    sb.append(newPattern);
                    pos = index + patLen;
                }

                sb.append(inString.substring(pos));
                return sb.toString();
            }
        } else {
            return inString;
        }
    }
	
	public static void main(String[] args) {
		String replace = StringUtils.replace("12345xx789", "xx", "woshini");
		System.out.println(replace);
	}
	
}
