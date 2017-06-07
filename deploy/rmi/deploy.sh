#!/bin/bash

#Install sshpass
#sudo apt-get install sshpass

#Login
user="sd0309"
pw="qwerty"

#Executables
jar="Heist.jar"
package="heist.rmi.run"

#Registry configuration
registry="10" 
registryport="22399"

#Server configuration
logger="01"
loggerport="22391"

control="02"
controlport="22392"

concentration="03"
concentrationport="22393"

museum="04"
museumport="22394"

assaulta="09"
assaultaport="22396"

assaultb="06"
assaultbport="22397"

#Client configuration
master="07"
thieves="08"

echo "----------------------------------------"
echo "           Distributed Systems"
echo "        University of Aveiro 2017"
echo "           Deploy and Run RMI"
echo "----------------------------------------"
echo "         Generating configuration"
echo "----------------------------------------"

echo "Removing old configuration"
rm configuration.txt

echo "Generationg configuration"
echo "l040101-ws$logger.ua.pt;$loggerport;logger" >> configuration.txt
echo "l040101-ws$control.ua.pt;$controlport;controlCollection" >> configuration.txt
echo "l040101-ws$concentration.ua.pt;$concentrationport;concentration" >> configuration.txt
echo "l040101-ws$museum.ua.pt;$museumport;museum" >> configuration.txt
echo "l040101-ws$assaulta.ua.pt;$assaultaport;assaultParty0" >> configuration.txt
echo "l040101-ws$assaultb.ua.pt;$assaultbport;assaultParty1" >> configuration.txt

echo "----------------------------------------" 
echo "            Prepare files"
echo "----------------------------------------"

#Copy $jar and configuration.txt to all servers
for i in "01" "02" "03" "04" "06" "07" "08" "09" "10" #"05"
do
	echo "Copying files to $user@l040101-ws$i.ua.pt"
	sshpass -p $pw scp "$jar" $user@l040101-ws$i.ua.pt:~
	sshpass -p $pw scp "configuration.txt" $user@l040101-ws$i.ua.pt:~
	sshpass -p $pw scp "java.policy" $user@l040101-ws$i.ua.pt:~

	#echo "Extracting files to $user@l040101-ws$i.ua.pt"
	#sshpass -p $pw ssh $user@l040101-ws$i.ua.pt "unzip -o -q Heist.jar -d ."
done

echo "----------------------------------------"
echo "          Lauching RMI registry"
echo "----------------------------------------"
echo "Decompressing jar file"
sshpass -p $pw ssh $user@l040101-ws$registry.ua.pt "unzip -o -q Heist.jar -d ."
echo "Lauching RMI registry on $registry port $registryport"
sshpass -p $pw ssh $user@l040101-ws$registry.ua.pt "nohup rmiregistry -J-Djava.rmi.server.useCodebaseOnly=false -J-Djava.security.policy=java.policy -J-Djava.rmi.server.hostname=l040101-ws$registry.ua.pt $registryport > reg.txt &"
sshpass -p $pw ssh $user@l040101-ws$registry.ua.pt "nohup java -cp $jar -Djava.security.policy=java.policy heist.rmi.register.RegistryServer l040101-ws$registry.ua.pt $registryport > regserver.txt &"
sleep 1

echo "----------------------------------------"
echo "               Servers"
echo "----------------------------------------"

echo "Starting Museum Server on $museum"
sshpass -p $pw ssh $user@l040101-ws$museum.ua.pt "nohup java -cp $jar -Djava.security.policy=java.policy $package.server.MuseumRMI l040101-ws$registry.ua.pt $registryport false > museum.txt &"
sleep 1

echo "Starting AssaultParty 0 Server on $assaulta"
sshpass -p $pw ssh $user@l040101-ws$assaulta.ua.pt "nohup java -cp $jar -Djava.security.policy=java.policy $package.server.AssaultPartyRMI 0 l040101-ws$registry.ua.pt $registryport false > assaultparty0.txt &"
sleep 1

echo "Starting AssaultParty 1 Server on $assaultb"
sshpass -p $pw ssh $user@l040101-ws$assaultb.ua.pt "nohup java -cp $jar -Djava.security.policy=java.policy $package.server.AssaultPartyRMI 1 l040101-ws$registry.ua.pt $registryport false > assaultparty1.txt &"
sleep 1

echo "Starting ControlCollection Site Server $control"
sshpass -p $pw ssh $user@l040101-ws$control.ua.pt "nohup java -cp $jar -Djava.security.policy=java.policy $package.server.ControlCollectionSiteRMI l040101-ws$registry.ua.pt $registryport false > control.txt &"
sleep 1

echo "Starting Concetration Site Server $concentration"
sshpass -p $pw ssh $user@l040101-ws$concentration.ua.pt "nohup java -cp $jar -Djava.security.policy=java.policy $package.server.ConcentrationSiteRMI l040101-ws$registry.ua.pt $registryport false > concentration.txt &"
sleep 1

echo "Starting Logger Server on $logger"
sshpass -p $pw ssh $user@l040101-ws$logger.ua.pt "nohup java -cp $jar -Djava.security.policy=java.policy $package.server.LoggerRMI l040101-ws$registry.ua.pt $registryport false > logger.txt &"
sleep 1

echo "----------------------------------------"
echo "               Clients"
echo "----------------------------------------"

echo "Lauching OrdinaryThieves"
sshpass -p $pw ssh $user@l040101-ws$thieves.ua.pt "nohup java -cp $jar $package.client.OrdinaryThievesRMI l040101-ws$registry.ua.pt $registryport > ordinary.txt &"

echo "Lauching MasterThief"
sshpass -p $pw ssh $user@l040101-ws$master.ua.pt "java -cp $jar $package.client.MasterThiefRMI l040101-ws$registry.ua.pt $registryport > master.txt"

echo "----------------------------------------"
echo "                 Get log"
echo "----------------------------------------"

echo "Getting log.txt file"
sshpass -p $pw scp $user@l040101-ws$logger.ua.pt:~/log.txt .
sleep 4

echo "Creating out dir"
mkdir out

echo "Getting AssutltParties stdout file"
sshpass -p $pw scp $user@l040101-ws$assaulta.ua.pt:~/assaultparty0.txt out
sshpass -p $pw scp $user@l040101-ws$assaultb.ua.pt:~/assaultparty1.txt out

echo "Getting Museum stdout file"
sshpass -p $pw scp $user@l040101-ws$museum.ua.pt:~/museum.txt out

echo "Getting ControlCollectionSite stdout file"
sshpass -p $pw scp $user@l040101-ws$control.ua.pt:~/control.txt out

echo "Getting ConcetrationSite stdout file"
sshpass -p $pw scp $user@l040101-ws$concentration.ua.pt:~/concentration.txt out

echo "Getting Logger stdout file"
sshpass -p $pw scp $user@l040101-ws$logger.ua.pt:~/logger.txt out

echo "Getting Thieves stdout files"
sshpass -p $pw scp $user@l040101-ws$thieves.ua.pt:~/ordinary.txt out
sshpass -p $pw scp $user@l040101-ws$master.ua.pt:~/master.txt out

#echo "----------------------------------------"
#echo "             Kill RMI Registry"
#echo "----------------------------------------"

#for i in "01" "02" "03" "04" "05" "06" "07" "08" "09" "10"
#do
#	echo "Kill $user@l040101-ws$i.ua.pt"
#	sshpass -p $pw ssh $user@l040101-ws$i.ua.pt 'kill -9 $(lsof -t -i:22399)'
#done

echo "----------------------------------------"
echo "               Done"
echo "----------------------------------------"
