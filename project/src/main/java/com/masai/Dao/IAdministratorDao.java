package com.masai.Dao;

import java.util.List;

import com.masai.Entities.Administrator;
import com.masai.Entities.Instructor;
import com.masai.Entities.Student;
import com.masai.Exceptions.SomethingWentWrongException;

public interface IAdministratorDao {
    Administrator login(String username, String password) throws SomethingWentWrongException;
    void addAdminstrator(Administrator administrator) throws SomethingWentWrongException;
    List<Student> getAllStudent() throws SomethingWentWrongException;
    List<Instructor> getAllInstructor() throws SomethingWentWrongException;
  
}