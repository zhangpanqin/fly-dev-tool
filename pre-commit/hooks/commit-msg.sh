#!/bin/bash
set -eo pipefail

RED='\033[0;31m'
Green='\033[0;32m'
NC='\033[0m'
MSG_CONTENT=$(cat "$1")

# Initialize constants here
REGEX="^\[(.*)\] (feat|fix|docs|style|refactor|test|build|ci|perf|release)(\(((.+)-[0-9]{1,}|N\/A)\): (.+))"
NOTICE="A valid commit message looks like:  ${Green}[a & b] feat(XX-123): hello, world!${NC}"
echo "$MSG_CONTENT"
if [[ ! $MSG_CONTENT =~ $REGEX ]]; then
	echo -e "${RED}invalid commit message format!${NC}"
	echo -e "$NOTICE"
	exit 1
fi