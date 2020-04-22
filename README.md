# robot-parent

依赖和版本管理

```shell
git clone https://github.com/Guigumua/robot-parent.git
cd robot-parent
mvn install
```

请查看wiki了解使用方法

[使用方法](https://github.com/Guigumua/robot-parent/wiki)

> 更新日志
>
> 1.0.2
>
> - 修复了在监听非聊天消息事件不配置@Filter就无法映射的bug
> - 增加了一些api
> - ListenerContext现在可以用他来保存一些数据
>
> 1.0.1-RELEASE
>
> - 为@Filter新增了一个matchTypeModel的参数
> - 现在RobotClient可以直接获取自己的qq号码了
> - 现在你可以配置一个全局RobotClient，由spring容器进行管理
> - 修复了上版本http的事件服务器解析事件返回null的bug
> - 为CQCode新增了一个removeAllCQ的方法
>
> 1.0.0-RELEASE
>
>  - 现在全局拦截器可以定制多个了
>
>  - 支持自定义的事件处理流程
>
>  - 对拦截器的参数定义做了细微调整
>
>  - 取消了ListenerContext中部分可能存在并发冲突的字段
>
>  - @Filter注解的参数增加，现在可以指定多个正则、指定qq号码和群号，以及匹配的方式
>
>  - 事件服务器的结构进行了优化
>
> 0.0.2-SNAPSHOT: 2020-04-18 17:21
>
> - 修复了getCQCodes只能获取一个CQCode的bug
> - 略微调整了一部分代码
>
> 0.0.1-RELEASE:发布第一个版本
