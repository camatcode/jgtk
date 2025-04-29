#!/bin/sh
apt-get update && apt-get install -y ansible | grep "Setting up"
