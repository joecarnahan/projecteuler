#!/bin/sh
MAIN_CLASSES="_001_Naive _001_Efficient _002_Naive _002_Efficient _003 _004_Naive _004_Efficient _005 _006 _007 _008 _009 _010_functional _010_mutable _011 _012_Naive _012_Efficient TreeTest"
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
