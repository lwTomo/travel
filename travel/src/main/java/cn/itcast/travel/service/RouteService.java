package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

public interface RouteService {

    /**
     * 分页查询route
     * @param cid
     * @param currentPage
     * @param rname
     * @return
     */
    PageBean<Route> findRoutPage(String cid, String currentPage, String rname);

    /**
     * 通过rid查询route详情
     * @param rid
     * @return
     */
    Route findDetailRoute(String rid);

    /**
     * 查询收藏次数
     * @param rid
     * @return
     */
    int findRouteCount(String rid);

}
