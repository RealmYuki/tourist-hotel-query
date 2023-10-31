package com.ltcyu.hotel;

import com.alibaba.fastjson.JSON;
import com.ltcyu.hotel.pojo.HotelDoc;
import com.ltcyu.hotel.service.IHotelService;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * ClassName: HotelIndexText
 * Description:
 * {@code @Author} 苏羽晨
 * {@code @Create} 2023/10/18 15:52
 * {@code @Version}  1.0
 */
@SpringBootTest
public class HotelSearchTest {
    @Resource
    private IHotelService hotelService;
    private RestHighLevelClient client;

    @Test
    public void testMatchAll() throws IOException {
        SearchRequest request = new SearchRequest("hotel");
        request.source().query(QueryBuilders.matchAllQuery());
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        handleResponse(response);
    }

    @Test
    public void testMatch() throws IOException {
        SearchRequest request = new SearchRequest("hotel");
        request.source()
                .query(QueryBuilders.matchQuery("all","如家"));
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        handleResponse(response);
    }

    @Test
    public void testBool() throws IOException {
        SearchRequest request = new SearchRequest("hotel");
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.termQuery("city","上海"));
        queryBuilder.filter(QueryBuilders.rangeQuery("price").lte(250));
        request.source()
                .query(queryBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        handleResponse(response);
    }

    @Test
    public void testSortAndPage() throws IOException {
        int page =2,size=4;
        SearchRequest request = new SearchRequest("hotel");
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.termQuery("city","上海"));
        queryBuilder.filter(QueryBuilders.rangeQuery("price").lte(250));
        request.source()
                .query(queryBuilder).sort("price", SortOrder.ASC).from((page-1)*size).size(size);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        handleResponse(response);
    }

    @Test
    public void testHighLight() throws IOException {
        SearchRequest request = new SearchRequest("hotel");
        request.source()
                .query(QueryBuilders.matchQuery("all","如家"))
                .highlighter(new HighlightBuilder().field("name").requireFieldMatch(false));
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        handleResponse(response);
    }

    private static void handleResponse(SearchResponse response) {
        SearchHits searchHits = response.getHits();
        long value = searchHits.getTotalHits().value;
        System.out.println("value = " + value);
        SearchHit[] hits = searchHits.getHits();
        ArrayList<HotelDoc> hotelDocs = new ArrayList<>();
        for (SearchHit hit : hits) {
            String json = hit.getSourceAsString();
            HotelDoc hotelDoc = JSON.parseObject(json, HotelDoc.class);
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField highlightField = highlightFields.get("name");
            if(highlightField != null){
                String name = highlightField.getFragments()[0].string();
                hotelDoc.setName(name);
            }
            hotelDocs.add(hotelDoc);
        }
        System.out.println("hotelDocs = " + hotelDocs);
    }

    @Test
    public void testAggregation() throws IOException {
        SearchRequest request = new SearchRequest("hotel");
        request.source().size(0);
        request.source().aggregation(AggregationBuilders
                .terms("brandAgg")
                .field("brand")
                .size(20));
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        Aggregations aggregations = response.getAggregations();
        Terms brandTerms = aggregations.get("brandAgg");
        for (Terms.Bucket bucket : brandTerms.getBuckets()) {
            String key = bucket.getKeyAsString();
            System.out.println("key = " + key);
        }

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
