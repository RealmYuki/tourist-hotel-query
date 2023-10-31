package com.ltcyu.hotel.mq;

import com.ltcyu.hotel.constants.MqConstants;
import com.ltcyu.hotel.service.IHotelService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * ClassName: HotelListener
 * Description:
 * {@code @Author} 苏羽晨
 * {@code @Create} 2023/10/20 15:51
 * {@code @Version}  1.0
 */
@Component
public class HotelListener {

    @Resource
    private IHotelService hotelService;

    @RabbitListener(queues = MqConstants.HOTEL_INSERT_QUEUE)
    public void listenHotelInsertOrUpdate(Long id){
        hotelService.insertById(id);
    }

    @RabbitListener(queues = MqConstants.HOTEL_DELETE_QUEUE)
    public void listenHotelDelete(Long id){
        hotelService.deleteById(id);
    }
}
