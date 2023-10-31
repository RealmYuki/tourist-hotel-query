package com.ltcyu.hotel;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.ltcyu.hotel.constants.HotelConstants.MAPPING_TEMPLATE;

/**
 * ClassName: HotelIndexText
 * Description:
 * {@code @Author} 苏羽晨
 * {@code @Create} 2023/10/18 15:52
 * {@code @Version}  1.0
 */
public class HotelIndexTest {
    private RestHighLevelClient client;

    @Test
    public void init(){
        System.out.println(client);
    }

    @Test
    public void createHotelIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("hotel");
        request.source(MAPPING_TEMPLATE, XContentType.JSON);
        client.indices().create(request, RequestOptions.DEFAULT);
    }

    @Test
    public void deleteHotelIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("hotel");
        client.indices().delete(request, RequestOptions.DEFAULT);
    }

    @Test
    public void existsHotelIndex() throws IOException {
        GetIndexRequest req = new GetIndexRequest("hotel");
        boolean exists = client.indices().exists(req, RequestOptions.DEFAULT);
        System.out.println(exists);
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
