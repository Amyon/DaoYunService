spring-boot demo

spring-boot工程demo，增加了一些公司内部开发相关的配置，方便开发使用。


spring-boot 配置


官方配置

https://start.spring.io，通过这个网站可以初始化spring-boot工程的配置信息，包括构建工具，起步依赖等。
由于公司软件仓库的原因，需要使用gradle。


公司配置

demo增加了一些公司内部开发相关的配置信息，如下：


添加公司的软件仓库，ivy，nexus
在bin目录添加了通用的启动脚本（JVM启动参数可以修改）
添加.gitignore文件（可以修改）
添加gradle wrapper（可以修改）
推荐日志使用logback
推荐使用spring profile
推荐使用java config的方式配置容器
web.xml中的servlet, filter等推荐使用ServletRegistrationBean，FilterRegistrationBean的方式
（默认project的名字是springboot，需要修改为实际开发的工程名字）



参考工程

https://newgitlab.corp.youdao.com/luna-dev/data-server