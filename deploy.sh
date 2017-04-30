

for i in 01 03 04 05 07 09 10
do
	echo "Copying heist.jar to sd0309@l040101-ws$i.ua.pt";
	sshpass -f qwerty scp "heist.jar" sd0309@l040101-ws$i.ua.pt:~;
	#sshpass -f qwerty ssh sd0309@l040101-ws$i.ua.pt 'unzip -o "dist.zip"';
done

xterm -hold -e "echo 'Server'; sshpass -f qwerty ssh sd0309@l040101-ws01.ua.pt 'cd dist; java -cp HTTM_TCPSockets.jar server/Run_Server 22307'" &
sleep 1;
xterm -hold -e "echo 'Ordinary Thieves'; sshpass -f qwerty ssh sd0309@l040101-ws03.ua.pt 'cd dist; java -cp HTTM_TCPSockets.jar client/ClientThieves 22307 l040101-ws01.ua.pt'" &
sleep 1;
xterm -hold -e "echo 'Master Thief'; sshpass -f qwerty ssh sd0309@l040101-ws04.ua.pt 'cd dist; java -cp HTTM_TCPSockets.jar client/ClientMasterThief 22307 l040101-ws01.ua.pt'" &
sleep 1;

#echo "Getting log.txt file";
#sshpass -f qwerty scp sd0309@l040101-ws05.ua.pt:~/src/log.txt .;

#java -cp Heist.jar heist.distributed.test.MuseumServerTest
#java -cp Heist.jar heist.distributed.test.MuseumServerTest
