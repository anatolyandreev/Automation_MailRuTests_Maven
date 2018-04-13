package by.htp.library.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import by.htp.library.bean.Employee;
import by.htp.library.dao.impl.EmployeeDaoDBImpl;
import by.htp.library.dao.util.DBConnectionHelper;

public class EmployeeDaoTest {

	private Connection connection;
	private List<Employee> expectedList;
	private EmployeeDao dao;

	@BeforeClass(alwaysRun = true)
	private void initDefaultDBConnection() {
		System.out.println("before class");
		connection = DBConnectionHelper.connect();

		System.out.println("Before class: connection opened");
	}

	@BeforeMethod(groups = "test1")
	private void getExpectedList() throws SQLException {
		System.out.println("Before method test1");

		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery("select * from employee");

		expectedList = new ArrayList<>();

		while (rs.next()) {
			expectedList.add(new Employee());
		}
		System.out.println("BeforeMethod: expected was recieved");
	}

	@BeforeMethod(groups = "test2")
	private void getExpectedEmployeeBySurname() throws SQLException {

		System.out.println("Before method test2");

		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery("select * from employee where surname = 'Andreev'");

		expectedList = new ArrayList<>();

		while (rs.next()) {
			expectedList.add(new Employee());
		}
		System.out.println("BeforeMethod: actualList was recieved");
	}

	@BeforeMethod(alwaysRun = true)
	private void initDao() {
		System.out.println("Simple before method");
		dao = new EmployeeDaoDBImpl();
	}

	@Test(groups = "test1")
	public void testRecievedCorrectEmployeeCount() {
		System.out.println("test tes1");
		List<Employee> actualList = dao.readAll();
		Assert.assertEquals(actualList.size(), expectedList.size(), "The received count of employees is not correct");
	}

	@Test(groups = "test2")
	public void testGetEmployeeBySurname() {
		System.out.println("test test2");
		List<Employee> actualList = dao.getEmployeeBySurname("Andreev");
		Assert.assertEquals(actualList.size(), expectedList.size(),
				"The received count of employees by surname is not correct");
	}

	@AfterClass(alwaysRun = true)
	private void closeDBConnection() {
		DBConnectionHelper.disconnect(connection);
		System.out.println("AfterClass: connection closed");
	}

	@AfterMethod(alwaysRun = true)
	private void cleanExpectedValues() {
		expectedList = null;
		System.out.println("After method");
	}

}
