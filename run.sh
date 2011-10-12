#!/bin/sh
MAIN_CLASSES="_001_Naive _001_Efficient _002_Naive _002_Efficient _003 _004_Naive _004_Efficient"
BUILD_DIR="target/scala-2.9.1/classes"

if [ $# -eq 0 ]
then
  for MAIN_CLASS in $MAIN_CLASSES
  do
    echo "Running $MAIN_CLASS"
    scala -cp $BUILD_DIR $MAIN_CLASS
  done
else
  for MAIN_CLASS in $*
  do
    scala -cp $BUILD_DIR $MAIN_CLASS
  done
fi