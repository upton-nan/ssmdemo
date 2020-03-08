package com.app.mybatis.mapper;

import java.util.List;

import com.app.entity.Student;

/**
 * 
 * @ClassName: StudentDao
 * @Description: 学生的数据访问层
 * @author lyn
 * @date 2019年8月10日
 *
 */
public interface StudentMapper {

  /**
   * 
   * @Title: selectAllStudentCount
   * @Description: 查询全部学生数量
   * @return
   */
  int selectAllStudentCount();

  /**
   * 
   * @Title: selectAllStudent
   * @Description: 查询全部学生
   * @return
   */
  List<Student> selectAllStudent();

}
