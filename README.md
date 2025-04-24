# Вариации

В зависимости от команды будут использоваться:

* префиксы для имен acc1 или acc2
* диапазоны портов 10_000 или 20_000

# Предварительные настройки проекта

* [codestyle.xml](codestyle.xml) - файл с настройками форматирования, импортируем в идею
* [checkstyle.xml](checkstyle.xml) - файл с настройками для lint'ера

Вызываем
```
mvn checkstyle:check
``` 
Или
```
mvn verify
``` 


# Переменные окружения

Экспорт переменных окружения для Unix ОС

```shell
export DB_NAME=acc1 \
&& export DB_USER=acc \
&& export DB_PASS=acc \
&& export DB_URL=jdbc:postgresql://localhost:5432/acc1?currentSchema=changeit'
```

Экспорт переменных окружения для Windows

```shell
$env:DB_NAME = 'acc1'
$env:DB_USER = 'acc'
$env:DB_PASS = 'acc'
$env:DB_URL = 'jdbc:postgresql://localhost:5432/acc1?currentSchema=changeit'
```

> Переменные окружения видны только в той консоли, в которую были экспортированы!

# База данных

Постоянный инстанс, выполняем
```sql
CREATE DATABESE acc1;
CREATE USER acc WITH PASSWORD 'acc';
GRANT ALL PRIVILEGES ON DATABASE "acc1" to acc;
```

Докер, запускаем

```shell
docker compose up -d
```

## Миграции

Все изменения в структуре базы оформляем через миграции, при старте приложения они автоматически
применятся.

Пример миграции:

```
./src/main/resources/db/scripts/0001-create-schema-acc.sql
```

Имя файла должно следовать соглашению:

* Сначала префикс `0001`, где `0001` это номер миграции.
* Потом краткое описание, что там за изменения `create-schema-acc`

# Сборка проекта

Для очистки от файлов сборки

```
mvn clean
```

Для сборки

```
mvn package
```

Для проверки и запуска тестов

```
mvn verify
```
