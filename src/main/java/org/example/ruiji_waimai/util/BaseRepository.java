package org.example.ruiji_waimai.util;

import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

@Repository
public interface BaseRepository<T> extends Mapper<T>, MySqlMapper<T>, ExampleMapper<T> {
}
