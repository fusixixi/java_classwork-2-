package cn.edu.sdu.java.server.services;

import cn.edu.sdu.java.server.models.ApprovalWorkflow;
import cn.edu.sdu.java.server.models.Person;
import cn.edu.sdu.java.server.repositorys.ApprovalWorkflowRepository;
import cn.edu.sdu.java.server.repositorys.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * ApprovalWorkflowService 审批流程服务类
 * 提供审批流程管理的业务逻辑
 */
@Service
@Transactional
public class ApprovalWorkflowService {

    @Autowired
    private ApprovalWorkflowRepository approvalWorkflowRepository;

    @Autowired
    private PersonRepository personRepository;

    /**
     * 创建审批流程
     */
    public ApprovalWorkflow createWorkflow(String workflowType, Integer relatedId,
                                           Integer applicantId, Integer firstApproverId,
                                           Integer totalSteps) {
        Optional<Person> applicant = personRepository.findById(applicantId);
        Optional<Person> approver = personRepository.findById(firstApproverId);

        if (applicant.isEmpty() || approver.isEmpty()) {
            throw new RuntimeException("申请人或审批人不存在");
        }

        ApprovalWorkflow workflow = new ApprovalWorkflow();
        workflow.setWorkflowType(workflowType);
        workflow.setRelatedId(relatedId);
        workflow.setApplicant(applicant.get());
        workflow.setCurrentApprover(approver.get());
        workflow.setApprovalStep(1);
        workflow.setTotalSteps(totalSteps);
        workflow.setState("pending");
        workflow.setApplyTime(LocalDateTime.now());
        workflow.setCreateTime(LocalDateTime.now());

        return approvalWorkflowRepository.save(workflow);
    }

    /**
     * 审批流程
     */
    public void approveWorkflow(Integer workflowId, String approvalComment, String result) {
        Optional<ApprovalWorkflow> workflow = approvalWorkflowRepository.findById(workflowId);
        if (workflow.isEmpty()) {
            throw new RuntimeException("审批流程不存在");
        }

        ApprovalWorkflow aw = workflow.get();
        aw.setApprovalComment(approvalComment);
        aw.setApprovalTime(LocalDateTime.now());

        if ("approved".equals(result)) {
            // 如果是最后一步，标记为已完成
            if (aw.getApprovalStep() >= aw.getTotalSteps()) {
                aw.setState("completed");
            } else {
                // 进入下一步
                aw.setApprovalStep(aw.getApprovalStep() + 1);
                aw.setState("pending");
            }
        } else if ("rejected".equals(result)) {
            aw.setState("rejected");
        }

        approvalWorkflowRepository.save(aw);
    }

    /**
     * 查询待审批的流程
     */
    public List<ApprovalWorkflow> getPendingWorkflows(Integer approverId) {
        return approvalWorkflowRepository.findPendingWorkflowsByApprover(approverId);
    }

    /**
     * 查询待审批流程数量
     */
    public long getPendingWorkflowCount(Integer approverId) {
        return approvalWorkflowRepository.countPendingByApprover(approverId);
    }

    /**
     * 查询工作流类型的待审批流程
     */
    public List<ApprovalWorkflow> getPendingByType(String workflowType) {
        return approvalWorkflowRepository.findPendingByType(workflowType);
    }

    /**
     * 查询申请人的所有审批流程
     */
    public List<ApprovalWorkflow> getApplicantWorkflows(Integer applicantId) {
        return approvalWorkflowRepository.findByApplicantId(applicantId);
    }

    /**
     * 获取审批流程详情
     */
    public ApprovalWorkflow getWorkflowDetail(Integer workflowId) {
        Optional<ApprovalWorkflow> workflow = approvalWorkflowRepository.findById(workflowId);
        if (workflow.isEmpty()) {
            throw new RuntimeException("审批流程不存在");
        }
        return workflow.get();
    }

    /**
     * 查询指定业务的最新审批流程
     */
    public ApprovalWorkflow getLatestWorkflow(Integer relatedId) {
        return approvalWorkflowRepository.findLatestByRelatedId(relatedId);
    }

    /**
     * 查询所有审批流程
     */
    public List<ApprovalWorkflow> getAllWorkflows() {
        return approvalWorkflowRepository.findAll();
    }

    /**
     * 查询指定状态的审批流程
     */
    public List<ApprovalWorkflow> getWorkflowsByState(String state) {
        return approvalWorkflowRepository.findByState(state);
    }
}