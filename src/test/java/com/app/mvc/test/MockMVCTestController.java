package com.app.mvc.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.app.controller.StudentController;
/**
 *
 * 1、@WebAppConfiguration：测试环境使用，用来表示测试环境使用的ApplicationContext将是WebApplicationContext类型的；value指定web应用的根；<br>
 * 2、@ContextHierarchy：加载spring容器的配置文件<br>
 * 3、@Autowired：WebApplicationContext wac-->注入web环境的ApplicationContext容器；<br>
 * 4、MockMvcBuilders.webAppContextSetup(wac).build()：创建一个MockMvc进行测试；<br>
 * @ClassName: MockMVCTestController
 * @Description: TODO
 * @author lyn
 * @date 2019年8月11日
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration(value="src/main/webapp")
@ContextHierarchy({
	  @ContextConfiguration(name = "contextConfigLocation", locations = "classpath:spring/applicationContext-*.xml"),  
      @ContextConfiguration(name = "springmvc", locations = "classpath:spring/springmvc.xml")  
})
public class MockMVCTestController {
	
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	@Before
	public void setUp(){
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	//开始测试
	/**
	 * 
	 * @Title: testDemo1
	 * @Description: <br>
	 * 1、mockMvc.perform执行一个请求；<br>
	 * 2、MockMvcRequestBuilders.get("/user/1")构造一个请求<br>
	 * 3、ResultActions.andExpect添加执行完成后的断言<br>
	 * 4、ResultActions.andDo添加一个结果处理器，表示要对结果做点什么事情，比如此处使用MockMvcResultHandlers.print()输出整个响应结果信息。<br>
	 * 5、ResultActions.andReturn表示执行完成后返回相应的结果。<br>
	 * @throws Exception
	 */
	@Test
	public void testDemo1() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/student/getStudentById?id=1"))
			.andExpect(MockMvcResultMatchers.view().name("index"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("student"))
			.andDo(MockMvcResultHandlers.print())
			.andReturn();
	}
	/**
	 * 
	 * @Title: testDemo2
	 * @Description: 测试普通控制器
	 * @throws Exception
	 */
	@Test
	public void testDemo2() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/student/getStudentById?id=1"))//执行请求
		.andExpect(MockMvcResultMatchers.model().attributeExists("student"))//验证存储模型数据  
		.andExpect(MockMvcResultMatchers.view().name("index"))//验证viewName
		.andExpect(MockMvcResultMatchers.forwardedUrl("/pages/index.jsp"))//验证视图渲染时forward到的jsp
		.andExpect(MockMvcResultMatchers.status().isOk())//验证状态码
		.andDo(MockMvcResultHandlers.print());//输出MvcResult到控制台
	}
	/**
	 * 
	 * @Title: testDemo3
	 * @Description: 找不到控制器404
	 * @throws Exception
	 */
	@Test
	public void testDemo3() throws Exception{
		//找不到控制器，404测试  
		MvcResult result =mockMvc.perform(MockMvcRequestBuilders.get("/student/getStudentById?id=1"))//执行请求
				.andDo(MockMvcResultHandlers.print())//输出MvcResult到控制台
		        .andExpect(MockMvcResultMatchers.status().isNotFound())//验证控制器不存在 
		        .andReturn();//返回mvcResult
		Assert.assertNull(result.getModelAndView());//自定义断言 
	}
	
	/**
	 * 
	 * @Title: testDemo4
	 * @Description: MvcResult自定义验证
	 * @throws Exception
	 */
	@Test
	public void testDemo4() throws Exception{
		MvcResult andReturn = mockMvc.perform(MockMvcRequestBuilders.get("/student/getStudentById?id=1"))//执行请求
		.andReturn();//返回MvcResult
		Assert.assertNotNull(andReturn.getModelAndView().getModel().get("student"));//自定义断言  
	}
	/**
	 * 
	 * @Title: testDemo5
	 * @Description: 验证请求参数绑定到模型数据及Flash属性
	 * @throws Exception
	 */
	@Test
	public void testDemo5() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.post("/student/getStudentByStudent").param("id", "1").param("username", "username1"))//执行传递参数的POST请求(也可以post("/student/getStudentByStudent?id=1&username=username1"))  
				.andExpect(MockMvcResultMatchers.handler().handlerType(StudentController.class))//验证执行的控制器类型
				.andExpect(MockMvcResultMatchers.handler().methodName("getStudentByStudent"))//验证执行的控制器方法名
				.andExpect(MockMvcResultMatchers.model().hasNoErrors())//验证页面没有错误
				.andExpect(MockMvcResultMatchers.flash().attributeExists("student"))//验证存在flash属性  
				.andExpect(MockMvcResultMatchers.view().name("redirect:/pages/index.jsp"))//验证视图 
				.andReturn();//返回MvcResult
	}
	
	/**
	 * 
	 * @Title: testDemo6
	 * @Description: 验证请求参数验证失败出错
	 * @throws Exception
	 */
	@Test
	public void testDemo6() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.post("/student/getStudentByStudent").param("id", "1").param("username", "username1"))//执行传递参数的POST请求(也可以post("/student/getStudentByStudent?id=1&username=username1"))  
				.andExpect(MockMvcResultMatchers.model().hasErrors())//验证模型有错误
				.andExpect(MockMvcResultMatchers.model().attributeHasErrors("student"))//验证存在错误的属性  
				.andExpect(MockMvcResultMatchers.view().name("redirect:/pages/index.jsp"))//验证视图  
				.andReturn();//返回MvcResult
	}
	/**
	 * 
	 * @Title: testDemo7
	 * @Description: 文件上传
	 * @throws Exception
	 */
	@Test
	public void testDemo7() throws Exception{
		byte[] bytes = new byte[] {1, 2};
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/student/uploadIcon").file("icon", bytes))//执行文件上传  
				.andExpect(MockMvcResultMatchers.model().attribute("icon", bytes))//验证属性相等性
				.andExpect(MockMvcResultMatchers.view().name("index"))//验证视图
				.andDo(MockMvcResultHandlers.print())
				.andReturn();//返回MvcResult
	}
	
	
	@Test
	public void testDemo8() throws Exception{
		String requestBody="{'id':1,'username':'usernameStr'}";
		mockMvc.perform(MockMvcRequestBuilders.post("/student/getStudentByStudent")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(requestBody)
				.accept(MediaType.APPLICATION_JSON_UTF8))//执行请求 
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))//验证响应contentType
			.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))//使用Json path验证JSON
			.andDo(MockMvcResultHandlers.print())
			.andReturn();//返回MvcResult
	}
	
	
	@Test
	public void testDemo9() throws Exception{
		String requestBody="{'id':1,'username':'usernameStr'}";
		mockMvc.perform(MockMvcRequestBuilders.post("/student/getStudentByStudent")
				.contentType(MediaType.APPLICATION_JSON).content(requestBody)
				.accept(MediaType.APPLICATION_JSON))//执行请求
				.andExpect(MockMvcResultMatchers.status().isBadRequest())//400错误请求
				.andDo(MockMvcResultHandlers.print())
				.andReturn();//返回MvcResult
	}
	
	
	
	
	
}
