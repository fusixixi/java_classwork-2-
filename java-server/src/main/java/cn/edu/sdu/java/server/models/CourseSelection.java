package cn.edu.sdu.java.server.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * CourseSelection 学生选课表实体类
 * 用于管理学生的选课信息，包括选课时间、选课状态等
 *
 * Integer selectionId - 选课ID，主键，自增
 * Student student - 选课学生，与Student表关联
 * Course course - 选课课程，与Course表关联
 * LocalDateTime selectTime - 选课时间
 * Integer state - 选课状态：1-已选，2-已取消，3-已完成
 * LocalDateTime cancelTime - 取消选课时间
 */
@Getter
@Setter
@Entity
@Table(name = "course_selection")
public class CourseSelection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer selectionId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonIgnore
    private Student student;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonIgnore
    private Course course;

    @Column(name = "select_time")
    private LocalDateTime selectTime;

    @Column(name = "state")
    private Integer state;  // 1-已选，2-已取消，3-已完成

    @Column(name = "cancel_time")
    private LocalDateTime cancelTime;
}