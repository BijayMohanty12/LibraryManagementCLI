package LibraryManagementSystem.Model;

import LibraryManagementSystem.Database.DAO.BookDAO;
import LibraryManagementSystem.Database.DAOImplement.BookDAOImpl;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.util.Random;

import static LibraryManagementSystem.Database.Utility.DBUtil.getConnection;

public class User extends Thread implements Serializable {
    public List<Book> bookList= new ArrayList<>();
    private final String userId;

    private final String name;
    private final String email;




    public User(String name, String email) {


        this.name = name;
        this.email = email;
        this.userId=generateUserID();



        // Define the file path, ensuring uniqueness

    }
    public User(String name, String email,String userId)
    {
        this.name=name;
        this.email=email;
        this.userId=userId;
    }



    // Load bookList from file if the file exists and is non-empty


    public String getUserId() {
        return userId;
    }

    private  String   generateUserID()
    {
        String nameValue=name.replace("\\s","").toLowerCase();
        int randomNum = new Random().nextInt(9000) + 1000;
        return nameValue+randomNum;
    }
    public String getEmail()
    {
        return email;
    }

    public List<Book> borrowedBook()
    {

        try(Connection connection = getConnection())
        {
            BookDAO bookDAO = new BookDAOImpl(connection);
           bookList= bookDAO.searchBooks(getUserId());
           return bookList;
        }catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

    }



    public synchronized void  setBorrow(Book book) {


       bookList.add(book);
       book.setBorrow(true);
       try(Connection connection = getConnection()){
           BookDAO bookDAO =new BookDAOImpl(connection);
           bookDAO.borrowBook(true,getUserId(),book.getBook_ID());


       } catch (SQLException e) {
           throw new RuntimeException(e);
       }


    }
    public String getNameValue()
    {
        return this.name;
    }

    protected   void bookHistory()
    {


        if (bookList.isEmpty()) {
            System.out.println("No books have been borrowed by  ."+name);
            return;
        }
        System.out.println("Borrowed books:");
        for (Book book : bookList) {


            System.out.println("Book Name: " + book.getBookName());
            System.out.println("Author: " + book.getAuthor());
            System.out.println(book.isBorrow() ? "Status: Borrowed" : "Status: Returned");
            //serialized object

            System.out.println("--------------------------");
        }
    }
    public  synchronized Book returnBook(Book book) {


        if (bookList.isEmpty()) {
            System.out.println("No books are borrowed yet.");
            return null; // No books to return
        }
        Iterator<Book> bookIterator= bookList.iterator();
        while(bookIterator.hasNext())
        {
            Book bookTemp = bookIterator.next();
            if (bookTemp.getBookName().equals(book.getBookName())
                    && bookTemp.getAuthor().equals(book.getAuthor())) {

                bookTemp.setBorrow(false);
                try (Connection connection = getConnection()) {
                    BookDAO bookDAO = new BookDAOImpl(connection);
                    bookDAO.borrowBook(false, null, bookTemp.getBook_ID());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                bookIterator.remove();
                return bookTemp;
            }
        }



        System.out.println("This book was not found in borrowed list.");
        return null;

    }


    @Override
    public void run() {
        bookHistory();

    }
}
