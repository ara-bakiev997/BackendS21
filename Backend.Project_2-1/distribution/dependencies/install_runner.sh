#!/bin/bash

curl -L "https://packages.gitlab.com/install/repositories/runner/gitlab-runner/script.deb.sh" | sudo bash
sudo apt install gitlab-runner
sudo systemctl enable gitlab-runner --now gitlab-runner