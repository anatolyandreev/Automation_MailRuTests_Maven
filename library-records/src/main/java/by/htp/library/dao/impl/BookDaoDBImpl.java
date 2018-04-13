package by.htp.library.dao.impl;

import static by.htp.library.dao.util.DBConnectionHelper.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

import by.htp.library.bean.Book;
import by.htp.library.dao.BookDao;

public class BookDaoDBImpl implements BookDao {

	private static final String SQL_SELECT_BOOKS = "select * from books";
	private static final String SQL_SEELECT_BOOK_BY_TITLE = "select * from books where title = '";

	public void create(Book entity) {

	}

	public Book read(int id) {
		return null;
	}
	
	private List<Book> setBookList (ResultSet rs) {
		List<Book> books = new ArrayList<>();

		try {
			while (rs.next()) {
				Book book = new Book();
				book.setId(rs.getInt("id"));
				book.setTitle(rs.getString("title"));
				book.setDescription(rs.getString("description"));
				book.setAuthor(rs.getString("author"));

				books.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return books;
	}
		


	public List<Book> readAll() {
		Connection connection = connect();
		ResultSet rs = null;

		try {
			Statement st = connection.createStatement();
			rs = st.executeQuery(SQL_SELECT_BOOKS); 
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		List<Book> books = setBookList(rs);
		disconnect(connection);

		return books;
	}

	public List<Book> getBookByTitle (String bookTitle) {
		Connection connection = connect();
		ResultSet rs = null;

		try {
			Statement st = connection.createStatement();
			rs = st.executeQuery(SQL_SEELECT_BOOK_BY_TITLE.concat(bookTitle).concat("'"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		List<Book> books = setBookList(rs);
		disconnect(connection);

		return books;
	}
	
	public void update(Book entity) {

	}

	public void delete(int id) {

	}

}
