package com.teamtreehouse.courses.dao;

import com.teamtreehouse.courses.exception.DaoException;
import com.teamtreehouse.courses.model.Review;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oReviewDao implements ReviewDao {

  private final Sql2o sql2o;

  public Sql2oReviewDao(Sql2o sql2o) {
    this.sql2o = sql2o;
  }

  @Override
  public void add(Review review) throws DaoException {
    String sql = "INSERT INTO reviews(course_id, rating, comment) VALUES (:course_id, :rating, :comment)";
    try (Connection connection = sql2o.open()) {
      int id = (int) connection.createQuery(sql)
          .bind(review)
          .executeUpdate()
          .getKey();
      review.setId(id);
    } catch (Sql2oException exception) {
      throw new DaoException(exception, "Problem adding review");
    }
  }

  @Override
  public List<Review> findAll() {
    try (Connection connection = sql2o.open()) {
      return connection.createQuery("SELECT * FROM reviews")
          .executeAndFetch(Review.class);
    }
  }

  @Override
  public List<Review> findByCourseId(int courseId) {
    try(Connection connection = sql2o.open()) {
      return connection.createQuery("SELECT * FROM reviews WHERE course_id = :courseId")
          .addParameter("courseId", courseId)
          .executeAndFetch(Review.class);
    }
  }
}
