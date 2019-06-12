package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.FavoriteService;

import java.util.List;

public class FavoriteServiceImpl implements FavoriteService {

    private FavoriteDao dao = new FavoriteDaoImpl();
    private RouteDao routeDao = new RouteDaoImpl();

    @Override
    public Boolean findFavoriteByUidAndRid(int uid, String rid) {
        Favorite favorite = dao.selectFavoriteByUidAndRid(uid,rid);
        if(favorite == null){
            return false;
        }
        return true;
    }

    @Override
    public boolean changeFavorite(int uid, String rid) {
        Boolean b = findFavoriteByUidAndRid(uid, rid);
        int row = 0;
        if(b){
            //已存在，删除
            row = dao.deleteFavorite(uid,rid);
            if(row>0){
                routeDao.changeOneRouteCount(rid,"-1");
            }
            return false;
        }else {
            row = dao.insertFavorite(uid,rid);
            if(row>0){
                routeDao.changeOneRouteCount(rid,"+1");
            }
            return true;
        }
    }

    @Override
    public PageBean<Route> findRoutPageByUid(int uid, String currentPage) {
        int totalRoute = dao.selectRouteByUid(uid);
        PageBean<Route> page = new PageBean(totalRoute,Integer.parseInt(currentPage));
        List<Route> list = dao.selectRoutePage(uid,page.getStartRow(),page.getRows());
        page.setList(list);
        return page;
    }

    @Override
    public PageBean<Route> findRoutePageByRank(String currentPage, String rname, String startPrice, String endPrice) {
        int totalRoute = dao.selectTotalRows(rname,startPrice,endPrice);
        PageBean<Route> page = new PageBean(totalRoute,Integer.parseInt(currentPage));
        List<Route> list = dao.selectRoutePageByRank(rname,startPrice,endPrice,page.getStartRow(),page.getRows());
        page.setList(list);
        return page;
    }
}
