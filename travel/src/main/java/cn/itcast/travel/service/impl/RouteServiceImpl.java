package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {

    private RouteDao routeDao = new RouteDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();

    @Override
    public PageBean<Route> findRoutPage(String cid, String currentPage, String rname) {
        int totalRows = routeDao.selectTotalRows(cid,rname);
        PageBean<Route> page = new PageBean<Route>(totalRows,Integer.parseInt(currentPage));
        List<Route> list = routeDao.selectRoutes(cid,page.getStartRow(),page.getRows(),rname);
        page.setList(list);
        return page;
    }

    @Override
    public Route findDetailRoute(String rid) {
        Route route = routeDao.selectDetailRoute(rid);

        List<RouteImg> imgs = routeImgDao.selectDetailImgsByRid(route.getRid());
        route.setRouteImgList(imgs);

        Seller seller = sellerDao.selectSellerBySid(route.getSid());
        route.setSeller(seller);

        return route;
    }

    @Override
    public int findRouteCount(String rid) {
        return routeDao.selectRouteCount(rid);
    }

}
