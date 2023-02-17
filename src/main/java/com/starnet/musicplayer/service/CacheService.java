package com.starnet.musicplayer.service;


public interface CacheService {

    public void cachePut(String key, Object toBeCached, long ttlMinutes);

    public void cachePut(String key, Object toBeCached);

    public <T> T cacheGet(String key, Class<T> type);
}
