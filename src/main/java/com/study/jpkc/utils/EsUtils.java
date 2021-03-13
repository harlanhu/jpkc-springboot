package com.study.jpkc.utils;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * Elasticsearch 工具类
 * @Author Harlan
 * @Date 2021/3/11
 */
@Component
@Slf4j
public class EsUtils {

    private final RestHighLevelClient restClient;

    private final int SUCCESS_STATUS = 200;

    public EsUtils(RestHighLevelClient restHighLevelClient) {
        this.restClient = restHighLevelClient;
    }

    /**
     * 创建索引
     *
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
     *
     * @param index 索引名称
     * @return 是否存在
     * @throws IOException 异常
     */
    public boolean existIndex(@NotNull String index) throws IOException {
        GetIndexRequest request = new GetIndexRequest(index);
        return restClient.indices().exists(request, RequestOptions.DEFAULT);
    }

    /**
     * 删除索引
     *
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
     *
     * @param data  数据
     * @param index 索引
     * @param id    文档id
     * @return 请求状态
     * @throws IOException 异常
     */
    public int createDocument(@NotNull Object data, @NotNull String index, @NotNull String id) throws IOException {
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
     *
     * @param data  数据
     * @param index 索引
     * @return 请求状态
     * @throws IOException 异常
     */
    public int createDocument(@NotNull Object data, @NotNull String index) throws IOException {
        return createDocument(data, index, GenerateUtils.getUUID());
    }

    /**
     * 删除文档
     *
     * @param index 索引
     * @param id    文档id
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
     *
     * @param data  数据
     * @param index 索引
     * @param id    文档id
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
     *
     * @param index  索引
     * @param id     文档id
     * @param fields 属性
     * @return Map
     * @throws IOException 异常
     */
    public Map<String, Object> getDocument(String index, String id, String... fields) throws IOException {
        GetRequest request = new GetRequest(index, id);
        List<String> fieldsList = new ArrayList<>(Arrays.asList(fields));
        fieldsList.addAll(Arrays.asList(fields));
        request.fetchSourceContext(new FetchSourceContext(true, Strings.toStringArray(fieldsList), Strings.EMPTY_ARRAY));
        GetResponse response = restClient.get(request, RequestOptions.DEFAULT);
        return response.getSource();
    }

    /**
     * 判断文档是否存在
     *
     * @param index 索引
     * @param id    文档id
     * @return 是否存在
     * @throws IOException 异常
     */
    public boolean existsDocument(String index, String id) throws IOException {
        GetRequest request = new GetRequest(index, id);
        request.fetchSourceContext(new FetchSourceContext(false));
        request.storedFields("_none_");
        return restClient.exists(request, RequestOptions.DEFAULT);
    }

    /**
     * 批量创建文档
     *
     * @param index    索引
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

    /**
     * 更具经纬度查询范围查找
     *
     * @param index     索引
     * @param longitude 经度
     * @param latitude  纬度
     * @param distance  距离
     * @return 查询结果
     * @throws IOException 异常
     */
    public SearchResponse geoDistanceQuery(String index, Float longitude, Float latitude, String distance) throws IOException {
        if (longitude == null || latitude == null) {
            return null;
        }
        //拼接条件
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 以某点为中心，搜索指定范围
        GeoDistanceQueryBuilder distanceQueryBuilder = new GeoDistanceQueryBuilder("location");
        distanceQueryBuilder.point(latitude, longitude);
        //查询单位：km
        distanceQueryBuilder.distance(distance, DistanceUnit.KILOMETERS);
        boolQueryBuilder.filter(distanceQueryBuilder);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.source(searchSourceBuilder);
        return restClient.search(searchRequest, RequestOptions.DEFAULT);
    }

    /**
     * 获取低级客户端
     *
     * @return 低级客户端
     */
    public RestClient getLowLevelClient() {
        return restClient.getLowLevelClient();
    }

    /**
     * 高亮结果集
     *
     * @param searchResponse 搜索结果集
     * @param highlightField 高亮字段
     * @return 高亮结果
     */
    private List<Map<String, Object>> setSearchResponse(SearchResponse searchResponse, String highlightField) {
        //解析结果
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            Map<String, HighlightField> fieldMap = hit.getHighlightFields();
            HighlightField title = fieldMap.get(highlightField);
            //原来的结果
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            //解析高亮字段，将原来的字段替换为高亮
            if (ObjectUtil.isNotNull(title)) {
                Text[] texts = title.fragments();
                StringBuilder newTitle = new StringBuilder();
                for (Text text : texts) {
                    newTitle.append(text);
                }
                //替换
                sourceAsMap.put(highlightField, newTitle.toString());
            }
            resultList.add(sourceAsMap);
        }
        return resultList;
    }

    /**
     * 分页查询
     * @param index 索引
     * @param query 查询条件
     * @param current 当前页
     * @param size 大小
     * @param sortField 排序字段
     * @param highlightField 高亮字段
     * @param fields 字段
     * @return 结果
     * @throws IOException 异常
     */
    public List<Map<String, Object>> searchListData(String index, SearchSourceBuilder query, Integer current, Integer size, String sortField, String highlightField, String... fields) throws IOException {
        SearchRequest request = new SearchRequest(index);
        if (!StringUtils.isAllEmpty(fields)) {
            //只查询特定字段。如果需要查询所有字段则不设置该项
            query.fetchSource(new FetchSourceContext(true, fields, Strings.EMPTY_ARRAY));
        }
        //设置确定结果要从哪个索引开始搜索的当前页选项，默认为0
        current = current <= 0 ? 0 : current * size;
        query.from(current);
        query.size(size);
        if (StringUtils.isNotEmpty(sortField)) {
            //排序字段，如果proposal_no是text类型会默认带有keyword性质，需要拼接.keyword
            query.sort(sortField + ".keyword", SortOrder.ASC);
        }
        //高亮处理
        HighlightBuilder highlight = new HighlightBuilder();
        highlight.field(highlightField);
        //关闭多个高亮
        highlight.requireFieldMatch(false);
        highlight.preTags("<span style:'color:red'>");
        highlight.postTags("</span>");
        query.highlighter(highlight);
        //不返回数据源，只有条数之类的数据
        request.source(query);
        SearchResponse response = restClient.search(request, RequestOptions.DEFAULT);
        log.info("==" + response.getHits().getTotalHits());
        if (response.status().getStatus() == SUCCESS_STATUS) {
            //解析对象
            return setSearchResponse(response, highlightField);
        }
        return Collections.emptyList();
    }
}
