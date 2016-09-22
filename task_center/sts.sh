#!/bin/bash

# Setup variables
EXEC=/usr/bin/jsvc
JAVA_HOME=/usr/local/jdk1.8.0_91

CLASS_PATH="/home/sysadmin/task_center/task_center-1.0-SNAPSHOT.jar"
CLASS=com.lezhi.sts.ServerMain
#USER=sysadmin
PID=/home/sysadmin/task_center/sts.pid
LOG_OUT=/home/sysadmin/task_center/logs/sts.out
LOG_ERR=/home/sysadmin/task_center/logs/sts.err

do_exec()
{
	#-user $USER
    $EXEC -home "$JAVA_HOME" -cp $CLASS_PATH -outfile $LOG_OUT -errfile $LOG_ERR -pidfile $PID $1 $CLASS
}

case "$1" in
    start)
        do_exec
            ;;
    stop)
        do_exec "-stop"
            ;;
    restart)
        if [ -f "$PID" ]; then
            do_exec "-stop"
            do_exec
        else
            echo "service not running, will do nothing"
            exit 1
        fi
            ;;
    *)
            echo "usage: daemon {start|stop|restart}" >&2
            exit 3
            ;;
esac