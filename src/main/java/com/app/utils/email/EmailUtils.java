package com.app.utils.email;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeUtility;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import com.app.utils.properties.PropertiesUtil;


/** * 
 * @ClassName: EmailUtils
 * @Description: 邮件工具类，支持发送带附件的邮件
 * @author lyn
 * @date 2019年8月7日
 *
 */
public class EmailUtils {

	private static final String UTF_8 = "UTF-8";

	private static final String FILE_PATH = "/otherConfig/email.properties";

	private static final Map<String, String> config = new HashMap<String, String>();

	static {
		config.put("hostName", PropertiesUtil.GetValueByKey(FILE_PATH, "email.hostName"));
		config.put("username", PropertiesUtil.GetValueByKey(FILE_PATH, "email.username"));
		config.put("password", PropertiesUtil.GetValueByKey(FILE_PATH, "email.password"));
	}

	private EmailUtils() {
	}
	/**	 * 
	 * @Title: send
	 * @Description: 发送邮件 (不要放在主线程调用, 请使用异步)
	 * @param toAddresses
	 * @param subject
	 * @param content 支持html格式
	 * @param directory 附件的路径
	 * @param fileName 附件的文件名
	 * @return
	 */
	public static boolean send(List<String> toAddresses, String subject, String content, String directory, String fileName) {
		try {
			HtmlEmail email = new HtmlEmail();
			email.setHostName(config.get("hostName"));
			email.setAuthentication(config.get("username"), config.get("password"));
			email.setDebug(true);
			// google
			// email.setSmtpPort(465);
			// email.setSSLOnConnect(true);
			// email.setStartTLSEnabled(true);
			email.setCharset(UTF_8);
			email.setFrom(config.get("username"));
			for (String toAddress : toAddresses) {
				email.addTo(toAddress);
			}
			email.setSubject(subject);
			email.setHtmlMsg(content);

			if (StringUtils.isNotBlank(directory) && StringUtils.isNotBlank(fileName)) {
				EmailAttachment attachment = new EmailAttachment();
				// 对附件名称进行编码, 防止乱码
				attachment.setName(MimeUtility.encodeText(fileName));
				attachment.setPath(directory + "/" + fileName);
				email.attach(attachment);
			}
			email.send();
			return true;
		} catch (EmailException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) {
		List<String> toAddresses = new ArrayList<String>();
		toAddresses.add("yangchen@che08.com");
		String subject = "驾校约车";
		String content = "<h2>大标题</h2><div>内容</div>";
		// boolean result = EmailUtils.send(toAddresses, subject, content, "E:/", "文本编辑工具.zip");
		boolean result = EmailUtils.send(toAddresses, subject, content, null, null);
		System.out.println("结果为" + result);
	}

}
