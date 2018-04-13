package by.htp.library.dao.impl;

import static by.htp.library.dao.util.DBConnectionHelper.connect;
import static by.htp.library.dao.util.DBConnectionHelper.disconnect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.htp.library.bean.Book;
import by.htp.library.bean.Employee;
import by.htp.library.dao.EmployeeDao;

public class EmployeeDaoDBImpl implements EmployeeDao{
	
	private static final String SQL_SELECT_EMPLOYEES = "select * from employee";
	private static final String SQL_SEELECT_EMPLOYEE_BY_SURNAME = "select * from employee where surname = '";

	@Override
	public void create(Employee entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Employee read(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<Employee> setEmployeeList (ResultSet rs) {
		List<Employee> employees = new ArrayList<>();

		try {
			while (rs.next()) {
				Employee employee = new Employee();
				employee.setId(rs.getInt("id"));
				employee.setName(rs.getString("name"));
				employee.setSurname(rs.getString("surname"));

				employees.add(employee);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employees;
	}
	
	@Override
	public List<Employee> readAll() {
		Connection connection = connect();
		ResultSet rs = null;

		try {
			Statement st = connection.createStatement();
			rs = st.executeQuery(SQL_SELECT_EMPLOYEES); 
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		List<Employee> employees = setEmployeeList(rs);
		disconnect(connection);

		return employees;
	}

	@Override
	public void update(Employee entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Employee> getEmployeeBySurname(String employeeSurname) {
		Connection connection = connect();
		ResultSet rs = null;

		try {
			Statement st = connection.createStatement();
			rs = st.executeQuery(SQL_SEELECT_EMPLOYEE_BY_SURNAME.concat(employeeSurname).concat("'"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		List<Employee> employees = setEmployeeList(rs);
		disconnect(connection);

		return employees;
	}

}
