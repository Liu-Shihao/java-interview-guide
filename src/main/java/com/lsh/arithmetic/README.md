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







