package com.xiang.home.common;

import com.xiang.home.constant.CommonConstant;
import lombok.Data;

/**
 * Pagination Request
 *
 * @author <a href="https://github.com/IceProgramer">chenjiahan</a>
 * @create 2024/10/19 16:17
 */
@Data
public class PageRequest {

    /**
     * Current Page Number
     */
    private int current = 1;

    /**
     * Page Size
     */
    private int pageSize = 10;

    /**
     * Sorting Field
     */
    private String sortField;

    /**
     * Sorting Order (default: Ascending)
     */
    private String sortOrder = CommonConstant.SORT_ORDER_ASC;
}
