#!/bin/bash

#Login
user="sd0309"
pw="qwerty"

echo "----------------------------------------"
echo "         Clean ports"
echo "----------------------------------------"

#Copy $jar and configuration.txt to all servers
for i in "01" "02" "03" "04" "05" "06" "07" "08" "09" "10"
do
	echo "Clean $user@l040101-ws$i.ua.pt"
	sshpass -p $pw scp "clean.sh" $user@l040101-ws$i.ua.pt:~
	sshpass -p $pw ssh $user@l040101-ws$i.ua.pt "chmod +x clean.sh"
	#sshpass -p $pw ssh $user@l040101-ws$i.ua.pt "kill -9 $(lsof -t -i:22399)"
done

