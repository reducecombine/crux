#!/usr/bin/env bash

(
    cd $(dirname $0)/..
    bb bin/lein-sub.bb check
)
