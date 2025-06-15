
# SQL 基础
- DML（数据操纵语言）：SELECT、INSERT、UPDATE、DELETE语句
- DDL（数据定义语言）：CREATE、ALTER、DROP语句
- DCL（数据控制语言）：GRANT、REVOKE语句
- 高级查询：GROUP BY、HAVING、ORDER BY、LIMIT等分组排序操作
- 聚合函数：COUNT、SUM、AVG、MAX、MIN等常用函数

## DDL数据定义语言
```sql
-- 创建数据库
CREATE DATABASE mydb;
-- 删除数据库
DROP DATABASE mydb;
-- 使用数据库
USE mydb;
-- 创建表
CREATE TABLE employee (
    emp_id INT PRIMARY KEY,
    emp_name VARCHAR(50) NOT NULL,
    hire_date DATE,
    age INT,
    salary DECIMAL(10,2)
)
-- 添加新列
ALTER TABLE employee ADD COLUMN department VARCHAR(30);

-- 删除现有列
ALTER TABLE employee DROP COLUMN department;

-- 修改列的数据类型
ALTER TABLE employees MODIFY COLUMN age FLOAT;

-- 重命名列
ALTER TABLE employees RENAME COLUMN age TO employee_age;

-- 添加主键约束
ALTER TABLE 表名 ADD PRIMARY KEY (列名);

-- 添加外键约束
ALTER TABLE 表名 ADD FOREIGN KEY (列名) REFERENCES 另一个表名(列名);

-- 删除约束
ALTER TABLE 表名 DROP CONSTRAINT 约束名;

```

## DQL 数据查询语言
```sql
-- 基本查询
SELECT emp_name, salary FROM employee;

-- 条件查询
SELECT * FROM employee WHERE salary > 5000;

-- 模糊查询
SELECT * FROM employee WHERE emp_name LIKE '张%';

-- 排序
SELECT * FROM employee ORDER BY salary DESC;

SELECT * FROM employee WHERE salary > 5000 

-- 分组统计
SELECT department, AVG(salary) as avg_salary 
FROM employee 
GROUP BY department 
HAVING AVG(salary) > 6000;

```

## FROM、JOIN、WHERE、GROUP BY、HAVING、SELECT、ORDER BY、LIMIT的执行步骤
1. FROM/JOIN: 从指定的表或视图中获取数据。
2. WHERE: 根据指定的条件过滤数据。
3. GROUP BY: 根据指定的列对数据进行分组。
4. HAVING: 对分组后的数据进行过滤。
5. SELECT: 从数据集中选择需要的列。
6. ORDER BY: 根据指定的列对数据进行排序。
7. LIMIT: 限制返回结果的数量。

举例：

```sql
SELECT u.region, SUM(o.amount) AS total_amount
FROM users u
JOIN orders o ON u.user_id = o.user_id
WHERE o.create_time BETWEEN '2025-01-01' AND '2025-12-31'
GROUP BY u.region
HAVING SUM(o.amount) > 10000
ORDER BY total_amount DESC
LIMIT 10;
```


## DML 数据操纵语言
```sql
-- 插入数据
INSERT INTO employee (emp_id, emp_name, hire_date, age, salary) 
VALUES (1001, '张三', '2022-01-01', 25, 5000);

-- 更新数据
UPDATE employee SET salary = 6000 WHERE emp_id = 1001;

-- 删除数据
DELETE FROM employee WHERE emp_id = 1001;
```

## DCL 数据控制语言
```sql
-- 授权
GRANT SELECT, INSERT ON employee TO user1;

-- 撤销权限
REVOKE INSERT ON employee FROM user1;
```




# 数据库的三范式是什么？
1. 第一范式：**字段不可再分**
    - 表中的每一个字段都是原子性的，不可再分，即每一个字段只能存储一个值。
    - 目的是为了消除重复字段和多值字段，让数据结构更规范。
2. 第二范式：**消除部分依赖**
    - 在第一范式的基础上，非主键字段完全依赖主键，不能产生部分依赖。
      举例：不符合2NF（复合主键为 课程号 + 学号）
        | 课程号  | 学号   | 学生姓名 | 成绩 |
        | ---- | ---- | ---- | -- |
        | C001 | S001 | 张三   | 90 |
      
      这里学生姓名只依赖于学号，而不是完整的主键（课程号 + 学号）。
      应该拆成两个表：
        学生表：
        | 学号   | 学生姓名 |
        | ---- | ---- |
        | S001 | 张三   |
        
        成绩表：
        | 课程号  | 学号   | 成绩 |
        | ---- | ---- | -- |
        | C001 | S001 | 90 |

3. 第三范式：**消除传递依赖**
    - 在第二范式的基础上，任何非主键字段不能依赖于其他非主键字段。
    举例：不符合3NF
        | 学号   | 姓名 | 班级ID | 班级名称  |
        | ---- | -- | ---- | ----- |
        | S001 | 张三 | B01  | 一年级一班 |

       班级名称 依赖于 班级ID，而 班级ID 又依赖于 学号（主键）。这是一种传递依赖。
       应拆成：学生表和班级表
        | 学号   | 姓名 | 班级ID |
        | ---- | -- | ---- |
        | S001 | 张三 | B01  |

        | 班级ID | 班级名称  |
        | ---- | ----- |
        | B01  | 一年级一班 |

---
总结
| 范式  | 核心要求        | 主要目的是      |
| --- | ----------- | ---------- |
| 1NF | 字段原子性       | 避免重复与多值字段  |
| 2NF | 非主属性完全依赖主键  | 避免部分依赖     |
| 3NF | 非主属性不传递依赖主键 | 避免传递依赖，去冗余 |

但是在实际项目中，我们会在范式与性能之间做权衡，视业务场景进行适当反范式设计。

# 数据库事务与锁机制
- ACID：原子性（Atomicity）、一致性（Consistency）、隔离性（Isolation）、持久性（Durability）
- 事务隔离级别：读未提交（Read Uncommitted）、读已提交（Read Committed）、可重复读（Repeatable Read）、串行化（Serializable）
- 锁机制：行锁、表锁、间隙锁、临键锁



# 数据库性能优化
## 索引优化

- 索引类型：B+树索引结构原理、聚簇索引与非聚簇索引的区别
- 索引设计：组合索引的最左前缀原则、覆盖索引、索引选择性计算
- 索引失效场景：函数操作、类型转换、LIKE模糊查询等导致索引失效的情况
- 索引优缺点

## SQL语句优化

## 架构层优化

### 数据库的索引是什么？



# MySQL数据库引擎有哪些？


# InnoDB 和 MyISAM 的区别？






- 数据库的事务是什么？
- 
- 如何定位慢查询？
- 什么情况下索引会失效？


# MySQL





# Oracle




