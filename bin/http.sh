#!/usr/bin/env bash
#
# This is start script for sprint boot application.
#
#

### Envionment Variables

BIN="`dirname "$0"`"
HOME="`cd "$BIN";cd ..; pwd`"
echo $HOME

export PROJECT_HOME=$HOME
export DATA_HOME=$HOME"/data"
export RUNNING_HOME=$PROJECT_HOME/running
export LOG_HOME=$PROJECT_HOME/log

### JVM
JVM_MEMORY="-Xmx12g -Xms12g -Xss1m -XX:MetaspaceSize=1g -XX:MaxMetaspaceSize=2g"
JVM_GC="-XX:+UseG1GC -XX:MaxGCPauseMillis=200"
JVM_GC_LOG="-XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintHeapAtGC -XX:+PrintGCApplicationStoppedTime -Xloggc:$LOG_HOME/gc.log"
JVM_PROPERTIES=""
JVM_ARGS=$JVM_MEMORY" "$JVM_GC" "$JVM_GC_LOG" -XX:+HeapDumpOnOutOfMemoryError"

### Functions
function usage(){
cat << EOF
Usage:
    http.sh <command> [service.jar] [-D key=value] [-JAVA_HOME <java_home>] [-JVM_ARGS <"jvm_args">] [args...]
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

function init(){
    if [ ! -d $LOG_HOME ];then
        mkdir $LOG_HOME
    fi
    if [ ! -d $RUNNING_HOME ]; then
        mkdir $RUNNING_HOME
    fi
}

### start script

command=$1
case $command in
    (start)
        if [ $# -lt 2 ]; then
            usage
            exit 1
        fi
        init
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
                    -JAVA_HOME)
                        export JAVA_HOME=$2
                        shift 2
                        ;;

                    -JVM_ARGS)
                        JVM_ARGS=$2
                        shift 2
                        ;;
                    *)
                        break
                        ;;
                esac
            done
        if [ -z "$JAVA_HOME" ]; then
            echo "Error: JAVA_HOME is not set."
            exit 111
        fi
        JAVA="$JAVA_HOME/bin/java -server"
        RUN_COMMAND="$JAVA $JVM_ARGS $JVM_PROPERTIES -jar $EXEC_JAR $@"
        echo "START SERVICE..."
        echo "[Command]" $RUN_COMMAND
        nohup $RUN_COMMAND > $LOG_HOME/stdout.log 2>$LOG_HOME/stderr.log &
        echo $! > $RUNNING_HOME/service.pid
     ;;
    (stop)
        check_process_is_running
        if [ $? -eq 0 ]; then
            kill $pid
            echo "Stop Service[pid=$pid]..."
            sleep 3
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
