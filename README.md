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
> 0.0.2-SNAPSHOT:修复了getCQCodes只能获取一个CQCode的bug,略微调整了一部分代码 2020-04-18 17:21
>
> 0.0.1-RELEASE:发布第一个版本
