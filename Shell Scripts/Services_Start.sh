#!/bin/sh
# v1.0 18/04/2016 
#author: Avin


echo "Starting all of the app services"
ssh -o ConnectTimeout=300 -p 22 root@10.0.77.11 "service supervisor start"
echo "Done";
