#!/bin/sh
# v1.0 22/04/2016 
#author: Avin

echo "-------- Getting latest build directory from KAHUNA --------------";
cd /store/builds/NIGHTLY_UPLOADS;
echo "Build Directory is::";
pwd;
#To get builds from main brach
#for i in $((ls -td */) | grep successfulbuildmain | head -1 | cut -d'/' -f1);
#To get builds from 9.3 branch
for i in $((ls -td */) | grep successfulbuild9.3 | head -1 | cut -d'/' -f1);
do echo ${i%%/};
echo "";
Kahuna=${i%%/};
echo "Latest build available in Kahuna is::: $Kahuna";
done;

echo "";
echo "-------- Getting latest build directory from QASIB1 --------------";

#Just change the build directory name to the one which is uncommented above. (Default: successfulbuildmain)
ssh root@16.150.57.108 "cd /clatest/;echo "Build Directory is::"; pwd;"; 
QASIB1_KS="$(ssh -o ConnectTimeout=300 -p 22 root@16.150.57.108 "bash -s" << EOF
cd /clatest/;
((ls -td */) | grep successfulbuild9.3 | head -1 | cut -d'/' -f1)
EOF
)"
echo "";
echo "Latest build present in QASIB1 is::: $QASIB1_KS";

echo "------------------------------------------------------------------";


if [ "$Kahuna" == "$QASIB1_KS" ]
then
echo "Directory already exists in Kahuna and QASIB1";
else
echo "Directory does not exists in QASIB1";
echo "Copying of the latest build directory will be initiated";
#cd /store/builds/NIGHTLY_UPLOADS;
pwd;
echo $Kahuna; 
scp -rv $Kahuna root@16.150.57.108:/clatest/;
echo "Copied $Kahuna to QASIB1 build directory";
fi

