package com.ltcyu.hotel.contorller;

import com.ltcyu.hotel.pojo.PageResult;
import com.ltcyu.hotel.pojo.RequestParams;
import com.ltcyu.hotel.service.IHotelService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * ClassName: HotelController
 * Description:
 * {@code @Author} 苏羽晨
 * {@code @Create} 2023/10/19 14:53
 * {@code @Version}  1.0
 */
@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Resource
    private IHotelService hotelService;

    @PostMapping("/list")
    public PageResult search(@RequestBody RequestParams params){
        return hotelService.search(params);
    }

    @PostMapping("/filters")
    public Map<String, List<String>> getFilters(@RequestBody RequestParams params){
        return hotelService.filters(params);
    }

    @GetMapping("/suggestion")
    public List<String> getSuggestions(@RequestParam("key") String prefix){
        return hotelService.getSuggestions(prefix);
    }

}
