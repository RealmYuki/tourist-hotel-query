package com.ltcyu.hotel.service.impl;

import com.ltcyu.hotel.mapper.HotelMapper;
import com.ltcyu.hotel.pojo.Hotel;
import com.ltcyu.hotel.service.IHotelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class HotelService extends ServiceImpl<HotelMapper, Hotel> implements IHotelService {
}
