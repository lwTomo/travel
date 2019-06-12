package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());


    @Override
    public int selectTotalRows(String cid, String rname) {
        String sql = "select count(1) from tab_route where 1 = 1 ";

        StringBuilder sb = new StringBuilder(sql);
        List<Object> params = new ArrayList();

        if(cid != null && !"".equals(cid) ){
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        if(rname != null && !"".equals(rname) ){
            sb.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }

        sql = sb.toString();
        Integer count = template.queryForObject(sql, Integer.class, params.toArray());
        return count;
    }

    @Override
    public List<Route> selectRoutes(String cid, Integer startRow, Integer rows, String rname) {
        String sql = "select * from tab_route where 1=1 ";

        StringBuilder sb = new StringBuilder(sql);
        List<Object> params = new ArrayList();

        if(cid != null && !"".equals(cid) ){
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        if(rname != null && !"".equals(rname) ){
            sb.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        sb.append(" limit ?,? ");
        params.add(startRow);
        params.add(rows);

        sql = sb.toString();
        List<Route> list = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
        return list;
    }

    @Override
    public Route selectDetailRoute(String rid) {
        Route route = null;
        try {
            String sql = "select * from tab_route where rid = ?";
            route = template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
        } catch (DataAccessException e) {

        }
        return route;
    }

    @Override
    public void changeOneRouteCount(String rid, String one) {
        String sql = "update tab_route set count=count"+one+" where rid=?";
        template.update(sql, rid);
    }

    @Override
    public int selectRouteCount(String rid) {
        String sql = "select count from tab_route where rid = ?";
        Integer count = template.queryForObject(sql, Integer.class, rid);
        return count;
    }
}
