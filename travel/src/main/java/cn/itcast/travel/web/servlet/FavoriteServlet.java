package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/favorite/*")
public class FavoriteServlet extends BaseServlet {

    private FavoriteService service = new FavoriteServiceImpl();
    private RouteService routeService = new RouteServiceImpl();

    /**
     * 是否已被收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo info = new ResultInfo();
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            //未登录
            info.setFlag(false);
        }else {
            String rid = request.getParameter("rid");
            boolean flag = service.findFavoriteByUidAndRid(user.getUid(),rid);
            info.setFlag(flag);
        }
        writeValue(info,response);
        return;
    }

    /**
     * 改变收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void changeFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo info = new ResultInfo();
        User user = (User) request.getSession().getAttribute("user");
        info.setData(user);
        if(user != null){
            //登录
            String rid = request.getParameter("rid");
            boolean flag = service.changeFavorite(user.getUid(),rid);
            //是否收藏
            info.setFlag(flag);
            //改变查询收藏数量，并作为登录成功标识
            int count = routeService.findRouteCount(rid);
            info.setData(count);
        }
        writeValue(info,response);
        return;
    }

    /**
     * 查询我的收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void myFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo info = new ResultInfo();

        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            info.setFlag(false);
        }else {
            String currentPage = request.getParameter("currentPage");
            if (currentPage == null || "".equals(currentPage) || "null".equals(currentPage)) {
                currentPage = "1";
            }
            PageBean<Route> page = service.findRoutPageByUid(user.getUid(), currentPage);
            info.setData(page);
            info.setFlag(true);
        }
        writeValue(info,response);
        return;
    }

    /**
     * 收藏排名
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void favoriteRank(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo info = new ResultInfo();
        String currentPage = request.getParameter("currentPage");
        String rname = request.getParameter("rname");
        String startPrice = request.getParameter("startPrice");
        String endPrice = request.getParameter("endPrice");

        rname = new String(rname.getBytes("iso-8859-1"),"utf-8");
        if (currentPage == null || "".equals(currentPage) || "null".equals(currentPage)) {
            currentPage = "1";
        }
        if (rname == null || "".equals(rname) || "null".equals(rname)) {
            rname = null;
        }
        if (startPrice == null || "".equals(startPrice) || "null".equals(startPrice)) {
            startPrice = null;
        }
        if (endPrice == null || "".equals(endPrice) || "null".equals(endPrice)) {
            endPrice = null;
        }

        PageBean<Route> page = service.findRoutePageByRank(currentPage,rname,startPrice,endPrice);
        info.setData(page);
        writeValue(info,response);
        return;
    }

}
