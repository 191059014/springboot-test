# springboot-test-springsecurity
spring-security
```
模拟两个用户zhangsan（密码是123）和lisi（密码是456）,对应角色权限是
zhangsan - r1 - p1
lisi - r2 - p2
其中：接口v1的访问需要p1权限，接口v2的访问需要p2权限
```
1. 将用户名(zhangsan)和密码(123)作为表单参数，请求登陆接口/login，拿到token；
2. 将token作为header参数，请求/v1接口，返回“成功访问v1”；
3. 将token作为header参数，请求/v2接口，返回“权限不足”；
4. 将token作为header参数，请求注销接口/logout，再次访问v1，返回“未登录”；
