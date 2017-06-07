#!/bin/bash

#-Djava.security.policy=java.policy

#Install sshpass
#sudo apt-get install sshpass

#Login
user="sd0309"
pw="qwerty"

#Executables
jar="Heist.jar"
package="heist.rmi.run"

#Registry configuration
registryport="22399"

#Server configuration
logger="01"
loggerport="22391"

control="03"
controlport="22392"

concentration="04"
concentrationport="22393"

museum="05"
museumport="22394"

assaulta="07"
assaultaport="22395"

assaultb="10"
assaultbport="22396"

#Client configuration
master="02"
thieves="09"

echo "----------------------------------------"
echo "           Distributed Systems"
echo "        University of Aveiro 2017"
echo "           Deploy and Run RMI"
echo "----------------------------------------"
echo "            Prepare files"
echo "----------------------------------------"

#Copy $jar and configuration.txt to all servers
for i in "01" "02" "03" "04" "05" "06" "07" "08" "09" "10"
do
	echo "Copying files to $user@l040101-ws$i.ua.pt"
	sshpass -p $pw scp "$jar" $user@l040101-ws$i.ua.pt:~
	sshpass -p $pw scp "configuration.txt" $user@l040101-ws$i.ua.pt:~
	sshpass -p $pw scp "java.policy" $user@l040101-ws$i.ua.pt:~

	#echo "Extracting files to $user@l040101-ws$i.ua.pt"
	#sshpass -p $pw ssh $user@l040101-ws$i.ua.pt "unzip -o -q Heist.jar -d ."
done

echo "----------------------------------------"
echo "               Servers"
echo "----------------------------------------"

echo "Starting Museum Server"
#sshpass -p $pw ssh $user@l040101-ws$museum.ua.pt "nohup rmiregistry -J-Djava.rmi.server.useCodebaseOnly=false $registryport > reg.txt &"
sshpass -p $pw ssh $user@l040101-ws$museum.ua.pt "nohup java -cp $jar -Djava.security.policy=java.policy $package.MuseumRMI l040101-ws$museum.ua.pt $registryport true > museum.txt &"
sleep 1

echo "Starting AssaultParty 0 Server"
#sshpass -p $pw ssh $user@l040101-ws$assaulta.ua.pt "nohup rmiregistry -J-Djava.rmi.server.useCodebaseOnly=false $registryport > reg.txt &"
sshpass -p $pw ssh $user@l040101-ws$assaulta.ua.pt "nohup java -cp $jar -Djava.security.policy=java.policy $package.AssaultPartyRMI 0 l040101-ws$assaulta.ua.pt $registryport true > assaultparty0.txt &"
sleep 1

echo "Starting AssaultParty 1 Server"
#sshpass -p $pw ssh $user@l040101-ws$assaultb.ua.pt "nohup rmiregistry -J-Djava.rmi.server.useCodebaseOnly=false $registryport > reg.txt &"
sshpass -p $pw ssh $user@l040101-ws$assaultb.ua.pt "nohup java -cp $jar -Djava.security.policy=java.policy $package.AssaultPartyRMI 1 l040101-ws$assaultb.ua.pt $registryport true > assaultparty1.txt &"
sleep 1

echo "Starting Logger Server"
#sshpass -p $pw ssh $user@l040101-ws$logger.ua.pt "nohup rmiregistry -J-Djava.rmi.server.useCodebaseOnly=false $registryport > reg.txt &"
sshpass -p $pw ssh $user@l040101-ws$logger.ua.pt "nohup java -cp $jar -Djava.security.policy=java.policy $package.LoggerRMI l040101-ws$logger.ua.pt $registryport true > logger.txt &"
sleep 1

echo "Starting ControlCollection Site Server"
#sshpass -p $pw ssh $user@l040101-ws$control.ua.pt "nohup rmiregistry -J-Djava.rmi.server.useCodebaseOnly=false $registryport > reg.txt &"
sshpass -p $pw ssh $user@l040101-ws$control.ua.pt "nohup java -cp $jar -Djava.security.policy=java.policy $package.ControlCollectionSiteRMI l040101-ws$control.ua.pt $registryport true > controlcollectonsite.txt &"
sleep 1

echo "Starting Concetration Site Server"
#sshpass -p $pw ssh $user@l040101-ws$concentration.ua.pt "nohup rmiregistry -J-Djava.rmi.server.useCodebaseOnly=false $registryport > reg.txt &"
sshpass -p $pw ssh $user@l040101-ws$concentration.ua.pt "nohup java -cp -Djava.security.policy=java.policy $jar $package.ConcentrationSiteRMI l040101-ws$concentration.ua.pt $registryport true > concentration.txt &"
sleep 1

echo "----------------------------------------"
echo "               Clients"
echo "----------------------------------------"

echo "Lauching OrdinaryThieves"
sshpass -p $pw ssh $user@l040101-ws$thieves.ua.pt "nohup java -cp $jar $package.client.OrdinaryThiefRMI > ordinary.txt &"
sleep 1

echo "Lauching MasterThief"
sshpass -p $pw ssh $user@l040101-ws$master.ua.pt "java -cp $jar $package.client.MasterThiefRMI > master.txt"
sleep 1

echo "----------------------------------------"
echo "                 Get log"
echo "----------------------------------------"

echo "Getting log.txt file"
sshpass -p $pw scp $user@l040101-ws$logger.ua.pt:~/log.txt .

echo "Creating out dir"
mkdir out

echo "Getting AssutltParties stdout file"
sshpass -p $pw scp $user@l040101-ws$assaulta.ua.pt:~/assaultparty0.txt out
sshpass -p $pw scp $user@l040101-ws$assaultb.ua.pt:~/assaultparty1.txt out

echo "Getting Museum stdout file"
sshpass -p $pw scp $user@l040101-ws$museum.ua.pt:~/museum.txt out

echo "Getting Thieves stdout files"
sshpass -p $pw scp $user@l040101-ws$thieves.ua.pt:~/ordinary.txt out
sshpass -p $pw scp $user@l040101-ws$master.ua.pt:~/master.txt out

echo "----------------------------------------"
echo "               Done"
echo "----------------------------------------"
