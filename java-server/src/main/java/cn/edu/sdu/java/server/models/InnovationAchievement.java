package cn.edu.sdu.java.server.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * InnovationAchievement 学生创新成果表实体类
 * 用于管理学生的创新成果信息，包括项目、竞赛、发明、论文等
 *
 * Integer achievementId - 成果ID，主键，自增
 * Student student - 学生，与Student表关联
 * String title - 成果名称
 * String description - 成果描述
 * String category - 成果类型：项目、竞赛、发明、论文等
 * LocalDate achievementDate - 成果完成日期
 * LocalDateTime submitTime - 提交时间
 * Integer state - 审批状态：1-待审批，2-审批中，3-已通过，4-已拒绝
 * String attachmentPath - 附件路径
 * LocalDateTime createTime - 创建时间
 * LocalDateTime updateTime - 更新时间
 */
@Getter
@Setter
@Entity
@Table(name = "innovation_achievement")
public class InnovationAchievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer achievementId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonIgnore
    private Student student;

    @NotNull
    @Size(max = 100)
    @Column(name = "title")
    private String title;

    @Size(max = 500)
    @Column(name = "description")
    private String description;

    @Size(max = 50)
    @Column(name = "category")
    private String category;  // 项目、竞赛、发明、论文等

    @Column(name = "achievement_date")
    private LocalDate achievementDate;

    @Column(name = "submit_time")
    private LocalDateTime submitTime;

    @Column(name = "state")
    private Integer state;  // 1-待审批，2-审批中，3-已通过，4-已拒绝

    @Size(max = 255)
    @Column(name = "attachment_path")
    private String attachmentPath;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;
}