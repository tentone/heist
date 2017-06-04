#!/bin/bash

#Install sshpass
#sudo apt-get install sshpass

#Login
user="sd0309"
pw="qwerty"

#Executables
jar="Heist.jar"
package="heist.distributed.run"

#Configuration
museum="05"
assaulta="07"
assaultb="10"
logger="01"
control="03"
concentration="04"
master="02"
thief="09"

echo "----------------------------------------"
echo "           Distributed Systems"
echo "        University of Aveiro 2017"
echo "        Deploy and run distributed"
echo "----------------------------------------"
echo "              Copy files"
echo "----------------------------------------"

#Copy $jar and configuration.txt to all servers
for i in "01" "02" "03" "04" "05" "06" "07" "08" "09" "10"
do
	echo "Copying $jar and configuration.txt to $user@l040101-ws$i.ua.pt"
	sshpass -p $pw scp "$jar" $user@l040101-ws$i.ua.pt:~
	sshpass -p $pw scp "configuration.txt" $user@l040101-ws$i.ua.pt:~
done

echo "----------------------------------------"
echo "               Servers"
echo "----------------------------------------"

echo "Starting Museum Server"
sshpass -p $pw ssh $user@l040101-ws$museum.ua.pt "nohup java -cp $jar $package.MuseumServerDistributed > museum.txt &"
sleep 1

echo "Starting AssaultParty 0 Server"
sshpass -p $pw ssh $user@l040101-ws$assaulta.ua.pt "nohup java -cp $jar $package.AssaultPartyServerDistributed 0 > assaultparty0.txt &"
sleep 1

echo "Starting AssaultParty 1 Server"
sshpass -p $pw ssh $user@l040101-ws$assaultb.ua.pt "nohup java -cp $jar $package.AssaultPartyServerDistributed 1 > assaultparty1.txt &"
sleep 1

echo "Starting Logger Server"
sshpass -p $pw ssh $user@l040101-ws$logger.ua.pt "nohup java -cp $jar $package.LoggerServerDistributed > logger.txt &"
sleep 1

echo "Starting ControlCollection Site Server"
sshpass -p $pw ssh $user@l040101-ws$control.ua.pt "nohup java -cp $jar $package.ControlCollectionSiteServerDistributed > controlcollectonsite.txt &"
sleep 1

echo "Starting Concetration Site Server"
sshpass -p $pw ssh $user@l040101-ws$concentration.ua.pt "nohup java -cp $jar $package.ConcentrationSiteServerDistributed > concentration.txt &"
sleep 1

echo "----------------------------------------"
echo "               Clients"
echo "----------------------------------------"

#l040101-ws09 OrdinaryThieves
echo "Lauching OrdinaryThieves"
sshpass -p $pw ssh $user@l040101-ws$thief.ua.pt "nohup java -cp $jar $package.client.OrdinaryThiefClient > ordinary.txt &"
sleep 1

#l040101-ws02 MasterThief
echo "Lauching MasterThief"
sshpass -p $pw ssh $user@l040101-ws$master.ua.pt "java -cp $jar $package.client.MasterThiefClient > master.txt"
sleep 1

echo "----------------------------------------"
echo "                 Get log"
echo "----------------------------------------"

echo "Getting log.txt file"
sshpass -p $pw scp $user@l040101-ws$logger.ua.pt:~/log.txt .

echo "Getting museum.txt stdout file"
sshpass -p $pw scp $user@l040101-ws$museum.ua.pt:~/museum.txt .

echo "----------------------------------------"
echo "               Done"
echo "----------------------------------------"
