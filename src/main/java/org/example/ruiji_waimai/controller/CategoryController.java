package org.example.ruiji_waimai.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.read.listener.PageReadListener;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import jdk.nashorn.internal.ir.ReturnNode;
import lombok.extern.slf4j.Slf4j;
import org.example.ruiji_waimai.common.Result;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.ruiji_waimai.VO.PageResult;
import org.example.ruiji_waimai.entity.Category;
import org.example.ruiji_waimai.entity.DemoData;
import org.example.ruiji_waimai.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.lang.reflect.Executable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Result<String> createCategory(HttpServletRequest request, @RequestBody Category category) {
        log.info("createCategory: " + category);
        //inser createTime uupdateTime
        LocalDateTime createTime = LocalDateTime.now();
        category.setCreateTime(createTime);
        category.setUpdateTime(createTime);
        //insert CreateUser updateUser
        long createUser = (Long) request.getSession().getAttribute("employee");
        category.setCreateUser(createUser);
        category.setUpdateUser(createUser);
        category.setIsDeleted(0); //未删除
        categoryService.createCategory(category);
        return Result.success("success");
    }

    @GetMapping("page")
    public Result<PageResult<Category>> queryCategoryPage(@RequestParam("page") int pageNum, @RequestParam("pageSize") int pageSize) {
        Page page = new Page(pageNum, pageSize);
        List<Category> categories = categoryService.queryCategoryByOrderBySort();
        PageInfo<Category> pageInfo = new PageInfo<>(categories);
        PageResult<Category> pageResult = new PageResult<>();
        pageResult.setRecords(pageInfo.getList());
        pageResult.setTotal(pageInfo.getTotal());
        return Result.success(pageResult);
    }

    @PutMapping
    public Result<String> updateCategory(HttpServletRequest request, @RequestBody Category category) {
        // 更行updateTime updateUser
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser( (Long) request.getSession().getAttribute("employee"));
        categoryService.updateCategory(category);
        String test = "";
        return Result.success("success");
    }

    @DeleteMapping
    public Result<String> deleteCategory(@RequestParam("ids") String id) {
        return categoryService.deleteCategory(id);
    }

    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String fileSuffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        List<String> strings = new ArrayList<>();
//        try {
//            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
//        XSSFSheet sheet = workbook.getSheetAt(0);
//        int startRow = sheet.getFirstRowNum();
//        int lastRow = sheet.getLastRowNum();
//        for (int i = startRow; i < lastRow; i++) {
//            XSSFRow row = sheet.getRow(i);
//            strings.add(row.getCell(0).getStringCellValue());
//        }
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
        //easyExcel
        try {
            EasyExcel.read(file.getInputStream(), DemoData.class, new PageReadListener<DemoData>(dateList -> {
                for (DemoData data: dateList) {
                    System.out.println(data.toString());
                    strings.add(data.getString() + "|" +data.getInteger() + "|" +data.getString1());
                }
            })).sheet(0).headRowNumber(2).doRead();
        } catch (Exception e) {
            log.error(e.getMessage());
        }


        return Result.success("1");
    }
}
