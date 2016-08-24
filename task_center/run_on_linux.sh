#!/usr/bin/env bash
export JSVC_HOME=/root/jsvc_unix_src
export TASK_CENTER_HOME=/home/sysadmin/task_center
$JSVC_HOME/jsvc -cp $TASK_CENTER_HOME/libs/commons-daemon-1.0.15.jar:$TASK_CENTER_HOME/task_center-1.0-SNAPSHOT.jar -pidfile $TASK_CENTER_HOME/sts.pid \
-outfile $TASK_CENTER_HOME/sts.out -errfile $TASK_CENTER_HOME/sts.err com.lezhi.sts.ServerMain

# -debug