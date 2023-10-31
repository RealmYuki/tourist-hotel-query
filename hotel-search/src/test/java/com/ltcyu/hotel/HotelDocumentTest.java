package com.ltcyu.hotel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ltcyu.hotel.pojo.Hotel;
import com.ltcyu.hotel.pojo.HotelDoc;
import com.ltcyu.hotel.service.IHotelService;
import com.sun.el.stream.Stream;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.ltcyu.hotel.constants.HotelConstants.MAPPING_TEMPLATE;

/**
 * ClassName: HotelIndexText
 * Description:
 * {@code @Author} 苏羽晨
 * {@code @Create} 2023/10/18 15:52
 * {@code @Version}  1.0
 */
@SpringBootTest
public class HotelDocumentTest {
    @Resource
    private IHotelService hotelService;
    private RestHighLevelClient client;

    @Test
    public void testAddDocument() throws IOException {
        Hotel hotel = hotelService.getById(47478L);
        HotelDoc hotelDoc = new HotelDoc(hotel);
        IndexRequest request = new IndexRequest("hotel").id(hotelDoc.getId().toString());
        request.source(JSON.toJSONString(hotelDoc),XContentType.JSON);
        client.index(request,RequestOptions.DEFAULT);
    }

    @Test
    public void testGetDocumentById() throws IOException {
        GetRequest request = new GetRequest("hotel", "47478");
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        String json = response.getSourceAsString();
        HotelDoc hotelDoc = JSON.parseObject(json, HotelDoc.class);
        System.out.println(hotelDoc);
    }

    @Test
    public void testUpdateDocumentById() throws IOException {
        UpdateRequest request = new UpdateRequest("hotel", "47478");
        request.doc(
                "price","555",
                "city","武汉"
        );
        client.update(request,RequestOptions.DEFAULT);
    }

    @Test
    public void testDeleteDocumentById() throws IOException {
        DeleteRequest request = new DeleteRequest("hotel", "47478");
        client.delete(request, RequestOptions.DEFAULT);
    }

    @Test
    public void testBulkRequest() throws IOException {
        List<Hotel> hotels = hotelService.list();
        List<HotelDoc> hotelDocs = hotels.stream().map(HotelDoc::new).collect(Collectors.toList());
        BulkRequest bulkRequest = new BulkRequest();
        for (HotelDoc hotelDoc : hotelDocs) {
            bulkRequest.add(new IndexRequest("hotel").id(hotelDoc.getId().toString()).source(JSON.toJSONString(hotelDoc),XContentType.JSON));
        }
        client.bulk(bulkRequest,RequestOptions.DEFAULT);
    }

    @BeforeEach
    void setup() {
        this.client = new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://192.168.10.100:9200")
        ));
    }

    @AfterEach
    void teardown() throws IOException {
        this.client.close();
    }
}
