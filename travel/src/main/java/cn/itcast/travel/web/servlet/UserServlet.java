package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    private UserService service = new UserServiceImpl();

    /**
     * 注册
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo info = new ResultInfo();

        String checkcodeServer = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        request.getSession().removeAttribute("CHECKCODE_SERVER");
        String check = request.getParameter("check");
        if(check != null && check.equalsIgnoreCase(checkcodeServer)){
            Map<String, String[]> map = request.getParameterMap();
            User user = new User();
            populate(user,map);
            boolean flag = service.regist(user);

            if(flag){
                info.setFlag(true);
            }else{
                info.setFlag(false);
                info.setErrorMsg("注册失败！！");
            }
        }else {
            info.setFlag(false);
            info.setErrorMsg("验证码错误！");
        }

        writeValue(info,response);
        return;
    }

    /**
     * 登录
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo info = new ResultInfo();
        //接收请求数据
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        populate(user,map);
        //
        User dbUser = service.findUserByUsernameAndPassword(user.getUsername(),user.getPassword());
        if(dbUser == null){
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误！");
        }
        if(dbUser != null && dbUser.getStatus().equals("N")){
            info.setFlag(false);
            info.setErrorMsg("非激活，请激活！");
        }
        if(dbUser != null && dbUser.getStatus().equals("Y")){

            request.getSession().setAttribute("user",dbUser);

            info.setFlag(true);
        }
        //
        writeValue(info,response);
        return;

    }

    /**
     * 激活
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        String username = request.getParameter("username");
        User user = service.findUserByUserName(username);
        String dbCode = user.getCode();

        String msg ;
        if(code != null && code.equals(dbCode)){
            service.modifyUserStatusByUid(user.getUid());
            msg = "激活成功，请<a href='http://localhost/travel/login.html'>登录</a>";
        }else {
            msg = "激活失败，请联系管理员！";
        }
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(msg);
        return;
    }

    /**
     * 获取登录的用户名
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo info = new ResultInfo();
        User user = (User) request.getSession().getAttribute("user");
        if(user != null){
            info.setData(user.getName());
        }
        writeValue(info,response);
        return;
    }

    /**
     * 退出登录
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath()+"/login.html");
    }

}
