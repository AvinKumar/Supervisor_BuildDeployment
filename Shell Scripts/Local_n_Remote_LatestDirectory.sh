#!/bin/sh
# v1.0 18/04/2016 
#author: Avin

echo "-------- Getting latest build directory from KAHUNA --------------";
cd /store/builds/NIGHTLY_UPLOADS;
echo "Build Directory is::";
pwd;
for i in $((ls -dr */) | grep successfulbuild9.2 | head -1); 
do echo ${i%%/};
echo "";
Kahuna=${i%%/};
echo "Latest build available in Kahuna is::: $Kahuna";
done;

echo "";
echo "-------- Getting latest build directory from QASIB2 --------------";

ssh root@16.150.57.113 "cd /avin/builds_92/;echo "Build Directory is::"; pwd;"; 
QASIB2_KS="$(ssh -o ConnectTimeout=300 -p 22 root@16.150.57.113 "bash -s" << EOF
cd /avin/builds_92/;
ls -dr * | grep successfulbuild9.2 | head -1
EOF
)"
echo "";
echo "Latest build present in QASIB2 is::: $QASIB2_KS";

echo "------------------------------------------------------------------";
