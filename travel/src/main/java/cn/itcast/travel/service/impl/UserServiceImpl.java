package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {

    private UserDao dao = new UserDaoImpl();

    @Override
    public boolean regist(User user) {
        User dbUser = dao.findUserByUsername(user.getUsername());
        if(dbUser == null){
            String uuid = UuidUtil.getUuid();
            user.setCode(uuid);
            user.setStatus("N");
            dao.saveUser(user);

            //邮箱激活
            String content = "<a href='http://localhost/travel/user/active?code="+user.getCode()+"&username="+user.getUsername()+"'>注册激活</a>";
//            System.out.println(content);
            MailUtils.sendMail(user.getEmail(), content, "旅游网");

            return true;
        }else {
            return false;
        }
    }

    @Override
    public User findUserByUserName(String username) {
        return dao.findUserByUsername(username);
    }

    @Override
    public void modifyUserStatusByUid(int uid) {
        dao.updateUserStatusByUid(uid);
    }

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        return dao.selectUserByUsernameAndPassword(username,password);
    }
}
