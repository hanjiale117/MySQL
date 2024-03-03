# 1.MySQL

##  1.1.MySQL指令

1. **mysql -h 主机ip -P 端口 -u 用户名 -p密码(p和密码之间不能有空格)**
2. 登录前,保证服务启动(端口3306)
3. **如果没有写-h 主机,默认为主机**
4. **如果没有写-P 端口,默认就是3306(如果是别的端口,就要带上 -P 端口)**

## 1.2.数据库的三层结构

1. 所谓安装Mysql数据库,就是在主机安装一个数据库管理系统DBMS,这个管理程序可以管理多个数据库,DBMS(database manage system)
2. 一个数据库中可以创建多个表,以保存数据
3. 数据库管理系统
4. 数据在数据库中的存储方式为一个表,一行(row)为记录一个对象的数据,一列(column)为一组

<a href="https://smms.app/image/YvrxtEyGN3flWPk" target="_blank"><img src="https://s2.loli.net/2024/01/14/YvrxtEyGN3flWPk.png" ></a>

- SQL语句分类

  DDL:数据定义语句[create表,库….]

  DML:数据操作语句[增加insert,修改update,删除delete]

  DQL:数据查询语句[select]

  DCL:数据控制语句[管理数据库:比如用户权限 授予 grant 撤回revoke]



## 1.3.数据库指令

1. CHARACTER SET :指定数据库采用的字符集,如果不指定字符集,默认utf8
2. COLLATE:指定数据库字符集的校对规则(常用utf8_bin[区分大小写]),utf8_general_ci[不区分大小写] 默认是utf8_general_ci

```mysql
#删除数据库
DROP DATABASE db04
#创建数据库
CREATE DATABASE db04
#创建一个使用utf8字符集的db04数据库
CREATE DATABASE db04 CHARACTER SET utf8
#创建一个使用utf8字符集的db05数据库 并且带校对规则的db05数据库
CREATE DATABASE db05 CHARACTER SET utf8 COLLATE utf8_bin
#校对规则 utf8_bin 区分大小写 默认为 utf8_general_ci 不区分大小写
#下面是一条查询的sql,select查询  *是表示所有字段 FROM 从哪个表查询 
#WHERE从哪个字段 NAME='tom'查询是tom
```

```mysql
#查看当前数据库服务器中所有的数据库
SHOW DATABASES
#查看前面创建的db01的数据库定义
SHOW CREATE DATABASE db01
#在创建数据库表的时候,为了规避关键字,可以使用反引号(`)解决
#举例说明
#创建
CREATE DATABASE `CREATE`
#删除
DROP DATABASE `CREATE`
```

```mysql
#备份数据库
#mysqldump -u 用户名 -p -B 数据库1 数据库2 数据库n >文件名.sql(只用一个\)
mysqldump -u root -p -B  db02 db03 >d:\1.sql
#备份数据库的表
mysqldump -u root -pHan0852963741 db03 t1 t2 ... >d:\2.sql
#恢复数据库(要进入Mysql的命令行再次执行)
source d:\1.sql
```

```mysql
#创建表
CREATE TABLE `user`(
  id INT,
  `name` VARCHAR(255),
  `password` VARCHAR(255),
  `birthday` DATE)
	CHARACTER SET utf8 COLLATE utf8_bin ENGINE INNODB
	
CREATE TABLE table_name
(
		field1 datatype,
  	field2 dataytpe,
  	field3 dataytpe

)character set 字符集 collate 校对准则 engine 存储引擎;
#field:指定列名 dataytpe:指定列类型(字段类型)
#character set :如不指定则为所在数据库字符集
#collate:如不指定则为所在数据库校对准则
#engine:引擎
```



## 1.4.Mysql常用数据类型

<a href="https://smms.app/image/hzYx7dSaW3rV1T6" target="_blank"><img src="https://s2.loli.net/2024/01/15/hzYx7dSaW3rV1T6.png" ></a>

**int,double,decimal,char,varchar,text,datetime,timestamp这几个数据类型最为常用**

- 数值型(整型)的使用

  1. 在能够满足需求的情况下,尽量选择占用空间小的类型

     ```mysql
     #如果没有指定unsigned 则tinyint就是有符号的
     CREATE TABLE t3(
     		id TINYINT
     );
     INSERT INTO t3 VALUES(127);#这是添加语句
     SELECT * FROM t3
     #这是创建有符号的
     CREATE TABLE t3(
     		id TINYINT UNSIGNED
     );
     ```

- 数值型(bit)的使用

  ```mysql
  mysql> create table t05(num bit(8));
  mysql> insert into t05(1,3);
  mysql> insert into t05 values(2,65);
  #bit(m) m在1-64
  #添加数据 范围是按照给定的位数来确定的 比如m=8 表示一个字节 0-255
  #显示是按照二进制的显示方法来显示
  ```

  1. bit字段显示时,按照 位的方式显示(二进制显示)
  2. 查询的时候仍然可以用使用 添加的数值
  3. 如果一个值只有0,1 可以考虑使用bit(1),可以节约空间
  4. 位类型,M指定位数,默认值为1,范围1-64

- 数值型(小数)的使用

  1. FOLAT/DOUBLE[UNSIGNED] Float 单精度精度 ,Double 双精度

  2. DECIMAL[M,D]\[UNSIGNED]

     支持更加精确的小数位,M是小数位(精度)的总数,D是小数点(标度)后面的位数

     如果D是0,则值没有小数点或分数部分,M最大65,D最大是30,如果D被省略,默认是0,如果M被省略,默认是10

     如果希望小数的精度更高,**推荐使用decimal**

     ```mysql
     #decimal,float,double的使用
     CREATE TABLE t06(
     		num1 FLOAT,
       	num2 DOUBLE,
       	num3 DECIMAL(30,20));
       	
     #添加数据
     INSERT INTO t06 VALUES(88.12345678912345,88.12345678912345,88.12345678912345);
     #88,1235,88.12345678912345,88,12345678912345000000
     ```

- 字符串的使用

  **CHAR(size)**

  固定长度字符 最大**255字符**

  **VARCHAR(size)(0-65535)**

  可变长度字符串 最大**65532字节** (utf8编码最大**21844字符** **1-3个字节用于记录大小**)

  ```mysql
  #注释的快捷键 shift+ctrl+c 注释取消 shift+ctrl+r
  CREATE TABLE t10(
    `name` VARCHAR(65530));
  #错误
  #Column length too big for column 'name' (max = 21845); use BLOB or TEXT instead
  #utf8编码最大21844字符 1-3个字节用于记录大小
  #如果编码是utf8 varchar(size) size = (65535-3)/3 = 21844字符
  #如果编码是gbk varchar(size) size = (65535-3)/2 = 32766字符
  ```

- 字符串使用细节

  ```mysql
  #char(4)和varchar(4)这个4表示的是字符,而不是字节
  CREATE TABLE t11(
    `name`CHAR(4));#这里表示最多四个字符
  	INSERT INTO t11 VALUES('abcdf');#这是五个字符
  	INSERT INTO t11 VALUES('abcd');#这是四个字符
  	INSERT INTO t11 VALUES('你好你好你好');#这是六个字符
  	#不区分汉字还是字母
  	
  #char(4)是定长(固定的大小),即使你插入'aa',也会占用分配4个字符的空间(可能会造成资源的浪费)
  #varchar(4)是变长(变化的大小),如果你插入了'aa',实际占用空间并不是4个字符,而是按照实际占用的空间来分配
  #varchar本身还需要占用1-3的字节的大小来记录字段的大小
  ```

  什么时候使用char 什么时候使用varchar?

  1. 如果数据是定长,推荐使用char,比如md5的密码,编码,手机号,身份证
  2. 如果一个字段的长度是不确定的,我们使用varchar,比如留言

  存放文本时,也可以使用Text数据类型,可以将TEXT列视为VARCHAR列,注意TEXT不能有默认值,大小0-2^16字节

  如果希望存放更多的字符,可以选择MEDIUMTEXT 0-2^24 或者 LONGTEXT 0-2^32

- 日期类型的使用

  ```mysql
  CREATE TABLE t14(
  		birthday DATE, -- 生日
  		job_time DATETIME, -- 记录年月日 时分秒
    	login_time TIMESTAMP
    					NOT NULL DEFAULT CURRENT_TIMESTAMP
    					ON UPDATE CURRENT_TIMESTAMP);-- 登录时间,如果希望login_tine列自动更新,就不要给他初始化
    					
  #例
  INTSERT INTO t14(brithday,job_time)
  				VALUES('2022-11-11','2022-11-11 10:10:10');
  				#如果我们更新,t14的表的某条记录,login_time列会自动的以当前时间进行更新
  ```





## 1.5.修改表

  ```mysql
  #添加列
  ALTER TABLE tablename
  ADD (column datatype[DEFAULT expr]
      [,column dataytpe]...);
  #修改列
  ALTER TABLE tablename
  MODIFY (Ccolumn datatype[DEFAULT expr]
         [,column datatype]...);
  #删除列
  ALTER TABLE tablename
  DROP (column);
  
  #查看表的结构:desc 表名;--可以查看表的列
  #修改表名:Rename table 表名 to 新表名
  #修改表字符集:alter table 表名 character set 字符集;
  ```

```mysql
#添加列
ALTER TABLE createtable
	ADD  tele INT;
#修改列
ALTER TABLE createtable
	MODIFY tele CHAR(11);
#显示表的结构
DESC createtable
DESC table1

#删除列
ALTER TABLE createtable	
	DROP job;
	
#修改表名
RENAME TABLE createtable TO table1

#修改表的字符集为utf8
ALTER TABLE table1 CHARSET utf8

#修改列表的名字
ALTER TABLE table1 CHANGE `name` user_name VARCHAR(255) NOT NULL DEFAULT ' ';
```





## 1.6.CRUD语句

### 1.6.1.Insert语句

- 细节说明

  1. **插入的数据应与字段的数据类型相同**

     比如:把‘abc’添加到 int类型会错误

  2. **数据的长度应在列的规定范围内**,例如:不能将一个长度为80的字符串加入到长度为40的列中

  3. **在values中列出的数据位置必须与被加入的列的排列位置相对应**

  4. **字符和日期型数据应包含在单引号中**

  5. 列可以插入空值[前提是该字段允许为空],insert into table value(null)

  6. **insert into tab_name(列名..) values(),(),()形式添加多条记录**

  7. **如果是给表中的所有字段添加数据,可以不写前面的字段名称**

  8. **默认值的使用,当不给某个字段值时,如果有默认值就会添加,否则报错**



### 1.6.2.Update语句

```mysql
UPDATE tbl_name
			 SET col_name1=expr1[,col_name2 ...]
			 [WHERE where_definition]
#例如
UPDATE employee SET salary = 5000; -- 设置薪水为5000
UPDATE employee SET salary = 3000	 -- 修改dick的薪水为3000
								WHERE user_name = 'dick';
UPDATE employee SET salary = salary + 1000; -- 修改tom的薪水 在原来的基础上+1000
								WHERE user_name = 'tom';
```

- 使用细节
  1. UPDATE语法可以用新值更新原有行中的各列
  2. SET子句指示要修改哪些列和要给予哪些值
  3. WHERE子句指定应更新哪些行,如果没有WHERE子句,则更新所有的行(记录)
  4. 如果需要修改多个字段,可以通过set字段1=值1,字段2=值2…..



### 1.6.3.delete语句

```mysql
delete from tbl_name
				[WHERE where_definition]
```

- 使用细节
  1. **如果不使用 where子句,将删除表中所有数据**
  2. Delete语句不能删除某一列的值(可使用update 设为null 或者 ‘ ’)
  3. 使用delete语句仅删除记录,不删除表本身,如果要删除表,使用drop table语句.DROP TABLE 表名;
  4. **delete删除是一行的语句**



### 1.6.4.select语句

```mysql
SELECT [DISTINCT] *[column1,column2,column3..]
								FROM table_name;
#select指定查询哪些列的数据
#column指定列名
#*号代表查询所有列
#From指定查询哪站表
#DISTINCT可选,只显示结果时,是否去掉重复数据
```

```mysql
#查询表中所有学生的信息
SELECT * FROM student
#查询表中所有学生的新买和对应的英语成绩
SELECT `name`,english FROM student;
#过滤表中重复的数据distinct
SELECT DISTINCT english FROM student;
#查询的记录,每个字段都相同,才会去重
SELECT DISTINCT `name`,english FROM student;
#上面的name和english都相同才会去重
```

- 使用表达式去的查询的列进行运算

  ```mysql
  SELECT * #统计每个学生的总分
  SELECT `name`,(chinese + english + math ) FROM student;
  #每个学生的总分+10分
  SELECT `name`,(chinese + english + math + 10) FROM student;
  #使用别名表示学生的分数
  SELECT `name`,(chinese + english + math + 10) AS total_scores FROM student;
  SELECT `name` AS '名字',(chinese + english + math + 10) AS '总分' FROM student;
  ```

- 在where子句中常用的运算符

  |            | > < >= <= =  !=               | 大于,小于,大于(小于)等于,不等于       |
  | ---------- | ----------------------------- | ------------------------------------- |
  |            | BETWEEN….AND….                | 显示在某一区间的值                    |
  | 比较运算符 | IN(set)                       | 显示在in列表中的值,例如:in(100,200)   |
  |            | LIKE ‘张pattern’ NOT LIKE ‘ ’ | 模糊查询                              |
  |            | IS NULL                       | 判断是否为空                          |
  |            | and                           | 多个条件同时成立                      |
  | 逻辑运算符 | or                            | 多个条件任意一个成立                  |
  |            | not                           | 不成立,例如:where not (salary > 100); |

- 使用order by 子句排序查询结果

  ```mysql
  SELECT column1,column2,column3...
  			FROM table;
  			order by column asc|desc
  ```

  1. Order by 指定排序的列,排序的列既可以是表中的列名,也可以是select语句后指定的列名
  2. Asc升序[默认],Desc 降序
  3. ORDER BY 子句应位于SELECT语句的结尾

  ```mysql
  #Order by 指令
  SELECT * FROM student
  	ORDER BY math DESC;
  
  #可以通过别名来排序
  SELECT `name`,(chinese+english+math) AS total_score FROM student
  	ORDER BY total_score DESC;
  
  #where + order by
  SELECT `name`,(chinese+english+math) AS total_score FROM student #先构建总分这个列 用别名去排序
  	WHERE `name` LIKE '4%'
  ```

## 1.7.函数语句

- 合计/统计函数-count

  count返回行的总数

  ```mysql
  #统计函数
  #统计一个班里共有多少个学生
  SELECT COUNT(*) FROM student #7
  #统计数学成绩大于90的学生有多少个
  SELECT COUNT(*) FROM student
  	WHERE math > 89
  #统计总分大于250的人数有多少
  SELECT COUNT(*) FROM student
  	WHERE (chinese + english + math) >250
  -- count(*) 和 count(列) 的区别
  -- 解释 :count(*) 返回满足条件的记录的行数
  -- count(列): 统计满足条件的某列有多少个，但是会排除 为 null 的情况
  CREATE TABLE t15 (
    `name` VARCHAR(20));
  INSERT INTO t15 VALUES('tom');
  INSERT INTO t15 VALUES('jack');
  INSERT INTO t15 VALUES('mary');
  INSERT INTO t15 VALUES(NULL);
  SELECT * FROM t15;
  SELECT COUNT(*) FROM t15; -- 4
  SELECT COUNT(`name`) FROM t15;-- 3
  ```

- 合计函数-sum

  sum函数返回满足where条件的行的和 一般使用在数值列

  ```mysql
  SELECT SUM(列名){,sum(列名)...} from tablename
  						WHERE where_definition
  ```

  ```mysql
  #sum的作用
  #统计一个班级数学总成绩
  SELECT SUM(math) FROM student;
  #统计一个班级的语文,数学,英语的各科的总成绩
  SELECT SUM(chinese),SUM(english),SUM(math) FROM student;
  #统计一个班级所有的总和
  SELECT SUM(chinese+english+math) FROM student
  #统计一个班级语文成绩平均分
  SELECT SUM(chinese)/COUNT(*) FROM student 
  ```

- 合计函数-AVG

  AVG函数返回满足where条件的一列的平均值

  ```mysql
  SELECT AVG(列名){,AVG(列名)....} FROM tablename
  				[WHERE where_definition]
  ```

  ```mysql
  #AVG的使用
  SELECT AVG(math) FROM student
  SELECT AVG(chinese+math+english) FROM student
  ```

- 合计函数-Max/min

  ```MYSQL
  #MAX和MIN的使用
  #求班级的最高分和最低分
  SELECT MAX(chinese+math+english)AS `MAX`,MIN(chinese+math+english)AS `MIN`
  	FROM student
  ```

- 使用group by 子句对列进行分类

  ```mysql
  #group by子句用于限制分组显示结果
  SELECT AVG(sal),MAX(sal),deptno FROM emp 
  				GROUP BY deptno;
  		#分组去查询,每个组的最高工资和平均工资
  		
  SELECT AVG(sal),MIN(sal),deptno , job
  				FROM emp GROUP BY deptno,job;
  		#从deptno中的job找出每中岗位的平局工资和最低工资
  		
  #显示平均工资低于2000的部门号和它的平均工资
  #化繁为简
  SELECT AVG(sal) AS avg_sal, deptno 
  	FROM emp GROUP BY deptno
  		HAVING	avg_sal < 2000;
  #使用别名的效率更高
  #别名使用中文名时,用单引号引起来
  SELECT AVG(sal) AS `工资`, deptno 
  	FROM emp GROUP BY deptno
  		HAVING	`工资` < 2000;
  #having 是进行过滤 限制条件
  ```

- 字符串相关函数

  | CHARSET(str)                                 | 返回字串字符集                                     |
  | -------------------------------------------- | -------------------------------------------------- |
  | CONCAT (string2 [….])                        | 连接字符串                                         |
  | INSTR(string, substring)                     | 返回substring在string中出现的位置,没有返回0        |
  | UCASE (string2)                              | 转换成大写                                         |
  | LCASE (string2)                              | 转换成小写                                         |
  | LEFT( string2, length),RIGHT(string2,length) | 从string2中的左边起取length个字符                  |
  | LENGTH(string)                               | string长度[按照字节]                               |
  | REPLACE(str,search_str,replace_str)          | 从str中用replace_str替换search_str                 |
  | STRCMP(string1,string2)                      | 逐字符串比较俩子串大小                             |
  | SUBSTRING(str , position ,[,length])         | 从str的position开始,从[从1开始计算],取length个字符 |
  | LTRIM(string2) ,RTRIM(string2)trim           | 去除前端空格或者后端空格                           |

  ```mysql
  #CHARACTER(str)返回字符串字符集
  SELECT CHARSET(ename) FROM emp;
  #CONCAT 连接字串,将多个列拼接成一列
  SELECT CONCAT(ename,'工作是',job) FROM emp;
  #INSTR (string, substring)返回subtring 在string中出现的位置,没有返回0
  #dual亚元表 ,系统表,作为测试表使用
  SELECT INSTR('ASDFGH','SDF')FROM DUAL;
  ```

- 数学相关函数

  | ABS(num)                        | 绝对值                          |
  | ------------------------------- | ------------------------------- |
  | BIN(decimal_number)             | 十进制转二进制                  |
  | CEILING(number 2)               | 向上取整,得到比num2大的最小整数 |
  | CONV(number2,from_base,to_base) | 进制转换                        |
  | FLOOR(number2)                  | 向下取整,得到比num2小的最大整数 |
  | FORMAT(number,decimal_places)   | 保留小数位数                    |
  | HEX(DecimalNumber)              | 转十六进制                      |
  | LEAST(number,number2,[…..])     | 求最小值                        |
  | MOD(numberator,denominator)     | 求余                            |
  | RAND([seed])                    | RAND([seed])                    |

  ```mysql
  #ABS绝对值
  SELECT ABS(-10) FROM DUAL;-- -10
  #BIN 十进制转换二进制
  SELECT BIN(10) FROM DUAL;-- 1010
  #CEILING(NUM2)向上取整 得到比num2大的最小整数
  SELECT CEILING(0.3) FROM DUAL;-- 1
  SELECT CEILING(-2.3) FROM DUAL;-- -2
  #CONV进制转换
  -- 这个8是十进制的8,转换为二进制去输出
  SELECT CONV(8,10,2) FROM DUAL;
  -- 这个12是十六进制的12,转换为10进制去输出
  SELECT CONV(12,16,10) FROM DUAL;
  #FLOOR(num2)向下取整,得到比num2小的最大整数
  SELECT FLOOR (1.1) FROM DUAL;-- 1
  SELECT FLOOR (-1.1) FROM DUAL; -- -2
  #FORMAT保留小数位数(四舍五入)
  SELECT FORMAT (345.345354,4) FROM DUAL; -- 345.3454
  #LEAST 求最小值
  SELECT LEAST(21,325,-3432,43) FROM DUAL; -- -3432
  #MOD求余
  SELECT MOD (10,3) FROM DUAL; -- 1
  #RAND([seed])返回随机数 其范围在 0 < v < 1.0
  SELECT RAND() FROM DUAL;
  SELECT RAND(5) FROM DUAL; -- 一个固定的随机数
  ```

- 时间相关的函数

  | CURRENT_DATE()                              | 当前日期                           |
  | ------------------------------------------- | ---------------------------------- |
  | CURRENT_TIME()                              | 当前时间                           |
  | CURRENT_TIMESTAMP()                         | 当前时间戳                         |
  | DATE(datetime)                              | 返回datetime的日期部分             |
  | DATE_ADD(date2,INTERVAL d_value d_type)     | 在date2中加上日期或时间            |
  | DATE_SUB(date2,INTERVAL d_value d_type )    | 在date2中减去一个时间              |
  | DATEDIFF(date1,date2)                       | 俩个日期差(结果是天)               |
  | TIMEDIFF(date1,date2)                       | 俩个时间差(多少小时多少分钟多少秒) |
  | NOW()                                       | 当前时间                           |
  | YEAR\|MONTH\|DATE(datetime) FROM_UNIXTIME() | 年月日                             |
  | LAST_DAY(日期)                              | 可以返回该日期所在月份的最后一天   |

  ```mysql
  -- YEAR|MONTH|DAY|DATE(datatime)
  SELECT YEAR(NOW()) FROM DUAL;
  SELECT MONTH(NOW()) FROM DUAL;
  SELECT DAY(NOW()) FROM DUAL;
  SELECT DATE(NOW()) FROM DUAL;
  -- unix_timestamp() 返回是1970-1-1到现在的秒数
  SELECT UNIX_TIMESTAMP() FROM DUAL;
  -- FROM_UNIXTIME() 可以把unix_timestamp秒数.转换为指定格式的日期
  SELECT FROM_UNIXTIME(1705651061,'%Y-%m-%d') FROM DUAL;
  SELECT FROM_UNIXTIME(1705651061,'%Y-%m-%d  %H:%i:%s') FROM DUAL;
  -- 实际应用中, 往往会保存这个秒数,然后通过FROM_UNIXTIME转换
  ```

- 加密和系统函数

  | USER()        | 查询用户                                                     |
  | ------------- | ------------------------------------------------------------ |
  | DATABASE()    | 数据库名称                                                   |
  | MDs(str)      | 为字符串算出一个MD5 32的字符串,(用户密码)加密                |
  | PASSWORD(str) | 从原文密码str计算并返回密码字符串,通常用于对mysql数据库的用户密码加密 |

  ```MYSQL
  #加密函数和系统函数
  -- user()查询用户 可以查看登录mysql有哪些用户 以及登录ip
  SELECT USER() FROM DUAL;-- root@localhost -- 用户@ip地址
  #DATABASE() 查询当前使用的数据库名称
  SELECT DATABASE() FROM DUAL;
  #MD5(str)为字符串算出一个MD5  32的字符串 常用(用户密码)加密
  SELECT MD5('12345') FROM DUAL;-- 827ccb0eea8a706c4c34a16891f84e7b
  #PASSWORD(str) --加密函数
  SELECT PASSWORD('12345') FROM DUAL;-- *00A51F3F48415C7D4E8908980D443C29C69B60C9
  ```

- 流程控制函数

  | IF(expr1,expr2,expr3)                                        | 如果expr1为True,则返回expr2,否则返回expr3                    |
  | ------------------------------------------------------------ | ------------------------------------------------------------ |
  | IFNULL(expr1,expr2,)                                         | 如果expr1不为NULL,则返回expr1,否则返回expr2                  |
  | (SELECT CASE WHEN expr1 THEN expr2 WHEN expr3 THEN expr4 ELSE expr5 END) | 如果expr1为True,则返回expr2,如果expr3为True,则返回expr4,否则返回expr5 |

  ```mysql
  #如果comm为null,则显示0.0
  -- 判断是否为null要是 is null,判断不为null,使用is not
  SELECT ename,IF(comm IS NULL, 0.0 ,comm)
  		FROM emp;
  SELECT ename,IFNULL(comm,0.0)
  		FROM emp;
  -- 如果emp表的job是clerk 则显示职员,如果是MANAGER则显示经理,如果是SALESMAN 则显示销售人员,其他正常显示
  SELECT ename,(SELECT CASE WHEN job = 'CLERK' THEN '职员'
                WHEN job = 'MANAGER' THEN '经理' 
                WHEN job = 'SALESMAN' THEN '销售人员'
                ELSE job END) AS 'job'
                					    FROM emp;
  ```





## 1.8.MySQL加强查询

```mysql
#入职日期为大于1992-01-01			  
SELECT * FROM emp
	WHERE hiredate > '1992-01-01';
#LIKE操作符
-- %:表示0到多个任意字符
-- _:表示单个任意字符
#如何显示首字母为s的员工姓名和工资
SELECT ename, sal FROM emp WHERE ename LIKE 'S%';
#如何显示第三个字符为大写o的所有员工的姓名和工资
SELECT ename, sal FROM emp WHERE ename LIKE'__O%';
#如果判断某一列是否为null,用IS NULL 而不是 = NULL;

#order by 子句 的加强
SELECT * FROM emp 
	ORDER BY sal;
SELECT * FROM emp ORDER BY deptno ASC, sal DESC;
#先按照deptno排序,然后同一个的deptno的sal再进行排序
```

```mysql
#分页查询
-- 分页查询 按照id号升序取出 每页显示3条记录,请分别显示 第一页 第二页 第三页
-- 第一页
SELECT * FROM emp
	ORDER BY empno
	LIMIT 0,3;
-- 第二页
SELECT * FROM emp
	ORDER BY empno
	LIMIT 3,3;
-- 第三页
SELECT * FROM emp
	ORDER BY empno
	LIMIT 6,3;

-- 推导一个公式
SELECT * FROM emp
	ORDER BY empno
	LIMIT 每页显示记录数 * (第几页-1), 每页显示记录数;

#基本语法:select...limit start , rows
#表示从start+1行开始取,取出rows行,start从0开始计算
```

```mysql
#显示每种岗位的雇员总数,平均工资
SELECT * COUNT(*),AVG(sal),job
				 FROM emp
				 GROUP BY job;
#显示雇员总数,以及获得补助的雇员数
SELECT COUNT(*),COUNT(comm)
			 FROM emp
-- comm列为非null 就是count(列) 如果改列的值为null 是不会统计的
```

```mysql
SELECT column1 , column2, column3....
				FROM table
				GROUP BY column
				HAVING condition
				order by column
				limit start, rows;
```









## 1.9.多表查询

- 说明

  多表查询是指基于俩个和俩个以上的表查询,在实际应用中,查询单个表可能不满足你的需求.

  ```mysql
  #多表查询
  SELECT ename,sal,dname,emp,deptno
  			FROM emp,dept
  			WHERE emp.deptno = dept.deptno
  ```

- 规则

  在默认情况下:当查询俩张表的时候

  1. 从第一张表中,取出第一行 和第二张表的每一行进行组合,返回结果[含有俩张表的所有列]
  2. 一共返回的记录数 第一张表的行数*第二张表的行数
  3. 这样多表查询默认处理返回的结果,称为笛卡尔集
  4. 解决这个多表的关键就是要写出正确的过滤条件where,需要进行分析
  5. 多表查询的条件不少于 表的个数-1 否则会出现笛卡尔集



### 1.9.1自连接

```mysql
#多表查询的自连接
SELECT worker.ename AS '职员',boss.name AS '上级名'
				FROM emp worker, emp boss
				WHERE worker.mgr = boss.empno;
				-- AS可以省略 直接加在后面
```

自连接的特点:

1. **把一张表当做俩种表使用**
2. **需要给表取别名  表名 表别名**
3. **列名不明确 可以指定列的别名 列名 AS 列的别名**



### 1.9.2子查询

- 什么是子查询

  子查询是指嵌入在其他sql语句中的select语句,也叫嵌套查询

- 单行子查询

  单行子查询是指返回一行数据的子查询语句

- 多行子查询

  多行子查询是指返回多行数据的子查询 使用关键字in

```mysql
SELECT DISTINCT job
				FROM emp
				WHERE deptno = 10;
#先查询是10的部门有哪些工作
#再使语句完整
SELECT ename, job , sal, deptno
				FROM emp
				WHERE job IN (
          SELECT DISTINCT job
					FROM emp
					WHERE deptno = 10
        ) AND deptno != 10 -- 或者是 <>10 这里的10 不能为0 或者是null
```

- 在多行子查询中使用all操作符

  ```mysql
  #显示工资比部门30的所有员工的工资高的员工的姓名,工资和部门号
  SELECT ename, sal, deptno
  			FROM emp,
  			WHERE sal > ALL(
        					SELECT sal
          							FROM emp
          							WHERE deptno = 30
        					)
  ```

- 在多行子查询中使用any操作符

  ```mysql
  #显示工资比部门30的任意一个员工的工资高的员工的姓名,工资和部门号
  SELECT ename, sal, deptno
  			FROM emp,
  			WHERE sal > ANY(
        					SELECT sal
          							FROM emp
          							WHERE deptno = 30
        					)
  ```

- 子查询临时表

  ```mysql
  SELECT goods_id,ecs_goods.cat_id,goods_name,shop_price
  		FROM( SELECT cat_id,MAX(shop_price) AS max_price
          FROM ecs_goods
          GROUP BY cat_id
          ) temp, ecs_goods
          WHERE temp.cat_id = ecs_goods.cat_id
          AND temp.max_price = ecs_goods.shop_price
  ```

- 多列子查询

  是指查询返回多个列数据的子查询语句

  ```MYSQL
  SELECT * 
  			FROM emp
  			WHERE(deptno,job) = (
        SELECT deptno,job
        	FROM emp
        	WHERE ename = 'ALLEN' 
        )AND ename != 'ALLEN'
  -- 语句意思是WHERE中的deptno与 = 后面()中的条件中的deptno相等 和 job与 = 后面()中的条件中的job相等 才行
  ```

  ```mysql
  -- 还有一种写法就是 表名.* 表示将该表所有列都显示出来
  SELECT tmp.* 
  			FROM dept, (
        			SELECT COUNT(*) AS per_num, deptno
          		FROM emp
          		GROUP BY deptno
        ) tmp
        WHERE tmp.deptno = dept.deptno
  ```
  
  **在多表查询中单多个表的列不重复时,才可以直接写列名**





### 1.9.3.表复制

- 自我复制数据

  为了对某个sql语句进行效率测试,我们需要海量数据时,可以使用此法为表创建海量数据

```mysql
-- 如何进行自我复制
-- 1.先把emp表的记录复制到my_tab01
INSERT INTO my_tab01
		(id,`name`,sal,job,deptno)
		SELECT empno,ename,sal,job,deptno FROM emp;
-- 2.自我复制
INSERT INTO my_tab01
				SELECT * FROM my_tab01;
				
```

```mysql
-- 如何删除掉一张表重复记录
CREATE TABLE my_tab01 LIKE emp; -- 这个语句 把emp表的结构(列),复制到my_tab02
-- 思路
/*
  1.先创建一张临时表my_tmp ,该表的结构和my_tab02一样
  2.把my_tmp的记录 通过 distinct 关键字 处理后 把记录复制到my_tmp
  3.清除掉 my_tab02记录
  4.把my_tmp 表的记录复制到my_tab02
  5.drop掉临时表my_tmp
*/
CREATE TABLE my_tmp LIKE my_tab02;-- 先创建临时表
INSERT INTO my_tmp
				SELECT DISTINCT * FROM my_tab02;-- 通过关键字处理 把表的记录复制到my_tmp
DELETE FROM my_tab02;-- 清除掉记录 
INSERT INTO my_tab02
				SELECT * FROM my_tmp;-- 把表的记录复制到另一个表中
DROP TABLE my_tmp;-- 删除原来的临时表

SELECT * FROM my_tab02;-- 查询表
```







### 1.9.4.合并查询

**为了合并多个select语句的结果,可以使用集合操作符号union,union all来对查询的结果进行合并**

1. union all

   该操作符用于取得俩个结果集的并集,当使用该操作符时,并不会取消重复行.

   ```mysql
   SELECT ename,sal,job FROM emp WHERE sal > 2500 
   UNION ALL -- 不去重
   SELECT ename,sal,job FROM emp WHERE job ='MANAGER'
   ```

2. union

   该操作符与union all 相似 但是会自动去掉结果集中重复行

   ```mysql
   SELECT ename,sal,job FROM emp WHERE sal > 2500 
   UNION -- 去重
   SELECT ename,sal,job FROM emp WHERE job ='MANAGER'
   ```



## 1.10.外连接

- 外连接

  1. 如果左侧的表完全显示我们就说是**左外连接**

     SELECT  …. FROM 表1 LEFT JOIN 表2 ON 条件 (表1:左表  表2:右表 )

     ```mysql
     SELECT `name`, stu.id, grade
     			FROM stu LEFT JOIN exam
     			ON stu.id = exam.id;
     ```

  2. 如果右侧的表完全显示我们就说是**右外连接**

     右边的表和左表没有匹配的记录,也会把右表的记录显示出来

     ```mysql
     SELECT `name`, stu.id, grade
     			FROM stu RIGHT JOIN exam
     			ON stu.id = exam.id;
     ```

     **实际开发中,绝大多数使用是类连接,**



## 1.11.约束

- 主键的使用

  primary key(主键)-基本使用

  ```mysql
  CREATE TABLE t17
  				(id INT PRIMARY KEY, -- 表示id列是主键
          `name` VARCHAR(32),
          email VARCHAR(32));
  -- 主键列的值是不能重复的
  INSERT  INTO t17
  				VAKUES(1,`JACK`,`JACK@QQ.COM`);
  INSERT  INTO t17
  				VAKUES(1,`TOM`,`TOM@QQ.COM`);-- 不能添加进去
  ```

- 主键的细节

  1. **primary key 不能重复 而且不能为null**

     ```mysql
     INSETR INTO t17
     				VALUES(NULL,`TOM`.`TOM@QQ.COM`);
     ```

  2. 一张表最多**只能有一个主键 ,但是可以复合主键**

     ```mysql
     CREATE TABLE t18
     						(id INT,
                 `ename` VARCHAR(32),
                 email VARCHAR(32),
                 PRIMARY KEY(id,`name`) -- 这个就是复合主键
                 );
     ```

  3. 主键的使用方法只有俩种

     ```mysql
     CREATE TABLE t17
     				(id INT PRIMARY KEY, -- 表示id列是主键
             `name` VARCHAR(32),
             email VARCHAR(32));
             
     CREATE TABLE t18
     						(id INT,
                 `ename` VARCHAR(32),
                 email VARCHAR(32),
                 PRIMARY KEY(id,`name`) -- 这个就是复合主键
                 );
     ```

  4. 使用desc 表名 可以查看primary key 的情况

  5. 当一个表中有俩个或多个以上的主键,不能说id是一个主键,name是一个主键,只能说id+name是复合主键

- not null(非空)

  如果在列上定义了not null,那么当插入数据时,必须为列提供数据

  `字段名 字段类型 not null`

- unique(唯一)

  当定义了唯一约束后,该列值是不能重复的

  `字段名 字段类型 unique`

  1. 如果没有指定not null ,则unique字段可以有多个null
  2. 如果一个列(字段),是 unique not null 使用的效果类似 primary key

- foregin key (外键)

  用于定义主表和从表之间的关系:外键约束要定义在从表上,主表必须具有主键约束或是unique约束,当定义外键约束后,要求外键列数据必须在主表的主键列存在或是为null

  1. **外键指向的表的字段,要求是primary key 或者是unique**

  2. **表的类型是innodb,这样的表才支持外键**

  3. 外键的字段的类型要和主键的字段的类型一致(长度可以不同)

  4. **外键字段的值,必须在主键字段中出现过,或者为null(前提是外键字段允许为null)**

     ```mysql
     INSERT INTO my_stu
     						VALUES(4,`TOM`,NULL);-- 外键没有写not null
     ```

  5. **一旦建立外键的关系,数据不能随意删除了**

  ```mysql
  -- 创建主表 my_class
  CREATE TABLE my_class (
  						id INT PRIMARY KEY, -- 班级编号
  						`name`VARCHAR (32) NOT NULL DEFAULT '');
  -- 创建 从表my_stu
  CREATE TABLE my_stu (
  							id INT PRIMARY KEY, -- 学生编号
  							`name`VARCHAR (32) NOT NULL DEFAULT '',
  							class_id INT,
  							FOREIGN KEY (class _id) REFERENCES my_class(id))
  ```

- check 

  用于强制行数据必须满足的条件,假定在sal列上定义了check约束,并要求sal列值在1000-2000之间如果不在1000-2000之间就会提示出错

   ```mysql
   CREATE TABLE t23 (
   						id INT PRIMARY KEY,
     					`name` VARCHAR(32),
     					sex VARCHAR(6) CHECK(sex IN(`man`,`woman`)),
     					sal DOUBLE CHECK (sal > 1000 AND sal < 2000)
   )
   ```

- 自增长

  ```mysql
  -- 添加 自增长的字段方式
  insert into XXX(字段1,字段2....) values(null,'值'....);
  insert into XXX(字段2...) values('值1','值2'....);
  insert into XXX values(null,'值1',....)
  ```

  ```mysql
  字段名 整型 primary key auto_increment
  ```

  ```mysql
  -- 自增长的使用方法
  -- 创建表
  CREATE TABLE T1
  				(id INT PRIMARY KEY AUTO_INCREMENT, 
           -- id INT UNIQUE AUTO_INCREMENT
          email VARCHAR(32) NOT NULL DEFAULT ' ' ,
          `name` VARCHAR(32) NOT NULL DEFAULT ' ');
  DESC T1 -- 这里查询时候 这个表后面会显示extra -- auto_increment
  -- 自增长的使用
  INSERT INTO T1 
  				VALUES(NULL,'TOM@QQ.COM','TOM');
  				-- 当输入这个语法的时候 这个null并不会输入到表中 而是会进行语法判断 填的是11
  INSERT INTO T1
  				(email,`name`) VALUES('tom@qq.com','tom');
  				-- 由于id是自增长所以会直接按照当前的顺序往下依次输入2,3,4,5,6....
  ```

- 细节
  1. 自增长是和primary key 配合使用的
  2. 自增长也可以单独使用(但是需要配合一个unique)
  3. 自增长修饰的字段为整数型的(虽然小数也可以但是非常少使用)
  4. 自增长默认从1开始,也可以通过命令修改**alter table 表名 auto_increment = XXX;**
  5. 如果指定了自增长,就按照自增长的规则进行添加数据





## 1.12.索引

索引会形成一个索引的数据结构,比如二叉树

代价就是:磁盘的占用和对dml(update delete insert)语句效率的影响

```mysql
SELECT COUNT(*) FROM emp;
-- 没有创建索引时 我们的查询一条记录
SELECT * 
	FROM emp
	WHERE empno = 1234567
	
-- 使用索引来优化查询数据的功能
-- 在没创建索引前 emp.ibd文件大小为500m
-- 创索引后为600m
-- 索引本身也会占用空间
-- emp_index索引名称
-- ON emp(empno):表示在emp表的empno列创建索引
CREATE INDEX empno_index ON emp(empno)
-- 没有创建索引时间是4.5s 
-- 创建后时间为0.002s
```

- 索引的类型

  1. 主键索引,主键自动的为主索引(类型为primary key)

     ```mysql
     CREATE TABLE T1(
     id INT PRIMARY KEY, -- 主键,同时也是索引,称为主键索引
     `name` VARCHAR(32));
     
     -- 添加主键索引
     CREATE TABLE T1(
     				id INT,
     				`name` VARCHAR(32));
     ALTER TABLE T1 ADD PRIMARY KEY (id)
     ```

  2. 唯一索引(UNIQUE)

     ```MYSQL
     CREATE TABLE T1(
     id INT UNIQUE, -- id是唯一的,同时也是索引,称为unique索引
     `name` VARCHAR(32));
     -- 查询是否有索引
     SHOW INDEXES FROM T1
     -- 添加唯一索引
     CREATE UNIQUE INDEX id_index ON t1(id);
     ```

  3. 普通索引(INDEX)

     ```MYSQL
     -- 添加普通索引
     CREATE INDEX id_index ON t1(id)
     -- 如果某列的值是不会重复的,则优先考虑使用unique索引,否则使用普通索引
     -- 添加普通索引方式2
     ALTER TABLE T1 ADD INDEX id_index (id)
     ```

  4. 全文索引(FULLTEXT)(适用于MylSAM)

     使用其他索引:Solr和ElasticSearch(ES)

  5. 删除索引

     ```mysql
     -- 删除索引
     DROP INDEX id_index ON T1
     
     -- 删除主键索引
     ALTER TABLE T1 DROP PRIMARY KEY -- 不需要指定 只能有一个主键索引
     ```

  6. 查询索引

     ```mysql
     -- 1.方式1
     SHOW INDEX FROM T1
     -- 2.方式2
     SHOW INDEXES FROM T1
     -- 3.方式3
     SHOW KEYS FROM T1
     -- 4.方式4
     DESC T1
     ```

- 使用索引规则

  1. 较为频繁的作为查询条件字段应该创建索引
  2. 唯一性太差的字段不适合单独创建索引,即使频繁作为查询条件
  3. 更新非常频繁的的字段不适合创建索引
  4. 不会出现在WHERE子句中字段不该创建索引







## 1.13.事务

- 什么是事务

  事务用于保证数据的一致性,它由一组相关的dml语句组成,该组的dml语句要么全部成功,要么全部失败

- 事务和锁

  当执行事务操作时(dml语句,mysql会在表上加锁防止其他用户改表的数据)

- 数据库控制台事务的几个重要操作

  1. start transaction  开始一个事物
  2. savepoint 保存点名 设置保存点
  3. rollback to 保存点名 回退事务
  4. rollback 回退全部事务
  5. commit 提交事务,所有的操作生效 不能回退

  ```mysql
  -- 先创建一张表
  CREATE TABLE T1
  (id INT,
  `name` VARCHAR(32));
  -- 开始事务
  START TRANSACTION 
  -- 设置保存点
  SAVEPOINT A
  -- 执行dml操作
  INSERT INTO T1 VALUES (100,`TOM`);
  SELECT * FROM T1;
  SAVEPOINT B
  -- 执行dml操作
  INSERT INTO T1 VALUES (200,`JACK`);
  -- 回退到B
  ROLLBACK TO B;
  -- 回退到A
  ROLLBACK TO A;
  
  -- 如果这样,表示直接回退到事务开始的状态
  ROLLBACK;
  ```

- 回退事务

  保存点是事务中的点,用于取消部分事务,当结束事务时,会**自动删除该事物所定义的所有保存点**,当执行回退事务时,通过指定保存点可以回退到指定的点

- 提交事务

  使用commit语句可以提交事务,当执行commit语句子后,会确认事务的变化,结束事务,删除保存点,释放锁,数据生效,当使用commit语句 结束事务子后,其他会话将可以查看到事务变化后的新的数据

- 细节

  1. 如果不开始事务,默认情况下,dml操作是自动提交的,不能回滚
  2. 如果开始一个事物,你没有创建保存点,你可以执行rollback,默认就是回退到你事务开始的状态
  3. 你也可以在这个事务中(还没有提交时),创建多个保存点,比如:savepoint aaa;执行 dml, savepoint bbb;
  4. 你可以在事务没有提交前,选择回退到哪个保存点
  5. mysql的事物机制需要innodb的存储引擎还可以使用,myisam不好使
  6. 开始一个事物 start transaction,set  autocommit = off







## 1.14.事务隔离级别

- 查看事务隔离级别

  1. 脏读:当一个事物读取另一个事物为提交的修改时,产生脏读
  2. 不可重复读:同一查询在同一事物中多次进行,由于其他提交事务所的修改或删除,每次返回不同的结果集,此时发生不可重复度
  3. 幻读:同一查询在同一事物中多次进行,由于其他提交事务所做的插入操作,每次返回不同的结果集,此时发生幻读

- MYSQL隔离级别(4种)

  | MYSQL隔离级别              | 脏读 | 不可重复读 | 幻读 | 加锁读 |      |
  | -------------------------- | ---- | ---------- | ---- | ------ | ---- |
  | 读未提交(Read uncommitted) | √    | √          | √    | 不加锁 |      |
  | 读已提交(Read  committed)  | ×    | √          | √    | 不加锁 |      |
  | 可重复读(Repetable read)   | ×    | ×          | ×    | 不加锁 |      |
  | 可串行化(Serializable)     | ×    | ×          | ×    | 加锁   |      |

- 隔离级别代码

  ```mysql
  -- 1.查看当前会话隔离级别
  SELECT @@tx_isolation;
  -- 2.查看系统当前隔离级别
  SELECT @@global.tx_isolation;
  -- 3.设置当前会话隔离级别
  SET SESSION TRANSACTION ISOLATION LEVEL REPEATABLE READ;
  -- 4.设置系统当前隔离级别
  SET GLOBAL TRANSACTION ISOLATION LEVEL REPEATABLE READ;
  -- 5.MYSQL默认事物隔离级别是repetable read,一般情况下,没有特殊要求,没有必要修改(因为该级别可以满足大部分项目需求)
  ```

  ```mysql
  -- 全局修改默认的配置文件
  #可选参数有:READ-UNCOMMITTED,READ-COMMITTED,REPEATEBLE-READ,SERIALIZABLE
  [mysqld]
  transaction-isolation = REOEATABLE-READ
  ```

- 事务ACID

  1. 原子性(Atomicity)

     原子性是指事务是一个不可分割的工作单位,事务中的操作要么都发生,要么都不发生

  2. 一致性(Consistency)

     事务必须使数据库从一个一致性状态变换到另外一个一致性状态

  3. 隔离性(Isolation)

     事务的隔离性是多个用户并发访问数据库时,数据库为每一个用户开启的事务,不能被其他事务的操作数据所干扰,多个并发事物之间要相互隔离.

  4. 持久性(Durability)

     持久性是指一个事物一旦被提交,它对数据库中数据的改变就是永久性的,接下来即使数据库发生故障也不应该对其有任何影响



## 1.15.表类型和存储引擎

1. MYSQL的表类型由存储引擎(Storage Engines)决定 ,主要包括 MyLSAM,innoDB,Memory等
2. MySQL数据表主要支持六种类型,分别是:CSV,Memorny,ARCHIVE,MRG_MYISAM,MYISAM,InnoBDB.
3. 这六种又分为俩类,一类是“事务安全型”(transaction-safe),比如:InnoDB;其余都属于第二类,称为“非事务安全型”(myisam和memory)

```mysql
-- 查看所有的存储引擎
SHOW ENGINES
-- innodb 存储引擎 
-- 1.支持事务 2.支持外键 3.支持行级锁

-- myisam存储引擎
-- 1.添加速度快 2.不支持外键和事务 3.支持表级锁
CREATE TABLE T1(
					id INT,
					`name` VARCHAR(32)) ENGINE MYISAM;

-- memory 存储引擎
-- 1.数据存储在内存中(关闭就丢失) 2. 执行速度快(没有IO读写) 3.默认支持索引(hash读写)
```

- 如何选择表的存储引擎
  1. **如果你的应用不需要事务,处理的只是 基本的CRUD操作,那么MyISAM是不二选择,速度快**
  2. **如果需要支持事务,选择InnoDB**
  3. Memory存储引擎就是将数据存储在内存中 ,由于没有磁盘I/O的等待,速度极快,但由于是内存存储引擎,所做的任何修改在服务器重启后将消失
- 细节说明
  1. MyISAM不支持事务,也不支持外键,但其访问速度快,对事务完整性没有要求
  2. **InnoDB存储引擎提供了具有的提交,回滚和崩溃的恢复的能力的事物安全,但是比起MyISAM存储引擎,InnoDB写的效率差一些并且会占用更多的磁盘空间以保留数据和索引**
  3. MEMORY存储引擎使用存在内存中的内容来创建表,每个MEMORY表实际对应一个磁盘文件.MEMORY类型的表访问非常的快,因为它的数据是放在内存中的,**并且默认使用HASH索引,但是一旦MYSQL服务关闭,表中的数据就会丢失掉,表的结构还在.**





## 1.16.视图

- 基本概念

  视图是一个**虚拟表**,其**内容由查询定义**,同真实的表一样,视图包含列,其**数据来自对应的真实表**(基表)

  1. 视图是根据基表来创建的 **视图是虚拟的表**
  2. 视图也有列,**数据来自基表**
  3. **通过视图可以修改基表的数据**
  4. **基本的改变,也会影响到视图的数据**

- 基本使用

  ```mysql
  -- 1. CREATE VIEW 视图名 AS SELECT语句
  -- 2. ALTER VIEM 视图名 AS SELECT语句
  -- 3. SHOW CREATE VIEW 视图名
  -- 4. DROP VIEM 视图名1,视图名2
  ```

  ```mysql
  -- 创建视图
  CREATE VIEW emp_view01
  				AS
  				SELECT empno,ename,job,deptno FROM emp;
  
  -- 查看视图
  DESC emp_view01;
  SELECT * FROM emp_view01;
  SELECT empno, job FROM emp_view01;
  
  -- 查看创建视图的指令
  SHOW CREATE VIEW emp_view01;
  
  -- 删除视图
  DROP VIEW emp_view01;
  ```

- 细节

  1. 创建视图后,到数据库去看,对应的视图**只有一个视图结构文件**(形式:视图.frm)
  2. **视图和基表是映射的关系**
  3. **视图的数据变化会影响到基表,基表的数据变化也会影响到视图(insert update delete)**
  4. **视图中可以再使用视图**

- 视图最佳实践

  1. 安全
  2. 性能
  3. 灵活







## 1.17.MYSQL管理

- MySQL中的用户都存储在数据库MYSQL中的USER表中

- USER表的重要字段说明

  1. host:允许登录的“位置”.localhost表示该用户只允许本机登录,也可以指定IP地址
  2. user:用户名
  3. authentication_strin:密码,是通过MySQL的password()函数加密之后的密码

- 创建用户

  ```mysql
  -- 创建新的用户
  -- 'USER' 是用户名 'localhost' 登录的IP 123456是密码 但存放到mysql.user表时是password('123456')加密后的密码
  CREATE USER 'USER'@'localhost' IDENTIFIED BY '123456'
  SELECT * 
  				FROM mysql.user
  -- 修改自己的密码
  SET PASSWORD = PASSWORD('abcdef');
  
  -- 修改别人的密码
  -- root用户修改'hsp_edu'@'localhost'密码是可以成功的
   SET PASSWORD FOR 'hsp_edu'@'localhost' = PASSWORD('123456')
  ```

- 给用户权限

  - 基本语法

    grant 权限列表 on *.\* to ‘用户名’ @ ‘登录位置(IP)’ identified by ‘密码’

  - 说明

    1. 权限列表,多个权限用逗号分开

       grant select on…………..

       grant select , delete, create on ………..

       grant all (privileges) on …….. //表示赋予该用户在该对象上的所有权限

    2. 特别说明

       *.\*:代表本系统中的所有数据库的所有对象(表,视图,存储过程)

       库.*:表示某个数据库中的所有数据对象(表,视图,存储过程)

    3. identified by 可以省略,也可以写出

       1. 如果该用户存在,就是修改该用户的密码
       2. 如果该用户不存在,就是创建该用户

- 回收用户授权

  revoke 权限列表 on 库.对象名 from ‘用户名’@‘登录位置’

- 权限生效指令

  如果权限没有生效 可以执行下面的命令

  基本语法

  FLUSH PRIVILEGES;

- 细节说明

  1. 在创建用户的时候,如果不指定Host,则为%,%表示所有IP都有连接权限

     CREATE user XXX;

  2. 还可以这样指定

     CREATE user ‘XXX’ @‘192.168.1.%’ 表示XXX用户在192.168.1.*的ip都可以登录MYSQL

  3. 在删除用户的时候,如果host不是% 需要明确指定 ‘ 用户 ’ @ ‘ host值 ’



```mysql
SELECT ename,sal*12 Annual Salary FROM emp; -- fasle
SELECT ename,sal,comm FROM emp WHERE comm <> null; -- false
SELECT ename,sal,comm FROM emp WHERE comm IS NOT null; -- true
SELECT ename,sal,comm FROM emp WHERE comm <> 0; -- fasle
```

