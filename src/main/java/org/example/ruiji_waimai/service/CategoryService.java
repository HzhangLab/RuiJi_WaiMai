package org.example.ruiji_waimai.service;

import org.example.ruiji_waimai.common.Result;
import org.example.ruiji_waimai.entity.Category;

import java.util.List;

public interface CategoryService {

    int createCategory(Category category);

    List<Category> queryCategoryByOrderBySort();

    int updateCategory(Category category);

    Result<String> deleteCategory(String id);
}
