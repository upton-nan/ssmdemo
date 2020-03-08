package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.Student;
import com.app.mybatis.mapper.StudentMapper;

/**
 * 
 * @ClassName: StudentService
 * @Description: 学生的service层
 * @author lyn
 * @date 2019年8月10日
 *
 */


@Service
public class StudentService {
	@Autowired
	private StudentMapper studentMapper;
	
	
	
	public int selectAllStudentCount(){
		int count = studentMapper.selectAllStudentCount();
		return count;
	}
	

	
	public List<Student> selectAllStudent(){
		List<Student> students = studentMapper.selectAllStudent();
		return students;
	}
	  
}
