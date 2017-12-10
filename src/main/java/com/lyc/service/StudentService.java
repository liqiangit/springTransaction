package com.lyc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class StudentService {

    @Autowired
    private TeacherService tService;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    //事物传递性

    /**
     * 注解@Transactional(propagation = Propagation.REQUIRED)
     * 事物的传递性
     * 方法被调用时自动开启事务，在事务范围内使用则使用同一个事务，否则开启新事务。
     * 若两个事物都不抛出异常，则两个事物都执行成功,有一个事物抛异常，则两个事物都回滚
     * 说明 REQUIRED的事物调用属于同一事物了
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void testRequired(){
        String sql = "insert into student(name) values('st0')";
        jdbcTemplate.execute(sql);
//		try{
//			int a = Integer.parseInt("s");
//		}catch(Exception e){
//            System.out.println("抛出异常事物回滚");
//            throw new RuntimeException();
//		}
		tService.addTeacher();
    }

    /**
     * r若父事物使用required子事物使用
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void testRequired2(){
        String sql = "insert into student(name) values('st0')";
        jdbcTemplate.execute(sql);
        tService.addTeacher6();// @Transactional(propagation = Propagation.SUPPORTS),且抛异常
    }

    /**
     * 测试@Transactional(propagation = Propagation.REQUIRES_NEW)
     *
     * 如果在父事物中抛出异常，父事物数据不能正确提交，子事物中的数据被正确提交。
     * 说明父事物和子事物是在两个独立的事务中运行，并且只要方法被调用就开启事务。
     *
     * 如果在子事物抛出异常，则两个事物都回滚。
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void testRequires_new(){
        String sql = "insert into student(name) values('st5')";
        jdbcTemplate.execute(sql);
        tService.addTeacher5();
//        throw new RuntimeException();//抛出异常
    }

    /**
     * 测试@Transactional(propagation = Propagation.SUPPORTS)
     * 如果父事物抛出异常，子事物没有抛异常，则两个事物都被提交，此时事物没有被spring事物管理
     * 而是使用了本地事务，由于本地事务默认自动提交因此数据都提交成功，但它们使用的却不是同一个事务，一旦出现异常将导致数据的不一致。
     *
     * 反之，也是一样，
     * 总结：使用SUPPORTS得情况下：父，子事物有异常抛出并不影响事物的提交
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public void testSupports(){
        String sql = "insert into student(name) values('st6')";
        jdbcTemplate.execute(sql);
        tService.addTeacher6();
//        throw new RuntimeException();
    }

    /**
     * 测试@Transactional(propagation = Propagation.NOT_SUPPORTED)
     * 父子事物抛异常，并不影响事物的提交，说明父子事物并没有开启spring事物，而是用了本地事物来管理
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void testNot_supported(){
        String sql = "insert into student(name) values('st4')";
        jdbcTemplate.execute(sql);
        tService.addTeacher4();
        throw new RuntimeException();
    }

    /**
     * @Transactional(propagation = Propagation.MANDATORY)
     * 必须在事物环境中运行，否则报错
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public void testMandatory(){
        String sql = "insert into student(name) values('st1')";
        jdbcTemplate.execute(sql);
    }

    @Transactional(propagation = Propagation.NESTED)
    public void addStudent2(){
        String sql = "insert into student(name) values('st2')";
        jdbcTemplate.execute(sql);
        tService.addTeacher2();
//		throw new RuntimeException();
    }

    @Transactional(propagation = Propagation.NEVER)
    public void addStudent3(){
        String sql = "insert into student(name) values('st3')";
        jdbcTemplate.execute(sql);
        tService.addTeacher6();
        throw new RuntimeException();
    }


    public TeacherService gettService() {
        return tService;
    }


    public void settService(TeacherService tService) {
        this.tService = tService;
    }
}
