package com.example.shiro.utils;

import com.example.shiro.common.bean.SpringContextHolder;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.*;

/**
 * Redisson 操作工具类
 * @author tanxuhui
 */
@Slf4j
public class RedissonUtil {

    private volatile static String sysCode = "SYS";

    /**`
     * 获取字符串对象
     *
     * @param name
     * @return
     */
    public static <T> RBucket<T> getRBucket(String name) {
        RBucket<T> bucket = getRedissonClient().getBucket(getRealName(name));
        return bucket;
    }

    /**
     * 获取Map对象
     *
     * @param name
     * @return
     */
    public static <K, V> RMap<K, V> getRMap(String name) {
        RMap<K, V> map = getRedissonClient().getMap(getRealName(name));
        return map;
    }

    /**
     * 获取有序集合
     *
     * @param name
     * @return
     */
    public static  <V> RSortedSet<V> getRSortedSet(String name) {
        RSortedSet<V> sortedSet = getRedissonClient().getSortedSet(getRealName(name));
        return sortedSet;
    }

    /**
     * 获取集合
     *
     * @param name
     * @return
     */
    public static  <V> RSet<V> getRSet(String name) {
        RSet<V> rSet = getRedissonClient().getSet(getRealName(name));
        return rSet;
    }

    /**
     * 获取列表
     *
     * @param name
     * @return
     */
    public static  <V> RList<V> getRList(String name) {
        RList<V> rList = getRedissonClient().getList(getRealName(name));
        return rList;
    }

    /**
     * 获取队列
     *
     * @param name
     * @return
     */
    public static  <V> RQueue<V> getRQueue(String name) {
        RQueue<V> rQueue = getRedissonClient().getQueue(getRealName(name));
        return rQueue;
    }

    /**
     * 获取阻塞队列
     *
     * @param name
     * @return
     */
    public static  <V> RBlockingQueue<V> getRBlockingQueue(String name) {
        RBlockingQueue<V> rQueue = getRedissonClient().getBlockingQueue(getRealName(name));
        return rQueue;
    }

    public static <V> RDelayedQueue<V> getRDelayedQueue(RBlockingQueue queue){
        return getRedissonClient().getDelayedQueue(queue);
    }

    /**
     * 获取双端队列
     *
     * @param name
     * @return
     */
    public static  <V> RDeque<V> getRDeque(String name) {
        RDeque<V> rDeque = getRedissonClient().getDeque(getRealName(name));
        return rDeque;
    }


    /**
     * 获取锁
     *
     * @param name
     * @return
     */
    public static RLock getRLock(String name) {
        RLock rLock = getRedissonClient().getLock(getRealName(name));
        return rLock;
    }

    /**
     * 获取读取锁
     *
     * @param name
     * @return
     */
    public static RReadWriteLock getRWLock(String name) {
        RReadWriteLock rwlock = getRedissonClient().getReadWriteLock(getRealName(name));
        return rwlock;
    }

    /**
     * 获取原子数
     *
     * @param name
     * @return
     */
    public static RAtomicLong getRAtomicLong(String name) {
        RAtomicLong rAtomicLong = getRedissonClient().getAtomicLong(getRealName(name));
        return rAtomicLong;
    }

    /**
     * 获取记数锁
     *
     * @param name
     * @return
     */
    public static RCountDownLatch getRCountDownLatch(String name) {
        RCountDownLatch rCountDownLatch = getRedissonClient().getCountDownLatch(getRealName(name));
        return rCountDownLatch;
    }

    /**
     * 获取消息的Topic
     *
     * @param name
     * @return
     */
    public static RTopic getRTopic(String name) {
        RTopic rTopic = getRedissonClient().getTopic(getRealName(name));
        return rTopic;
    }

    /**
     * 解锁
     * @param lock
     */
    public static void unlock(RLock lock){
        if(lock == null){
            return;
        }
        try {
            if(lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        } catch (Exception e) {
            log.warn("unlock catch exception",e);
        }
    }


    public static String getRealName(String name) {
        return new StringBuilder(sysCode).append("_").append(name).toString();
    }

    public static void setSysCode(String sysCode) {
        RedissonUtil.sysCode = sysCode;
    }

    public static RedissonClient getRedissonClient(){
        return RedissonClientHolder.redissonClient;
    }

    private static class RedissonClientHolder{
        private static RedissonClient redissonClient;

        static{
            try {
                redissonClient = SpringContextHolder.getBean("redissonClient");
            } catch (Exception e) {
                log.error("spring未注入redissonClien");
                Throwables.propagate(e);
            }
        }
    }

}
