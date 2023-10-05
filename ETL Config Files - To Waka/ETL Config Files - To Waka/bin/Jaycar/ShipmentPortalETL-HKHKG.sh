#!/bin/sh
JAR_DIR=/var/local/Projects/JavaLib8/
LOG_DIR=/var/local/ProjectLogs/ShipmentPortal_Test
CLASS_PATH=/var/local/Projects/ShipmentPortal_Test/classes

#All libraries except csfe-*
JAR_LIB=
for i in $(ls $JAR_DIR |egrep -v '^csfe-')
do
	JAR_LIB=$JAR_LIB:$JAR_DIR$i
done

#Extract ediSON data
/usr/java/jdk1.8.0_191/bin/java -cp "$JAR_LIB:$CLASS_PATH" -Duser.language=en -Duser.country=US -Dcustom.logging.root=$LOG_DIR -Dlog4j.configuration=./log4j/ETL/Jaycar/ShipmentPortal-EdisonLogImportRequest-HKHKG-log4j.xml com.csfe.etl.business.main.EdisonLogImportRequest ./conf/etl/Jaycar/HKG-ETL-servlet.xml

#Import ediSON data to Azure
/usr/java/jdk1.8.0_191/bin/java -cp "$JAR_LIB:$CLASS_PATH" -Duser.language=en -Duser.country=US -Dcustom.logging.root=$LOG_DIR -Dlog4j.configuration=./log4j/ETL/Jaycar/ShipmentPortal-AzureImportRequest-HKHKG-log4j.xml com.csfe.etl.business.main.AzureImportRequest ./conf/etl/Jaycar/HKG-ETL-servlet.xml
