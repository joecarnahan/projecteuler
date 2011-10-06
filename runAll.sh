#!/bin/sh
MAIN_CLASSES="_001_Naive _001_Efficient _002_Naive _002_Efficient _003 _004_Naive _004_Efficient"
for MAIN_CLASS in $MAIN_CLASSES
do
  echo "Running $MAIN_CLASS"
  scala -cp target/scala-2.9.1/classes $MAIN_CLASS
done
