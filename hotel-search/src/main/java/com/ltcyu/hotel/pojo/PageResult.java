package com.ltcyu.hotel.pojo;

import lombok.Data;

import java.util.List;

/**
 * ClassName: PageResult
 * Description:
 * {@code @Author} 苏羽晨
 * {@code @Create} 2023/10/19 14:52
 * {@code @Version}  1.0
 */
@Data
public class PageResult {
    private Long total;
    private List<HotelDoc> hotels;

    public PageResult() {
    }

    public PageResult(Long total, List<HotelDoc> hotels) {
        this.total = total;
        this.hotels = hotels;
    }
}
