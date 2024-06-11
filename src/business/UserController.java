package business;

import dao.UserDao;
import entity.User;
import java.util.ArrayList;

public class UserController {

    private final UserDao userDao;

    public UserController() {
        this.userDao = new UserDao();
    }

    public ArrayList<User> findAll() {
        return this.userDao.findAll();
    }

    public User getById(int id) {
        if (id==0) {
            System.out.println("Id not equal to be zero!");
            return new User();
        }
        return this.userDao.getById(id);
    }

    public boolean update(User user) {
        User checkUser = this.getById(user.getId());
        if (checkUser==null || checkUser.getId()==0) {
            return false;
        }
        return this.userDao.update(user);
    }

    public boolean save(User user) {
        return this.userDao.save(user);
    }

    public boolean delete(User user) {
        return this.userDao.delete(user);
    }

}

