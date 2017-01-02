#!/bin/sh
# v1.0 25/04/2016
# v2.0 08/12/2016
# author: Avin

echo "~~~~~~~~~~~~~~~~~~~~ LDAP Users sync ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";

echo "~~~~~Logging to ID Server and executing runSVUserToGroupsMappingDownload.sh file~~~~~~";

ssh root@10.0.93.1 "cd /opt/svusertogroupmapping/; ./runSVUserToGroupsMappingDownload.sh;
cd /store/data/testdomain1; /opt/identitytools/tools/massageLDIF.sh testdomain1-DSmailUsers-template.ldif avinJenkins testdomain1;"

echo "";
echo "~~~~~~~~~~~~~~~~~ Fetching the User mapping file. ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";
userMappingfile="$(ssh -o ConnectTimeout=300 -p 22 root@10.0.93.1 "bash -s" << EOF
cd /store/data/testdomain1; 
ls -r * | grep avinJenkins- | head -1
EOF
)"

echo $userMappingfile;

echo "";
echo "~~~~~~~~~~~~~ Importing the User mapping file to the import directory. ~~~~~~~~~~~~~~~";

ssh root@10.0.93.1 "echo 'Executing the command ==>  /opt/identitytools/tools/importLDIF.sh testdomain1 $userMappingfile';
cd /store/data/testdomain1; 
/opt/identitytools/tools/importLDIF.sh testdomain1 $userMappingfile;

echo "";
echo "~~~~~~~~~~~~~ Initiating copy of LDAP data from import directory. ~~~~~~~~~~~~~~~~~~~~";
/opt/identitytools/tools/applyNewEntries.sh testdomain1;"


echo "";
echo "~~~~~~~~~~~~~~~ Refresh PBJ cache ~~~~~~~~~~~~~~~~";

ssh root@10.0.70.1 "echo''; echo 'Executing the following command to refresh the cache value ==>';
echo 'export JAVA_HOME=/usr/java; /opt/jboss.8090/bin/twiddle.sh -s localhost:9990 invoke Zantaz:name=IdentityProvider refreshIdentityCache testdomain1';
echo 'Output:';
export JAVA_HOME=/usr/java; /opt/jboss.8090/bin/twiddle.sh -s localhost:9990 invoke Zantaz:name=IdentityProvider refreshIdentityCache testdomain1;
echo '';
echo 'The following command will show the cache value ==>';
echo 'export JAVA_HOME=/usr/java; /opt/jboss.8090/bin/twiddle.sh -s localhost:9990 invoke Zantaz:name=IdentityProvider refreshIdentityCache testdomain1';
echo 'Output:';
export JAVA_HOME=/usr/java; /opt/jboss.8090/bin/twiddle.sh -s localhost:9990 invoke Zantaz:name=IdentityProvider showCurrentCacheVersion testdomain1;"


echo "";
echo "~~~~~~~~~~~~~~~~~~~~~ LDAP Users sync process is completed ~~~~~~~~~~~~~~~~~~~~~~~~~~";
