#!/usr/bin/env bash

td=$(mktemp -d foooXXXXXX)
f1="$td/out.txt"
f2="$td/res.txt"

cat out.txt | sort -k2,2 > $f1
cat res.txt | sort -k2,2 > $f2

diff $f1 $f2

if [ -n "$td" ]; then
    rm -rf "$td"
fi
