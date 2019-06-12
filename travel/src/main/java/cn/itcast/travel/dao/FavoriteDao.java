package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface FavoriteDao {

    /**
     * 查询该用户是否是否收藏了该路径
     * @param uid
     * @param rid
     * @return
     */
    Favorite selectFavoriteByUidAndRid(int uid, String rid);

    /**
     * 添加收藏路径
     * @param uid
     * @param rid
     */
    int insertFavorite(int uid, String rid);

    /**
     * 删除收藏路径
     * @param uid
     * @param rid
     * @return
     */
    int deleteFavorite(int uid, String rid);

    /**
     * 查询用户的收藏路径数量
     * @param uid
     * @return
     */
    int selectRouteByUid(int uid);

    /**
     * 用户收藏分页查询
     * @param uid
     * @param startRow
     * @param rows
     * @return
     */
    List<Route> selectRoutePage(int uid, Integer startRow, Integer rows);

    /**
     * 收藏排行分页查询
     * @param startRow
     * @param rows
     * @return
     */
    List<Route> selectRoutePageByRank(String rname, String startPrice, String endPrice,Integer startRow, Integer rows);


    /**
     * 查询总记录数
     * @param rname
     * @param startPrice
     * @param endPrice
     * @return
     */
    int selectTotalRows(String rname, String startPrice, String endPrice);
}
