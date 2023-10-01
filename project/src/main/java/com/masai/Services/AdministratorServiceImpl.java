package com.masai.Services;

import java.util.List;

import com.masai.Dao.AdministratorDaoImpl;
import com.masai.Dao.IAdministratorDao;
import com.masai.Entities.Administrator;
import com.masai.Entities.Instructor;
import com.masai.Entities.Student;
import com.masai.Exceptions.SomethingWentWrongException;

public class AdministratorServiceImpl implements IAdministratorService {
    
	 IAdministratorDao administratorDao = new AdministratorDaoImpl();
	@Override
	public Administrator login(String username, String password) throws SomethingWentWrongException {
		return administratorDao.login(username, password);
	}

	@Override
	public void addAdminstrator(Administrator administrator) throws SomethingWentWrongException {
		administratorDao.addAdminstrator(administrator);
		
	}

	@Override
	public List<Student> getAllStudent() throws SomethingWentWrongException {
		
		return administratorDao.getAllStudent();
	}

	@Override
	public List<Instructor> getAllInstructor() throws SomethingWentWrongException {
		// TODO Auto-generated method stub
		return administratorDao.getAllInstructor();
	}


	
}
