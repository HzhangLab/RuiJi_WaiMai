package org.example.ruiji_waimai.controller.VO;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageResult<T> implements Serializable {

    private List<T> records;

    private Long total;

}
