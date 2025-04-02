#!/bin/bash

openssl req -x509 -newkey rsa:4096 -keyout store.key -out store.crt -sha256 -days 365 -nodes \
  -subj "/C=RU/ST=Tatarstan/L=Kazan/O=school21/OU=IT Department tysaneno/CN=store.com"

# 127.0.0.1 store.com >> /etc/hosts