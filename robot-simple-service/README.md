## 基于netty和springboot的机器人框架

> 需要cqhttp插件支持

### 快速开始

- 安装cq和cqhttp插件[cqhttp的安装使用方法](https://cqhttp.cc/docs/4.14/#/?id=%E4%BD%BF%E7%94%A8%E6%96%B9%E6%B3%95)

  请配置post_url端口为80。

- java端开发

  依赖

  ```xml
  <dependency>
      <groupId>com.wymc.robot</groupId>
      <artifactId>robot-simple-service</artifactId>
      <version>0.0.1-SNAPSHOT</version>
  </dependency>
  ```

  启动类

  ```java
  package com.wymc.demo;
  
  import org.springframework.boot.SpringApplication;
  import org.springframework.boot.autoconfigure.SpringBootApplication;
  
  @SpringBootApplication
  public class HelloApplication {
  	public static void main(String[] args) {
  		SpringApplication.run(HelloApplication.class, args);
  	}
  }
  ```

  监听器

  ```java
  package com.wymc.demo.listener;
  
  import org.springframework.stereotype.Component;
  
  import com.wymc.robot.annotation.Filter;
  import com.wymc.robot.annotation.Listener;
  import com.wymc.robot.client.RobotClient;
  import com.wymc.robot.event.EventType;
  import com.wymc.robot.event.bean.message.PrivateMessageEvent;
  
  @Component
  public class HelloListener {
  
  	@Listener(EventType.PRIVATE)
  	@Filter("^hello.*")
  	public void hello(PrivateMessageEvent event, RobotClient client) {
  		client.sendPrivateMsg(event.getUserId(), "hello");
  	}
  }
  ```

  当接收到`hello`开头的消息时，会回复hello。

### 事件

cqhttp将群中的消息包装成一个POST请求，请求体是一个JSON，称之为事件。

事件对象与cqhttp中事件上报一章的内容基本相同。[cqhttp事件上报](https://cqhttp.cc/docs/4.14/#/Post)

事件有三种大类型：`message`, `notice`, `request`，`message`是聊天消息类型，`notice`是通知类型，`request`是好友请求、加群请求/邀请。事件对象中包含一个`eventType`字段，详细分为11种类型，被封装成了`EventType`枚举类。

每个事件都包含一个响应对象，这个响应对象用于快速返回事件的处理方式，在之后的文档中说明如何使用。

### 配置

自动配置以`robot.service`开头

`robot.service.host`: 配置java端将会接收的事件上报的ip，0.0.0.0将会接收所有

`robot.service.port`: 配置java端接收事件上报的端口

`robot.service.robots`: coolq端开放的http api配置，这是一个`list`配置项，支持配置多个coolq端

`robot.service.robots.host`: coolq端开放的http api的ip

`robot.service.robots.port`: coolq端开放的http api的port

### 注解

#### @Listener

```java
@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Listener {
	/**
	 * 监听类型，默认监听所有
	 * @return 需要监听的事件类型的数组
	 */
	@AliasFor(attribute = "value")
	EventType[] type() default EventType.ALL;

	@AliasFor(attribute = "type")
	EventType[] value() default EventType.ALL;

	/**
	 * 监听器执行优先级，数值越大优先级越低
	 * @return 数值表示的优先级
	 */
	int sort() default Integer.MAX_VALUE;

	/**
	 * 当这个监听器执行完成后，是否跳过优先级在之后的其他监听器的执行
	 * @return 是否跳过其他监听器执行
	 */
	boolean isBreak() default false;
}
```

#### @Filter

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Filter {
	/**
	 * 如果监听的消息是一个MESSAGE类型，将会通过正则进行匹配
	 * @return
	 */
	@AliasFor(attribute = "value")
	String regex() default ".*";

	@AliasFor(attribute = "regex")
	String value() default ".*";

	/**
	 * 拦截器
	 * @return
	 */
	Class<? extends EventFilter>[] filters() default {};
}
```

这个注解中的`EventFilter`是框架提供的一个拦截器接口，用于在事件执行前完成逻辑，这个拦截器的执行时期是在匹配到监听器之后（如果需要正则匹配的话），执行监听器之前。

```java
import com.wymc.robot.client.RobotClient;
import com.wymc.robot.event.Event;
import com.wymc.robot.server.filter.EventFilter;

public class TestFilter implements EventFilter{
	@Override
	public boolean doFilter(Event e, RobotClient client) {
		System.out.println("do something");
		return false;
	}
}
// 然后在注解上引入即可使用：@Filter(value = "hello", filters = TestFilter.class)
```

### RobotClient

这是框架提供的一个用于访问cqhttp api的类。在cqhttp api中定义的接口都可以访问。

其中以`get`和`_get`开头的所有api都是同步的，其他api都是默认异步，你可以通过`setAsync(false)`来设置为同步（但是不推荐），在默认情况下，这些api的返回值都是null，如果你设置为同步，他们将返回响应对象，在你需要响应来完成逻辑的时候可以使用。注意，调用`setAsync(false)`后，同一个coolq端的事件再次被响应时，它的值依然是`false`，所以如果不再需要同步的情况下请再调用`setAsync(false)`。

### CQCode

请先阅读：[cqhtp对于CQ码的说明](https://cqhttp.cc/docs/4.14/#/CQCode)

`CQCode`提供了从消息中获取`CQCode`的api和直接获取某种类型`CQCode`的方法。

```java
	/**
	 * 获取cq码类型
	 * @return
	 */
	public String getType();
	/**
	 * 获取cq码的值
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		return data.get(key);
	}
	/**
	 * 获取消息中的cqcode
	 * 
	 * @param message 消息内容
	 * @return 消息中的第一个cqcode
	 */
	public static CQCode getCQCode(String message);
	/**
	 * 获取消息中的所有cqcode
	 * 
	 * @param message 消息
	 * @return 消息中的所有cqcode数组
	 */
	public static List<CQCode> getCQCodes(String message);

	/**
	 * 获取系统表情的cqcode
	 * 
	 * @param id 系统表情id
	 * @return
	 */
	public static CQCode getFace(int id);
	/**
	 * 获取emoji表情的cqcode
	 * 
	 * @param id emoji表情的id
	 * @return
	 */
	public static CQCode getEmoji(int id);

	public static CQCode getBface(int id);

	/**
	 * 推送名片
	 * 
	 * @param type 名片类型 group private
	 * @param id   推送的id
	 * @return
	 */
	public static CQCode getContact(String type, int id);
	/**
	 * 
	 * @param id
	 * @return
	 */
	public static CQCode getSface(int id);
	/**
	 * 获取图片的cq
	 * 
	 * @param file data/image下的图片路径，或者网络图片url，或者是file
	 *             url(file:///****),又或者使用base64编码(base64://***)
	 * 
	 * @return
	 */
	public static CQCode getImage(String file);
	/**
	 * 获取语音
	 * 
	 * @param file  语音文件路径，同图片格式
	 * @param magic 是否变声
	 * @return
	 */
	public static CQCode getRecord(String file, boolean magic);
	/**
	 * 
	 * @param userId 被@的人的id
	 * @return
	 */
	public static CQCode getAt(long userId);
	/**
	 * 猜拳
	 * 
	 * @param type 1:石头 2:剪刀 3:布
	 * @return
	 */
	public static CQCode getRps(int type;
'
	/**
	 * 掷骰子
	 * 
	 * @param type 点数
	 * @return
	 */
	public static CQCode getDice(int type);
	/**
	 * 戳一戳 私聊有效
	 * 
	 * @param type
	 * @return
	 */
	public static CQCode getShake();
	/**
	 * 匿名，加在群消息开头
	 * 
	 * @param ignore 为 true 时，代表不强制使用匿名，如果匿名失败将转为普通消息发送。 为 false 或 ignore
	 *               参数被忽略时，代表强制使用匿名，如果匿名失败将取消该消息的发送。
	 * @return
	 */
	public static CQCode getAnonymous(boolean ignore);
	/**
	 * 位置分享
	 * 
	 * @param content 位置名称
	 * @param title   位置标题
	 * @param lat     经度
	 * @param lon     纬度
	 * @return
	 */
	public static CQCode getLocation(String content, String title, double lat, double lon) {
		CQCode code = new CQCode();
		code.setType("CQ:location");
		code.put("content", content);
		code.put("title", title);
		code.put("lat", String.valueOf(lat));
		code.put("lon", String.valueOf(lon));
		code.put("style", String.valueOf(1));
		return code;
	}

	/**
	 * 音乐分享
	 * 
	 * @param type qq或者163
	 * @param id   对应平台的音乐数字id
	 * @return
	 */
	public static CQCode getMusic(String type, long id);

	/**
	 * 自定义音乐分享
	 * 
	 * @param url      音乐分享链接，点击后将会跳转的页面
	 * @param audioUrl 音乐的链接
	 * @param title    音乐标题，建议12字以内
	 * @param content  音乐简介，建议30字以内
	 * @param image    音乐封面图片
	 * @return
	 */
	public static CQCode getCustomMusic(String url, String audioUrl, String title, String content, String image);
	/**
	 * 分享链接
	 * 
	 * @param url     跳转的链接
	 * @param title   标题
	 * @param content 简介
	 * @param image   分享封面
	 * @return
	 */
	public static CQCode getShare(String url, String title, String content, String image);
```

