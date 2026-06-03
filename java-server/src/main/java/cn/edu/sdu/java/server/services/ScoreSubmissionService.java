package cn.edu.sdu.java.server.services;

import cn.edu.sdu.java.server.models.ScoreSubmission;
import cn.edu.sdu.java.server.models.Score;
import cn.edu.sdu.java.server.models.Teacher;
import cn.edu.sdu.java.server.models.Student;
import cn.edu.sdu.java.server.models.Course;
import cn.edu.sdu.java.server.repositorys.ScoreSubmissionRepository;
import cn.edu.sdu.java.server.repositorys.ScoreRepository;
import cn.edu.sdu.java.server.repositorys.TeacherRepository;
import cn.edu.sdu.java.server.repositorys.StudentRepository;
import cn.edu.sdu.java.server.repositorys.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * ScoreSubmissionService 成绩提交审核服务类
 * 提供成绩提交和审核的业务逻辑
 */
@Service
@Transactional
public class ScoreSubmissionService {

    @Autowired
    private ScoreSubmissionRepository scoreSubmissionRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    /**
     * 教师提交成绩
     */
    public ScoreSubmission submitScore(Integer scoreId, Integer teacherId,
                                       Integer studentId, Integer courseId,
                                       Integer submittedScore) {
        Optional<Score> score = scoreRepository.findById(scoreId);
        Optional<Teacher> teacher = teacherRepository.findById(teacherId);
        Optional<Student> student = studentRepository.findById(studentId);
        Optional<Course> course = courseRepository.findById(courseId);

        if (score.isEmpty() || teacher.isEmpty() || student.isEmpty() || course.isEmpty()) {
            throw new RuntimeException("成绩、教师、学生或课程不存在");
        }

        ScoreSubmission submission = new ScoreSubmission();
        submission.setScore(score.get());
        submission.setTeacher(teacher.get());
        submission.setStudent(student.get());
        submission.setCourse(course.get());
        submission.setSubmittedScore(submittedScore);
        submission.setSubmissionTime(LocalDateTime.now());
        submission.setState(1);  // 1-待审批

        return scoreSubmissionRepository.save(submission);
    }

    /**
     * 审批成绩
     */
    public void approveScore(Integer submissionId, Integer newState, String comment, Integer approverId) {
        Optional<ScoreSubmission> submission = scoreSubmissionRepository.findById(submissionId);
        if (submission.isEmpty()) {
            throw new RuntimeException("成绩提交记录不存在");
        }

        ScoreSubmission ss = submission.get();
        ss.setState(newState);  // 2-审批中，3-已通过，4-已拒绝，5-已发布
        ss.setApprovalComment(comment);
        ss.setApprovalTime(LocalDateTime.now());

        if (newState == 5) {  // 已发布
            ss.setPublishTime(LocalDateTime.now());
            // 更新score表的成绩
            Score score = ss.getScore();
            score.setMark(ss.getSubmittedScore());
            scoreRepository.save(score);
        }

        scoreSubmissionRepository.save(ss);
    }

    /**
     * 查询教师的成绩提交记录
     */
    public List<ScoreSubmission> getTeacherSubmissions(Integer teacherId) {
        Optional<Teacher> teacher = teacherRepository.findById(teacherId);
        if (teacher.isEmpty()) {
            throw new RuntimeException("教师不存在");
        }
        return scoreSubmissionRepository.findByTeacher(teacher.get());
    }

    /**
     * 查询教师待审批的成绩
     */
    public List<ScoreSubmission> getPendingSubmissionsByTeacher(Integer teacherId) {
        return scoreSubmissionRepository.findPendingSubmissionsByTeacher(teacherId);
    }

    /**
     * 查询所有待审批的成绩
     */
    public List<ScoreSubmission> getPendingApprovals() {
        return scoreSubmissionRepository.findPendingApprovals();
    }

    /**
     * 查询已发布的成绩
     */
    public List<ScoreSubmission> getPublishedScores() {
        return scoreSubmissionRepository.findPublishedScores();
    }

    /**
     * 查询学生的成绩提交记录
     */
    public List<ScoreSubmission> getStudentScores(Integer studentId) {
        return scoreSubmissionRepository.findByStudentId(studentId);
    }

    /**
     * 查询学生未发布的成绩
     */
    public List<ScoreSubmission> getUnpublishedScores(Integer studentId) {
        return scoreSubmissionRepository.findUnpublishedByStudent(studentId);
    }

    /**
     * 查询拒绝的成绩提交
     */
    public List<ScoreSubmission> getRejectedSubmissions() {
        return scoreSubmissionRepository.findRejectedSubmissions();
    }

    /**
     * 统计教师待审批的成绩数量
     */
    public long countPendingByTeacher(Integer teacherId) {
        return scoreSubmissionRepository.countPendingByTeacher(teacherId);
    }

    /**
     * 获取成绩提交详情
     */
    public ScoreSubmission getSubmissionDetail(Integer submissionId) {
        Optional<ScoreSubmission> submission = scoreSubmissionRepository.findById(submissionId);
        if (submission.isEmpty()) {
            throw new RuntimeException("成绩提交记录不存在");
        }
        return submission.get();
    }

    /**
     * 删除成绩提交记录
     */
    public void deleteSubmission(Integer submissionId) {
        scoreSubmissionRepository.deleteById(submissionId);
    }

    /**
     * 查询所有成绩提交记录
     */
    public List<ScoreSubmission> getAllSubmissions() {
        return scoreSubmissionRepository.findAll();
    }
}