package com.masai.Services;

import com.masai.Dao.AdministratorDaoImpl;
import com.masai.Dao.IAdministratorDao;

public class AdministratorServiceImpl implements IAdministratorService {

	@Override
	public void login(String username, String password) {
		IAdministratorDao ad = new AdministratorDaoImpl();
		ad.login(username, password);

	}

	@Override
	public void monitorCourseProgress() {
		// TODO Auto-generated method stub

	}

	@Override
	public void trackStudentPerformance() {
		// TODO Auto-generated method stub

	}

	@Override
	public void generateCourseStatistics() {
		// TODO Auto-generated method stub

	}

	@Override
	public void logout() {
		// TODO Auto-generated method stub

	}

}
