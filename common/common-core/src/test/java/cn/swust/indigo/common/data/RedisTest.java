package cn.swust.indigo.common.data;

import cn.swust.indigo.common.CommonTestApplication;
import cn.swust.indigo.common.core.constants.CacheConstants;
import cn.swust.indigo.common.core.constants.SecurityConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = CommonTestApplication.class)
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CacheManager cacheManager;

    @Test
    public void saveRedisTest() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "sfa");
        map.put(2, "大方");
        redisTemplate.opsForValue().set("test", map
                , SecurityConstants.CODE_TIME, TimeUnit.SECONDS);
    }

    @Test
    public void readCatchTest() {
        String accessToken = "e917535c-e58b-464f-82b6-a2e1786e660c";
        Cache tokenCache = cacheManager.getCache(CacheConstants.USER_TOKEN);
//        Object temp = tokenCache.get(accessToken);
//        Object temp2 = tokenCache.get(accessToken).get();
//        Assertions.assertNotNull(tokenCache.get(accessToken));
//        System.out.printf(temp.toString());
//        System.out.printf(temp2.toString());
    }


    @Test
    public void saveCatchTest() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "sfa");
        map.put(2, "大方");
        Cache cache = cacheManager.getCache("myTest");
        cache.put("www", map);
    }
}
