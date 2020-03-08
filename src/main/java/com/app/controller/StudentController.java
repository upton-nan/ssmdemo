package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.entity.Student;
import com.app.service.StudentService;

@Controller
@RequestMapping("/student")
public class StudentController {
	@Autowired
	private StudentService studentService;
	
	@RequestMapping(value="/selectAllStudentCount",method=RequestMethod.GET)
	@ResponseBody
	public Integer selectAllStudentCount(){
		int count = studentService.selectAllStudentCount();
		return count;
	}
	@RequestMapping(value="/selectAllStudent",method=RequestMethod.GET)
	@ResponseBody
	public List<Student> selectAllStudent(){
		List<Student> students = studentService.selectAllStudent();
		return students;
	}
	
	@RequestMapping(value="/getStudentById",method=RequestMethod.GET)
	public String getStudentById(Integer id,Model model){
		//模拟数据
		Student student = new Student();
		student.setId(id);
		student.setUsername("张三");
		
		model.addAttribute("student", student);
		return "index";
	}
	
	@RequestMapping(value="/getStudentByStudent",method=RequestMethod.POST)
	@ResponseBody
	public Object getStudentByStudent(Student student,RedirectAttributes redirectAttributes,Model model){
		//模拟数据
		//student.setId(id);
		//student.setUsername("张三");
		//System.out.println(student.getId());
		model.addAttribute("student", student);
		//redirectAttributes.addFlashAttribute("student",student);
		return "stringObject";
	}
	
	@RequestMapping(value = "/uploadIcon", method = RequestMethod.POST)
    public String uploadIcon(@RequestParam("icon") MultipartFile icon, Model model) throws Exception {
        model.addAttribute("icon", icon.getBytes());
        return "index";
    }
	
	
	
	
}
