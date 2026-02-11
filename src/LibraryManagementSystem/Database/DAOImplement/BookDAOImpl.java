package LibraryManagementSystem.Database.DAOImplement;

import LibraryManagementSystem.Database.DAO.BookDAO;
import LibraryManagementSystem.Model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements BookDAO {

    private final Connection connection;
    public BookDAOImpl(Connection connection)
    {
        this.connection=connection;
    }

    @Override
    public void addBook(Book book) {
        String query="INSERT INTO books (bookName,author) VALUES(?,?)";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,book.getBookName());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public  List<Book> listBook() {
        List<Book> bookList= new ArrayList<>();
          String query ="SELECT * FROM books";
          try{
              Statement statement = connection.createStatement();
              ResultSet resultSet=statement.executeQuery(query);
              while(resultSet.next())
              {
                  Book book= new Book(resultSet.getString("bookName"),resultSet.getString("author"),resultSet.getBoolean("is_borrow"),resultSet.getInt("book_id"));
                   bookList.add(book);
              }
              return bookList;

          } catch (SQLException e) {
              throw new RuntimeException(e);
          }


    }

    @Override
    public List<Book> searchBooks(String userID) {
        List<Book> bookList = new ArrayList<>();
        String query ="SELECT * FROM books WHERE borrow_by = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,userID);
            ResultSet resultSet =preparedStatement.executeQuery();
            while(resultSet.next())
            {
                Book book= new Book(resultSet.getString("bookName"),resultSet.getString("author"),resultSet.getBoolean("is_borrow"),resultSet.getInt("book_id"));
                bookList.add(book);
            }
            return bookList;
        }catch(SQLException e)
        {
            throw new RuntimeException(e);
        }
    }






    @Override
    public void borrowBook(boolean isBorrow, String userId,int bookID) {
          String query= "Update books SET is_borrow = ? ,borrow_by = ?  WHERE book_id = ?";
          try{
              PreparedStatement stmt = connection.prepareStatement(query);
              stmt.setBoolean(1,isBorrow);
              stmt.setString(2,  userId);
              stmt.setInt(3, bookID );
              stmt.executeUpdate();

          }catch (SQLException e)
          {
              throw new RuntimeException(e);
          }
    }
}
