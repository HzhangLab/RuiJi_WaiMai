package org.example.ruiji_waimai.service;

import org.example.ruiji_waimai.entity.Dish;

import java.util.List;

public interface DishService {
    List<Dish> queryDishByCreateTimeDesc();
}
