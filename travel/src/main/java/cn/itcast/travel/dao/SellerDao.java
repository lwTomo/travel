package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

public interface SellerDao {

    /**
     * 通过sid查询seller
     * @param sid
     * @return
     */
    Seller selectSellerBySid(int sid);
}
