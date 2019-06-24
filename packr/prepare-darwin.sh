#!/bin/bash

echo Setting world execute permissions on SanLite
cd $1
chmod g+x,o+x Contents/MacOS/SanLite
