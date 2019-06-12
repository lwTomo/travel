package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService service = new RouteServiceImpl();

    /**
     * 分页查询Route
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo info = new ResultInfo();

        String cid = request.getParameter("cid");
        String currentPage = request.getParameter("currentPage");
        String rname = request.getParameter("rname");

        rname = new String(rname.getBytes("iso-8859-1"),"utf-8");

        if(currentPage == null || "".equals(currentPage)){
            currentPage = "1";
        }
        if("null".equals(cid)){
            cid = null;
        }
        if("null".equals(rname)){
            rname = null;
        }

        PageBean<Route> page= service.findRoutPage(cid,currentPage,rname);
        info.setData(page);
        writeValue(info,response);
        return;

    }

    /**
     * 查询路线详细信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo info = new ResultInfo();
        String rid = request.getParameter("rid");
        Route route = service.findDetailRoute(rid);
        info.setData(route);
        writeValue(info,response);
        return;
    }


}
