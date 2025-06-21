# Redis



## Redis为什么快？

## Redis基本数据类型

### 1. String字符串
字符串是Redis最基本的数据类型，可以存储文本、整数或二进制数据，最大可存储512MB。
常用命令：
```
SET key value
GET key
INCR key
DECR key
INCRBY key increment
DECRBY key decrement
APPEND key value
SETNX key value
SETEX key seconds value
```
应用场景：
- 缓存：存储频繁访问的数据如网页内容、API响应等，减轻数据库压力
- 计数器：利用INCR/DECR实现文章浏览量、点赞数等统计功能
- 分布式锁：通过SETNX(SET if Not eXists)命令实现简单的分布式锁

### 2. Hash哈希
哈希类型是字段与值的映射表，特别适合存储对象。一个哈希可以存储多达2³²-1个字段-值对
常用命令：
```
HSET key field value
HGET key field
HGETALL key
HDEL key field
```
应用场景：
- 对象存储：存储用户信息、商品信息等，方便快速查询


### 3. List列表
列表是按插入顺序排序的字符串集合，支持从两端推入或弹出元素。列表的最大长度为2³²-1个元素
常用命令：
```
LPUSH key value
RPUSH key value
LPOP key
RPOP key
LRANGE key start stop
```
应用场景：
- 消息队列：：实现简单的消息队列系统，生产者用LPUSH添加消息，消费者用RPOP获取消息
- 最新列表：存储最新发布的文章、评论等，使用LRANGE获取最近N条记录

### 4. Set集合
集合是无序且唯一的字符串集合，提供高效的添加、删除和判断元素存在性操作。集合最大可存储2³²-1个成员
常用命令：
```
SADD key member
SREM key member
SMEMBERS key
```
应用场景：
- 唯一性集合​​：存储不重复的元素如用户ID集合、IP白名单等
- 社交网络​​：存储好友关系，使用SINTER计算共同好友、SADD实现添加好友等
- 标签系统：存储用户的标签，方便快速查询用户拥有哪些标签
- 抽奖系统：存储参与抽奖的用户ID，利用SADD实现去重，使用SRANDMEMBER命令实现随机抽取功能

### 5. Sorted Set / ZSet有序集合
有序集合类似于集合，但每个成员都关联一个分数(score)，用于排序。成员是唯一的(去重)，但分数可以重复。
常用命令：
```
ZADD key score member
ZRANGE key start stop [WITHSCORES]
ZREM key member
```
应用场景：
- 排行榜：存储用户的积分、排名等，根据分数排序实现用户排名、商品热度榜等。使用ZADD添加成员，ZRANGE获取排名前N的用户
- 优先队列：将优先级作为分数，实现优先级队列
- 时间线：使用时间戳作为分数，实现按时间排序的消息流
- 范围查询：查找分数在某一区间的成员，如查找价格在100-200元的商品


## Redis持久化


## Redis缓存淘汰策略


## Redis缓存穿透、击穿、雪崩






## Redis分布式锁

