package LibraryManagementSystem.Database.DAO;

import LibraryManagementSystem.Model.Book;

import java.util.List;

public interface BookDAO {
    void  addBook(Book book);
    List<Book>  listBook();
    List<Book>searchBooks(String userID);


    void borrowBook(boolean isBorrow, String userID,int bookID);



}
