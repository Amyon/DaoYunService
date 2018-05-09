#!/usr/bin/env bash
#
# This is start script for sprint boot application.
#

# JVM
JVM_MEMORY="-Xmx12g -Xms12g -Xss1m -XX:MetaspaceSize=1g -XX:MaxMetaspaceSize=2g"
JVM_GC="-XX:+UseG1GC -XX:MaxGCPauseMillis=200"
JVM_GC_LOG="-XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintHeapAtGC -XX:+PrintGCApplicationStoppedTime -Xloggc:log/gc.log"
JVM_PROPERTIES=""
JVM_ARGS=$JVM_MEMORY" "$JVM_GC" "$JVM_GC_LOG" -XX:+HeapDumpOnOutOfMemoryError"

BIN="`dirname "$0"`"
HOME="`cd "$BIN";cd ..; pwd`"
echo $HOME

export PROJECT_HOME=$HOME
export DATA_HOME=$HOME"/data"
export RUNNING_HOME=$PROJECT_HOME/running

if [ ! -d $RUNNING_HOME ]; then
    mkdir $RUNNING_HOME
fi

function usage(){
cat << EOF
Usage:
    http.sh <command> [service.jar] [-D key=value] [args...]
The commands are:
    start   start service
    stop    stop service
Examples
    http.sh start <service.jar> [-D key=value] [args...]
    http.sh stop
EOF
}

function check_process_is_running(){
    if [ -f $RUNNING_HOME/service.pid ]; then
        pid=`cat $RUNNING_HOME/service.pid`
        ps $pid > /dev/null 2>&1
        return $?
    fi
    return 1
}

command=$1
case $command in
    (start)
        if [ $# -lt 2 ]; then
            usage
            exit 1
        fi
        check_process_is_running
        if [ $? -eq 0 ]; then
            echo "Service[pid="`cat $RUNNING_HOME/pid`"] Already Started, Stop It First"
            exit 11
        fi
        EXEC_JAR=$2
        shift 2
        while [[ $# > 0 ]]
            do
                case $1 in
                    -D)
                        JVM_PROPERTIES="-D$2 $JVM_PROPERTIES"
                        shift 2
                        ;;
                    *)
                        break
                        ;;
                esac
            done
        RUN_COMMAND="java -server $JVM_ARGS $JVM_PROPERTIES -jar $EXEC_JAR $@"
        echo Start Service
        echo "Command," $RUN_COMMAND
        nohup $RUN_COMMAND > nohup.out 2>nohup.err &
        echo $! > $RUNNING_HOME/service.pid
     ;;
    (stop)
        check_process_is_running
        if [ $? -eq 0 ]; then
            kill $pid
            echo "Stop Service[pid=$pid]..."
            sleep 1
        else
            echo "No Service Is Running"
        fi
     ;;
    (*)
        echo Unsupported operation: $command
        usage
     exit 9
   ;;
esac
