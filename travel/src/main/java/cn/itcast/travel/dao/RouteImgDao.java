package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

public interface RouteImgDao {

    /**
     * 通过rid外键查询图片详情列表
     * @param rid
     * @return
     */
    List<RouteImg> selectDetailImgsByRid(int rid);

}
