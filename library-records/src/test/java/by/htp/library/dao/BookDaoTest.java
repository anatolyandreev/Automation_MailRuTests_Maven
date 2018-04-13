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
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import by.htp.library.bean.Book;
import by.htp.library.dao.impl.BookDaoDBImpl;
import by.htp.library.dao.util.DBConnectionHelper;

public class BookDaoTest {

	private Connection connection;
	private List<Book> expectedList;
	private BookDao dao;

	@BeforeClass (alwaysRun = true)
	private void initDefaultDBConnection() {
		System.out.println("before class");
		connection = DBConnectionHelper.connect();

		System.out.println("Before class: connection opened");
	}

	@BeforeMethod(groups = "test1")
	private void getExpectedList() throws SQLException {
		System.out.println("Before method two");

		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery("select * from books");

		expectedList = new ArrayList<>();

		while (rs.next()) {
			expectedList.add(new Book());
		}
		System.out.println("BeforeMethod: actualList was recieved");
	}

	@BeforeMethod(groups = "test2")
	private void getExpectedBookByTitle() throws SQLException {

		System.out.println("Before method one");

		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery("select * from books where title = 'title1'");

		expectedList = new ArrayList<>();

		while (rs.next()) {
			expectedList.add(new Book());
		}
		System.out.println("BeforeMethod: actualList was recieved");
	}

	@BeforeMethod (alwaysRun = true)
	private void initDao() {
		System.out.println("Simple before method");
		dao = new BookDaoDBImpl();
	}

	@Test(groups = "test1")
	public void testRecievedCorrectBookCount() {
		System.out.println("test two");
		List<Book> actualList = dao.readAll();
		Assert.assertEquals(actualList.size(), expectedList.size(), "The received count of books is not correct");
	}

	@Test(groups = "test2")
	public void testGetBookByTitle() {
		System.out.println("test one");
		List<Book> actualListTitle = dao.getBookByTitle("title1");
		Assert.assertEquals(actualListTitle.size(), expectedList.size(), "The received count of books by title is not correct");
	}

	@AfterClass (alwaysRun = true)
	private void closeDBConnection() {
		DBConnectionHelper.disconnect(connection);
		System.out.println("AfterClass: connection closed");
	}

	@AfterMethod (alwaysRun = true)
	private void cleanExpectedValues() {
		expectedList = null;
		System.out.println("After method");
	}

}
