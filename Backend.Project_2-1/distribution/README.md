## CI/CD настройка

- На серваке нужно установить раннер ([ссылка на инструкцию](https://snakeproject.ru/rubric/article.php?art=gitlab_31012022) или скрипт `install_runner.sh`)
  - Обязательно при регистрации ранера указать теги которые будут использованны в pipeline
  - Для пользователя `gitlab-runner` сделать записи в visudo `gitlab-runner ALL=(ALL) NOPASSWD: ALL`
- Добавить evn для ранера, логин/пароль для dockerHub и токен для телеграм бота
  - `/etc/gitlab-runner/config.toml` добавить `environment = ["DOCKER_USERNAME=ваш_логин", "DOCKER_PASSWORD=ваш_пароль", "TELEGRAM_BOT_TOKEN=токен_телеграм_бота"]`
  - Не забыть `sudo gitlab-runner restart`
- Установить `kubectl` и `helm`, можно использовать `install_k8s_dependencies.sh`
- Перекинуть файл конфигурации k8s в директорию /home/gitlab-runner/.kube/config
  - `scp ~/edu/timeweb/config.yaml root@194.87.43.155:/home/gitlab-runner/.kube/config `
