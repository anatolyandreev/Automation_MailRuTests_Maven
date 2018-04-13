package by.htp.library.console;

import java.util.List;

import by.htp.library.bean.Book;
import by.htp.library.bean.Employee;
import by.htp.library.dao.BookDao;
import by.htp.library.dao.EmployeeDao;
import by.htp.library.dao.impl.BookDaoDBImpl;
import by.htp.library.dao.impl.EmployeeDaoDBImpl;

public class MainApp {

	public static void main(String[] args) {

		BookDao bDao = new BookDaoDBImpl();
		
		List<Book> books = bDao.getBookByTitle("title2");
				
		for (Book book : books) {
			System.out.println(book);
		}
		
		EmployeeDao eDao = new EmployeeDaoDBImpl();
				
//		List<Employee> employees = eDao.readAll();
		List<Employee> employees = eDao.getEmployeeBySurname("andreev");
		for (Employee employee : employees) {
			System.out.println(employee);
		}
	}

}
