# 一个简单的 SpringBoot 初始化模板

## 前言

你发现这个初始化模板说明咱们就是有缘的, 那么请动动你的小手指点一下 Star 吧, 这将是我最大的动力!

## 快速开始

1. 安装好 JDK、MySQL、Redis
2. 克隆当前项目
```git
git clone git@github.com:SilasYan/silas-boot-init.git
```
3. 执行 `sql` 下的 `silas_boot_init.sql` 文件
4. 修改 `application.yml` 文件; 或新建 `application-dev.yml` 文件
5. 启动 `BootInitApplication.java`

## 环境、依赖

| 集成内容        | 版本         | 说明        |
|-------------|------------|-----------|
| JDK         | 17         | 开发环境      |
| SpringBoot  | 3.0.2      | 框架        |
| MySQL       | 8.0.33     | 数据库       |
| Redis       | 7.2.0      | 缓存        |
| MyBatisPlus | 3.5.9      | ORM 框架    |
| SaToken     | 1.39.0     | 权限认证框架    |
| Knife4j     | 4.4.0      | 接口文档      |
| Gson        | 2.11.0     | JSON 工具   |
| Guava       | 33.4.0-jre | 通用工具      |
| HuTool      | 5.8.26     | 通用工具      |
| EasyCaptcha | 1.6.2      | 验证码工具     |
| Redisson    | 3.17.4     | 分布式锁框架    |
| Jsoup       | 1.15.3     | HTML 解析工具 |

## 目录结构

```
src
├── main
│   ├── java
│   │   └── com.silas.init
│   │       ├── BootInitApplication.java                                    - 项目启动主类
│   │       │
│   │       ├── annotation
│   │       │   └── AuthCheck.java                                         - 权限校验注解
│   │       │
│   │       ├── aop
│   │       │   ├── AuthCheckAspect.java                                   - 权限校验切面实现
│   │       │   └── RequestAspect.java                                     - 请求处理切面
│   │       │
│   │       ├── auth                                                       - 认证授权
│   │       │   ├── SaTokenAuth.java                                       - Sa-Token认证核心类
│   │       │   ├── SaTokenConfig.java                                     - Sa-Token配置类
│   │       │   └── SaTokenUtil.java                                       - Sa-Token工具类
│   │       │
│   │       ├── common                                                     - 通用功能包
│   │       │   ├── enums                                                  - 枚举类包
│   │       │   │   └── BaseEnum.java                                      - 枚举基类
│   │       │   │
│   │       │   ├── exception                                              - 异常处理包
│   │       │   │   ├── BusinessException.java                             - 业务异常类
│   │       │   │   ├── GlobalExceptionHandler.java                        - 全局异常处理器
│   │       │   │   └── ThrowUtil.java                                     - 异常工具类
│   │       │   │
│   │       │   ├── model                                                  - 基础模型包
│   │       │   │   ├── DeleteRequest.java                                 - 删除请求模型
│   │       │   │   ├── DeleteStatusEnum.java                              - 删除状态枚举
│   │       │   │   └── IdRequest.java                                     - ID请求模型
│   │       │   │
│   │       │   ├── page                                                   - 分页相关包
│   │       │   │   ├── PageRequest.java                                   - 分页请求模型
│   │       │   │   └── PageResponse.java                                  - 分页响应模型
│   │       │   │
│   │       │   ├── redis                                                  - Redis相关包
│   │       │   │   ├── RedisConfig.java                                   - Redis配置类
│   │       │   │   └── RedisUtil.java                                     - Redis工具类
│   │       │   │
│   │       │   ├── redisson                                               - Redisson相关包
│   │       │   │   ├── RedissonConfig.java                                - Redisson配置类
│   │       │   │   └── RedissonProperties.java                            - Redisson属性配置
│   │       │   │
│   │       │   ├── request                                                - 基础请求模型包
│   │       │   │   └── DeleteRequest.java                                 - 删除请求模型
│   │       │   │
│   │       │   ├── response                                               - 响应相关包
│   │       │   │   ├── BaseResponse.java                                  - 基础响应模型
│   │       │   │   ├── RespCode.java                                      - 响应码枚举
│   │       │   │   └── Result.java                                        - 统一返回结果封装
│   │       │   │
│   │       │   ├── thread                                                 - 线程相关包
│   │       │   │   └── ThreadPoolConfig.java                              - 线程池配置
│   │       │   │
│   │       │   └── utils                                                  - 工具类包
│   │       │       ├── EmailUtil.java                                     - 邮件工具类
│   │       │       ├── LambdaUtil.java                                    - Lambda工具类
│   │       │       └── ServletUtil.java                                   - Servlet工具类
│   │       │
│   │       ├── config                                                     - 配置类包
│   │       │   ├── CorsConfig.java                                        - 跨域配置
│   │       │   ├── EncodingConfig.java                                    - 编码配置
│   │       │   ├── JsonConfig.java                                        - JSON序列化配置
│   │       │   └── MyBatisPlusConfig.java                                 - MyBatis-Plus配置
│   │       │
│   │       ├── constants                                                  - 常量类包
│   │       │   ├── BaseConstant.java                                      - 基础常量
│   │       │   ├── KeyConstant.java                                       - 键名常量
│   │       │   └── TextConstant.java                                      - 文本常量
│   │       │
│   │       ├── controller                                                 - 控制器包
│   │       │   ├── MainController.java                                    - 主控制器
│   │       │   └── UserController.java                                    - 用户控制器
│   │       │
│   │       ├── manager                                                    - 管理层包
│   │       │   └── EmailManager.java                                      - 邮件管理类
│   │       │
│   │       └── module                                                     - 业务模块包
│   │           ├── main                                                   - 主业务模块
│   │           │   ├── entity                                             - 实体类包
│   │           │   │   ├── request                                        - 请求模型包
│   │           │   │   │   └── SendEmailCodeRequest.java                  - 发送邮件验证码请求
│   │           │   │   └── vo                                             - 视图对象包
│   │           │   │       └── CaptchaVO.java                             - 验证码视图对象
│   │           │   └── service                                            - 服务层
│   │           │       ├── MainService.java                               - 主业务服务接口
│   │           │       └── impl                                           - 服务实现包
│   │           │           └── MainServiceImpl.java                       - 主业务服务实现
│   │           │
│   │           └── user                                                   - 用户模块
│   │               ├── assembler                                          - 装配器包
│   │               │   └── UserAssembler.java                             - 用户对象转换器
│   │               ├── entity                                             - 实体类包
│   │               │   ├── DO                                             - 数据库实体包
│   │               │   │   ├── User.java                                  - 用户实体
│   │               │   │   └── UserLoginLog.java                          - 用户登录日志实体
│   │               │   ├── enums                                          - 枚举类包
│   │               │   │   ├── UserDisabledEnum.java                      - 用户禁用状态枚举
│   │               │   │   └── UserRoleEnum.java                          - 用户角色枚举
│   │               │   ├── request                                        - 请求模型包
│   │               │   │   ├── UserBanRequest.java                        - 用户封禁请求
│   │               │   │   ├── UserLoginRequest.java                      - 用户登录请求
│   │               │   │   ├── UserQueryRequest.java                      - 用户查询请求
│   │               │   │   ├── UserRegisterRequest.java                   - 用户注册请求
│   │               │   │   ├── UserUpdatePasswordRequest.java             - 用户密码更新请求
│   │               │   │   └── UserUpdateRequest.java                     - 用户信息更新请求
│   │               │   └── vo                                             - 视图对象包
│   │               │       ├── UserInfoVO.java                            - 用户信息视图对象
│   │               │       └── UserVO.java                                - 用户视图对象
│   │               ├── mapper                                             - 数据访问层
│   │               │   ├── UserLoginLogMapper.java                        - 用户登录日志Mapper
│   │               │   └── UserMapper.java                                - 用户Mapper
│   │               └── service                                            - 服务层
│   │                   ├── UserLoginLogService.java                       - 用户登录日志服务接口
│   │                   ├── UserService.java                               - 用户服务接口
│   │                   └── impl                                           - 服务实现包
│   │                       ├── UserLoginLogServiceImpl.java               - 用户登录日志服务实现
│   │                       └── UserServiceImpl.java                       - 用户服务实现
│   │
│   └── resources                                                          - 资源文件目录
│       ├── application-dev.yml                                            - 开发环境配置
│       ├── application-prod.yml                                           - 生产环境配置
│       ├── application.yml                                                - 主配置文件
│       ├── banner.txt                                                     - 启动Banner文本
│       └── logback-spring.xml                                             - 日志配置文件
│           ├── email                                                      - 邮件模板目录
│           │   ├── RegisterSuccess.html                                   - 注册成功邮件模板
│           │   └── SendCode.html                                          - 验证码邮件模板
│           └── mapper                                                     - MyBatis映射文件目录
│               ├── UserLoginLogMapper.xml                                 - 用户登录日志SQL映射
│               └── UserMapper.xml                                         - 用户SQL映射                               -
```

