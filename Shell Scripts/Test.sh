#!/bin/sh
# v1.0 23/04/2016 
#author: Avin

echo "-------- LDAP Users sync ---------------";

echo "~~~~~Logging to ID Server ~~~~~~~~~~~~~~";
#ssh root@10.0.93.1 "cd /avin/builds_92/;echo "Build Directory is::"; pwd;"; 
QASIB2_IDServer="$(ssh -o ConnectTimeout=300 -p 22 root@10.0.93.1 "bash -s" << EOF
cd /opt/svusertogroupmapping/;
./runSVUserToGroupsMappingDownload.sh;
cd /store/data/testdomain1;
#ls -ltr and search for desired ldif file.

#executing the below command
/opt/identitytools/tools/massageLDIF.sh s6user1.ldif avinJenkins testdomain1
ls -r * | grep avinJenkins- | head -1
  
EOF
)"
echo "";
echo "Latest build present in QASIB2 is::: $QASIB2_IDServer";

echo "------------------------------------------------------------------";


#~~~~~~~~~~~~~~~~~~delete below commaNDs ~~~~~~~~~~~~~~~~~~~
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


if [ "$Kahuna" == "$QASIB2_KS" ]
then
echo "Directory already exists in Kahuna and QASIB2";
else
echo "Directory does not exists in QASIB2";
echo "Copying of the latest build directory will be initiated";
#cd /store/builds/NIGHTLY_UPLOADS;
pwd;
echo $Kahuna; 
scp -rv $Kahuna root@16.150.57.113:/avin/builds_92/;
echo "Copied $Kahuna to QASIB2 build directory";
fi

