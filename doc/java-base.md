# 面试题
- JDK和JRE的区别是什么？
- hashCode()和equals()之间的关系
- 为什么重写equals()方法必须要重写hashCode()方法？
- final、finalize、finally的区别
- 为什么String类是不可变的？
- String、StringBuffer、StringBuilder的区别
- 重载和重写的区别
- 说一下ArrayList和LinkedList的区别
- 说一下HashMap的底层实现原理
- HashMap的put方法流程
- HashMap的扩容机制
- HashMap与Hashtable的区别
- ConcurrentHashMap的实现原理
- ThreadLocal参数如何传递？线程池如何传递？
- 线程池如何知道一个线程的任务已经执行完成？
- 

# JDK各个版本新特性

## Java 8（2014）LTS
Java 8 是长期支持版本（LTS），引入了函数式编程和现代API。

1. Lambda表达式
   - 允许以简洁的方式实现函数式接口 @FunctionalInterface
     ```
        //使用Lambda表达式实现Runnnable接口，重写run方法，创建新线程
        new Thread(() -> System.out.println("run...");).start();
     ```
2. 函数式接口 Functional Interface
  
3. Stream 流式API
    - 支持链式操作的流式数据处理
      ```
        List<Integer> numbers = Arrays.asList(1, 2, 3);
        int sum = numbers.stream()
                    .filter(n -> n % 2 == 0)
                    .mapToInt(n -> n * 2)
                    .sum();
      ```
4. 接口允许默认方法 Default Methods
   - 允许接口提供默认实现，避免破坏现有实现类：
     ```
        interface MyInterface {
            default void log() {
                System.out.println("Default method");
            }
        }
     ```
5. 方法引用
   - 简化 Lambda 表达式的语法：
     ```
        list.forEach(System.out::println); // 等同于 s -> System.out.println(s)
     ```
6. Optional类
   - 解决 `NullPointerException` 问题：
     ```
        Optional<String> opt = Optional.ofNullable(str);
        opt.ifPresent(System.out::println);
     ```
7. 新的日期时间API java.time
   - 引入 LocalDate、LocalTime、ZonedDateTime 等不可变类(线程安全)：
     ```
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
     ```

## Java 9 (2017) LTS
1. 集合工厂方法
    - 快速创建不可变集合
      ```
        List<String> list = List.of("a", "b", "c");
        Set<Integer> set = Set.of(1, 2, 3);
      ```

2. 接口私有方法
   - 允许在接口中定义私有方法
     ```
     interface MyInterface {
        private void log() {
            System.out.println("Private method");
        }
     }
     ```

## Java 10 （2018）
1. 局部变量类型判断 var
   - 简化局部变量声明：
     ```
      var list = new ArrayList<String>(); // 自动推断为 ArrayList<String>
     ```

2. G1 并行 Full GC
   - 改进G1垃圾回收器的性能

## Java 11 （2018） LTS
1. HTTP Client API (标准化)
   - 支持同步/异步 HTTP请求
     ```
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://example.com"))
                .build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
     ```

2. 字符串API增强
   - isBlank()、lines()、repeat() 等方法。

3. 单文件源码直接运行
   - 无需编译，直接执行`.java`文件
    ```
        java HelloWorld.java
    ```

## Java 12 ~ 17 （非LTS版本）

1. Switch表达式（Java 12预览，Java14正式）
   - 支持箭头语法和返回值
     ```
        String day = switch (num) {
            case 1 -> "Monday";
            case 2 -> "Tuesday";
            default -> "Unknown";
            };
     ```
2. 文本块（Java 13预览，Java 15正式）
   - 多行字符串
     ```
        String json = """
                {
                    "name": "Java",
                    "version": 17
                }
            """;
     ```
3. Record类（Java 14预览，Java 16正式）
   - 简化不可变数据类的定义
     ```
        record Person(String name, int age) {}
        Person p = new Person("Alice", 30);
     ```
4. 密封类（Sealed Class， Java 15预览，Java 17正式）
   - 限制类的继承：
     ```
        public sealed class Shape permits Circle, Square {}
     ```
5. Pattern Matching for instanceof (Java 16 正式)
   - 简化类型判断和转换
     ```
        // 新特性
            if (obj instanceof String s) {    
                System.out.println(s.length());
            }

            //旧写法
            if (obj instanceof String) {      // 1. 类型检查
                String s = (String) obj;     // 2. 显式类型转换
                System.out.println(s.length());
            }
     ```
## Java 17 LTS
长期支持版本，整合了之前的预览特性
- 密封类，模式匹配，文本块等称为正式功能。

## Java 后续版本（18 ~ 21）

- Java 18：简单web服务器（jwebserver），UTF-8默认编码
- Java 19：虚拟线程（预览）、结构化并发 
- Java 21：（2023） LTS版本，虚拟线程正式发布，模式匹配增强。

## 总结
建议根据需求选择 LTS （Long-Term Support，长期支持) 版本（如 8、11、17、21），以获得长期支持。

- Java 8：函数式编程（Lambda、Stream）、新日期 API。
- Java 9：模块化、集合工厂方法。
- Java 11：HTTP Client、var 关键字。
- Java 17：密封类、Record、文本块等现代特性。


# JVM、JRE和JDK的关系
1. JDK（Java Development Kit），Java开发工具包，包含 JRE 的所有功能，并额外提供开发工具（如编译器、调试器）。
2. JRE（Java Runtime Environment），Java运行时环境，包含JVM和Java程序所需要的核心类库(java.lang)。
3. JVM（Java Virtual Machine），Java虚拟机，负责执行Java字节码（`.class`文件），“一次编译，到处运行”（Write Once, Run Anywhere）实现跨平台运行。


```
JDK (开发工具包)
│
├── JRE (运行时环境)
│   │
│   ├── JVM (执行引擎)
│   │
│   └── 核心类库 (如 java.lang, java.util)
│
└── 开发工具 (javac, javadoc, jdb 等)
```


Java源代码(.java文件) -> 编译器 -> 字节码文件（.class文件）  -> JVM  -> JVM解释器 -> 可执行的二进制机器码  ->程序执行 

# Java基础

## 8种基本数据类型

Java 的基本数据类型是语言内置的、不可再分割的简单数据类型，它们直接存储值（而非引用），并分配在栈内存（Stack）中，效率高。Java 共有 **8 种基本数据类型**，分为 4 大类：

| **数据类型** | **关键字** | **大小（字节）** | **默认值**   | **取值范围**                          | **示例**              |
|-------------|-----------|----------------|-------------|--------------------------------------|----------------------|
| **整数型**   | `byte`    | 1              | `0`         | -128 ~ 127                           | `byte b = 100;`      |
|             | `short`   | 2              | `0`         | -32,768 ~ 32,767                     | `short s = 1000;`    |
|             | `int`     | 4              | `0`         | -2³¹ ~ 2³¹-1（约 ±21 亿）             | `int i = 100000;`    |
|             | `long`    | 8              | `0L`        | -2⁶³ ~ 2⁶³-1                         | `long l = 100L;`     |
| **浮点型**   | `float`   | 4              | `0.0f`      | 约 ±3.4e38（6-7 位有效数字）           | `float f = 3.14f;`   |
|             | `double`  | 8              | `0.0d`      | 约 ±1.7e308（15 位有效数字）           | `double d = 3.14;`   |
| **字符型**   | `char`    | 2              | `'\u0000'`  | 0 ~ 65,535（Unicode 字符）            | `char c = 'A';`      |
| **布尔型**   | `boolean` | 1（理论上）      | `false`     | `true` 或 `false`                     | `boolean flag = true;` |


### 整数类型
整数类型在计算机中以二进制形式存储，其取值范围由存储位数和编码方式决定，通常采用二进制补码表示有符号整数。对于有符号整数，最高位为符号位（0 表示正数，1 表示负数），其余位表示数值大小。
- **byte**：在内存中占 8 位（1 字节）。由于最高位为符号位，所以剩下 7 位用于表示数值。因此，其取值范围是$-2^7$ 到 $2^7 - 1$，即 -128 到 127。
- **short**：占 16 位（2 字节）。最高位为符号位，剩下 15 位用于表示数值，取值范围是$-2^{15}$ 到 $2^{15} - 1$，也就是 -32768 到 32767。
- **int**：占 32 位（4 字节）。最高位为符号位，其余 31 位表示数值，取值范围是$-2^{31}$ 到 $2^{31} - 1$，即 -2147483648 到 2147483647。
- **long**：占 64 位（8 字节）。最高位为符号位，其余 63 位表示数值，取值范围是$-2^{63}$ 到 $2^{63} - 1$，即 -9223372036854775808 到 9223372036854775807。



#### **1. `int` 和 `Integer` 的区别？**
| **对比项**   | `int`（基本类型）          | `Integer`（包装类）               |
|-------------|--------------------------|----------------------------------|
| **存储方式** | 栈内存                   | 堆内存（对象引用）                 |
| **默认值**   | `0`                      | `null`                           |
| **功能**     | 仅存储值                 | 提供方法（如 `parseInt()`）        |
| **比较**     | `==` 比较值              | `==` 比较引用，`equals()` 比较值   |

#### **2. 为什么需要包装类？**
- **泛型支持**：如 `List<Integer>`（泛型不能使用基本类型）。
- **对象操作**：如 `Integer.parseInt("123")`。
- **允许 `null`**：数据库字段可能为 `NULL`，需用包装类表示。

#### **3. 自动装箱（Autoboxing）与拆箱（Unboxing）**
Java 5 开始支持自动转换：
```java
Integer i = 100;   // 自动装箱（int → Integer）
int j = i;         // 自动拆箱（Integer → int）
```

#### **4. 大数值如何表示？**
- 超出 `long` 范围时，用 `BigInteger`（任意精度整数）或 `BigDecimal`（高精度浮点数）。

---



### **总结**
- **8 种基本类型**：`byte`、`short`、`int`、`long`、`float`、`double`、`char`、`boolean`。
- **注意范围**：如 `byte` 超范围会溢出，`float/double` 有精度问题（金融计算用 `BigDecimal`）。

## 引用数据类型
- 类 Class
- 接口 Interface 
- 数组


## 访问修饰符
Java 提供了 4 种访问修饰符，用于控制类、方法、变量的可见性范围，它们决定了其他类或包能否访问某个成员（类、方法、变量等）。

| 修饰符 |   当前类  | 同包 | 子类 |   其他包  |
| --- | --- | --- | --- | --- |
|private|  ✅   | ❌ | ❌|❌ | 
|default|  ✅   | ✅| ❌| ❌| 
|protected|  ✅   |✅ | ✅| ❌| 
|public|  ✅   | ✅| ✅|✅ | 

注意：
- **private：** 仅当前类可以访问。不能修饰类
- **default：** 是隐式的（不写任何修饰符时默认的访问权限）。允许当前类、同包的其他类访问，禁止不同包的类（即使子类也不行）访问。
- **protected：** 允许当前类、同包的其他类、不同包的子类访问，禁止不同包的非子类访问。不能修饰类
- **public：** 任何地方 都可以访问（跨类、跨包）。

## final 关键字
**final** 是 Java 中的一个修饰符，用于表示 **不可变**（immutable）的特性，可以应用于 类、方法、变量。

1. final 修饰变量：修饰基本数据类型时，变量一旦被赋值，不能再修改。必须显式初始化。修饰引用数据类型时，引用地址不可变，但是对象内部的状态可以修改（如对象的属性）。
2. final 修饰方法：禁止子类重写该方法，但可以继承使用。
3. final 修饰类：禁止继承该类（不能有子类），常用于设计不可变类（String、Integer）。

总结：
- 变量不可变
- 方法不能重写
- 类无法继承

## final、finally、finalize的区别

1. final关键字修饰类、属性、方法，表示变量不可变，方法不能重写，类不能被继承。
2. finally用于try-catch结构。无论异常是否发生，都会执行，常用于异常释放（如关闭文件、数据库连接）。
3. finalize是Object类的一个方法，垃圾回收（GC）前调用，用于对象清理（已废弃，不推荐使用）。

## static 关键字 
**static** 是 Java 中用于修饰 类成员（变量、方法、代码块、内部类） 的关键字，表示这些成员属于类本身，而非类的某个实例。其主要特点是 与对象无关，直接通过类访问。

- static修饰变量和方法时，无需创建对象实例即可调用。`类名.变量名` `类名.方法名()`
- static修饰代码块时，类加载时执行一次，（优先级高于构造方法）。用于初始化静态变量
- static修饰内部类，静态内部类，不依赖外部类实例，可以直接创建。`new 外部类.内部类()`

- 静态方法能否被重写？
  不能，静态方法属于类，与对象无关

- 静态变量线程安全吗？
  不安全，多个线程修改同一静态变量会导致`竞态条件`，需要用`synchronized`或者`Atomic类`保证安全。

# Java面向对象编程
面向对象编程（OOP，Object-Oriented Programming）的三大核心特性是：
封装（Encapsulation）、继承（Inheritance）、多态（Polymorphism）。
这些特性使得 Java 代码更模块化、可复用、易维护。
- 封装：隐藏对象的内部细节，仅对外提供公共访问方式。通过方法（如 getter/setter）访问或修改数据，避免直接操作字段。
- 继承：子类继承父类的属性和方法（`extends` 关键字），实现代码复用。Java 是单继承（一个类只能继承一个父类），但支持多层继承（A → B → C）。
- 多态：同一操作作用于不同对象时，表现出不同的行为。主要通过 **方法重写（Override）** 和 **向上转型（Upcasting）** 实现。


## 抽象类（Abstract Class）与接口（Interface）
抽象类和接口是 Java 中实现多态和代码复用的两种重要机制，它们的核心区别如下：

1. 关键字：抽象类 `abstract class`修饰，接口使用 `interface` 修饰。
2. 抽象类和接口都无法实例化。
3. 抽象类可以包含具体方法和抽象方法，接口Java8之后支持静态方法和default方法。
4. 抽象类可以有构造方法（用于子类初始化），接口不能有构造方法。
5. Java是单继承，一个类只能继承一个抽象类，一个类可以实现多个接口，一个接口可以继承多个接口。


## 重写与重载
**重写（Override**）和  **重载（Overload）** 是 Java 中实现多态的两种重要方式。


1. 重写：子类重新定义父类的方法，方法名，参数列表必须完全相同。
   - 方法名、参数列表、返回值类型、必须相同。
   - 访问权限不能更严格：子类方法的访问修饰符 大于等于 父类方法。
   - 异常范围不能扩大：子类方法抛出的异常 小于等于 父类方法抛出的异常。
   - 不能重写 private、final、static方法
2. 重载：同一个类中的多个重名的方法，但是参数列表不同（类型、顺序、数量）
   - 方法名相同，参数列表不同（与返回类型无关）。
   - 可以有不同的访问修饰符、异常声明、返回类型。


- 不能根据返回值类型区分重载，只有参数列表不同才能重载。
- main方法可以重载，但是JVM只会调用标准的`main(String[] args)`。
- 构造方法不能重写，但是可以重载。

**重写是 父子类方法一致（变实现），重载是 同类方法名同参数不同（变参数）。**

## ==和equals的区别
- 基本数据类型，==比较的是值是否相等
- 引用数据类型，==比较的是内存地址（是否为同一对象）
- equals方法未重写时，与==一样，比较的是内存地址
- 重写equals方法后，比较的是对象的内容是否相等，例如String、Integer等类已经重写。比较的是值是否相等
- 重写equals方法需要同时重写hashCode方法

## 重写equals方法为什么需要重写haseCode方法
在Java中，当你重写了 `equals()` 方法时，必须同时重写 `hashCode()` 方法，这是为了遵守 Java 中规定的 hashCode() 和 equals() 的通用约定。否则，你的对象在使用基于哈希的集合（如 HashMap、HashSet、Hashtable 等）时会出现逻辑错误。

如果只重写了 `equals()`，虽然两个对象 equals 方法判断为相等，但是在添加到 HaseSet、HashMap时会因为`hashCode`值不相等被判断为不同的对象。
必须同时重写 `equals()` 和 `hashCode()`，才能让集合类如 HashSet、HashMap 正常工作。



- 若 `a.equals(b)` 为 `true`，则 `a.hashCode() == b.hashCode()`。
- `a.hashCode() == b.hashCode()` 不一定意味着 `a.equals(b)` 为 `true`。相同的 hashCode 不一定是相等的对象，但相等的对象必须有相同的 hashCode。


## String、StringBuffer和StringBuilder
|  特性  |  String   |  StringBuilder   |  StringBuffer   |
| --- | --- | --- | --- |
|​​不可变性​​|不可变（final修饰）|  可变  |   可变  |
|​​线程安全​​|天然线程安全（不可变）| 非线程安全   |线程安全（synchronized方法）|
|​​性能​​|频繁修改时性能差（生成新对象）|单线程下性能最优（无锁）|多线程安全但性能较低（同步开销）|

- 为什么String不可变？​
   安全性（防止SQL注入）、线程安全、支持字符串常量池缓存


# Java 异常

**Throwable** 是 Java 语言中所有错误或异常的超类。，分为 **Error** 和 **Exception**

1. 检查型异常：编译时必须处理
2. 运行时异常：运行时异常，不强制处理
3. Error：JVM级别的问题，不能捕获处理，通常直接终止程序。


异常的集成结构
```
java.lang.Throwable
  ├─ java.lang.Error          (错误)
  └─ java.lang.Exception      (异常)
       ├─ java.lang.RuntimeException (运行时异常)
       └─ 其他检查异常

```

## throw 与 throws 的区别？

- throw 用来主动抛出一个具体的异常实例，在方法内使用，是抛出异常的动作。
- throws 用于方法声明处，声明该方法可能抛出的异常类型，通知调用者必须处理这些异常。后面跟一个或多个异常类，用逗号分隔。

总结：
throw 是抛出异常的动作，程序运行时执行。
throws 是声明异常，让调用者知道并做好异常处理准备，是编译时机制。

# Java集合

集合与数组的区别
- 数组是固定长度的，集合长度是可变的。
- 数组可以存储基本数据类型，也可以存储引用数据类型；集合只能存储引用数据类型。
- 数组存储的元素必须是同一个数据类型；集合存储的对象可以是不同的数据类型。


Java 集合容器一共分为 Collection 和 Map 两大类。

Collection有 List、Set 和 Queue 三种子接口。比较常用的是List和Set。

- List：有序集合，元素可以重复，可以插入多个null元素。常用的实现类有ArrayList、LinkedList。
  - ArrayList：基于数组实现，随机访问元素效率高。但在插入和删除元素时可能需要移动大量元素，效率相对较低。适用于频繁访问元素、较少修改元素的场景。
  - LinkedList：基于双向链表实现，插入和删除元素效率高。但随机访问元素需要遍历链表，效率较低。适合频繁插入和删除元素的场景。
  - Vector：基于数组实现，Vector是线程安全的，但性能相对较低。
- Set：无序集合，元素不能重复，只允许存入一个null元素，必须保证元素的唯一性。常用的实现类有HashSet、LinkedHashSeth和TreeSet。
  - HashSet：基于哈希表实现，元素无序，添加、删除和查找元素的效率都很高。但它不保证元素的顺序，也不支持元素的排序。
  - TreeSet：基于红黑树实现，元素有序，可以按照自然顺序或指定的比较器顺序进行排序。它在添加、删除和查找元素时的效率相对较低。
  - LinkedHashSet：继承自HashSet，保留元素的插入顺序。它内部使用链表维护插入顺序，因此保证了元素的迭代顺序与插入顺序一致。适用于需要保持插入顺序并且不允许重复元素存在的场景。

Map是键值对集合，Key无序，唯一。Value允许重复。常用的实现类有HashMap、TreeMap、LinkedHashMap、ConcurrentHashMap。
   - HashMap：基于哈希表实现，键值对无序，添加、删除和查找元素的效率都很高
   - TreeMap：基于红黑树实现，键值对有序，可以按照键的自然顺序或指定的比较器顺序进行排序。它在添加、删除和查找元素时的效率相对较低
   - Hashtable：一个早期的实现了Map接口的类，它类似于HashMap，但是它的所有方法都是同步的（synchronized）。这使得Hashtable在多线程环境中是线程安全的，然而，由于其所有方法都进行同步处理，可能会导致性能上的一些损失。
   - LinkedHashMap：继承自HashMap，但保留了元素的插入顺序。它通过链表维护插入顺序，因此保证了元素的迭代顺序与插入顺序一致。这使得LinkedHashMap在需要按照插入顺序迭代元素时非常有用。


## HashMap的原理
HashMap基于 `数组 + 链表/红黑树`实现。
- 数组（桶数组）：每个数组元素称为一个桶（bucket）,用于快速定位键值对，首先根据key计算Hash值，在将键值对存储到对应的桶中。
- 链表：当哈希冲突（不同的对象的哈希值相同）时。冲突的键值对以链表形式存储。
- 红黑树（Java8+）：当链表长度大于等于8且桶数组容量大于等于64时，链表会转化为红黑树（查询效率从O(n)提升为O(log n)）。



### 插入键值对 put
1. 计算键的哈希值，定位到桶。
2. 处理冲突：
        - 桶为空：直接插入新节点。
        - 桶非空：遍历链表/树，检查键是否已存在（equals() 判断）：
            - 存在：更新值。
            - 不存在：插入新节点（Java 8 尾插法）。
3. 树化检查：链表长度 ≥8 且桶数组容量 ≥64 时，转换为红黑树。
4. 扩容检查：当元素数量 ≥ 容量 × 负载因子（默认 0.75） 时，扩容为原容量的 2 倍。

### 查找值 get
1. 计算键的哈希值，定位到桶。
2. 遍历链表/树，通过 equals() 匹配键，返回对应值。若未找到，返回 null。


### 扩容机制

- 触发条件：元素数量 ≥ 容量 × 负载因子（0.75）
- 过程：
    1. 创建新数组（容量 ×2）。
    2. 遍历旧数组，重新计算每个节点的索引（hash & (newCapacity - 1)）。
    3. 迁移数据到新数组（Java 8 优化：节点新位置 = 原位置 或 原位置 + 旧容量)



## ConcurrentHashMap 原理
**ConcurrentHashMap** 是 Java 并发包 (java.util.concurrent) 中的一个线程安全的哈希表实现。它是为了解决在并发环境下使用 HashMap 时可能出现的线程安全问题而设计的，**既保证线程安全，又提供高并发性能。**
ConcurrentHashMap 底层是一个 数组 + 链表 + 红黑树 的结构（和 HashMap 类似）。

Segment 已淘汰（Java 7 之前）Java 7 使用了 分段锁（Segment），把整个 map 分成多个段，每个段用 ReentrantLock 保护。
Java 8 之后，不再使用 Segment，而是采用更加精细化的锁策略： CAS + synchronized + volatile

| 操作       | 同步机制                     | 说明                    |
| -------- | ------------------------ | --------------------- |
| **读操作**  | 无锁（volatile 保证可见性）       | 读取操作不加锁，提高并发性能        |
| **写操作**  | `synchronized` 锁住桶（Node） | 只锁住当前桶（链表头），不是整个表，支持多个线程并行写入不同桶     |
| **扩容操作** | 多线程协助（transfer）          | 使用 CAS 控制标记位，多个线程协助扩容 |


### put 操作流程
1. 根据 key 计算 hash，找到桶的位置
2. 如果桶为空，用 CAS 插入新节点（无锁）
3. 如果桶不为空：
    - 使用 synchronized 锁住该桶（链表头节点）
    - 遍历链表或树查找 key，插入或替换
    - 链表长度超过阈值时转换为红黑树
4. 释放锁




### 扩容机制 resize
- ConcurrentHashMap 自动扩容时，不是单线程操作，而是多个线程一起迁移桶中的数据。
- 迁移过程中，桶位置被标记，避免多线程重复迁移。


ConcurrentHashMap 不允许 key 或 value 为 null


---


| 集合类 | 实现接口 | 顺序性 | 唯一性 | 线程安全 | 底层实现 | 扩容机制 | 允许null | 特点 |
|--------|----------|--------|--------|----------|----------|----------|----------|------|
| ArrayList | List | 有序 | 允许重复 | 不安全 | 动态数组 | 默认10，扩容1.5倍 | 允许 | 随机访问快，增删慢 |
| LinkedList | List, Deque | 有序 | 允许重复 | 不安全 | 双向链表 | 无需扩容 | 允许 | 增删快，随机访问慢 |
| Vector | List | 有序 | 允许重复 | 安全(synchronized) | 动态数组 | 默认10，扩容2倍 | 允许 | 线程安全但性能差 |
| Stack | List(Vector子类) | 有序 | 允许重复 | 安全 | 动态数组 | 同Vector | 允许 | LIFO结构 |
| HashSet | Set | 无序 | 唯一 | 不安全 | HashMap | 默认16，负载因子0.75 | 允许1个 | 快速查找，无序 |
| LinkedHashSet | Set | 插入顺序 | 唯一 | 不安全 | LinkedHashMap | 同HashSet | 允许1个 | 保持插入顺序 |
| TreeSet | NavigableSet | 排序 | 唯一 | 不安全 | TreeMap | 无需扩容 | 不允许 | 自然排序或Comparator |
| PriorityQueue | Queue | 堆顺序 | 允许重复 | 不安全 | 二叉堆(数组) | 默认11，扩容策略复杂 | 不允许 | 优先级队列 |

---

| 集合类 | 实现接口 | 顺序性 | 键唯一性 | 线程安全 | 底层实现 | 扩容机制 | 允许null键值 | 特点 |
|--------|----------|--------|----------|----------|----------|----------|--------------|------|
| HashMap | Map | 无序 | 唯一 | 不安全 | 数组+链表/红黑树(JDK8+) | 默认16，负载因子0.75 | 允许1个null键和多个null值 | 高效查找，非线程安全 |
| LinkedHashMap | Map | 插入顺序/访问顺序 | 唯一 | 不安全 | 哈希表+双向链表 | 同HashMap | 同HashMap | 保持插入/访问顺序 |
| Hashtable | Map | 无序 | 唯一 | 安全(synchronized) | 数组+链表 | 默认11，负载因子0.75 | 不允许 | 线程安全但性能差 |
| TreeMap | NavigableMap | 键排序 | 唯一 | 不安全 | 红黑树 | 无需扩容 | 不允许null键 | 自然排序或Comparator |
| ConcurrentHashMap | ConcurrentMap | 无序 | 唯一 | 安全(分段锁/CAS) | 数组+链表/红黑树 | 动态扩容 | 不允许 | 高并发性能好 |
| WeakHashMap | Map | 无序 | 唯一 | 不安全 | 哈希表 | 同HashMap | 允许 | 弱引用键，适合缓存 |

---
| 集合类 | 对应非线程安全类 | 线程安全机制 | 性能 | 适用场景 |
|--------|------------------|--------------|------|----------|
| Vector | ArrayList | synchronized方法 | 差 | 已过时，不推荐使用 |
| Hashtable | HashMap | synchronized方法 | 差 | 已过时，不推荐使用 |
| ConcurrentHashMap | HashMap | 分段锁+CAS(JDK7)/synchronized+CAS(JDK8+) | 高 | 高并发场景首选 |
| CopyOnWriteArrayList | ArrayList | 写时复制 | 读高写低 | 读多写少场景 |
| CopyOnWriteArraySet | HashSet | 写时复制 | 读高写低 | 读多写少场景 |
| ConcurrentSkipListMap | TreeMap | 跳表结构 | 中等 | 需要排序的并发Map |
| ConcurrentSkipListSet | TreeSet | 跳表结构 | 中等 | 需要排序的并发Set |
| ConcurrentLinkedQueue | LinkedList | CAS操作 | 高 | 高并发队列 |
| BlockingQueue接口实现类 | - | 锁+条件变量 | 中等 | 生产者-消费者模式 |

## 选择指南

1. **需要唯一性**：
   - Set接口实现类(HashSet, LinkedHashSet, TreeSet)
   
2. **需要键值对**：
   - Map接口实现类(HashMap, LinkedHashMap, TreeMap)

3. **需要线程安全**：
   - 并发包下的集合(ConcurrentHashMap, CopyOnWriteArrayList等)
   
4. **需要排序**：
   - TreeSet, TreeMap, ConcurrentSkipListSet, ConcurrentSkipListMap

5. **需要保持插入顺序**：
   - LinkedHashSet, LinkedHashMap, ArrayList, LinkedList

6. **需要高性能随机访问**：
   - ArrayList

7. **需要频繁增删**：
   - LinkedList

8. **高并发环境**：
   - ConcurrentHashMap > Collections.synchronizedMap > Hashtable




# Java IO
Java 的 IO（Input/Output）流是用于处理数据的输入和输出的基础机制。它广泛应用于读取文件、写文件、网络通信、序列化等。
Java IO 中的流可以从两个维度来分类：字节流（InputStream/OutputStream）和字符流(Reader/Writer)

字节流
```
InputStream        ← 抽象类
  └─ FileInputStream
  └─ BufferedInputStream
  └─ ObjectInputStream
  └─ ByteArrayInputStream

OutputStream       ← 抽象类
  └─ FileOutputStream
  └─ BufferedOutputStream
  └─ ObjectOutputStream
  └─ ByteArrayOutputStream

```


字符流
```
Reader             ← 抽象类
  └─ FileReader
  └─ BufferedReader
  └─ InputStreamReader

Writer             ← 抽象类
  └─ FileWriter
  └─ BufferedWriter
  └─ OutputStreamWriter

```

| 需求           | 用什么流？                                                       |
| ------------ | ----------------------------------------------------------- |
| 读取二进制文件（图片等） | `InputStream` / `OutputStream`                              |
| 读取文本文件       | `Reader` / `Writer`                                         |
| 提高效率（加缓冲）    | `BufferedReader` / `BufferedWriter` / `BufferedInputStream` |
| 按行读取文本       | `BufferedReader.readLine()`                                 |
| 将字节转为字符      | `InputStreamReader`                                         |


# NIO

NIO 是 Java 1.4 引入的 全新 I/O 框架，全称是 Non-blocking I/O（非阻塞 I/O），也称为 New I/O。
相比传统的 IO（面向流、阻塞式），NIO 具有：
- 面向缓冲区（Buffer）
- 非阻塞 IO
- 选择器（Selector）支持多路复用


 NIO 核心组件

| 组件         | 作用说明                   |
| ---------- | ---------------------- |
| `Buffer`   | 数据的读写容器（代替流）           |
| `Channel`  | 类似管道，用于与文件、网络数据的连接     |
| `Selector` | 多路复用器，用于非阻塞 IO 模型中事件监听 |
| `Charset`  | 编码/解码字符                |


- Buffer（核心数据结构）常见的 Buffer：
    - ByteBuffer：最常用，读写字节
    - CharBuffer、IntBuffer、DoubleBuffer 等：读写不同类型数据

- Channel支持读写数据的“桥梁”接口，可以同时进行读/写操作（双向），不像 InputStream/OutputStream 单向。

- Selector（多路复用）适用于非阻塞网络编程，可以同时监听多个 Channel 的 IO 事件（如连接、读取、写入）。

    工作流程：
        1. 创建 Selector
        2. 注册多个 Channel（非阻塞模式）到 Selector
        3. 使用 select() 检查是否有就绪的事件
        4. 遍历就绪事件并处理


| 特性    | IO（传统）     | NIO（New IO）       |
| ----- | ---------- | ----------------- |
| 编程模型  | 面向流        | 面向缓冲区             |
| 阻塞方式  | 阻塞式        | 非阻塞式（可配置）         |
| 数据处理  | 一次只能处理一个通道 | 多通道同时处理（Selector） |
| 使用复杂度 | 简单         | 略复杂（更灵活）          |
| 适合场景  | 文件操作、低并发   | 高并发网络服务器          |


NIO总结：
| 组件    | 关键类               | 说明            |
| ----- | ----------------- | ------------- |
| 数据缓冲区 | `ByteBuffer` 等    | 用于读写数据        |
| 数据通道  | `SocketChannel` 等 | 读/写数据，支持非阻塞模式 |
| 多路监听  | `Selector`        | 监听多个通道，提高效率   |



## 同步与阻塞的概念

IO模型分为阻塞IO（BIO），非阻塞IO（NIO），异步IO（AIO）。
实际上IO操作分为两部分：发起IO和实际IO


- 同步（Synchronous）：用户线程自己发起和等待操作完成（不管是等待连接还是等待数据）
- 阻塞（Blocking）：操作调用后，线程会卡住（阻塞）直到结果返回，不能干别的事


| 概念        | 说明                                       |
| --------- | ---------------------------------------- |
| 🔄 **同步** | 线程发起 IO 操作时，**必须等到操作完成后才能继续执行**（谁发起，谁等待） |
| ⛔ **阻塞**  | 在 IO 操作期间，线程 **被挂起，无法做任何其他事情**（线程被“卡住”）  |


| 模型  | 类比                            |
| --- | ----------------------------- |
| 同步  | 自己去饭店点餐，等上菜，吃完走（你得自己等）        |
| 异步  | 点餐后店员帮你盯着菜什么时候上，你可以干别的，做好了通知你 |
| 阻塞  | 点餐后站在原地啥也干不了，只能等（卡住）          |
| 非阻塞 | 点餐后看还没好就继续刷手机，一会再来看（可以干别的）    |

