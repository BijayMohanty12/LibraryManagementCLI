package LibraryManagementSystem.Database.DAOImplement;

import LibraryManagementSystem.Database.DAO.UserDAO;
import LibraryManagementSystem.Model.Book;
import LibraryManagementSystem.Model.User;
import com.mysql.cj.xdevapi.Collection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    Connection connection;
    public UserDAOImpl(Connection connection) {
        this.connection=connection;
    }

    @Override
    public void addUser(User user) {
        String query="INSERT INTO users(user_id,name,email) VALUES(?,?,?)";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getUserId());
            preparedStatement.setString(2, user.getNameValue());
            preparedStatement.setString(3,user.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteUser(User user) {

    }

    @Override
    public User searchUser(int id) {
        return null;
    }



    @Override
    public List<User> getAllUser() {
        List<User> users= new ArrayList<>();
       String query="SELECT * FROM users";
       try{
           Statement statement = connection.createStatement();
           ResultSet resultSet=statement.executeQuery(query);
           while(resultSet.next())
           {
             User user= new User(resultSet.getString("name"),resultSet.getString("email"),resultSet.getString("user_id"));
             users.add(user);
           }
           return users;
       }catch (SQLException e) {
           throw new RuntimeException(e);
       }
    }
}
