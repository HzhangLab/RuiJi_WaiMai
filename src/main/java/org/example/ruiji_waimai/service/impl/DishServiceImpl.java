package org.example.ruiji_waimai.service.impl;

import org.example.ruiji_waimai.entity.Dish;
import org.example.ruiji_waimai.repository.DishRepository;
import org.example.ruiji_waimai.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishRepository dishRepository;

    @Override
    public List<Dish> queryDishByCreateTimeDesc() {
        Example example = new Example(Dish.class);
        example.setOrderByClause("create_time desc");
        return dishRepository.selectByExample(example);
    }
}
