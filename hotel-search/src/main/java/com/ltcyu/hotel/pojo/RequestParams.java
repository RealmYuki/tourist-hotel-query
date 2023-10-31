package com.ltcyu.hotel.pojo;

import lombok.Data;

/**
 * ClassName: RequestParams
 * Description:
 * {@code @Author} 苏羽晨
 * {@code @Create} 2023/10/19 14:50
 * {@code @Version}  1.0
 */
@Data
public class RequestParams {
    private String key;
    private Integer page;
    private Integer size;
    private String sortBy;
    private String city;
    private String brand;
    private String starName;
    private String minPrice;
    private String maxPrice;
    private String location;
}
