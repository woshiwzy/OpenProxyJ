
# 申明

本项目只是作者记录和分享Java网络编程学习心得，请勿用于非法用途，否则后果自负!

原理介绍博客：

https://blog.csdn.net/wang382758656/article/details/123098032

https://juejin.cn/post/7069218800226271239

# 介绍

纯Java实现的网络代理小工具，可以实现http网络协议隐藏，突破局域网拦截，上班就可以开心的摸鱼了...

当然 你也可以用来学习Socket通信，多线程编程，也可以用于简单的生产环境，实现正向代理，流量监控，上网行为监控等。

本项目原始基于idea，可导入idea直接运行。

# 如何使用

## 直接运行已经打包好的jar（快速使用）
[已经打包好的jar在这里](./out/artifacts/)

### 1.设置系统代理

要想看到效果，需要把你操作系统代理设置到 你LocalConfig.json文件设置的host和port上

设置代理方法，以win10 为例（Mac 自己搜索一下，也很简单，移动设备要使用这个代理的话，需要和运行这个代理程序客户端的主机在同一个局域网络
，并且把代理设置到这个主机的IP和配置端口上）

&nbsp;&nbsp;&nbsp;&nbsp;1.右击桌面右下角的wifi符号：选择弹出菜单“打开网络和internet设置“

&nbsp;&nbsp;&nbsp;&nbsp;2.接着在打开的“设置“窗口中，点击”代理“

&nbsp;&nbsp;&nbsp;&nbsp;3.在右侧的代理设置面板开启"使用代理服务器"开关

&nbsp;&nbsp;&nbsp;&nbsp;4.并且在地址一栏填写: 127.0.0.1,端口一栏填写，LocalConfig.json中配置的port值


如果是PC 或者Mac 上，上述步骤可以通过bat脚本完成 ,前提需要配置JRE环境变量，

**当然你也可以把JRE拷贝出来做一个整体包，直接点击脚本直接运行**

Win shell:open_proxy.bat,close_proxy.bat

Mac shell: open_proxy.sh,close_proxy.sh

### 2.运行Jar包

分别将配置文件和jar放在同一个目录

在服务器上，运行server: java -jar MyNatServer.jar（如果在一台电脑上既运行客户端又运行服务器端，仅仅相当于charles抓包效果）

在你的电脑上，运行client: java -jar MyNatClient.jar

到此，你可以打开浏览器访问"一些网站"试试看，


#源码运行

##客户端
运行本地代理端主方法

        com.proxy.local.starter.LocalStarter

以这个类为主类打包可执行jar,LocalConfig.json文件放在和这个jar同一个目录下
运行时直接 java -jar MyNatClient.jar 即可.

###客户端配置文件详解
LocalConfig.json 这个文件和jar包放在同一个目录下


        {
         "remoteServer": "xx.xx.xx.xx",
         "remotePort": "1667",
         "host": "127.0.0.1",
         "port": "1666"
        }

remoteServer:远程代理服务器的IP

remotePort:远程代理服务器的端口，必须和RemoteConfig.json 配置的端口一致

host:本地代理ip，永远为127.0.0.1(设置到系统代理的Ip)

port:本地代理端口,可以是任意本地没有使用的端口(设置到系统代理的端口)

***

##服务器端
运行在服务器的主类

        com.proxy.remote.starter.RemoteStarter

以这个类为主类打包可执行jar,RemoteConfig.json文件放在和这个jar同一个目录下
运行时直接 java -jar MyNatServer.jar 即可.



###服务器端的配置详解
        {
            "host": "127.0.0.1",
            "port": 1666,
            "responseHttps": "true",
            "enc": "true"
        }


host:远程代理服务器IP，因为是本地所以就是127.0.0.1" 永远不变，实际上你配置了也不会有任何影响

port:服务器接受客户端端网络请求端口（服务器端配置文件唯一需要修改的）

responseHttps:是否需要响应Https,永远是true(只有当做中间件的时候为false)

enc:是否加密，解密,建议永远是true


### 欢迎fork star ！！！
