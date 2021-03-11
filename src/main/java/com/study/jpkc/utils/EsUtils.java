package com.study.jpkc.utils;

import com.alibaba.fastjson.JSON;
import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Elasticsearch 工具类
 * @Author Harlan
 * @Date 2021/3/11
 */
@Component
@Slf4j
public class EsUtils {

    private final RestHighLevelClient restClient;

    public EsUtils(RestHighLevelClient restHighLevelClient) {
        this.restClient = restHighLevelClient;
    }

    /**
     * 创建索引
     * @param index 索引名称
     * @return 是否成功
     * @throws IOException 异常
     */
    public boolean createIndex(@NotNull String index) throws IOException {
        if (existIndex(index)) {
            log.error("Index is exist!");
            return false;
        }
        CreateIndexRequest request = new CreateIndexRequest(index);
        CreateIndexResponse response = restClient.indices().create(request, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    /**
     * 判断索引是否存在
     * @param index 索引名称
     * @return 是否存在
     * @throws IOException 异常
     */
    public boolean existIndex(@NotNull  String index) throws IOException {
        GetIndexRequest request = new GetIndexRequest(index);
        return restClient.indices().exists(request, RequestOptions.DEFAULT);
    }

    /**
     * 删除索引
     * @param index 索引名称
     * @return 是否成功
     * @throws IOException 异常
     */
    public boolean deleteIndex(@NotNull String index) throws IOException {
        if (existIndex(index)) {
            log.error("Index is not exits!");
            return false;
        }
        DeleteIndexRequest request = new DeleteIndexRequest(index);
        AcknowledgedResponse response = restClient.indices().delete(request, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    /**
     * 创建文档
     * @param data 数据
     * @param index 索引
     * @param id 文档id
     * @return 请求状态
     * @throws IOException 异常
     */
    public int createDocument(@NotNull Object data,@NotNull String index,@NotNull String id) throws IOException {
        IndexRequest request = new IndexRequest(index);
        request.id(id);
        request.timeout(TimeValue.timeValueSeconds(1));
        request.source(JSON.toJSONString(data), XContentType.JSON);
        IndexResponse response = restClient.index(request, RequestOptions.DEFAULT);
        return response.status().getStatus();
    }

    /**
     * 创建文档
     * 使用UUID
     * @param data 数据
     * @param index 索引
     * @return 请求状态
     * @throws IOException 异常
     */
    public int createDocument(@NotNull Object data, @NotNull String index) throws IOException {
        return createDocument(data, index, GenerateUtils.getUUID());
    }

    /**
     * 删除文档
     * @param index 索引
     * @param id 文档id
     * @return 请求状态
     * @throws IOException 异常
     */
    public int deleteDocument(@NotNull String index, String id) throws IOException {
        DeleteRequest request = new DeleteRequest(index, id);
        DeleteResponse response = restClient.delete(request, RequestOptions.DEFAULT);
        return response.status().getStatus();
    }

    /**
     * 更新文档
     * @param data 数据
     * @param index 索引
     * @param id 文档id
     * @return 请求状态
     * @throws IOException 异常
     */
    public int updateDocument(@NotNull Object data, String index, String id) throws IOException {
        UpdateRequest request = new UpdateRequest(index, id);
        request.timeout("1s");
        request.doc(JSON.toJSONString(data), XContentType.JSON);
        UpdateResponse response = restClient.update(request, RequestOptions.DEFAULT);
        return response.status().getStatus();
    }

    /**
     * 获取文档
     * @param index 索引
     * @param id 文档id
     * @param fields 属性
     * @return Map
     * @throws IOException 异常
     */
    public Map<String, Object> getDocument(String index, String id, String ...fields) throws IOException {
        GetRequest request = new GetRequest(index ,id);
        List<String> fieldsList = new ArrayList<>(Arrays.asList(fields));
        fieldsList.addAll(Arrays.asList(fields));
        request.fetchSourceContext(new FetchSourceContext(true, Strings.toStringArray(fieldsList), Strings.EMPTY_ARRAY));
        GetResponse response = restClient.get(request, RequestOptions.DEFAULT);
        return response.getSource();
    }

    /**
     * 判断文档是否存在
     * @param index 索引
     * @param id 文档id
     * @return 是否存在
     * @throws IOException 异常
     */
    public boolean existsDocument(String index, String id) throws IOException {
        GetRequest request = new GetRequest(index, id );
        request.fetchSourceContext(new FetchSourceContext(false));
        request.storedFields("_none_");
        return restClient.exists(request, RequestOptions.DEFAULT);
    }

    /**
     * 批量创建文档
     * @param index 索引
     * @param dataList 文档List
     * @return 是否成功
     * @throws IOException 异常
     */
    public boolean createDocuments(String index, List<?> dataList) throws IOException {
        BulkRequest request = new BulkRequest();
        BulkResponse response = null;
        for (Object data : dataList) {
            IndexRequest indexRequest = new IndexRequest(index);
            indexRequest.source(JSON.toJSONString(data), XContentType.JSON);
            request.add(indexRequest);
        }
        response = restClient.bulk(request, RequestOptions.DEFAULT);
        return response.hasFailures();
    }
}
