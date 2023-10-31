package com.ltcyu.hotel.constants;

/**
 * ClassName: MqConstants
 * Description:
 * {@code @Author} 苏羽晨
 * {@code @Create} 2023/10/20 15:23
 * {@code @Version}  1.0
 */
public class MqConstants {
    //交换机名称
    public static final String HOTEL_EXCHANGE="hotel.topic";
    //监听新增和修改的队列
    public static final String HOTEL_INSERT_QUEUE="hotel.insert.queue";
    //监听删除的队列
    public static final String HOTEL_DELETE_QUEUE="hotel.delete.queue";
    //新增或修改的RoutingKey
    public static final String HOTEL_INSERT_KEY="hotel.insert";
    //删除的RoutingKey
    public static final String HOTEL_DELETE_KEY="hotel.delete";
}
