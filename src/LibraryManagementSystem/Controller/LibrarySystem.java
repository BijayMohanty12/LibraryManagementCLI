package LibraryManagementSystem.Controller;

import LibraryManagementSystem.Database.DAO.BookDAO;
import LibraryManagementSystem.Database.DAO.UserDAO;
import LibraryManagementSystem.Database.DAOImplement.BookDAOImpl;


import LibraryManagementSystem.Database.DAOImplement.UserDAOImpl;
import LibraryManagementSystem.Model.Library;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import static LibraryManagementSystem.Database.Utility.DBUtil.getConnection;


public class LibrarySystem {

    public static void main(String[] args)  {

        Library library= Library.getInstance();
      try(Connection connection = getConnection())
        {
            //Get value from Database
              BookDAO bookDAO =new BookDAOImpl(connection);
              UserDAO userDao= new UserDAOImpl(connection);
            library.setBookList(bookDAO.listBook());
            library.setUserList(userDao.getAllUser());




        }
        catch (SQLException e)
        {
            System.out.println("Hello world");
        }

      Scanner in= new Scanner(System.in);

        System.out.println("Enter your Name");
         String name= in.nextLine();
        System.out.println(name);


        System.out.println("Enter your EmailID");
        String email= in.nextLine();

        library.addUser(name,email);
        










    }


}
