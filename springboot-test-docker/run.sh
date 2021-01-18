#!/bin/bash
java -server -Xmx3000m -Xms3000m -Xmn2000m -Xss512k -XX:+DisableExplicitGC \
     -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled \
     -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods \
     -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 \
     -Dspring.profiles.active=$profiles_active \
     -jar /app/springboottestdocker.jar

