package cn.itcast.travel.service;


import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

public interface FavoriteService {

    /**
     * 查询该用户是否收藏该路径
     * @param uid
     * @param rid
     * @return
     */
    Boolean findFavoriteByUidAndRid(int uid, String rid);

    /**
     * 添加收藏路径
     * @param uid
     * @param rid
     * @return
     */
    boolean changeFavorite(int uid, String rid);

    /**
     * 分页查询用户收藏路径
     * @param uid
     * @param currentPage
     * @return
     */
    PageBean<Route> findRoutPageByUid(int uid, String currentPage);

    /**
     * 收藏排名分页
     * @param currentPage
     * @param rname
     * @param startPrice
     * @param endPrice
     * @return
     */
    PageBean<Route> findRoutePageByRank(String currentPage, String rname, String startPrice, String endPrice);
}
