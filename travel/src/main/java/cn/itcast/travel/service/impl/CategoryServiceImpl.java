package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao dao = new CategoryDaoImpl();


    @Override
    public List<Category> findAllCategory() {
        List<Category> cates = new ArrayList<>();

        Jedis jedis = JedisUtil.getJedis();
        Set<Tuple> tupleSet = jedis.zrangeWithScores("cates", 0, -1);

        if(tupleSet.isEmpty()){
            cates = dao.selectAllCategory();
            for (Category cate : cates) {
                jedis.zadd("cates",cate.getCid(),cate.getCname());
            }
        }else{
            for (Tuple tuple : tupleSet) {
                Category cate = new Category();
                cate.setCid((int) tuple.getScore());
                cate.setCname(tuple.getElement());
                cates.add(cate);
            }
        }
        jedis.close();

        return cates;
    }

}
