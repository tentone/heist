#Copy Heist.jar and configuration.txt to all computers
for i in 01 03 04 05 07 09 10
do
	echo "Copying Heist.jar and configuration.txt to sd0309@l040101-ws$i.ua.pt"
	sshpass -p qwerty scp "Heist.jar" sd0309@l040101-ws$i.ua.pt:~
	sshpass -p qwerty scp "configuration.txt" sd0309@l040101-ws$i.ua.pt:~
done

#l040101-ws05|23294|museum
echo "Starting Museum Server"
sshpass -p qwerty ssh sd0309@l040101-ws05.ua.pt "nohup java -cp Heist.jar heist.distributed.test.MuseumServerTest > /dev/null 2>&1 &"

#l040101-ws07|23295|assaultParty0
#l040101-ws07|23296|assaultParty1
echo "Starting AssaultParty Servers"
sshpass -p qwerty ssh sd0309@l040101-ws07.ua.pt "nohup java -cp Heist.jar heist.distributed.test.AssaultPartyServerTest > /dev/null 2>&1 &"

#l040101-ws01|23291|logger
echo "Starting Logger Server"
sshpass -p qwerty ssh sd0309@l040101-ws01.ua.pt "nohup java -cp Heist.jar heist.distributed.test.LoggerServerTest > /dev/null 2>&1 &"

#l040101-ws03|23292|controlCollection
echo "Starting ControlCollection Site Server"
sshpass -p qwerty ssh sd0309@l040101-ws03.ua.pt "nohup java -cp Heist.jar heist.distributed.test.ControlCollectionSiteServerTest > /dev/null 2>&1 &"

#l040101-ws04|23293|concentration
echo "Starting Concetration Site Server"
sshpass -p qwerty ssh sd0309@l040101-ws04.ua.pt "nohup java -cp Heist.jar heist.distributed.test.ConcentrationSiteServerTest > /dev/null 2>&1 &"

#l040101-ws09 OrdinaryThieves
echo "Lauching OrdinaryThieves"
sshpass -p qwerty ssh sd0309@l040101-ws09.ua.pt "nohup java -cp Heist.jar heist.distributed.test.client.OrdinaryThiefClient > /dev/null 2>&1 &"

#l040101-ws10 MasterThief
echo "Lauching MasterThief"
sshpass -p qwerty ssh sd0309@l040101-ws10.ua.pt "java -cp Heist.jar heist.distributed.test.client.MasterThiefClient > /dev/null 2>&1"

echo "Getting log.txt file"
sshpass -p qwerty scp sd0309@l040101-ws01.ua.pt:~/log.txt .

