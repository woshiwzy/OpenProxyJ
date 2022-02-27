chcp 65001
@echo off
echo "请确保代理ip和端口和配置文件一致"
echo start set proxy
reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\Internet Settings" /v ProxyEnable /t REG_DWORD /d 1 /f 
reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\Internet Settings" /v ProxyServer /d "127.0.0.1:8888" /f
echo "代理已经设置，本地程序已经开启，请勿关闭这个窗口，否则无法访问网络！\n 如需关闭代理，请用双击关闭脚本 close_proxy脚本"
java -jar MyNatClient.jar
pause>nul

