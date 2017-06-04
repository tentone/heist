#!/bin/bash

#Install sshpass
#sudo apt-get install sshpass

#Vars
jar="Heist.jar"
package="heist.distributed.run"


echo "----------------------------------------"
echo "           Distributed Systems"
echo "        University of Aveiro 2017"
echo "----------------------------------------"
echo "               Servers"
echo "----------------------------------------"

echo "Starting Museum Server"
nohup java -cp $jar $package.MuseumServerDistributed > museum.txt &
sleep 1

echo "Starting AssaultParty 0 Server"
nohup java -cp $jar $package.AssaultPartyServerDistributed 0 > assaultparty0.txt &
sleep 1

echo "Starting AssaultParty 1 Server"
nohup java -cp $jar $package.AssaultPartyServerDistributed 1 > assaultparty1.txt &
sleep 1

echo "Starting Logger Server"
nohup java -cp $jar $package.LoggerServerDistributed > logger.txt &
sleep 1

echo "Starting ControlCollection Site Server"
nohup java -cp $jar $package.ControlCollectionSiteServerDistributed > controlcollectonsite.txt &
sleep 1

echo "Starting Concetration Site Server"
nohup java -cp $jar $package.ConcentrationSiteServerDistributed > concentration.txt &
sleep 1

echo "----------------------------------------"
echo "               Clients"
echo "----------------------------------------"

echo "Lauching OrdinaryThieves"
nohup java -cp $jar $package.client.OrdinaryThiefClient > ordinary.txt &
sleep 1

echo "Lauching MasterThief"
java -cp $jar $package.client.MasterThiefClient > master.txt

echo "----------------------------------------"
echo "               Done"
echo "----------------------------------------"
