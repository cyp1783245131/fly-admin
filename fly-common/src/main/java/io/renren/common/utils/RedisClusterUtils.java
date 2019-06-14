/**
 * Copyright 2018 FLY开源 http://www.mcc.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.renren.common.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Redis工具类
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-07-17 21:12
 */
@Component
public class RedisClusterUtils {

    @Autowired(required = false)
    private JedisCluster jedisCluster;

    /**  默认过期时长，单位：秒 */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24;
    /**  不设置过期时长 */
    public final static long NOT_EXPIRE = -1;

    public void set(String key, Object value, long expire){
        if(expire != NOT_EXPIRE){
            int expireTime= (int) expire;
            jedisCluster.set(key,toJson(value));
            jedisCluster.expire(key,expireTime);
        }
    }

    public void setBytes(String key, byte[] value, long expire){
        if(expire != NOT_EXPIRE){
            int expireTime= (int) expire;
            jedisCluster.set(key.getBytes(),value);
            jedisCluster.expire(key.getBytes(),expireTime);
        }
    }

    public byte[]  getBytes(String key) {
        byte[] value = jedisCluster.get(key.getBytes());
        return value;
    }

    public void expire(String key,long expire){
        if(expire != NOT_EXPIRE){
            int expireTime= (int) expire;
            jedisCluster.expire(key,expireTime);
        }
    }

    public void set(String key, Object value){
        set(key, value, DEFAULT_EXPIRE);
    }

    public <T> T get(String key, Class<T> clazz, long expire) {
        String value = jedisCluster.get(key);
        if(expire != NOT_EXPIRE){
            int expireTime= (int) expire;
            jedisCluster.expire(key,expireTime);
        }
        return value == null ? null : fromJson(value, clazz);
    }

    public <T> T get(String key, Class<T> clazz) {
        return get(key, clazz, NOT_EXPIRE);
    }

    /**
     * 实现redis keys 模糊查询
     * @author hq
     * @param pattern
     * @return
     */
    public Set<String> redisKeys(String pattern){
        Set<String> keys = new HashSet<String>();
        //获取所有连接池节点
        Map<String, JedisPool> nodes = jedisCluster.getClusterNodes();
        //遍历所有连接池，逐个进行模糊查询
        for(String k : nodes.keySet()){
            JedisPool pool = nodes.get(k);
            //获取Jedis对象，Jedis对象支持keys模糊查询
            Jedis connection = pool.getResource();
            try {
                keys.addAll(connection.keys(pattern));
            } catch(Exception e){
                e.printStackTrace();
            } finally{
                //一定要关闭连接！
                connection.close();
            }
        }
        return keys;
    }


    public String get(String key, long expire) {
        String value = jedisCluster.get(key);
        if(expire != NOT_EXPIRE){
            int expireTime= (int) expire;
            jedisCluster.expire(key,expireTime);
        }
        return value;
    }

    public String get(String key) {
        return get(key, NOT_EXPIRE);
    }

    public void delete(String key) {
        jedisCluster.del(key);
    }

    /**
     * Object转成JSON数据
     */
    private String toJson(Object object){
        if(object instanceof Integer || object instanceof Long || object instanceof Float ||
                object instanceof Double || object instanceof Boolean || object instanceof String){
            return String.valueOf(object);
        }
        return JSON.toJSONString(object);
    }

    /**
     * JSON数据，转成Object
     */
    private <T> T fromJson(String json, Class<T> clazz){
        return JSON.parseObject(json, clazz);
    }
}
