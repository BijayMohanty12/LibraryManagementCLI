package LibraryManagementSystem.Database.DAO;

import LibraryManagementSystem.Model.User;

import java.util.List;

public interface UserDAO {
    void addUser(User user);
    void deleteUser(User user);
    User searchUser(int id);
    List<User> getAllUser();



}
