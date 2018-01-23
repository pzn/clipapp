clipapp
=======
[![Build Status](https://travis-ci.org/pzn/clipapp.svg?branch=master)](https://travis-ci.org/pzn/clipapp)

what is it?
===========
A simple webapp, tinyurl-alike, built with Spring Boot.

live demo
=========
# Web UI
http://clipapp.herokuapp.com

# GET
- http://clipapp.herokuapp.com/{TINY_URL}: redirects to the original URL if existing, otherwise redirects to the web ui.

# POST
- http://clipapp.herokuapp.com/{TINY_URL}: outputs the original URL if existing, otherwise throws an error.
```json
{
  "url": "https://www.google.ca"
}
```

- http://clipapp.herokuapp.com/?u={LONG_URL}: outputs a tiny URL. If already available in the system, it gives the already associated tiny URL. If long URL malformed, throws an error.
```json
{
  "value": "jfcnsie"
}
```

launch it locally?
==================
## prerequisites
- jdk8+
- maven
- postgresql 10.x, with database *clipapp* created (optional, for data persistance: use Spring profile `database`)
- redis (optional, for caching: use Spring profile `redis-caching`)

```
mvn spring-boot:run -Dspring.profiles.active=<profiles>
```

where `<profiles>` can be:
1. `simple`: everything in memory
2. `database`: uses postgresql for storage
3. `redis-caching`: uses redis for caching
4. `database,redis-caching`: 2+3

refer to [project's application.yml](./src/main/resources/application.yml) for default settings, or [override them](https://docs.spring.io/spring-boot/docs/1.5.9.RELEASE/reference/html/howto-properties-and-configuration.html#howto-use-short-command-line-arguments).

locally, but in docker
======================
because why not.

```
mvn install
docker run --name clipapp -p 8080:8080 -e SPRING_PROFILES_ACTIVE=<profiles> clipapp:0.0.1-SNAPSHOT
```
