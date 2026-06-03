package cn.edu.sdu.java.server.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * ScoreSubmission 成绩提交审核表实体类
 * 用于管理教师提交的成绩和审核流程
 *
 * Integer submissionId - 提交ID，主键，自增
 * Score score - 成绩，与Score表关联
 * Teacher teacher - 教师，与Teacher表关联
 * Student student - 学生，与Student表关联
 * Course course - 课程，与Course表关联
 * Integer submittedScore - 提交的分数
 * LocalDateTime submissionTime - 提交时间
 * Integer state - 审批状态：1-待审批，2-审批中，3-已通过，4-已拒绝，5-已发布
 * Person currentApprover - 当前审批人，与Person表关联
 * String approvalComment - 审批意见
 * LocalDateTime approvalTime - 审批时间
 * LocalDateTime publishTime - 发布时间
 */
@Getter
@Setter
@Entity
@Table(name = "score_submission")
public class ScoreSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer submissionId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "score_id")
    @JsonIgnore
    private Score score;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    @JsonIgnore
    private Teacher teacher;

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

    @NotNull
    @Column(name = "submitted_score")
    private Integer submittedScore;

    @Column(name = "submission_time")
    private LocalDateTime submissionTime;

    @Column(name = "state")
    private Integer state;

    @ManyToOne
    @JoinColumn(name = "current_approver_id")
    @JsonIgnore
    private Person currentApprover;

    @Size(max = 500)
    @Column(name = "approval_comment")
    private String approvalComment;

    @Column(name = "approval_time")
    private LocalDateTime approvalTime;

    @Column(name = "publish_time")
    private LocalDateTime publishTime;
}