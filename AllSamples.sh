#!/bin/sh
# v1. 04/2016 Avin


echo "Installing Supervisor build"
ssh -o ConnectTimeout=300 -p 22 root@10.0.77.11 "bash -s" << EOF
# here you just type all your commmands, as you can see, i.e.
#service supervisor stop;
service supervisor status;
sleep 1m;
ls -ltr;		
#service supervisor start;
EOF
echo "Done";


#-------------------Partially working code-----------------
#!/bin/sh
# v1.0 04/2016 Avin

cd /store/builds/NIGHTLY_UPLOADS;
pwd;
for j in $((ls -dr */) | grep successfulbuild9.2 | head -1); 
do echo Latest Build Kahuna: ${j%%/};
Kahuna=${j%%/};
echo "$Kahuna";
done;

ssh root@16.150.57.113 "cd /avin/builds_92/; 
pwd; 
for i in $((ls -dr */) | grep successfulbuild9.2 | head -1); 
do echo Latest Build KS: ${i%%/}; 
QASIBKS=${i%%/}; echo "$QASIBKS"; 
done;" 
 
Output:
/home/prashantm/avin
/store/builds/NIGHTLY_UPLOADS
Latest Build Kahuna: successfulbuild9.2-20-Apr-2016-09-22
successfulbuild9.2-20-Apr-2016-09-22
/avin/builds_92
Latest Build KS:

exit-status: 0

#2nd part of the code is not giving any results.
#-----------------------------------------------------------------------

