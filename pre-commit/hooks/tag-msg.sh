#!/bin/bash
set -eo pipefail

RED='\033[0;31m'
Green='\033[0;32m'
NC='\033[0m'

REGEX="^\[(.*)\] (feat|fix|docs|style|refactor|test|build|ci|perf|release)(\(((.+)-[0-9]{1,}|N\/A)\): (.+))"

function validate {
  local message="$2"
  if [[ ! "$message" =~ $REGEX ]]; then
    echo -e "${RED}The message format of tag \"$1\" is invalid!${NC}. A valid tag message looks like:  ${Green}[a & b] feat(XX-123): hello, world!${NC}"
    echo "Message: \"$message\""
    exit 1
  fi
}
# PRE_COMMIT_LOCAL_BRANCH 是 pre-commit 设置的环境变量
if [[ "$PRE_COMMIT_LOCAL_BRANCH" == *"refs/tags/"* ]]
then
  tag_name=$(echo "$PRE_COMMIT_LOCAL_BRANCH" | sed 's/refs\/tags\/\(.*\)/\1/')
  tag_message=$(git show -s --pretty=full "$tag_name" | sed -n 4p | xargs)

  validate "$tag_name" "$tag_message"
fi
