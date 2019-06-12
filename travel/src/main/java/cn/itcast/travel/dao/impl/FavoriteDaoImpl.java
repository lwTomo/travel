package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FavoriteDaoImpl implements FavoriteDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Favorite selectFavoriteByUidAndRid(int uid, String rid) {
        Favorite favorite = null;
        try {
            String sql = "select * from tab_favorite where uid=? and rid=?";
            favorite = template.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), uid, rid);
        } catch (DataAccessException e) {

        }
        return favorite;
    }

    @Override
    public int insertFavorite(int uid, String rid) {
        String sql = "insert into tab_favorite(rid,date,uid) values(?,?,?)";
        return template.update(sql, rid, new Date(), uid);
    }

    @Override
    public int deleteFavorite(int uid, String rid) {
        String sql = "delete from tab_favorite where uid=? and rid=?";
        return template.update(sql, uid, rid);
    }

    @Override
    public int selectRouteByUid(int uid) {
        String sql = "select count(1) from tab_favorite where uid = ?";
        Integer count = template.queryForObject(sql,Integer.class,uid);
        return count;
    }

    @Override
    public List<Route> selectRoutePage(int uid, Integer startRow, Integer rows) {
        String sql = "select * from tab_route rou left join tab_favorite fav on rou.rid=fav.rid where uid=? limit ?,? ";
        List<Route> list = template.query(sql, new BeanPropertyRowMapper<>(Route.class), uid, startRow, rows);
        return list;
    }

    @Override
    public List<Route> selectRoutePageByRank(String rname, String startPrice, String endPrice,Integer startRow, Integer rows) {
        String sql = "select * from tab_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);
        List<Object> params = new ArrayList();
        if(rname != null && !"".equals(rname)){
            sb.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        if(startPrice != null && !"".equals(startPrice)){
            sb.append(" and price >= ? ");
            params.add(startPrice);
        }
        if(endPrice != null && !"".equals(endPrice)){
            sb.append(" and price <= ? ");
            params.add(endPrice);
        }
        sb.append(" order by count desc limit ?,? ");
        params.add(startRow);
        params.add(rows);
        sql = sb.toString();
        List<Route> list = template.query(sql, new BeanPropertyRowMapper<>(Route.class), params.toArray());
        return list;
    }

    @Override
    public int selectTotalRows(String rname, String startPrice, String endPrice) {
        String sql = "select count(1) from tab_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);
        List<Object> params = new ArrayList();
        if(rname != null && !"".equals(rname)){
            sb.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        if(startPrice != null && !"".equals(startPrice)){
            sb.append(" and price >= ? ");
            params.add(startPrice);
        }
        if(endPrice != null && !"".equals(endPrice)){
            sb.append(" and price <= ? ");
            params.add(endPrice);
        }
        sql = sb.toString();
        Integer count = template.queryForObject(sql, Integer.class, params.toArray());
        return count;
    }
}
