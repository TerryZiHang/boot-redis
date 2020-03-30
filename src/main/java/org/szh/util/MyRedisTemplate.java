package org.szh.util;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class MyRedisTemplate {

	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;

	// private MyRedisTemplate(RedisTemplate<Object, Object> redisTempalte) {
	// this.redisTemplate = redisTempalte;
	// }

	/**
	 * 指定缓存失效时间
	 * 
	 * @param key
	 * @param timeout
	 * @param unit
	 * @return
	 * @description
	 */
	public boolean expire(String key, long timeout, TimeUnit unit) {
		try {
			if (timeout > 0) {
				return redisTemplate.expire(key, timeout, unit);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 获取指定时间单位的过期时间
	 * 
	 * @param key
	 * @param unit
	 * @return
	 * @description
	 */
	public long getExpire(String key, TimeUnit unit) {
		return redisTemplate.getExpire(key, unit);
	}

	public boolean hasKey(String key) {
		try {
			return redisTemplate.hasKey(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 可根据多个key删除缓存
	 * 
	 * @param key
	 * @description
	 */
	@SuppressWarnings("unchecked")
	public void delete(String... key) {
		if (key != null && key.length > 0) {
			if (key.length == 1) {
				redisTemplate.delete(key[0]);
			} else {
				redisTemplate.delete(CollectionUtils.arrayToList(key));
			}
		}
	}

	/**
	 * 获取普通缓存的操作
	 * 
	 * @param key
	 * @return
	 * @description
	 */
	public Object get(String key) {
		return key == null ? null : redisTemplate.opsForValue().get(key);
	}

	/**
	 * 设置普通缓存操作
	 * 
	 * @param key
	 * @param value
	 * @return
	 * @description
	 */
	public boolean set(String key, Object value) {
		try {
			redisTemplate.opsForValue().set(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 设置指定过期时间的普通缓存
	 * 
	 * @param key
	 * @param value
	 * @param timeout
	 * @param unit
	 * @return
	 * @description
	 */
	public boolean set(String key, Object value, long timeout, TimeUnit unit) {
		try {
			if (timeout > 0) {
				redisTemplate.opsForValue().set(key, value, timeout, unit);
			} else {
				set(key, value);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 递增
	 * 
	 * @param key
	 *            键
	 * @param delta
	 *            要增加几(大于0)
	 * @return
	 */
	public long add(String key, long delta) {
		if (delta < 0) {
			throw new RuntimeException("递增因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, delta);
	}

	/**
	 * 递减
	 * 
	 * @param key
	 *            键
	 * @param delta
	 *            要减少几(小于0)
	 * @return
	 */
	public long sub(String key, long delta) {
		if (delta < 0) {
			throw new RuntimeException("递减因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, -delta);
	}

	/**
	 * 获取hash值
	 * 
	 * @param key
	 *            缓存的key 不为空
	 * @param hkey
	 *            hashset的key 不为空
	 * @return
	 * @description
	 */
	public Object hget(String key, String hashKey) {
		return redisTemplate.opsForHash().get(key, hashKey);
	}

	/**
	 * 获取hashKey对应的所有键值
	 * 
	 * @param key
	 *            缓存key
	 * @return
	 * @description
	 */
	public Map<Object, Object> hmget(String key) {
		return redisTemplate.opsForHash().entries(key);
	}

	/**
	 * 设置多个HashSet值
	 * 
	 * @param key
	 * @param map
	 * @return
	 * @description
	 */
	public boolean hmset(String key, Map<Object, Object> map) {
		try {
			redisTemplate.opsForHash().putAll(key, map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * HashSet 并设置时间
	 * 
	 * @param key
	 *            键
	 * @param map
	 *            对应多个键值
	 * @param time
	 *            时间(秒)
	 * @return true成功 false失败
	 */
	public boolean hmset(String key, Map<String, Object> map, long timeout, TimeUnit unit) {
		try {
			redisTemplate.opsForHash().putAll(key, map);
			if (timeout > 0) {
				expire(key, timeout, unit);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 向hashset中添加数据
	 * 
	 * @param key
	 * @param hkey
	 * @param value
	 * @return
	 * @description
	 */
	public boolean hset(String key, String hashKey, Object value) {
		try {
			redisTemplate.opsForHash().put(key, hashKey, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 向hashset中添加数据 设置过期时间
	 * 
	 * @param key
	 * @param hkey
	 * @param value
	 * @return
	 * @description
	 */
	public boolean hset(String key, String hashKey, Object value, long timeout, TimeUnit unit) {
		try {
			redisTemplate.opsForHash().put(key, hashKey, value);
			if (timeout > 0) {
				expire(key, timeout, unit);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 删除hash表中的值
	 * 
	 * @param key
	 * @param hkeys
	 * @description
	 */
	public void hdel(String key, Object... hashKeys) {
		redisTemplate.opsForHash().delete(key, hashKeys);
	}

	/**
	 * 判断hash表中是否有该项的值
	 * 
	 * @param key
	 * @param hashKey
	 * @return
	 * @description
	 */
	public boolean hHasKey(String key, String hashKey) {
		return redisTemplate.opsForHash().hasKey(key, hashKey);
	}

	/**
	 * hash递增 如果不存在,就会创建一个 并把新增后的值返回
	 * 
	 * @param key
	 *            键
	 * @param item
	 *            项
	 * @param by
	 *            要增加几(大于0)
	 * @return
	 */
	public double hincr(String key, String hashKey, double by) {
		return redisTemplate.opsForHash().increment(key, hashKey, by);
	}

	/**
	 * hash递减
	 * 
	 * @param key
	 *            键
	 * @param item
	 *            项
	 * @param by
	 *            要减少记(小于0)
	 * @return
	 */
	public double hdecr(String key, String hashKey, double by) {
		return redisTemplate.opsForHash().increment(key, hashKey, -by);
	}

	/**
	 * 根据key获取Set中的所有值
	 * 
	 * @param key
	 *            键
	 * @return
	 */
	public Set<Object> sGet(String key) {
		try {
			return redisTemplate.opsForSet().members(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据value从一个set中查询,是否存在
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return true 存在 false不存在
	 */
	public boolean sHasKey(String key, Object value) {
		try {
			return redisTemplate.opsForSet().isMember(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 将set数据放入缓存
	 * 
	 * @param key
	 *            键
	 * @param values
	 *            值 可以是多个
	 * @return 成功个数
	 */
	public long sSet(String key, Object... values) {
		try {
			return redisTemplate.opsForSet().add(key, values);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 将set数据放入缓存 ，指定过期时间
	 * 
	 * @param key
	 *            键
	 * @param time
	 *            时间(秒)
	 * @param values
	 *            值 可以是多个
	 * @return 成功个数
	 */
	public long sSetAndTime(String key, long timeout, TimeUnit unit, Object... values) {
		try {
			Long count = redisTemplate.opsForSet().add(key, values);
			if (timeout > 0) {
				expire(key, timeout, unit);
			}
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 获取set缓存的长度
	 * 
	 * @param key
	 *            键
	 * @return
	 */
	public long sGetSetSize(String key) {
		try {
			return redisTemplate.opsForSet().size(key);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 移除值为value
	 * 
	 * @param key
	 *            键
	 * @param values
	 *            值 可以是多个
	 * @return 移除的个数
	 */
	public long setRemove(String key, Object... values) {
		try {
			Long count = redisTemplate.opsForSet().remove(key, values);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 获取list缓存的内容
	 * 
	 * @param key
	 *            键
	 * @param start
	 *            开始
	 * @param end
	 *            结束 0 到 -1代表所有值
	 * @return
	 */
	public List<Object> lGet(String key, long start, long end) {
		try {
			return redisTemplate.opsForList().range(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取list缓存的长度
	 * 
	 * @param key
	 *            键
	 * @return
	 */
	public long lGetListSize(String key) {
		try {
			return redisTemplate.opsForList().size(key);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 通过索引 获取list中的值
	 * 
	 * @param key
	 *            键
	 * @param index
	 *            索引 index>=0时， 0 表头，1 第二个元素，依次类推；index < 0时，-1，表尾，-2倒数第二个元素，依次类推
	 * @return
	 */
	public Object listByIndex(String key, long index) {
		try {
			return redisTemplate.opsForList().index(key, index);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 *  将数据放入队列缓存
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return
	 */
	public boolean lLeftPush(String key, Object value) {
		try {
			redisTemplate.opsForList().leftPush(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 将数据放入队列缓存,指定时间
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param time
	 *            时间(秒)
	 * @return
	 */
	public boolean lLeftPush(String key, Object value, long time, TimeUnit unit) {
		try {
			redisTemplate.opsForList().leftPush(key, value);
			if (time > 0) {
				expire(key, time, unit);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 将数据放入队列缓存
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return
	 */
	public boolean lLeftPushAll(String key, List<Object> value) {
		try {
			redisTemplate.opsForList().leftPushAll(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 将数据放入队列缓存
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param time
	 *            时间(秒)
	 * @return
	 */
	public boolean lLeftPushAll(String key, List<Object> value, long time, TimeUnit unit) {
		try {
			redisTemplate.opsForList().leftPushAll(key, value);
			if (time > 0) {
				expire(key, time, unit);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 从队列中取出数据（按顺序取）
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param time
	 *            时间(秒)
	 * @return
	 */
	public Object lRightGet(String key) {
		return redisTemplate.opsForList().rightPop(key);
	}

	/**
	 * 根据索引修改list中的某条数据
	 * 
	 * @param key
	 *            键
	 * @param index
	 *            索引
	 * @param value
	 *            值
	 * @return
	 */
	public boolean updateListByIndex(String key, long index, Object value) {
		try {
			redisTemplate.opsForList().set(key, index, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 移除N个值为value
	 * 
	 * @param key
	 *            键
	 * @param count
	 *            移除多少个
	 * @param value
	 *            值
	 * @return 移除的个数
	 */
	public long listRemove(String key, long count, Object value) {
		try {
			Long remove = redisTemplate.opsForList().remove(key, count, value);
			return remove;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 模糊查询获取key值
	 * 
	 * @param pattern
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Set keys(String pattern) {
		return redisTemplate.keys(pattern);
	}

	/**
	 * 使用Redis的消息队列
	 * 
	 * @param channel
	 * @param message
	 *            消息内容
	 */
	public void convertAndSend(String channel, Object message) {
		redisTemplate.convertAndSend(channel, message);
	}

	public boolean setBitMap(String key, long offset, boolean t) {
		return redisTemplate.opsForValue().setBit(key, offset, t);
	}

	public boolean getBitMap(String key, long offset) {
		return redisTemplate.opsForValue().getBit(key, offset);
	}

	// =========BoundListOperations 用法 start============

	/**
	 * 将数据添加到Redis的list中（从右边添加
	 * 
	 * @param listKey
	 * @param expireEnum
	 *            有效期的枚举类
	 * @param values
	 *            待添加的数据
	 */
	public void addToListRight(String listKey, long timeout, TimeUnit unit, Object... values) {
		// 绑定操作
		BoundListOperations<Object, Object> boundValueOperations = redisTemplate.boundListOps(listKey);
		// 插入数据
		boundValueOperations.rightPushAll(values);
		// 设置过期时间
		boundValueOperations.expire(timeout, unit);
	}

	/**
	 * 根据起始结束序号遍历Redis中的list
	 * 
	 * @param listKey
	 * @param start
	 *            起始序号
	 * @param end
	 *            结束序号
	 * @return
	 */
	public List<Object> rangeList(String listKey, long start, long end) {
		// 绑定操作
		BoundListOperations<Object, Object> boundValueOperations = redisTemplate.boundListOps(listKey);
		// 查询数据
		return boundValueOperations.range(start, end);
	}

	/**
	 * 弹出右边的值 --- 并且移除这个值
	 * 
	 * @param listKey
	 */
	public Object rifhtPop(String listKey) {
		// 绑定操作
		BoundListOperations<Object, Object> boundValueOperations = redisTemplate.boundListOps(listKey);
		return boundValueOperations.rightPop();
	}

}
