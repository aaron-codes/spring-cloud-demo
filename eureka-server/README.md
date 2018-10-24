### Pos 后端服务注册中心
> 使用 spring cloud eureka 作为注册中心

### Pos 后端模块说明

| 模块 |服务| 端口 | 备注 |仓库地址
| --- | --- | --- | --- | --- |
|  注册中心 | | 9214、9215、9216 | 集群服务，基于spring cloud eureka实现 |
|  配置中心 | POS-CONFIG-SERVER| 9212 | 获取全局配置信息，基于spring cloud config | |
|  熔断服务 |  POS-TURBINE-SERVER| 9217 | 服务熔断监控服务-基于hystrix、hystrix-dashboard、turbine实现| |
|  订单模块 | | - |  | |
|  商品模块 | | - |  | |
|  统计模块 | | - |  | |
|  第三方业务交互 | | - |  | |
|  Pos Api | | - |  | |
|  Boss Api| | - |  | |
|  CUser | | - |  | |
