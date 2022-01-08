# lms
Это модельная версия LMS(англ. learning management system) для вузов

Автор: Сизинцев Александр (https://github.com/MSIZIN)

[Документация по api сервиса](doc/api.md)

## Локальное развертывание

Чтобы запустить сервис должна быть установлена java 11 и sbt. Пример установки на linux машинах:
```
curl -s "https://get.sdkman.io" | bash &&
source "$HOME/.sdkman/bin/sdkman-init.sh" &&
sdk install java 11.0.9.open-adpt &&
sdk install sbt
```

Затем нужно зайти в директорию с этим проектом и запустить следующие команды:
* sbt run - запуск сервиса
* sbt test - запуск юнит-тестов

В приложении используется база данных PostgreSQL. Требуется следующая конфигурация базы:
```
url="jdbc:postgresql://localhost:5432/lms"
username="postgres"
password="frvg54CDd"
```

SQL запрос, накатывающий все таблицы с тестовыми данными: [postgres/migration/all.sql](postgres/migration/all.sql)

Сам сервис запустится на http://localhost:9000/