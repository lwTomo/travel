package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    /**
     * 通过用户名查询用户
     * @param username
     * @return
     */
    User findUserByUsername(String username);

    /**
     * 保存用户
     * @param user
     */
    void saveUser(User user);

    /**
     *
     * @param uid
     */
    void updateUserStatusByUid(int uid);

    /**
     *
     * @param username
     * @param password
     * @return
     */
    User selectUserByUsernameAndPassword(String username, String password);
}
