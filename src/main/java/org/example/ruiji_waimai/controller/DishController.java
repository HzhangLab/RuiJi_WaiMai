package org.example.ruiji_waimai.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.example.ruiji_waimai.VO.PageResult;
import org.example.ruiji_waimai.common.Result;
import org.example.ruiji_waimai.entity.Dish;
import org.example.ruiji_waimai.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("dish")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @GetMapping("page")
    public Result<PageResult<Dish>> queryDishPage(@RequestParam("page") int pageNum, @RequestParam("pageSize") int pageSize) {
        Page<Dish> page = new Page<>(pageNum, pageSize);
        List<Dish> dishes = dishService.queryDishByCreateTimeDesc();
        PageInfo<Dish> pageInfo = new PageInfo<>(dishes);
        PageResult<Dish> pageResult = new PageResult<>();
        pageResult.setRecords(pageInfo.getList());
        pageResult.setTotal(pageInfo.getTotal());
        return Result.success(pageResult);
    }



}
