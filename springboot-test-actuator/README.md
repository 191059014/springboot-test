# springboot-test-actuator
Springboot的健康检查组件
### endpoints的列表如下：  
| 请求方式 | 请求路径        | 描述                                         | 鉴权  |
| -------- | --------------- | -------------------------------------------- | ----- |
| GET      | /autoconfig     | 查看自动配置的使用情况                       | true  |
| GET      | /configprops    | 显示一个所有@ConfigurationProperties         | true  |
| GET      | /beans          | 显示一个应用中所有Spring Beans的完整列表     | true  |
| GET      | /dump           | 打印线程栈                                   | true  |
| GET      | /env            | 查看所有环境变量                             | true  |
| GET      | /env/{name}     | 查看具体变量值                               | true  |
| GET      | /health         | 查看应用健康指标                             | false |
| GET      | /info           | 查看应用信息                                 | false |
| GET      | /mappings       | 查看所有url映射                              | true  |
| GET      | /metrics        | 查看应用基本指标                             | true  |
| GET      | /metrics/{name} | 查看具体指标                                 | true  |
| POST     | /shutdown       | 允许应用以优雅的方式关闭（默认情况下不启用） | true  |
| GET      | /trace          | 查看基本追踪信息                             | true  |
