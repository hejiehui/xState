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
有些时候需要传递业务信息给状态机的各个触发器做判断，虽然缺省的Event类里面没有这些属性，但由于Event 是个普通类，用户可以自定义自己的Event子类，在子类里面定义需要的业务属性。允许时可以在各个Action里面cast 标准的Event 为你自定义的Event类即可获得额外的属性。

# 如何恢复状态机之前的状态
有时需要保存状态机的当前状态，并在之后恢复。可以通过调用StateMachine的restore（String id），传入需要恢复的状态id实现。

# 如何重置状态机
在状态机没有处于End状态的情况下，可以通过调用reset()方法重置状态机的状态。

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
