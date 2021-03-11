package com.study.jpkc.elasticsearch;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.study.jpkc.elasticsearch.entity.EsEntity;
import lombok.SneakyThrows;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @Author Harlan
 * @Date 2021/3/11
 */
@SpringBootTest
class ElasticSearchTest {

    @Qualifier("restHighLevelClient")
    @Autowired
    RestHighLevelClient restClient;

    @Test
    @SneakyThrows
    void createIndex() {
        CreateIndexRequest request = new CreateIndexRequest("test");
        CreateIndexResponse response = restClient.indices().create(request, RequestOptions.DEFAULT);
        boolean isSuccess = response.isAcknowledged();
        assertThat(isSuccess).isTrue();
    }

    @Test
    @SneakyThrows
    void existIndex() {
        GetIndexRequest request = new GetIndexRequest("test");
        boolean exists = restClient.indices().exists(request, RequestOptions.DEFAULT);
        assertThat(exists).isTrue();
    }

    @Test
    @SneakyThrows
    void deleteIndex() {
        DeleteIndexRequest request = new DeleteIndexRequest("test");
        AcknowledgedResponse response = restClient.indices().delete(request, RequestOptions.DEFAULT);
        boolean isSuccess = response.isAcknowledged();
        assertThat(isSuccess).isTrue();
    }

    @Test
    @SneakyThrows
    void createDocument() {
        List<String> strings = new ArrayList<>();
        strings.add("重庆");
        strings.add("北京");
        strings.add("上海");
        EsEntity entity = new EsEntity("StringTest", 1000, true, 999L, LocalDateTime.now(), new Date(new Date().getTime()), 888.88, strings);
        IndexRequest request = new IndexRequest("test");
        request.id("testId");
        request.source(JSON.toJSONString(entity), XContentType.JSON);
        IndexResponse response = restClient.index(request, RequestOptions.DEFAULT);
        int status = response.status().getStatus();
        assertThat(status).isEqualTo(201);
    }
    @SneakyThrows
    @Test
    void getDocument() {
        GetRequest request = new GetRequest("test",  "testId");
        GetResponse response = restClient.get(request, RequestOptions.DEFAULT);
        Map<String, Object> sourceMap = response.getSource();
        EsEntity entity = BeanUtil.fillBeanWithMap(sourceMap, new EsEntity(), true);
        assertThat(entity).isNotNull();
    }
}
