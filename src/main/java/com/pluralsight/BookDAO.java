package com.pluralsight;

import java.sql.*;
import java.util.ArrayList;

public class BookDAO {
    private Connection jdbcConnection;
    private Pagination pagination;
    private int numberOfBooks;

    public int getNumberOfBooks() {
        String sql = "SELECT  COUNT (*) AS count FROM book";
        try {
            Statement statement = jdbcConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                numberOfBooks = resultSet.getInt("count");
            }
            resultSet.close();
            statement.close();
            } catch (SQLException e) {
                numberOfBooks=0;
                e.printStackTrace();
            }
        return numberOfBooks;
    }


    public BookDAO(Connection connection)
    {
      jdbcConnection = connection;
    }

    public Book getBook(int id) {
      Book book = null;
      String sql = "SELECT * FROM book WHERE id = ?";

      try {
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String title = resultSet.getString("title");
            String author = resultSet.getString("author");
            float price = resultSet.getFloat("price");

            book = new Book(id, title, author, price);
        }

        resultSet.close();
        statement.close();
      } catch (SQLException e) {
          e.printStackTrace();
      }

      return book;
    }

    public ArrayList<Book> listAllBooks() {
      ArrayList<Book> listBook = new ArrayList<>();

      String sql = "SELECT * FROM book";

        return getBooks(listBook, sql);
    }

    public ArrayList<Book> listBooks(int limit, int offset) {
        ArrayList<Book> listBook = new ArrayList<>();

        String sql = "SELECT * FROM book LIMIT " + limit + " OFFSET " + offset;

        return getBooks(listBook, sql);
    }

    public boolean insertBook(Book book)  {
        String sql = "INSERT INTO book (title, author, price) VALUES (?, ?, ?)";

        try {
	        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
	        statement.setString(1, book.getTitle());
	        statement.setString(2, book.getAuthor());
	        statement.setFloat(3, book.getPrice());

	        boolean rowInserted = statement.executeUpdate() > 0;
	        statement.close();
	        return rowInserted;
        } catch (SQLException e) {
        		e.printStackTrace();
        }

        return false;
    }

    public boolean updateBook(Book book){
        String sql = "UPDATE book SET title = ?, author = ?, price = ? WHERE id = ?";
        try {
            PreparedStatement statement = jdbcConnection.prepareStatement(sql);
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setFloat(3, book.getPrice());
            statement.setInt(4, book.getId());
            boolean rowUpdated = statement.executeUpdate() >0;
            statement.close();
            return  rowUpdated;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

    public boolean deleteBook(int id){
        String sql = "DELETE FROM book WHERE id = ?";
        try {
            PreparedStatement statement = jdbcConnection.prepareStatement(sql);
            statement.setInt(1, id);
            boolean rowDeleted = statement.executeUpdate() >0;
            statement.close();
            return  rowDeleted;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

    private ArrayList<Book> getBooks(ArrayList<Book> listBook, String sql) {
        try {
            Statement statement = jdbcConnection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                float price = resultSet.getFloat("price");

                Book book = new Book(id, title, author, price);
                listBook.add(book);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listBook;
    }
}
