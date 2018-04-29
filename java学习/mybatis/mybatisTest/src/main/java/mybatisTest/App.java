package mybatisTest;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * Hello world!
 *
 */
public class App 
{
    private static SqlSessionFactory sqlSessionFactory= MyBatisUtil.getSqlSessionFactory();

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        //插入学生信息
        insertStudent();

        //根据学号删除学生信息
        //deleteStudentByNumber("1960053011");

        //根据学号更新学生信息
//        updateStudentByNumber("1960053012");

        //根据学号查询学生信息
        //getStudentByNumber("2013053011");
    }

    public static void getStudentByNumber(String number){
        SqlSession sqlSession=sqlSessionFactory.openSession();
        try{
            StudentDAO studentDAO=sqlSession.getMapper(StudentDAO.class);
            Student student=studentDAO.getStudentByNumber(number);
            System.out.println("姓名："+student.getName());
            System.out.println("年龄："+student.getAge());
            System.out.println("性别："+student.getGender());
            System.out.println("学号："+student.getNumber());
        }finally {
            sqlSession.close();
        }
    }

    public static void insertStudent(){
        SqlSession sqlSession=sqlSessionFactory.openSession();
        try{
            StudentDAO studentDAO=sqlSession.getMapper(StudentDAO.class);
            Student student=new Student();
            student.setName("赵四");
            student.setAge(40);
            student.setGender(GenderEnum.MALE);
            student.setNumber("1960053011");
            studentDAO.insertStudent(student);
            sqlSession.commit();
        }finally {
            sqlSession.close();
        }
    }

    public static void deleteStudentByNumber(String number){
        SqlSession sqlSession=sqlSessionFactory.openSession();
        try{
            StudentDAO studentDAO=sqlSession.getMapper(StudentDAO.class);
            studentDAO.deleteStudentByNumber(number);
            sqlSession.commit();
        }finally {
            sqlSession.close();
        }
    }

    public static void updateStudentByNumber(String number){
        SqlSession sqlSession=sqlSessionFactory.openSession();
        try{
            StudentDAO studentDAO=sqlSession.getMapper(StudentDAO.class);
            Student student=new Student();
            student.setName("刘能");
            student.setAge(60);
            student.setGender(GenderEnum.MALE);
            student.setNumber(number);
            studentDAO.updateStudentByNumber(student);
            sqlSession.commit();
        }finally {
            sqlSession.close();
        }
    }
}

