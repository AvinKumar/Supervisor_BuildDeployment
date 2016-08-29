#!/bin/sh
# v1.

echo "Starting all of the app services"
ssh -o ConnectTimeout=300 -p 22 root@10.0.77.11 "service supervisor start"
echo "Done";