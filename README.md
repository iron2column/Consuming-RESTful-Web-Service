# Spring Guide

# 使用 RESTful Web 服务

## 依赖

`Spring Web`

## 思考：使用 Web 服务？

假设这里有一个已经建立起来的 RESTful 服务：（已失效）~~https://quoters.apps.pcfone.io/api/random~~

通过在浏览器中打开该链接，会得到一个 JSON 文档：

```json
{
   type: "success",
   value: {
      id: 10,
      quote: "Really loving Spring Boot, makes stand alone Spring apps easy."
   }
}
```

---

在浏览器中使用 Web 服务来获取资源是简单的，但是并不够用，有时候我们需要**通过编程的方式来访问（使用，consume）一个 Web 服务**
。

Spring 提供了一种简便的模版类（template class），叫做 `RestTemplate`，它能帮助开发者完成这类任务。

`RestTemplate` 使得与大多数 RESTful 服务的交互成为*单行咒语（one-line incantation）*。它甚至可以绑定数据到自定义领域类型。

## 建模：创建表示数据的领域类

我们知道 Web 服务的返回结果是其顶层包含了两个字段：`type` 和 `value`，因此我们**
创建一个能表示这个数据的领域类——`Quuote`:**

```java
public class Quote {
    private String type;
    private Value value;

    public Quote() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "type='" + type + '\'' +
                ", value=" + value +
                '}';
    }
}
```

这是一个简单的 Java 类，它拥有一些属性和对应的 getter 方法。

其中 `private Value value` 的 `Value` 是另一个亟需创建的领域类：

```java
public class Value {
    private long id;
    private String quote;

    public Value() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }


    @Override
    public String toString() {
        return "Value{" +
                "id=" + id +
                ", quote='" + quote + '\'' +
                '}';
    }
}
```

## 请求：使用 RestTemplate 处理

- `RestTemplate`：它使用 Jackson JSON 处理库来处理到达的数据。
- `CommandLineRunner`：它负责在启动时运行 `RestTemplate`（以此来获取请求）。

我们可以在项目入口处编写上述所需代码，也可另辟文件编写，简便起见，我们在主程序入口处编写——`DemoApplication.java`:

```java
@SpringBootApplication
public class DemoApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) {
        return args -> {
            Quote quote = restTemplate.getForObject("http://localhost:8081/greeting", Quote.class);
            System.out.println(quote);
        };
    }

}
```

🎉 之后只要运行项目，就可以在控制台看到输出！

---

简单明了，但是还有很多隐藏的机制需要理解：

- **构建 `RestTemplate`：**要使用 RestTemplate ，就需要先构建它——通过 `RestTemplateBuilder` 的实例方法 `build()` 方法。
- **构建 `CommandLineRunner`：**通过 Lambda 表达式返回一个 CommandLineRunner 以使用构建的 `RestTemplate`

> 需要注意的是，上面构建的都需要注册为 Spring 的 bean。本例使用 `@Bean` 注解的方式进行注册。
>
> 此外，注册`@Bean`的有效还需要在类上标记 `@Component`，但本例中 `@SpringBootApplication` 已经包含了此标记。

## 运行

直接运行项目或打包后运行 JAR 包，可在控制台中看到 Web 的请求结果。