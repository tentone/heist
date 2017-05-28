#!/bin/bash

user="sd0309"
pw="qwerty"
jar="Heist.jar"

#Install sshpass
#sudo apt-get install sshpass

#Copy $jar and configuration.txt to all computers
for i in "01" "02" "03" "04" "05" "07" "09" "10"
do
	echo "Copying $jar and configuration.txt to $user@l040101-ws$i.ua.pt"
	sshpass -p $pw scp "$jar" $user@l040101-ws$i.ua.pt:~
	sshpass -p $pw scp "configuration.txt" $user@l040101-ws$i.ua.pt:~
done

#l040101-ws05|23294|museum
echo "Starting Museum Server"
sshpass -p $pw ssh $user@l040101-ws05.ua.pt "nohup java -cp $jar heist.distributed.test.MuseumServerDistributed > /dev/null 2>&1 &"

#l040101-ws07|23295|assaultParty0
echo "Starting AssaultParty 0 Server"
sshpass -p $pw ssh $user@l040101-ws07.ua.pt "nohup java -cp $jar heist.distributed.test.AssaultPartyServerDistributed 0 > /dev/null 2>&1 &"

#l040101-ws10|23296|assaultParty1
echo "Starting AssaultParty 1 Server"
sshpass -p $pw ssh $user@l040101-ws10.ua.pt "nohup java -cp $jar heist.distributed.test.AssaultPartyServerDistributed 1 > /dev/null 2>&1 &"

#l040101-ws01|23291|logger
echo "Starting Logger Server"
sshpass -p $pw ssh $user@l040101-ws01.ua.pt "nohup java -cp $jar heist.distributed.test.LoggerServerDistributed > /dev/null 2>&1 &"

#l040101-ws03|23292|controlCollection
echo "Starting ControlCollection Site Server"
sshpass -p $pw ssh $user@l040101-ws03.ua.pt "nohup java -cp $jar heist.distributed.test.ControlCollectionSiteServerDistributed > /dev/null 2>&1 &"

#l040101-ws04|23293|concentration
echo "Starting Concetration Site Server"
sshpass -p $pw ssh $user@l040101-ws04.ua.pt "nohup java -cp $jar heist.distributed.test.ConcentrationSiteServerDistributed > /dev/null 2>&1 &"

#l040101-ws09 OrdinaryThieves
echo "Lauching OrdinaryThieves"
sshpass -p $pw ssh $user@l040101-ws09.ua.pt "nohup java -cp $jar heist.distributed.test.client.OrdinaryThiefClient > /dev/null 2>&1 &"

#l040101-ws02 MasterThief
echo "Lauching MasterThief"
sshpass -p $pw ssh $user@l040101-ws02.ua.pt "java -cp $jar heist.distributed.test.client.MasterThiefClient > /dev/null 2>&1"

#Getting 
echo "Getting log.txt file"
sshpass -p $pw scp $user@l040101-ws01.ua.pt:~/log.txt .

