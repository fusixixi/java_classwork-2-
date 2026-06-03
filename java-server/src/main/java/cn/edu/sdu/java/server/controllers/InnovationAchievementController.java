package cn.edu.sdu.java.server.controllers;

import cn.edu.sdu.java.server.models.InnovationAchievement;
import cn.edu.sdu.java.server.services.InnovationAchievementService;
import cn.edu.sdu.java.server.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * InnovationAchievementController 创新成果管理控制器
 * 处理与创新成果相关的HTTP请求
 */
@RestController
@RequestMapping("/api/innovationAchievement")
@CrossOrigin(origins = "*", maxAge = 3600)
public class InnovationAchievementController {

    @Autowired
    private InnovationAchievementService innovationAchievementService;

    /**
     * 学生提交创新成果
     * POST /api/innovationAchievement/submit
     */
    @PostMapping("/submit")
    public ResponseEntity<?> submitAchievement(
            @RequestParam Integer studentId,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String category,
            @RequestParam String achievementDate,
            @RequestParam(required = false) String attachmentPath) {
        try {
            LocalDate date = LocalDate.parse(achievementDate);
            InnovationAchievement achievement = innovationAchievementService.submitAchievement(
                    studentId, title, description, category, date, attachmentPath);
            return ResponseUtil.success("提交成功", achievement);
        } catch (Exception e) {
            return ResponseUtil.error("提交失败：" + e.getMessage());
        }
    }

    /**
     * 审批创新成果
     * POST /api/innovationAchievement/approve
     */
    @PostMapping("/approve")
    public ResponseEntity<?> approveAchievement(
            @RequestParam Integer achievementId,
            @RequestParam Integer state,
            @RequestParam(required = false) String comment) {
        try {
            innovationAchievementService.approveAchievement(achievementId, state, comment);
            return ResponseUtil.success("审批成功", null);
        } catch (Exception e) {
            return ResponseUtil.error("审批失败：" + e.getMessage());
        }
    }

    /**
     * 查询学生的所有创新成果
     * GET /api/innovationAchievement/student/{studentId}
     */
    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getStudentAchievements(@PathVariable Integer studentId) {
        try {
            List<InnovationAchievement> achievements = innovationAchievementService.getStudentAchievements(studentId);
            return ResponseUtil.success("查询成功", achievements);
        } catch (Exception e) {
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 查询学生已通过的创新成果
     * GET /api/innovationAchievement/approved/{studentId}
     */
    @GetMapping("/approved/{studentId}")
    public ResponseEntity<?> getApprovedAchievements(@PathVariable Integer studentId) {
        try {
            List<InnovationAchievement> achievements = innovationAchievementService.getApprovedAchievements(studentId);
            return ResponseUtil.success("查询成功", achievements);
        } catch (Exception e) {
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 查询待审批的创新成果
     * GET /api/innovationAchievement/pending
     */
    @GetMapping("/pending")
    public ResponseEntity<?> getPendingAchievements() {
        try {
            List<InnovationAchievement> achievements = innovationAchievementService.getPendingAchievements();
            return ResponseUtil.success("查询成功", achievements);
        } catch (Exception e) {
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 查询已通过的创新成果
     * GET /api/innovationAchievement/all/approved
     */
    @GetMapping("/all/approved")
    public ResponseEntity<?> getAllApprovedAchievements() {
        try {
            List<InnovationAchievement> achievements = innovationAchievementService.getAllApprovedAchievements();
            return ResponseUtil.success("查询成功", achievements);
        } catch (Exception e) {
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 按类型查询创新成果
     * GET /api/innovationAchievement/category/{category}
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<?> getAchievementsByCategory(@PathVariable String category) {
        try {
            List<InnovationAchievement> achievements = innovationAchievementService.getAchievementsByCategory(category);
            return ResponseUtil.success("查询成功", achievements);
        } catch (Exception e) {
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 按标题模糊查询
     * GET /api/innovationAchievement/search
     */
    @GetMapping("/search")
    public ResponseEntity<?> searchAchievements(@RequestParam String keyword) {
        try {
            List<InnovationAchievement> achievements = innovationAchievementService.searchAchievements(keyword);
            return ResponseUtil.success("查询成功", achievements);
        } catch (Exception e) {
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 统计学生已通过的创新成果数量
     * GET /api/innovationAchievement/count/approved/{studentId}
     */
    @GetMapping("/count/approved/{studentId}")
    public ResponseEntity<?> countApprovedByStudent(@PathVariable Integer studentId) {
        try {
            long count = innovationAchievementService.countApprovedByStudent(studentId);
            return ResponseUtil.success("查询成功", count);
        } catch (Exception e) {
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 统计指定类型的创新成果总数
     * GET /api/innovationAchievement/count/category/{category}
     */
    @GetMapping("/count/category/{category}")
    public ResponseEntity<?> countByCategory(@PathVariable String category) {
        try {
            long count = innovationAchievementService.countByCategory(category);
            return ResponseUtil.success("查询成功", count);
        } catch (Exception e) {
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 获取创新成果详情
     * GET /api/innovationAchievement/{achievementId}
     */
    @GetMapping("/{achievementId}")
    public ResponseEntity<?> getAchievementDetail(@PathVariable Integer achievementId) {
        try {
            InnovationAchievement achievement = innovationAchievementService.getAchievementDetail(achievementId);
            return ResponseUtil.success("查询成功", achievement);
        } catch (Exception e) {
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 删除创新成果
     * DELETE /api/innovationAchievement/{achievementId}
     */
    @DeleteMapping("/{achievementId}")
    public ResponseEntity<?> deleteAchievement(@PathVariable Integer achievementId) {
        try {
            innovationAchievementService.deleteAchievement(achievementId);
            return ResponseUtil.success("删除成功", null);
        } catch (Exception e) {
            return ResponseUtil.error("删除失败：" + e.getMessage());
        }
    }

    /**
     * 查询所有创新成果
     * GET /api/innovationAchievement/all
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllAchievements() {
        try {
            List<InnovationAchievement> achievements = innovationAchievementService.getAllAchievements();
            return ResponseUtil.success("查询成功", achievements);
        } catch (Exception e) {
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }
}