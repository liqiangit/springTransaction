package com.lyc.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class StudentServiceTest {
    @Autowired
    private StudentService sService;
    @Test
    public void testRequired() throws Exception {
        sService.testRequired();
    }
    @Test
    public void testRequires_new(){
        sService.testRequires_new();
    }
    @Test
    public void testSupports(){
        sService.testSupports();
    }
    @Test
    public void testNot_supported(){
        sService.testNot_supported();
    }
    @Test
    public void testMandatory(){
        sService.testMandatory();
    }
    @Test
    public void testRequired2(){
        sService.testRequired2();
    }

}