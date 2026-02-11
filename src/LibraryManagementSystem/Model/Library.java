package LibraryManagementSystem.Model;


import LibraryManagementSystem.Database.DAO.BookDAO;
import LibraryManagementSystem.Database.DAO.UserDAO;
import LibraryManagementSystem.Database.DAOImplement.BookDAOImpl;
import LibraryManagementSystem.View.LibraryConsole;
import LibraryManagementSystem.Database.DAOImplement.UserDAOImpl;

import java.sql.Connection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static LibraryManagementSystem.Database.Utility.DBUtil.getConnection;

public class Library {
   private static  Library library;

    private List<Book> bookList;



    private List<User> userList;

    private Library() {

        // Initialize lists
        bookList = new ArrayList<>();
        userList = new ArrayList<>();
        // Deserialize user list
        // Deserialize book list



    }

    public static synchronized Library getInstance()
    {

        if (library==null)
        {
            library= new Library();
        }

        return library;
    }
    public  void setBookList (List<Book> bookList)
    {
        this.bookList=bookList;

    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<User> getUserList(){
        return userList;
    }

    public Book addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }

        // Check for duplicate books
        for (Book existingBook : bookList) {
            if (existingBook.getBookName().equalsIgnoreCase(book.getBookName())
                    && existingBook.getAuthor().equalsIgnoreCase(book.getAuthor())) {
                System.out.println("This book is already in the library.");
                return existingBook;
            }
        }

        bookList.add(book);
        try(Connection connection =getConnection()) {

            BookDAO bookDAO= new BookDAOImpl(connection);
            bookDAO.addBook(book);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return book;
    }
    public void addUser(String name, String email)
    {

            if (name.isEmpty() && email.isEmpty()) {
                throw new IllegalArgumentException("Please Enter  name and email");
            }


            for (User user1 : userList) {
                if (name.equalsIgnoreCase(user1.getNameValue())
                        && email.equalsIgnoreCase(user1.getEmail())) {
                    System.out.println("Welcome " + user1.getNameValue() + " how can we help you");

                    LibraryConsole.userInput(library,user1);
                    user1.start();
                    return;

                }
            }
            User user = new User(name, email);
            userList.add(user);
            try(Connection connection =getConnection()) {
            UserDAO userDao= new UserDAOImpl(connection);
            userDao.addUser(user);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Thank you for registration " + name);

        LibraryConsole.userInput(library,user);
        user.start();

    }

    public void listBook() {
        if (bookList.isEmpty()) {
            System.out.println("No books available in the library.");
            return;
        }

        System.out.println("Books available in the library:");
        for (Book book : bookList) {
            String borrowStatus = book.isBorrow() ? "(Borrowed)" : "(Available)";
            System.out.println("Book Name: " + book.getBookName() + ", Author: " + book.getAuthor() + " " + borrowStatus);
        }
    }
    public List<Book> searchBook(String bookName, String author) {
        List<Book> booksTemp = new ArrayList<>();
        try {

            if(!bookName.isEmpty()||!author.isEmpty()) {
                boolean isNameMatch;
                boolean isAuthorMatch;
                boolean flag = true;
                for (Book book : bookList) {
                    isNameMatch =  book.getBookName().equalsIgnoreCase(bookName);
                    isAuthorMatch =  book.getAuthor().equalsIgnoreCase(author);

                    if (isNameMatch || isAuthorMatch) {
                        booksTemp.add(book);

                        flag=false;

                    }


                }
                if (flag) {
                    System.out.println("This book \"" + bookName + "\" by " + author + " are not available in library");
                }

            }
            else {
                System.out.println("!!!Waring!!!!");

                throw new IllegalArgumentException("At Least   Book Name or author Name needed");
            }
        }catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }




        return booksTemp;
    }



}
