xState
======

A state machine editot and runtime. Can be used to model simple workflow

# 简介
xState编辑器是一个允许开发人员创建状态机的编辑器，通过通用直观的解决方案。
![overview](https://github.com/hejiehui/xState/blob/master/doc/overview.png)

# 适用场景
状态机用处极其广泛，适用于订单，用户，任务等等具有确定状态的领域模型


# 特点

1. 结合模型和代码
1. 可以创建仅包含状态和变迁的状态机
1. 也可以提供状态变迁时的触发器

# 状态转移触发器
1. EntryAction
1. ExitAction
1. TransitionAction

## 状态转移校验
1. TransitionGuard
![event](https://github.com/hejiehui/xState/blob/master/doc/events.png)

# 使用范例
模型可以被工具用于在运行时触发状态转移
![sample](https://github.com/hejiehui/xState/blob/master/doc/sample.png)

# 如何传递业务属性
Event 是个普通类，这个类会传递给各个action，所以你只要定义自己的event子类，子类里面定义业务属性，action里面cast Event 为你自定义的类即可获得额外的属性

# 集成说明
[参考样例POM](https://github.com/hejiehui/xState/blob/master/com.xrosstools.xstate.sample/pom.xml)

Depenency

	<dependency>
		<groupId>com.xrosstools</groupId>
		<artifactId>xstate</artifactId>
		<version>0.9.0</version>
	</dependency>

repository

	<repositories>
		<repository>
			<id>xtools-repo</id>
			<url>https://raw.github.com/hejiehui/xtools-repo/mvn-repo/</url>
			<snapshots>
				<enabled>true</enabled>
				<updatePolicy>always</updatePolicy>
			</snapshots>
		</repository>
	</repositories>

# Demo project
[Demo](https://github.com/hejiehui/xState/tree/master/com.xrosstools.xstate.sample)

# 实际案例
## 简单状态机
![uc1](https://github.com/hejiehui/xState/blob/master/doc/uc1.png)

## 复杂状态机
![uc2](https://github.com/hejiehui/xState/blob/master/doc/uc2.png)
