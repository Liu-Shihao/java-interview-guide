# 算法题

## 字符串
- 最长无重复子串：使用双指针滑动窗口解法，利用HashSet去重。
- 字母异位词分组：排序，将排序后的字符串作为key，（异位词排序后的字符串是相同的）将原始字符串作为value，放入HashMap<String,List<String>>中。

## easy

- 两数之和
- 移动零
- 相交链表
- 反转链表
- 回文链表
- 环形链表
- 合并两个有序链表
- 二叉树的中序遍历
- 二叉树的最大深度（DFS 深度优先搜索）
- 反转二叉树
- 对称二叉树
- 二叉树的直径
- 将有序数组转化成BST二叉搜索树
- 搜索插入位置 （二分查找）
- 有效括号（栈）
- 买股票的最佳时机（贪心）
- 爬楼梯（动态规划）
- 杨辉三角（动态规划）
- 只出现一次的数字 （位运算：异或）
- 多数元素


## medium

- 字母异位词分组
- 最长连续序列
- 盛水最多的容器
- 三数之和 
- 无重复字符的最长子串
- 找到字符串中所有字母异位词
- 和为k的子数组
- 最大子数组和
- 合并区间

## hard

- 接雨水
- 滑动窗口最大值











# 总结


```java
// 基本数据类型
int age = 25;
double price = 19.99;
char grade = 'A';
boolean isAvailable = true;

// 创建数组
int[] numbers = {1, 2, 3, 4, 5};
String[] names = new String[3]; // 创建长度为3的String数组

// 二维数组
int[][] matrix = {{1, 2}, {3, 4}};

// 创建对象
String message = new String("Hello, World!");
Date today = new Date();

// 字符串转Char数组
char[] charArray = str.toCharArray();

// 直接使用Arrays工具类对Char数组排序
Arrays.sort(charArray);

//直接将排序后的Char数组转换为字符串
String key = new String(charArray);


// 使用 List.of() 创建的列表是不可变的（immutable），无法对其进行添加、删除等操作
hashMap.put(key, List.of(word));
hashMap.get(key).add(word);// 报错

// getOrDefault 方法的第二个参数是一个默认值，当 key 不存在时，getOrDefault 方法会返回这个默认值
List<String> list = hashMap.getOrDefault(key, new ArrayList<String>());
```

注意：
`map.values()` 返回的是 `Collection<Integer>` 类型，不能直接强制转换为 `ArrayList<Integer>` ，这会导致 `ClassCastException` 。正确的做法是创建一个新的ArrayList：
```java
HashMap<Integer, Integer> map = new HashMap<>();
ArrayList<Integer> list = (ArrayList<Integer>)map.values(); //报错
        
ArrayList<Integer> list = new ArrayList<>(map.values());
```







