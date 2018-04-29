package mybatisTest;

public interface StudentDAO {
    //增加学生信息
    public void insertStudent(Student Student);

    //根据学号删除学生信息
    public void deleteStudentByNumber(String number);

    //根据学号更新学生信息
    public void updateStudentByNumber(Student Student);

    //通过学号获取学生信息
    public Student getStudentByNumber(String number);
}
