#!/usr/bin/env bash

set -e

(
    cd $(dirname $0)/..
    bb bin/lein-sub.bb install
    bb bin/lein-sub.bb test
)
