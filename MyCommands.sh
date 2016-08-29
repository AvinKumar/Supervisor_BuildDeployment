#!/bin/sh
# v1.
# Dummy.
startSafe()
{
    zecho "Removing old logs"
    ssh -o ConnectTimeout=300 -p $NMS_PORT root@$NMS_IP "/opt/bin/distcmd --timeout 600 --cmd 'find /var/log/jboss.8090/log.0 -type f -mtime +5 -exec rm -f {} \; -print' --hostfile /opt/bin/etc/*.ip.lst"
	echo; echo '-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-='; echo
    zecho "Adding 10.0.108.1 to /opt/bin/etc/hosts.jboss3x file "
    ssh -o ConnectTimeout=300 -p $NMS_PORT root@$NMS_IP "echo 10.0.108.1 > /opt/bin/etc/hosts.jboss3x"
    ssh -o ConnectTimeout=300 -p $NMS_PORT root@$NMS_IP "/opt/bin/distcmd --timeout 600 --cmd 'service mysqld restart' --hostfile /opt/bin/etc/sc.ip.lst"
	echo; echo '-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-='; echo
    zecho "Starting Safe on $SIBNAME ..."
    STIME=$(date +%s)
    ssh -o ConnectTimeout=300 -p $NMS_PORT root@$NMS_IP "/opt/bin/startmeta"
    ssh -o ConnectTimeout=300 -p $NMS_PORT root@$NMS_IP "/opt/bin/dscontrol start all"
    ssh -o ConnectTimeout=300 -p $NMS_PORT root@10.0.172.1 "sh /var/log/timeout.sh -t 300 /opt/bin/dscontrol start all"
    ssh -o ConnectTimeout=300 -p $NMS_PORT root@10.0.204.1 "sh /var/log/timeout.sh -t 300 /opt/bin/dscontrol start all"
    ssh -o ConnectTimeout=300 -p $NMS_PORT root@$NMS_IP "/opt/bin/startac"
    ssh -o ConnectTimeout=300 -p $NMS_PORT root@10.0.70.1 "/opt/bin/dscontrol start all"
	echo; echo '-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-='; echo
	#releaseHoldPBJ
    ETIME=$(date +%s)
    let ELAPSE=$ETIME-$STIME
    zecho "$SIBNAME Safe started in $ELAPSE sec."
}