package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {

    /**
     * 查询总条数
     * @param cid
     * @param rname
     * @return
     */
    int selectTotalRows(String cid,String rname);

    /**
     * 分页查询routes
     * @param cid
     * @param startRow
     * @param rows
     * @param rname
     * @return
     */
    List<Route> selectRoutes(String cid, Integer startRow, Integer rows, String rname);

    /**
     * 通过rid查询route
     * @param rid
     * @return
     */
    Route selectDetailRoute(String rid);

    /**
     * 路线收藏增减一
     * @param rid
     */
    void changeOneRouteCount(String rid,String one);

    /**
     * 通过rid查询route的收藏数量
     * @param rid
     * @return
     */
    int selectRouteCount(String rid);
}
