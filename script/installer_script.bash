#!/bin/bash
ZIP_FILE=/tmp/simon.zip
rm -f ${ZIP_FILE}
wget -O ${ZIP_FILE} "https://docs.google.com/uc?id=1CGXmqsu3aXeHxtu1B59-hNaCi_jVQoKa&export=download"
unzip -d /tmp/ ${ZIP_FILE}
cd /tmp/tilegame-1.0-SNAPSHOT/bin/
./tilegame
