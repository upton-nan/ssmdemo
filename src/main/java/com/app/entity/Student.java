package com.app.entity;



/**
 * 
 * @ClassName: Student
 * @Description: 学生信息实体类
 * @author lyn
 * @date 2019年8月10日
 *
 */

public class Student {
	
	
	private int id;
	private String username;
	private String password;
	private String phone;
	private String email;
	
	public Student(int id, String username, String password, String phone, String email) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.phone = phone;
		this.email = email;
	}
	
	public Student() {
		super();
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
  
}
