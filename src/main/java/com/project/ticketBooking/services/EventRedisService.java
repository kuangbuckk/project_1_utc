package com.project.ticketBooking.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ticketBooking.models.Event;
import com.project.ticketBooking.responses.EventResponse;
import com.project.ticketBooking.services.interfaces.IEventRedisService;
import io.lettuce.core.GeoArgs;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EventRedisService implements IEventRedisService {
    private static final Logger logger = LoggerFactory.getLogger(EventRedisService.class.getName());
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper redisObjectMapper;

    /*
    {
        "all_events:1:10:desc": list of events object
    }
    */
    private String getKeyFrom(PageRequest pageRequest) {
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        Sort sort = pageRequest.getSort();
        String sortDirection = Objects.requireNonNull(sort.getOrderFor("id")).getDirection() == Sort.Direction.ASC ? "asc" : "desc";
        return String.format("all_events:%d:%d:%s", pageNumber, pageSize, sortDirection);
    }


    @Override
    public void clear() {
        redisTemplate.getConnectionFactory().getConnection().flushAll();
    }

    @Override
    public List<EventResponse> getAllEvents(PageRequest pageRequest) throws JsonProcessingException {
        try {
            //Tìm key trong Redis để lấy ra value
            String key = this.getKeyFrom(pageRequest);
            //Lấy ra value từ key
            String json = (String) redisTemplate.opsForValue().get(key); //json
            //Chuyển value từ dạng json sang List<EventResponse> để trả về nếu có
            List<EventResponse> eventResponses = json != null ?
                    redisObjectMapper.readValue(json, new TypeReference<List<EventResponse>>() {}) //json -> java object
                    : null;
            logger.info("Redis connection test value: " + eventResponses);
            return eventResponses;
        } catch (Exception e) {
            logger.error("Error retrieving events from Redis", e);
            return null;
        }

    }

    //save to Redis
    @Override
    public void saveAllEvents(List<EventResponse> eventResponses, PageRequest pageRequest) throws JsonProcessingException {
        try {
            //Tạo key từ pageRequest
            String key = this.getKeyFrom(pageRequest);
            //Chuyển List<EventResponse> sang dạng json để lưu vào Redis
            String json = redisObjectMapper.writeValueAsString(eventResponses); //java object -> json
            //Lưu vào Redis
            redisTemplate.opsForValue().set(key, json);
        } catch (Exception e) {
            logger.error("Error saving events to Redis", e);
        }
    }
}
