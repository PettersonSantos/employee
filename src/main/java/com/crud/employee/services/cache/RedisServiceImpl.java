package com.crud.employee.services.cache;

import com.crud.employee.domain.Employee;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    private JedisPool jedisPool;

    private final Gson gson = new Gson();

    @Value("${redis.sessiondata.ttl}")
    private int sessiondataTTL;

    private Jedis acquireJedisInstance() {
        return jedisPool.getResource();
    }

    private void realeaseJedisIntance(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    private final Logger logger = LogManager.getLogger(RedisServiceImpl.class);

    @Override
    public Employee storeEmployee(String employeeId, Employee employee) {

        Jedis jedis = null;

        try {
            jedis = acquireJedisInstance();

            String json = gson.toJson(employee);
            jedis.set(employeeId, json);
            jedis.expire(employeeId, sessiondataTTL);
        } catch (Exception e) {
            logger.error("Error occured while storing data to the cache ", e.getMessage());
            realeaseJedisIntance(jedis);
            throw new RuntimeException(e);
        } finally {
            realeaseJedisIntance(jedis);
        }
        return employee;
    }

    @Override
    public Employee retrieveEmployee(String employeeId) {
        Jedis jedis = null;

        try {

            jedis = acquireJedisInstance();

            String employeeJson = jedis.get(employeeId);

            if (StringUtils.hasText(employeeJson)) {
                return gson.fromJson(employeeJson, Employee.class);
            }

        } catch (Exception e) {
            logger.error("Error occured while retrieving data from the cache ", e.getMessage());
            realeaseJedisIntance(jedis);
            throw new RuntimeException(e);

        } finally {
            realeaseJedisIntance(jedis);
        }

        return null;
    }

    @Override
    public void clearEmployeeCache(String employeeId) {
        Jedis jedis = null;
        try {

            jedis = acquireJedisInstance();

            List<String> keys = jedis.lrange(employeeId, 0, -1);
            if (!CollectionUtils.isEmpty(keys)) {
                // add the list key in as well
                keys.add(employeeId);

                // delete the keys and list
                jedis.del(keys.toArray(new String[keys.size()]));
            }
        } catch (Exception e) {
            logger.error("Error occured while flushing specific data from the cache ", e.getMessage());
            realeaseJedisIntance(jedis);
            throw new RuntimeException(e);

        } finally {
            realeaseJedisIntance(jedis);
        }
    }

    @Override
    public void clearAll() {
        Jedis jedis = null;
        try {

            jedis = acquireJedisInstance();
            jedis.flushAll();

        } catch (Exception e) {
            logger.error("Error occured while flushing all data from the cache ", e.getMessage());
            realeaseJedisIntance(jedis);
            throw new RuntimeException(e);

        } finally {
            realeaseJedisIntance(jedis);
        }
    }
}
