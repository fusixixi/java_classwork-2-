package cn.edu.sdu.java.server.controllers;

import cn.edu.sdu.java.server.models.Attendance;
import cn.edu.sdu.java.server.services.AttendanceService;
import cn.edu.sdu.java.server.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * AttendanceController 考勤管理控制器
 * 处理与考勤相关的HTTP请求
 */
@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    /**
     * 记录考勤
     * POST /api/attendance/record
     */
    @PostMapping("/record")
    public ResponseEntity<?> recordAttendance(
            @RequestParam Integer studentId,
            @RequestParam Integer courseId,
            @RequestParam String date,
            @RequestParam String status,
            @RequestParam(required = false) String remark) {
        try {
            LocalDate attendanceDate = LocalDate.parse(date);
            Attendance attendance = attendanceService.recordAttendance(studentId, courseId, attendanceDate, status, remark);
            return ResponseUtil.success("考勤记录成功", attendance);
        } catch (Exception e) {
            return ResponseUtil.error("考勤记录失败：" + e.getMessage());
        }
    }

    /**
     * 更新考勤状态
     * PUT /api/attendance/{attendanceId}
     */
    @PutMapping("/{attendanceId}")
    public ResponseEntity<?> updateAttendanceStatus(
            @PathVariable Integer attendanceId,
            @RequestParam String status,
            @RequestParam(required = false) String remark) {
        try {
            attendanceService.updateAttendanceStatus(attendanceId, status, remark);
            return ResponseUtil.success("更新成功", null);
        } catch (Exception e) {
            return ResponseUtil.error("更新失败：" + e.getMessage());
        }
    }

    /**
     * 查询学生的所有考勤记录
     * GET /api/attendance/student/{studentId}
     */
    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getStudentAttendance(@PathVariable Integer studentId) {
        try {
            List<Attendance> attendances = attendanceService.getStudentAttendance(studentId);
            return ResponseUtil.success("查询成功", attendances);
        } catch (Exception e) {
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 查询学生在某课程的考勤记录
     * GET /api/attendance/course/{studentId}/{courseId}
     */
    @GetMapping("/course/{studentId}/{courseId}")
    public ResponseEntity<?> getCourseAttendance(
            @PathVariable Integer studentId,
            @PathVariable Integer courseId) {
        try {
            List<Attendance> attendances = attendanceService.getCourseAttendance(studentId, courseId);
            return ResponseUtil.success("查询成功", attendances);
        } catch (Exception e) {
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 计算学生在某课程的出勤率
     * GET /api/attendance/rate/{studentId}/{courseId}
     */
    @GetMapping("/rate/{studentId}/{courseId}")
    public ResponseEntity<?> calculateAttendanceRate(
            @PathVariable Integer studentId,
            @PathVariable Integer courseId) {
        try {
            double rate = attendanceService.calculateAttendanceRate(studentId, courseId);
            return ResponseUtil.success("查询成功", rate);
        } catch (Exception e) {
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 查询学生的缺勤记录
     * GET /api/attendance/absent/{studentId}
     */
    @GetMapping("/absent/{studentId}")
    public ResponseEntity<?> getAbsentRecords(@PathVariable Integer studentId) {
        try {
            List<Attendance> attendances = attendanceService.getAbsentRecords(studentId);
            return ResponseUtil.success("查询成功", attendances);
        } catch (Exception e) {
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 查询学生的迟到记录
     * GET /api/attendance/late/{studentId}
     */
    @GetMapping("/late/{studentId}")
    public ResponseEntity<?> getLateRecords(@PathVariable Integer studentId) {
        try {
            List<Attendance> attendances = attendanceService.getLateRecords(studentId);
            return ResponseUtil.success("查询成功", attendances);
        } catch (Exception e) {
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 查询指定日期的所有考勤记录
     * GET /api/attendance/date/{date}
     */
    @GetMapping("/date/{date}")
    public ResponseEntity<?> getAttendanceByDate(@PathVariable String date) {
        try {
            LocalDate attendanceDate = LocalDate.parse(date);
            List<Attendance> attendances = attendanceService.getAttendanceByDate(attendanceDate);
            return ResponseUtil.success("查询成功", attendances);
        } catch (Exception e) {
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 获取考勤详情
     * GET /api/attendance/{attendanceId}
     */
    @GetMapping("/{attendanceId}")
    public ResponseEntity<?> getAttendanceDetail(@PathVariable Integer attendanceId) {
        try {
            Attendance attendance = attendanceService.getAttendanceDetail(attendanceId);
            return ResponseUtil.success("查询成功", attendance);
        } catch (Exception e) {
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 删除考勤记录
     * DELETE /api/attendance/{attendanceId}
     */
    @DeleteMapping("/{attendanceId}")
    public ResponseEntity<?> deleteAttendance(@PathVariable Integer attendanceId) {
        try {
            attendanceService.deleteAttendance(attendanceId);
            return ResponseUtil.success("删除成功", null);
        } catch (Exception e) {
            return ResponseUtil.error("删除失败：" + e.getMessage());
        }
    }

    /**
     * 查询所有考勤记录
     * GET /api/attendance/all
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllAttendance() {
        try {
            List<Attendance> attendances = attendanceService.getAllAttendance();
            return ResponseUtil.success("查询成功", attendances);
        } catch (Exception e) {
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }
}