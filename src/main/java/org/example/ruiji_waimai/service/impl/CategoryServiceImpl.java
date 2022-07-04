package org.example.ruiji_waimai.service.impl;

import org.example.ruiji_waimai.common.Result;
import org.example.ruiji_waimai.entity.Category;
import org.example.ruiji_waimai.entity.Dish;
import org.example.ruiji_waimai.entity.Setmeal;
import org.example.ruiji_waimai.repository.CategoryRepository;
import org.example.ruiji_waimai.repository.DishRepository;
import org.example.ruiji_waimai.repository.SetmealRepository;
import org.example.ruiji_waimai.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private SetmealRepository setmealRepository;

    public int createCategory(Category category) {
        return categoryRepository.insert(category);
    }

    @Override
    public List<Category> queryCategoryByOrderBySort() {
        Example example = new Example(Category.class);
        example.setOrderByClause("sort asc");
        return categoryRepository.selectByExample(example);
    }

    @Override
    public int updateCategory(Category category) {
        return categoryRepository.updateByPrimaryKeySelective(category);
    }

    @Override
    public Result<String> deleteCategory(String id) {
        Category category = categoryRepository.selectByPrimaryKey(id);
        if (category.getIsDeleted().equals(0) && category.getType().equals(1)) {
            // 菜品: 下架分类时需要判断分类下是否有菜品
            Example example = new Example(Dish.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("categoryId", category.getId());
            List<Dish> dishes = dishRepository.selectByExample(example);
            if (dishes.size() > 0) {
                return Result.error("取消失败！分类下有菜品未下架。");
            }
        } else {
            // 套餐
            Example example = new Example(Setmeal.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("categoryId", category.getId());
            List<Setmeal> setmeals = setmealRepository.selectByExample(example);
            if (setmeals.size() > 0) {
                return Result.error("取消失败！分类下有套餐未下架。");
            }
        }
        category.setIsDeleted(category.getIsDeleted() == 0 ? 1 : 0);
        int deleteSize = categoryRepository.updateByPrimaryKeySelective(category);
        return deleteSize > 0 ? Result.success("success") : Result.error("失败请重试！");
    }
}
