package cn.edu.sdu.java.server.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * ApprovalWorkflow 审批流程表实体类
 * 用于管理各类审批流程的状态和进度
 *
 * Integer workflowId - 工作流ID，主键，自增
 * String workflowType - 工作流类型：leave-请假，achievement-创新成果，score-成绩审核
 * Integer relatedId - 关联业务ID（如student_leave_id、achievement_id等）
 * Person applicant - 申请人，与Person表关联
 * Person currentApprover - 当前审批人，与Person表关联
 * Integer approvalStep - 当前审批步骤
 * Integer totalSteps - 总审批步骤数
 * String state - 状态：pending-待审批，approved-已通过，rejected-已拒绝，completed-已完成
 * LocalDateTime applyTime - 申请时间
 * LocalDateTime approvalTime - 最后审批时间
 * String approvalComment - 审批意见
 * LocalDateTime createTime - 创建时间
 */
@Getter
@Setter
@Entity
@Table(name = "approval_workflow")
public class ApprovalWorkflow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer workflowId;

    @NotNull
    @Size(max = 50)
    @Column(name = "workflow_type")
    private String workflowType;  // leave-请假，achievement-创新成果，score-成绩审核

    @NotNull
    @Column(name = "related_id")
    private Integer relatedId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "applicant_id")
    @JsonIgnore
    private Person applicant;

    @ManyToOne
    @JoinColumn(name = "current_approver_id")
    @JsonIgnore
    private Person currentApprover;

    @Column(name = "approval_step")
    private Integer approvalStep;

    @Column(name = "total_steps")
    private Integer totalSteps;

    @Size(max = 50)
    @Column(name = "state")
    private String state;  // pending-待审批，approved-已通过，rejected-已拒绝，completed-已完成

    @Column(name = "apply_time")
    private LocalDateTime applyTime;

    @Column(name = "approval_time")
    private LocalDateTime approvalTime;

    @Size(max = 500)
    @Column(name = "approval_comment")
    private String approvalComment;

    @Column(name = "create_time")
    private LocalDateTime createTime;
}