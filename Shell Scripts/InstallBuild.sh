#!/bin/sh
# v2.0 21/06/2016 
#author: Avin

cd /clatest;
pwd;
for i in $((ls -td */) | grep successfulbuild9.3 | head -1 | cut -d'/' -f1); 
do echo Latest Build: ${i%%/};
echo "--------------------------------------------------------------------";
cd ${i%%/}/linux; 
done;
for j in $((ls) | grep supervisor | head -1); 
do echo GZ File: ${j%%/};
echo "--------------------------------------------------------------------";
echo "Copying $j to 'clatest' directory"
scp -p ${j%%/} root@16.150.57.108:/clatest
done;
echo "--------------------------------------------------------------------";
echo "Installing Supervisor build ($j)";
echo "--------------------------------------------------------------------";
echo "Connecting to app server";
ssh -o ConnectTimeout=300 -p 22 root@10.0.77.11 "bash -s" << EOF

service supervisor status;
#sleep 10s;
echo "Install Command to be Executed==> /opt/bin/svcontrol.py --install -z http://16.150.57.108/build/code/latest/$j -v;";

/opt/bin/svcontrol.py --install -z http://16.150.57.108/build/code/latest/$j -v;

#sleep 2m;
#service supervisor start;
#sleep 1m;
EOF

echo "-----------------Installation Task Completed-------------------------";