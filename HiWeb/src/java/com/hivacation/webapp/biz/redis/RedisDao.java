package com.hivacation.webapp.biz.redis;

public interface RedisDao {

	/**
	 * 设置缓存数据
	 * @param key
	 * @param value
	 */
	public void setData(String key,Object value);
	/**
	 * 设置缓存数据
	 * @param key
	 * @param value
	 * @param expirtTime	有过期时间(单位秒)
	 */
	public void setData(String key,Object value,Integer expirtTime);
	/**
	 * 设置缓存数据
	 * @param key
	 * @param value
	 * @param expirtTime	有过期时间(单位秒)
	 * @param dbIndex	指定缓存到的DB
	 */
	public void setData(String key,Object value,Integer expirtTime,Integer dbIndex);
	/**
	 * 指定DB来缓存数据
	 * @param key
	 * @param value
	 * @param dbIndex
	 */
	public void setDataToSpecifiedDB(String key,Object value,Integer dbIndex);
	
	/**
	 * 获取字符数据
	 * @param key
	 * @return
	 */
	public String getStringData(String key);
	/**
	 * 获取字符数据
	 * @param key
	 * @param dbIndex	指定DB
	 * @return
	 */
	public String getStringData(String key,Integer dbIndex);
	/**
	 * 获取byte数据
	 * @param key
	 * @return
	 */
	public Object getData(String key);
	/**
	 * 获取byte数据
	 * @param key
	 * @param dbIndex	指定DB
	 * @return
	 */
	public Object getData(String key,Integer dbIndex);
	/**
	 * 递增缓存某个key的值，值必须是整数
	 * @param key
	 * @param expirTime
	 */
	public void increaseInt(String key,Integer expirTime);
	/**
	 * 删除数据
	 * @param key
	 */
	public void deleteData(String key);
	/**
	 * 删除数据
	 * @param key
	 * @param dbIndex	指定DB
	 */
	public void deleteData(String key,Integer dbIndex);
	
	/**
	 * hash方式设置数据
	 * @param key
	 * @param value
	 * @param expirtTime
	 * @param dbIndex
	 */
	public void hSetData(String key,String field,Object value,Integer expirtTime,Integer dbIndex);
	/**
	 * 获取hash方式存储的数据
	 * @param key
	 * @param dbIndex
	 * @return
	 */
	public String hGetStringData(String key,String field,Integer dbIndex);
	/**
	 * 删除hash方式存储的数据
	 * @param key
	 * @param field
	 * @param dbIndex
	 */
	public void hDeleteData(String key,String field,Integer dbIndex);
}
