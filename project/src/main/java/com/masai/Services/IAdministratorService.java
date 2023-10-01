package com.masai.Services;

import java.util.List;

import com.masai.Entities.Administrator;
import com.masai.Entities.Instructor;
import com.masai.Entities.Student;
import com.masai.Exceptions.SomethingWentWrongException;

public interface IAdministratorService {
	Administrator login(String username, String password) throws SomethingWentWrongException;
    void addAdminstrator(Administrator administrator) throws SomethingWentWrongException;
    List<Student> getAllStudent() throws SomethingWentWrongException;
    List<Instructor> getAllInstructor() throws SomethingWentWrongException;
  
}