package com.forohub.forohub.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Topic {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  @Column(length = 1000)
  private String message;

  private LocalDateTime creationDate;

  @Enumerated(EnumType.STRING)
  private Status status;

  private String author;

  private String course;

  public enum Status {
    OPEN,
    CLOSED
  }

  public Topic() {
    this.creationDate = LocalDateTime.now();
    this.status = Status.OPEN;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public LocalDateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getCourse() {
    return course;
  }

  public void setCourse(String course) {
    this.course = course;
  }
}
