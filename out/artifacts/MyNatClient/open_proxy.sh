
trim(){
	trimmed=$1
	trimmed=${trimmed%% }
	trimmed=${trimmed## }

	echo $trimmed
}


row=$(lsof -i:1666 | wc -l)
irow=$(trim $row)
# echo ${irow}

if [ ${irow} -eq 0 ]; then
	echo "start jar....."
	nohup java -jar OPC_v1.7.jar &
	echo "jar starting..."	
else
	echo "jar has started"
fi

networksetup -setwebproxy "Wi-Fi" 127.0.0.1 1666
networksetup -setsecurewebproxy "Wi-Fi" 127.0.0.1 1666

networksetup -setsecurewebproxystate "Wi-Fi" on
networksetup -setwebproxystate "Wi-Fi" on

echo "ok you can surfing now..."
  
