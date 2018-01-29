package com.teamtreehouse.courses.dao;

import com.teamtreehouse.courses.exception.DaoException;
import com.teamtreehouse.courses.model.Course;

import java.util.List;

public interface CourseDao {
  void add(Course course) throws DaoException;

  List<Course> findAll();

  Course findById(int id);
}
