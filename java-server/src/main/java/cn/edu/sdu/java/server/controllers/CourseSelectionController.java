package cn.edu.sdu.java.server.controllers;

import cn.edu.sdu.java.server.models.CourseSelection;
import cn.edu.sdu.java.server.services.CourseSelectionService;
import cn.edu.sdu.java.server.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CourseSelectionController 选课管理控制器
 * 处理与选课相关的HTTP请求
 */
@RestController
@RequestMapping("/api/courseSelection")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseSelectionController {

    @Autowired
    private CourseSelectionService courseSelectionService;

    /**
     * 学生选课
     * POST /api/courseSelection/select
     * 参数：studentId, courseId
     */
    @PostMapping("/select")
    public ResponseEntity<?> selectCourse(@RequestParam Integer studentId, @RequestParam Integer courseId) {
        try {
            CourseSelection selection = courseSelectionService.selectCourse(studentId, courseId);
            return ResponseUtil.success("选课成功", selection);
        } catch (Exception e) {
            return ResponseUtil.error("选课失败：" + e.getMessage());
        }
    }

    /**
     * 取消选课
     * POST /api/courseSelection/cancel
     * 参数：selectionId
     */
    @PostMapping("/cancel")
    public ResponseEntity<?> cancelSelection(@RequestParam Integer selectionId) {
        try {
            courseSelectionService.cancelSelection(selectionId);
            return ResponseUtil.success("取消选课成功", null);
        } catch (Exception e) {
            return ResponseUtil.error("取消选课失败：" + e.getMessage());
        }
    }

    /**
     * 查询学生的所有选课记录
     * GET /api/courseSelection/student/{studentId}
     */
    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getStudentSelections(@PathVariable Integer studentId) {
        try {
            List<CourseSelection> selections = courseSelectionService.getStudentSelections(studentId);
            return ResponseUtil.success("查询成功", selections);
        } catch (Exception e) {
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 查询学生已选的课程（未取消）
     * GET /api/courseSelection/active/{studentId}
     */
    @GetMapping("/active/{studentId}")
    public ResponseEntity<?> getActiveSelections(@PathVariable Integer studentId) {
        try {
            List<CourseSelection> selections = courseSelectionService.getActiveSelections(studentId);
            return ResponseUtil.success("查询成功", selections);
        } catch (Exception e) {
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 查询学生已选的课程数量
     * GET /api/courseSelection/count/{studentId}
     */
    @GetMapping("/count/{studentId}")
    public ResponseEntity<?> getSelectedCourseCount(@PathVariable Integer studentId) {
        try {
            long count = courseSelectionService.getSelectedCourseCount(studentId);
            return ResponseUtil.success("查询成功", count);
        } catch (Exception e) {
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 查询课程的选课学生数量
     * GET /api/courseSelection/course/count/{courseId}
     */
    @GetMapping("/course/count/{courseId}")
    public ResponseEntity<?> getCourseStudentCount(@PathVariable Integer courseId) {
        try {
            long count = courseSelectionService.getCourseStudentCount(courseId);
            return ResponseUtil.success("查询成功", count);
        } catch (Exception e) {
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 获取选课详情
     * GET /api/courseSelection/{selectionId}
     */
    @GetMapping("/{selectionId}")
    public ResponseEntity<?> getSelectionDetail(@PathVariable Integer selectionId) {
        try {
            CourseSelection selection = courseSelectionService.getSelectionDetail(selectionId);
            return ResponseUtil.success("查询成功", selection);
        } catch (Exception e) {
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 标记选课完成
     * POST /api/courseSelection/complete
     * 参数：selectionId
     */
    @PostMapping("/complete")
    public ResponseEntity<?> completeSelection(@RequestParam Integer selectionId) {
        try {
            courseSelectionService.completeSelection(selectionId);
            return ResponseUtil.success("标记完成成功", null);
        } catch (Exception e) {
            return ResponseUtil.error("标记完成失败：" + e.getMessage());
        }
    }

    /**
     * 删除选课记录
     * DELETE /api/courseSelection/{selectionId}
     */
    @DeleteMapping("/{selectionId}")
    public ResponseEntity<?> deleteSelection(@PathVariable Integer selectionId) {
        try {
            courseSelectionService.deleteSelection(selectionId);
            return ResponseUtil.success("删除成功", null);
        } catch (Exception e) {
            return ResponseUtil.error("删除失败：" + e.getMessage());
        }
    }

    /**
     * 查询所有选课记录
     * GET /api/courseSelection/all
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllSelections() {
        try {
            List<CourseSelection> selections = courseSelectionService.getAllSelections();
            return ResponseUtil.success("查询成功", selections);
        } catch (Exception e) {
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }
}